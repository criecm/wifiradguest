<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="ecm.arrubaguest.server.LdapRead"%>
<%@page import="ecm.arrubaguest.server.UserData"%>
<%@page import="javax.naming.CommunicationException"%>
<%!  private String LDAP_HOST;
    private String LDAP_CONFIG;
    private String LDAP_USER;
    private String LDAP_PWD;
    private String LDAP_SSL;
    private String LDAP_SEARCHDN;
    private String ADMINS;
 
    public LdapRead xconx;
    private final static String INITCTX = "com.sun.jndi.ldap.LdapCtxFactory";
    
    public void jspInit() {

        ServletContext sc = getServletContext();

        LDAP_HOST = sc.getInitParameter("ldaphost_r");
        LDAP_CONFIG = sc.getInitParameter("config");
        LDAP_USER = sc.getInitParameter("ldapuser_r");
        LDAP_PWD = sc.getInitParameter("password_r");
        LDAP_SSL = sc.getInitParameter("ssl");
        LDAP_SEARCHDN = sc.getInitParameter("search_r");
        
        ADMINS = sc.getInitParameter("admingroup");
        xconx = new LdapRead(LDAP_HOST, LDAP_USER, LDAP_PWD, LDAP_SSL);                
    }
%><%
            UserData ud = null;
            String admin = "NOK";
            String super_admin = "NOK";
            Boolean status_admin = false;
            String nense = "NOK";
            int suu;
            
     //        session.setAttribute("user", request.getRemoteUser());
        
           session.setAttribute("user", "jlf");
             session.setMaxInactiveInterval(9000);
             
             ServletContext sc = getServletContext();
             String hosturl = request.getServerName();
             String contexturl = sc.getContextPath() + "/";
             
           String serveur_url = hosturl + ":8080" + contexturl; 
        //     String serveur_url = hosturl +  contexturl; 
          
              System.out.println(serveur_url);
             
                try {                   
         //          xconx.init(request.getRemoteUser(), "uid", LDAP_SEARCHDN);              
                    
                 xconx.init("jlf", "uid", LDAP_SEARCHDN);
                    
                     ud = xconx.ReadUid();
                    
                        session.setAttribute("userdata", ud);
                        
                        String uu = ud.getSn();
                        suu = ud.getsupannEmpId();
                        String uus = ud.getSn() + " " + ud.getGivenName();
                                
                       if ( ud.getSupannAffectation().equals("cri") ) {
                           super_admin = "OK";
                           status_admin = true;
                       }   
 
                       if (ADMINS.contains(ud.getUid())) {
                            admin = "OK";
                            status_admin = true;
                        }
                       
                       if  ( (ud.getEduPersonAffiliation().equals("faculty") || ud.getEduPersonAffiliation().equals("employee")) && !status_admin) {
                               nense = "OK";
                       } 
                       
                       if ( (super_admin == "OK" || admin == "OK" || nense == "OK") ) {
                           
                       System.out.println(uus);
%>
<%@include file="index.html" %><%
                    } else if ( nense != "OK") {
%>                        
<%@include file="nologin.html"%><%
                        session.invalidate();
                    }
                } catch (CommunicationException cn) {
                    System.err.println("Pb comm ldap");
                    System.out.println(cn.getMessage());
%><%@include file="ldap_comerr.html"%><%
                }  catch (NullPointerException n) {
%><%@include file="ldap_usererr.html"%><%
                }  catch (Exception ne) {
                    System.out.println("ERRORERRO :=)");
                    System.err.println(ne.getCause().toString());
                    out.println(" Erreur Générale");
                }
%><%!
        public void jspDestroy() {
        log("The lifecycle jsp is being destroyed");
    }
%>