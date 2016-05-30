package ecm.arrubaguest.server;

import java.net.ConnectException;
import java.util.Hashtable;
import java.util.List;
import javax.naming.*;
import javax.naming.directory.*;

	public class LdapRead  {
	    
	    private final static String INITCTX = "com.sun.jndi.ldap.LdapCtxFactory";
            private Hashtable env = new Hashtable();
            private final static String SambaMustValue = "2147483647";
            private NamingEnumeration ans, ans1, ans2, ans3;
            private SearchControls ctls, ctls1;
            boolean is_ssa = false;
            boolean is_pos = false;
            boolean is_spcc = false;
            boolean is_spmc = false;
            boolean is_spls = false;
            boolean is_supid = false;
      
	    void  printAttrs(Attributes attrs) {
				if (attrs == null) {
				    System.out.println("No attributes");
				} else {
				    /* Print each attribute */
				    try {
					for (NamingEnumeration ae = attrs.getAll();
					     ae.hasMore();) {
					    Attribute attr = (Attribute)ae.next();
					    System.out.println("attribute: " + attr.getID());

					    /* print each value */
					    for (NamingEnumeration e = attr.getAll();
						 e.hasMore();
						 System.out.println("value: " + e.next()));
					}
				    } catch (NamingException e) {
					e.printStackTrace();
				    }
				}
			    }
	    
	    void is_attr(Attributes attrs){
			  
			 try {
					for (NamingEnumeration ae = attrs.getAll();ae.hasMore();)
					{
					    Attribute attr = (Attribute)ae.next();
					    //System.out.println("attribute: " + attr.getID());
					    if (attr.getID().equals("sambaPwdCanChange" )) is_spcc = true;
					    if (attr.getID().equals("sambaPwdMustChange" )) is_spmc = true;
					    if (attr.getID().equals("sambaPwdLastSet" )) is_spls = true;
					    /* print each value */
					    for (NamingEnumeration e = attr.getAll();
						 e.hasMore();)
					    {
						 //System.out.println("value: " + e.next());
					    Object value = e.next();
						 if (value.equals("sambaSamAccount")) is_ssa = true;
						 if (value.equals("posixAccount")) is_pos = true;
						 //System.out.println(is_ssa);
					    }
					}
				    } catch (NamingException e) {
					e.printStackTrace();
				    }
				}

		  boolean is_SambaSam(Attributes attrs){
			 
			 boolean is_ssa = false;
			 
			 try {
					for (NamingEnumeration ae = attrs.getAll();ae.hasMore();)
					{
					    Attribute attr = (Attribute)ae.next();
					    //System.out.println("attribute: " + attr.getID());

					    /* print each value */
					    for (NamingEnumeration e = attr.getAll();
						 e.hasMore();)
					    {
						 //System.out.println("value: " + e.next());
					     //Object value = e.next();
						 if (e.next().equals("sambaSamAccount")) is_ssa= true;
						 //System.out.println(is_ssa);
					    }
					}
				    } catch (NamingException e) {
					e.printStackTrace();
				    }
				    return(is_ssa);
				}
		 
	      boolean is_SambaPwdMC(Attributes attrs){
			 
			 boolean is_ssa = false;
			 
			 try {
					for (NamingEnumeration ae = attrs.getAll();ae.hasMore();)
					{
					    Attribute attr = (Attribute)ae.next();
					    System.out.println("attribute: " + attr.getID());
					    if (attr.getID().equals("sambaPwdMustChange" )) is_ssa =true;
					    /* print each value */
					    for (NamingEnumeration e = attr.getAll();
						 e.hasMore();)
					    {
						 System.out.println("value: " + e.next());
					     //Object value = e.next();
						 //if (e.next().equals("sambaPwdMustChange")) is_ssa= true;
						 //System.out.println(is_ssa);
					    }
					}
				    } catch (NamingException e) {
					e.printStackTrace();
				    }
				    return(is_ssa);
				}

	    boolean is_Posix(Attributes attrs){
			 
			 boolean is_ssa = false;
			 
			 try {
					for (NamingEnumeration ae = attrs.getAll();ae.hasMore();)
					{
					    Attribute attr = (Attribute)ae.next();
					    //System.out.println("attribute: " + attr.getID());

					    /* print each value */
					    for (NamingEnumeration e = attr.getAll();
						 e.hasMore();)
					    {
						 //System.out.println("value: " + e.next());
					     //Object value = e.next();
						 if (e.next().equals("posixAccount")) is_ssa= true;
						 //System.out.println(is_ssa);
					    }
					}
				    } catch (NamingException e) {
					e.printStackTrace();
				    }
				    return(is_ssa);
				}

        boolean is_SupannEmpId(Attributes attrs) {

            boolean is_supid = false;

            try {
                for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();) {
                    Attribute attr = (Attribute) ae.next();
                    //System.out.println("attribute: " + attr.getID());
                    if (attr.getID().equals("supannEmpId")) {
                        is_supid = true;
                    }
                    /* print each value */
                    for (NamingEnumeration e = attr.getAll();
                            e.hasMore();) {
                    /*
                     System.out.println("value: " + e.next());
                        Object value = e.next();
                        if (value.equals("sambaSamAccount")) {
                            is_ssa = true;
                        }
                        if (value.equals("posixAccount")) {
                            is_pos = true;
                        }
                    System.out.println(is_ssa);
                    */
                     }
                }
            } catch (NamingException e) {
                e.printStackTrace();
            }
            return (is_supid);
        }

           /* 
	     public void ReadCn(List l) {

                try {
                    while (ans.hasMore()) {
                        
                        SearchResult sr = (SearchResult) ans.next();
                        //   System.out.println(">>>" + sr.getName());
                        //   System.out.println(">>>" + sr.getAttributes().get("cn").toString());
                        l.add(sr.getAttributes().get("cn").get().toString().toLowerCase());
                        //printAttrs(sr.getAttributes().);
                        
                    }

                } catch (NamingException na) {
                    System.out.println(na.getCause().toString());
                    System.out.println(na.getMessage());
                }

            }
            
         */ 
 
            public void ReadCnPlugin(List l, boolean casok) throws NamingException {

           //     int i = 0;

                System.out.println("Dans ReadCnPlugin");

                try {
                   while (ans2.hasMore()) {

                        UserData cnl = new UserData();
                        SearchResult sr = (SearchResult) ans2.next();

                 //       if (!((casok == false) && (sr.getAttributes().get("supannListeRouge").get().equals("TRUE")))) {

                            if ((sr.getAttributes().get("supannEmpId") != null) && (sr.getAttributes().get("supannEmpId").get().toString().equals("service"))) {
                                System.out.println(">>>" + sr.getAttributes().get("supannEmpId").get().toString() + ">>>> " + sr.getAttributes().get("cn").toString());
                            } else {

                                //    System.out.println(">>>" + sr.getAttributes().get("cn").toString());

                                cnl.setUid(sr.getAttributes().get("uid").get().toString());
                                cnl.setsupannEmpId(sr.getAttributes().get("supannEmpId").get().toString());
                                cnl.setSn(sr.getAttributes().get("sn").get().toString());
                                cnl.setmail(sr.getAttributes().get("mailLocalAddress").get().toString());
                                cnl.setGivenName(sr.getAttributes().get("givenName").get().toString());
                               
                                l.add(cnl);
                            }
              //          }
                 //       i++;
                    }
                    ans2.close();
                } catch (SizeLimitExceededException e) {
                  System.out.println("Entrées > 20 ==> on léve l'exception");

                } catch (NamingException na) {

                    System.out.println(na.getExplanation());
                    System.out.println(na.getCause().toString());
                    System.out.println(na.getMessage());
                }
            }

            public void ReadCn(List l, boolean casok) throws NamingException {

                System.out.println("Dans ReadCn");

                try {
                    while (ans.hasMore()) {

                        UserData cnl = new UserData();
                        SearchResult sr = (SearchResult) ans.next();
                                 
                        if (! ( (casok == false) && (sr.getAttributes().get("supannListeRouge").get().equals("TRUE")) ) ) {

                          if ((sr.getAttributes().get("supannEmpId") != null) && (sr.getAttributes().get("supannEmpId").get().toString().equals("service"))) {
                                System.out.println(">>>" + sr.getAttributes().get("supannEmpId").get().toString() + ">>>> " + sr.getAttributes().get("cn").toString());
                            } else {
                     
                                cnl.setUid(sr.getAttributes().get("uid").get().toString());
                                cnl.setsupannEmpId(sr.getAttributes().get("supannEmpId").get().toString());
                                cnl.setSn(sr.getAttributes().get("sn").get().toString());
                                cnl.setmail(sr.getAttributes().get("mailLocalAddress").get().toString());
                                cnl.setGivenName(sr.getAttributes().get("givenName").get().toString());
                        
                                l.add(cnl);
                            }
                        }
                    }
                   
                    ans.close();
                } catch (NamingException na) {

                    System.out.println(na.getExplanation());
                    System.out.println(na.getCause().toString());
                    System.out.println(na.getMessage());
                }
            }

            public UserData ReadUid() throws NamingException {

                System.out.println("Dans ReadUid");

                if (!ans1.hasMore()) {
                    UserData cnl = new UserData();
                    cnl.setSn("notpermit");
                     ans1.close();
                     return cnl;
                } else {
                try {
                    while (ans1.hasMore()) {

                        UserData cnl = new UserData();
                        SearchResult sr = (SearchResult) ans1.next();

                        if ((sr.getAttributes().get("supannEmpId") != null) && (sr.getAttributes().get("supannEmpId").get().toString().equals("service"))) {
                            System.out.println(">>>" + sr.getAttributes().get("supannEmpId").get().toString() + ">>>> " + sr.getAttributes().get("cn").toString());
                        } else {

                            cnl.setUid(sr.getAttributes().get("uid").get().toString());
                            cnl.setsupannEmpId(sr.getAttributes().get("supannEmpId").get().toString());
                            cnl.setSn(sr.getAttributes().get("sn").get().toString());
                            cnl.setmail(sr.getAttributes().get("mailLocalAddress").get().toString());
                            cnl.setSupannAffectation(sr.getAttributes().get("supannAffectation").get().toString());
                            cnl.setEduPersonAffiliation(sr.getAttributes().get("eduPersonAffiliation").get().toString());
                            cnl.setGivenName(sr.getAttributes().get("givenName").get().toString());
                        }
                        return cnl;
                    }

                    ans1.close();
                } catch (NamingException na) {

                    System.out.println(na.getExplanation());
                    System.out.println(na.getCause().toString());
                    System.out.println(na.getMessage());
                }
                return null;
            }
           }

        public LdapRead(String host, String ldapuser, String passwd, String withssl) {


            env.put(Context.INITIAL_CONTEXT_FACTORY, INITCTX);
            env.put(Context.PROVIDER_URL, host);
            env.put("com.sun.jndi.ldap.connect.pool", "true");
            
            if (withssl.equals("1")) {
                env.put(Context.SECURITY_AUTHENTICATION, "simple");
                env.put(Context.SECURITY_PROTOCOL, "ssl");
            }

            env.put(Context.SECURITY_PRINCIPAL, ldapuser);
            env.put(Context.SECURITY_CREDENTIALS, passwd);
 
 }

	    public void init(String strmatch,String type, String searchdn) throws NamingException ,ConnectException {

                String filter_cn = "";
                String filter_cnens = "";
                String filter_uid = "";
                String filter_supannid = "";
                boolean is_uid = false;
                boolean is_cn = false;
                boolean is_cnens = false;
                boolean is_supannid = false;

                ctls = new SearchControls();         
                ctls.setReturningObjFlag(false);
                ctls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
                ctls.setReturningAttributes(new String[]{"uid", "cn" ,"sn", "givenName", "mailLocalAddress","eduPersonAffiliation","supannAffectation" ,"supannEmpId"});
               // ctls.setReturningAttributes(new String[0]);

                if ( type.equals("uid")) {
                filter_uid = "(& (objectclass=eduPerson) (!(supannAffectation=parti)) (!(eduPersonAffiliation=student)) " + 
                             "(uid=*" + strmatch + "*))";
                is_uid= true;
                }

                if (type.equals("cn")) {
                filter_cn = "(& (| (supannAffectation=cri) (supannEmpId=556171) (supannEmpId=5569999) (eduPersonAffiliation=employee))"
                        +      " (!(supannAffectation=parti)) (!(eduPersonAffiliation=student)) "
                        +      " (|(cn="+ strmatch +"*)(cn=* " + strmatch + "*)(sn=" + strmatch + "*)(givenName="+ strmatch +"*))"
                        + " )";
                is_cn = true;
                }

                if (type.equals("cn_ens")) {
                filter_cnens = "(& (| (eduPersonAffiliation=researcher) (eduPersonAffiliation=faculty))"
                        +      " (!(supannAffectation=parti)) (!(eduPersonAffiliation=student)) "
                        +      " (|(cn="+ strmatch +"*)(cn=* " + strmatch + "*)(sn=" + strmatch + "*)(givenName="+ strmatch +"*))"
                        + " )";
                is_cnens = true;
                }
                
                if (type.equals("supannid")) {
                filter_supannid = "(& (| (eduPersonAffiliation=researcher) (supannAffectation=cri) (cn=FORNACCIARI*) (eduPersonAffiliation=faculty))"
                        +      " (!(supannAffectation=parti))"
                        +      " (supannEmpId=" + strmatch + ")"
                        + " )";
                is_supannid = true;
                }

	        try {
                    
	            DirContext ctx = new InitialDirContext(env);
	            System.out.println("Connexion reussi");
	          //printAttrs(ctx.getAttributes(name));
	            
	            if (is_cn) {
                    System.out.println("filterCN : " + filter_cn);
                    ans = ctx.search(searchdn , filter_cn, ctls);
                    }
                  
                    if (is_uid) {
                    System.out.println("filterUID : " + filter_uid);
                    ans1 = ctx.search(searchdn , filter_uid, ctls); 
                    }

                    if (is_supannid) {
                    System.out.println("filterSUPID : " + filter_supannid);
                    ans2 = ctx.search(searchdn , filter_supannid, ctls); 
                    }

                    if (is_cnens) {
                    System.out.println("filterCN : " + filter_cnens);
                    ans = ctx.search(searchdn , filter_cnens, ctls);
                    }
       
	            ctx.close();
	            
	        } catch (NamingException na) {
	            System.out.println("Sortie LdapBind en Erreur");
	            System.out.println(na.getCause().toString());
	            System.out.println(na.getMessage());
	            throw na;
	        }          
	    }
}
