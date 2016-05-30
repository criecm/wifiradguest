package ecm.arrubaguest.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LireEvenement extends HttpServlet {

     private String convertFrench(Date s) {
         
         String res = null;
         DateFormat fmt = new SimpleDateFormat("d MMM yyyy HH:mm",Locale.FRENCH);
        
         res = fmt.format(s);
         return res ;
     }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String search = null;
        String action = null;
        JSONArray ma = new JSONArray();
        JSONArray mp = new JSONArray();
      
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.setStatus(400);
            out.write("Votre session a expirée\nVous allez être reconnecté");
        } else {

            List<Radadmin> lue = new ArrayList<Radadmin>();
            Map<String,String> lr = new HashMap<String,String>();
            
            Radadmin ra = new Radadmin();

            action = request.getParameter("action");
            search = request.getParameter("term");
            search = search.replace("+", " ");

            System.out.println(search + " " + action);

            if (action.equals("0")) {
                synchronized (this) {

                    DataBase r = new DataBase();
                    lue = r.Search_Evnt(search);
                    if (lue.size() == 0) {
                        System.out.println("Aucun résultat dans la base ");
                    } else {
                        for (int i = 0; i < lue.size(); i++) {
                            System.out.println("Résultats : " + lue.get(i));
                        }
                    }
                }
                for (int k = 0; k < lue.size(); k++) {
                    JSONObject o = new JSONObject();
                    o.put("nom", lue.get(k).getEvenement());
                    o.put("nombre", lue.get(k).getNbcompte());
                    o.put("firstaccount", lue.get(k).getFirstaccount());
                    o.put("ID", lue.get(k).getId());
                    mp.add(o);
                }
            } else if (action.equals("1")) {
                synchronized (this) {

                    DataBase r = new DataBase();
                    lue = r.Lire_Radadmin();

                    if (lue.size() == 0) {
                        System.out.println("Aucun résultat dans la base ");
                    } else {

                        lr = r.Lire_ExpireAfter();
                    }
                }
                
                    for (int k = 0; k < lue.size(); k++) {
                    JSONObject o = new JSONObject();
                    o.put("evenement", lue.get(k).getEvenement());
                    o.put("createur", lue.get(k).getCreateur());
                    o.put("nbacct", lue.get(k).getNbcompte());
                    o.put("firstaccount", lue.get(k).getFirstaccount());
                    o.put("firstlogin", convertFrench( lue.get(k).getFirstlogin()));
                    
                    for (Map.Entry<String, String> entry : lr.entrySet()) {
               
                        if (entry.getKey().equals(lue.get(k).getFirstaccount())) {
                            int g = Integer.parseInt(entry.getValue())/3600;
                            o.put("firstlogin", Integer.toString(g) + " heures" ); 
                            System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
                        }    
                    }

                    //     o.put("firstlogin",lue.get(k).getFirstlogin().toString());
                    o.put("expiration", convertFrench( lue.get(k).getExpiration() ));
                    
                    o.put("infos", lue.get(k).getInfos());
                    //    o.put("id", lue.get(k).getId());
                    mp.add(o);

                    /*    List o = new LinkedList();
                     o.add(lue.get(k).getEvenement());
                     o.add(lue.get(k).getCreateur());
                     o.add(lue.get(k).getNbcompte());
                     o.add(lue.get(k).getFirstaccount());
                     o.add(lue.get(k).getFirstlogin().toString());
                     o.add(lue.get(k).getExpiration().toString());
                     o.add(lue.get(k).getInfos());
                     o.add(lue.get(k).getId());
                     mp.add(o);
                     */ }
            }
            
            try {
                out.println(mp);
            } finally {
                out.close();
            }
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
        protected void doGet
        (HttpServletRequest request, HttpServletResponse response)
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
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>

    }
