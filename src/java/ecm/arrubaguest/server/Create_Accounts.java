package ecm.arrubaguest.server;

import java.io.File;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Create_Accounts extends HttpServlet {
    
     private static String PREFIX = "ECM";
     private static String PREFIX_GUEST = "Guest";
     private int MAX_VALID = 30;
       
     private java.sql.Date converttoDate(String s, boolean format) {

         SimpleDateFormat sdf = null;
         
         if (format) {
            sdf = new SimpleDateFormat("MMM dd yyyy HH:mm",Locale.ENGLISH);
         } else {   
            sdf = new SimpleDateFormat("dd MMMM yyyy");
         }
            java.util.Date date = new java.util.Date();

            try {
                date = sdf.parse(s);
            } catch (java.text.ParseException ex) {
               ex.printStackTrace();
            }

            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            System.out.println("SQLDATE : " + sqlDate);
            return sqlDate;
        }
    
     private String convertDate (String s) {
         
         SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy",Locale.FRENCH);
         SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMMM yyyy HH:mm",Locale.FRENCH);
         java.util.Date date = new java.util.Date();
         java.util.Date date1 = new java.util.Date();
         
         try {
                date = sdf.parse(s);
                date1 = sdf1.parse(s + " 9:30");
            } catch (java.text.ParseException ex) {
               ex.printStackTrace();
            }
         
         DateFormat DateFormatEN = DateFormat.getDateInstance(
         DateFormat.MEDIUM,new Locale("EN","en"));
      
         DateFormat TimeFormat = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.FRANCE);
         
         // return ( (DateFormatEN.format(date)).toString().replace(",", "") + " " + TimeFormat.format("14:17".toString() ));
         return ( (DateFormatEN.format(date)).toString().replace(",", "") + " " + TimeFormat.format(date1).toString() );
     }
     
     private String convertFrench(String s) {
         
         String res = null;
         
         Locale lc = new Locale("FR", "fr");
         Calendar cal = Calendar.getInstance(lc);
      //   DateFormat fmt = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM,Locale.FRENCH);
         DateFormat fmt = new SimpleDateFormat("dd MMMM yyyy HH:mm",Locale.FRENCH);
         fmt.setCalendar(cal);
         
         try {
             cal.setTime(fmt.parse(s));
         } catch (ParseException ex) {
             Logger.getLogger(Create_Accounts.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         res = fmt.format(cal.getTime());
          
         System.out.println( "French Date :" + res);
         
         return res ;
     }
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        
        String result1 = null ;                              
        String action = null ;
        String datedebut= null;
        String currentdate = null;
        String responsable = null;
        String evntlieu = null;
        
        String sqldatedeb = null;
        String sqldatefin = null;
        String firstaccount = null;
        
        String nbacct = null;
        String duree = null;
        String valduree = null;
        String shortdeb = null;
        String user = null;
        String ssid = null;
        
        String fid = null;
        
        GenPasswd g;
        List<Radcheck>  Usertab = new ArrayList<Radcheck>();
        
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
      
      if (session == null) {
            response.setStatus(400);
            out.write("Votre session a expirée\nVous allez être reconnecté");
        } else {
         
        String contextPath = getServletContext().getRealPath(File.separator); 
        String duree_txt = null;
        
        user = session.getAttribute("user").toString();
        System.out.println(" 00=============>> " + user);
        
        ssid = getServletContext().getInitParameter("ssid").toString();
        System.out.println(" 00=============>> " + ssid);
         
        fid = request.getParameter("formid");
        System.out.println("Arruba_Form = " + fid );
         
         if ( fid.equals("cprov")) {
             
             nbacct = request.getParameter("nbacct");
             duree = request.getParameter("duree");
             responsable = request.getParameter("respon");
             
             System.out.println( nbacct);
             System.out.println( duree );
             System.out.println( responsable );
             
             switch (duree) {
                 
                 case "0" : duree = "90";
                            duree_txt = "90 secondes";
                     break;
                 case "1" : duree = "28800";
                            duree_txt = "8 heures";
                     break;    
                 case "2" : duree = "43200";
                            duree_txt = "12 heures";
                     break;
                 case "3" : duree = "86400";
                            duree_txt = "24 heures";
                     break;
                 case "4" : duree = "129600";
                            duree_txt = "36 heures";
                     break;   
                 case "5" : duree = "172800";
                            duree_txt = "48 heures";
                     break;     
                 default : duree = "90";
                          break;    
             }
              
             int nbc = Integer.parseInt(nbacct);
             String [] t = new String [nbc];
             
             g = new GenPasswd(nbc);
             t = g.gettabpaswd();
             
             DataBase ds = new DataBase();
             int nb_seq = ds.Read_Radmin_Sequence();
             System.out.println(nb_seq);
             
             firstaccount = PREFIX + (nb_seq + 1);
             
             for (int i = 0 ;i< t.length;i++) {
             System.out.println(firstaccount  + "_" + Integer.toString(1 + i) + " " + t[i]);
             Radcheck r = new Radcheck(null, firstaccount  + "_" + Integer.toString(1 + i)
                                    ,"MD5-Password",":=","azerty" + Integer.toString(1 + i) );
             Usertab.add(r);
             }
         
         ds.SetExpireAfter(duree);
         
         try {
         ds.WriteUsers(Usertab, false);
         } catch (Exception ex) {
             System.out.println("EXCEPTION CLASS NAME: " + ex.getClass().getName().toString());
       
          //   System.out.println("THROWABLE CLASS NAME: " + ex.getCause().getClass().getName().toString());
        //     Throwable th = ex.getCause();
       //      System.out.println("THROWABLE INFO: " + th.getCause().toString());
         }
         
         Locale lc = new Locale("FR", "fr");
         Calendar cal = Calendar.getInstance(lc);
         DateFormat fmt = new SimpleDateFormat("dd MMMM yyyy HH:mm",Locale.FRENCH);
         fmt.setCalendar(cal);
         
         currentdate = fmt.format(cal.getTime());
         System.out.println("=========> " + currentdate);
         
         String debdate = convertDate( currentdate);
         
         cal.add(Calendar.DAY_OF_MONTH, MAX_VALID);
         String curdate = convertDate( fmt.format(cal.getTime()));
       
         System.out.println( " CURDATE " + curdate);
         
         try { 
         ds.WriteAdmin("Provisioning",nbc,firstaccount, converttoDate(debdate,true), converttoDate(curdate,true) ,user,true,'#',responsable);
         } catch ( Exception e) {
             System.out.println("EXCEPTION CLASS NAME: " + e.getClass().getName().toString());
         //  System.out.println("THROWABLE CLASS NAME: " + e.getCause().getClass().getName().toString());
         //  Throwable th = e.getCause();
         //  System.out.println("THROWABLE INFO: " + th.getCause().toString());
              ds.CloseFactory();
              out.print("erreur database");
              out.close();
              return;
         }
         
         CreatePdf pdf = new CreatePdf(contextPath,Usertab,user,currentdate,"prov");
        
               try {
                    pdf.HtmlToPDF("Provisioning","",duree_txt,ssid,responsable,firstaccount);
                    } catch (DocumentException | IOException ex) {
                    Logger.getLogger(Sendpdf.class.getName()).log(Level.SEVERE, null, ex);
                    ds.CloseFactory();
                    out.print("erreur PDF");
                    out.close();
                    return;
                } 
         
         ds.CloseFactory();
         
         out.print(firstaccount + ".pdf");
         out.close();
         
         } else if (fid.equals("cevt")) {
         
         result1 = request.getParameter("evenement");                              
         action = request.getParameter("nbaccount");
         datedebut = request.getParameter("datedeb");
         valduree = request.getParameter("duree");
         evntlieu = request.getParameter("evntlieu");
         
         Locale lc = new Locale("FR", "fr");
         Calendar cal = Calendar.getInstance(lc);
         DateFormat fmt = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
         fmt.setCalendar(cal);
         
         currentdate = fmt.format(cal.getTime());
         System.out.println("=========> " + currentdate);
          
         SimpleDateFormat sf = new SimpleDateFormat("ddMM",Locale.FRENCH); 
         System.out.println("shortdeb : " + sf.format(cal.getTime()));
        
         shortdeb = sf.format(cal.getTime());
         
         SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm",Locale.FRENCH);
                
             try {
                 java.util.Date md = sdf.parse(datedebut + " 08:00");
                 cal.setTime(md);
             } catch (ParseException ex) {
                 Logger.getLogger(Create_Accounts.class.getName()).log(Level.SEVERE, null, ex);
             }
                 
         System.out.println(fmt.format(cal.getTime()));
         sqldatedeb = convertDate( fmt.format(cal.getTime()));
         
         String debfr = convertFrench(datedebut + " 08:00");
         
         cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(valduree));
         cal.add(Calendar.HOUR_OF_DAY,14);
         System.out.println(fmt.format(cal.getTime())); 
         System.out.println( "OO===Q " + convertDate( fmt.format(cal.getTime())));
         sqldatefin = convertDate( fmt.format(cal.getTime()));
         
         String finfr = convertFrench( fmt.format(cal.getTime()));
         
         int nbc = Integer.parseInt(action);
         
         System.out.println("Evenement = " + result1 );
         System.out.println("nbre de comptes = " + action);
         System.out.println("Duree = " + valduree );
         System.out.println("Date Debut = " + datedebut + " " + convertDate(datedebut));
     //    System.out.println("ShortDebut = " + shortdeb);
         
         System.out.println("Valeur num = " + nbc);
         
         String [] t = new String [nbc];
         
         g = new GenPasswd(nbc);
         t = g.gettabpaswd();
         
         DataBase ds = new DataBase();
         int nb_seq = ds.Read_Radcheck_Sequence();
         System.out.println(nb_seq);
          
       //  String firstacccount = result1.substring(0,4).toLowerCase() + Integer.toString(nb_seq + 1 );
         
         String tc = ds.TestAdmin();
         System.out.println("====>> " + tc);
         
         try {
         if ( tc.equals("0") || tc.equals("+") ) {
         firstaccount = 'A' + shortdeb;   
         ds.WriteAdmin(result1,nbc,firstaccount,converttoDate(sqldatedeb,true),converttoDate(sqldatefin,true),user,true,'A',evntlieu);
         } else {
         firstaccount = (char) ( tc.charAt(0) + 1) + shortdeb;
         ds.WriteAdmin(result1,nbc,firstaccount,  converttoDate(sqldatedeb,true),converttoDate(sqldatefin,true),user,true, (char) ( tc.charAt(0) + 1) , evntlieu);
         } 
         } catch ( Exception e) {
              ds.CloseFactory();
              out.print("erreur database");
              out.close();
              return;
         }
         
         for (int i = 0 ;i< t.length;i++) {
             System.out.println(firstaccount + "_" + Integer.toString(1 + i) + " " + t[i]);
             Radcheck r = new Radcheck(null, firstaccount + "_" + Integer.toString(1 + i)
                                    ,"MD5-Password",":=","azerty" + Integer.toString(1 + i) );
             Usertab.add(r);
         }
         
         ds.SetExpireAfter(sqldatedeb);
         ds.SetExpiration(sqldatefin);
         
         try {
         ds.WriteUsers(Usertab, true);
          } catch (Exception ex) {
             System.out.println("EXCEPTION CLASS NAME: " + ex.getClass().getName().toString());
      //     System.out.println("THROWABLE CLASS NAME: " + ex.getCause().getClass().getName().toString());
      //     Throwable th = ex.getCause();
      //     System.out.println("THROWABLE INFO: " + th.getCause().toString());
         }
         
         System.out.print("Divison entiere = " + Usertab.size() /25);
         System.out.print("Modulo = " + Usertab.size() % 25);
         
         CreatePdf pdf = new CreatePdf(contextPath,Usertab,user,currentdate,"evnt");
        
               try {
                    pdf.HtmlToPDF(result1,debfr,finfr,ssid,evntlieu,firstaccount);
                    } catch (DocumentException | IOException ex) {
                    Logger.getLogger(Sendpdf.class.getName()).log(Level.SEVERE, null, ex);
                    ds.CloseFactory();
                    out.print("erreur PDF");
                    out.close();
                    return;
                }
         
         ds.CloseFactory();
         
         out.print(firstaccount + ".pdf");
         out.close();
         
         } else if (fid.equals("cpadd")) {
             
            result1 = request.getParameter("oldnb");
            action = request.getParameter("nbadd");
            shortdeb = request.getParameter("id");
            String firsta   = request.getParameter("firstacct");
            
            System.out.println( "CPADD : " + result1 + " " + action + " " + shortdeb + " " + firsta );
            
            int nbadd = Integer.parseInt(action);
            int oldnb = Integer.parseInt(result1);
            int nid = Integer.parseInt(shortdeb);
             
            String [] t = new String [nbadd];
            
            g = new GenPasswd(nbadd);
            t = g.gettabpaswd();
         
            DataBase ds = new DataBase();
            
            Radadmin ra = new Radadmin();
            ra = ds.Lire_Evnt(nid);
            
            ds.Change_EvntNB( nbadd + ra.getNbcompte(), shortdeb);
            
            for (int i = 0 ;i< t.length;i++) {
            System.out.println(firsta + "_" + Integer.toString(1 + oldnb + i) + " " + t[i]);
            Radcheck r = new Radcheck(null, firsta + "_" + Integer.toString(1 + oldnb + i)
                                    ,"MD5-Password",":=","azerty" + Integer.toString(1 + oldnb + i) );
            Usertab.add(r);
            }
         
            firstaccount = ra.getFirstaccount() + "_" + Integer.toString(1 + oldnb) + "to" + Integer.toString( oldnb + t.length);
            
             System.out.println(firstaccount);
            
            Locale lc = new Locale("FR", "fr");
            Calendar cal = Calendar.getInstance(lc);
            DateFormat fmt = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
            fmt.setCalendar(cal);
            
            currentdate = fmt.format(cal.getTime());
         
            cal.setTime(ra.getFirstlogin());
            sqldatedeb = convertDate( fmt.format( cal.getTime() ));
            System.out.println(sqldatedeb);
            ds.SetExpireAfter( sqldatedeb );
            
            String debfr = convertFrench( fmt.format(cal.getTime()));        
            
            cal.setTime(ra.getExpiration());
            sqldatefin = convertDate(fmt.format( cal.getTime() ));
            System.out.println(sqldatefin);
            ds.SetExpiration(sqldatefin);
         
            String finfr = convertFrench( fmt.format(cal.getTime()));
                      
            try {
            ds.WriteUsers(Usertab, true);
             } catch (Exception ex) {
             System.out.println("EXCEPTION CLASS NAME: " + ex.getClass().getName().toString());
       //      System.out.println("THROWABLE CLASS NAME: " + ex.getCause().getClass().getName().toString());
       //      Throwable th = ex.getCause();
       //      System.out.println("THROWABLE INFO: " + th.getCause().toString());
            }
            
             CreatePdf pdf = new CreatePdf(contextPath,Usertab,user,currentdate,"evnt");
          
               try {
                    pdf.HtmlToPDF(ra.getEvenement(),debfr,finfr,ssid,ra.getInfos(),firstaccount);
                    } catch (DocumentException | IOException ex) {
                    Logger.getLogger(Sendpdf.class.getName()).log(Level.SEVERE, null, ex);
                    ds.CloseFactory();
                    out.print("erreur PDF");
                    out.close();
                    return;
                }
            
            ds.CloseFactory();
            
             out.print(firstaccount + ".pdf");
             out.close();
            
         } else if (fid.equals("cpers")) {
             
            String dureeacct = request.getParameter("combopers");
            String nbpers = request.getParameter("nbpers");
            
            UserData ud = (UserData) session.getAttribute("userdata");
            
            System.out.println(dureeacct + " " + nbpers);
            
             switch (dureeacct) {
                  
                 case "0" : duree = "90";
                            duree_txt = "90 secondes";
                     break;
                 case "1" : duree = "28800";
                            duree_txt = "8 heures";
                     break;    
                 case "2" : duree = "43200";
                            duree_txt = "12 heures";
                     break;
                 case "3" : duree = "86400";
                            duree_txt = "24 heures";
                     break;
                 case "4" : duree = "129600";
                            duree_txt = "36 heures";
                     break;   
                 case "5" : duree = "172800";
                            duree_txt = "48 heures";
                     break;     
                 default : duree = "90";
                          break;    
             }
                 
            int nbc = Integer.parseInt(nbpers);
            
            String [] t = new String [nbc];
            
            g = new GenPasswd(nbc);
            t = g.gettabpaswd();
            
            DataBase ds = new DataBase();
            int nb_seq = ds.Read_Radmin_Sequence();
            int nb_cnt = ds.Ncount(user);
            
            System.out.println(nb_seq + " " + nb_cnt);
            
            if ( nb_cnt == 5) {
            
            ds.CloseFactory();
            
            out.print("ismax");
            out.close();
            return;
            }
            
            firstaccount = PREFIX_GUEST +  (nb_seq + 1);
            
            System.out.println(firstaccount  +  " " + t[0]);
            Radcheck r = new Radcheck(null, firstaccount,"MD5-Password",":=","azerty" + Integer.toString(nb_seq + 1) );
            Usertab.add(r);
            
            ds.SetExpireAfter(duree);
            
            try {
            ds.WriteUsers(Usertab, false);
             } catch (Exception ex) {
             System.out.println("EXCEPTION CLASS NAME: " + ex.getClass().getName().toString());
       //    System.out.println("THROWABLE CLASS NAME: " + ex.getCause().getClass().getName().toString());
       //    Throwable th = ex.getCause();
       //    System.out.println("THROWABLE INFO: " + th.getCause().toString());
            }
            
             Locale lc = new Locale("FR", "fr");
             Calendar cal = Calendar.getInstance(lc);
             DateFormat fmt = new SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.FRENCH);
             fmt.setCalendar(cal);
         
             currentdate = fmt.format(cal.getTime());
             System.out.println("=========> " + currentdate);

             String debdate = convertDate( currentdate);
             
             cal.add(Calendar.DAY_OF_MONTH, MAX_VALID);
             String curdate = convertDate(fmt.format(cal.getTime()));

             System.out.println(" CURDATE " + curdate);
             
             try {
                 ds.WriteAdmin(PREFIX_GUEST, nbc, firstaccount, converttoDate(debdate, true), converttoDate(curdate, true), user, true, '#', user);
             } catch (Exception e) {
                 ds.CloseFactory();
                 out.print("erreur database");
                 out.close();
                 return;
             }
           
             CreatePdf pdf = new CreatePdf(contextPath, Usertab, user, currentdate, "guest");

             try {
                 pdf.HtmlToPDF(PREFIX_GUEST, "", duree_txt, ssid, ud.getSn() + " " + ud.getGivenName(), firstaccount);
             } catch (DocumentException | IOException ex) {
                 Logger.getLogger(Sendpdf.class.getName()).log(Level.SEVERE, null, ex);
                  ds.CloseFactory();
                  out.print("erreur PDF");
                  out.close();
                  return;
             }

             ds.CloseFactory();
           
              out.print(firstaccount + ".pdf");
              out.close();
             
            } else if (fid.equals("logout")) {
                
            request.getSession().invalidate();
           
            }
         
         out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
