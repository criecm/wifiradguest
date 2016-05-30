package ecm.arrubaguest.server;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class DataBase {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private String Expiration = "";
    private String ExpireAfter = "";

    public DataBase() {
        emf = Persistence.createEntityManagerFactory("ArrubaguestPU");
    }

    public int Read_Radcheck_Sequence() {

        // emf = Persistence.createEntityManagerFactory("ArrubaguestPU");
        em = emf.createEntityManager();
        em.getTransaction().begin();

        Query q = em.createNativeQuery("select last_value from radcheck_id_seq");
        q.setMaxResults(1);

        String s = q.getResultList().get(0).toString();

        em.getTransaction().commit();
        em.close();
    // emf.close();

        return Integer.parseInt(s);
    }

    public int Read_Radmin_Sequence() {

    //   emf = Persistence.createEntityManagerFactory("ArrubaguestPU");
        em = emf.createEntityManager();
        em.getTransaction().begin();

        Query q = em.createNativeQuery("select last_value from radadmin_id_seq");
        q.setMaxResults(1);

        String s = q.getResultList().get(0).toString();

        em.getTransaction().commit();
        em.close();
    // emf.close();

        return Integer.parseInt(s);
    }

    public void SetExpiration(String s) {
        Expiration = s;
    }

    public void SetExpireAfter(String s) {
        ExpireAfter = s;
    }

    public void CloseFactory() {
        emf.close();
    }

    public Radadmin Lire_Evnt(int evt) {

        //     emf = Persistence.createEntityManagerFactory("ArrubaguestPU");
        em = emf.createEntityManager();

        Query q = em.createQuery("select r from Radadmin r where r.id = :name");
        q.setParameter("name", evt);
        Radadmin l = (Radadmin) q.getSingleResult();

        em.close();
   //     emf.close();

        return l;
    }

    public List<Radadmin> Lire_Radadmin() {
      
        //     emf = Persistence.createEntityManagerFactory("ArrubaguestPU");
        em = emf.createEntityManager();
  
        Query q0 = em.createQuery("select r from Radadmin r where r.status = true");   
        List<Radadmin> lue = (List < Radadmin >)q0.getResultList();
     
        em.close();
   //   emf.close();

        return lue;
    }
    
    public Map<String,String> Lire_ExpireAfter() {
        
        Map m = new HashMap<String, String>();
        
        em = emf.createEntityManager();
        
        Query q1 = em.createNativeQuery("SELECT distinct firstaccount, value from radadmin left join radcheck on firstaccount = substring(username"
                + " from 1 for length(firstaccount)) where attribute = 'Expire-After' and status = true order by firstaccount asc");
        
        List lr = q1.getResultList();
        
        for(int i=0;i<lr.size();i++){
            Object[] result = (Object[])lr.get(i);
            m.put(result[0],result[1]);
            System.out.println("Resultat :: " + result[0] + " " + result[1]);
        }
        
        em.close();
        
        return m; 
    }
    
    public List<Radadmin> Search_Evnt(String s) {

        String ReqQ = "select r from Radadmin r where ( UPPER(r.evenement) LIKE :search and r.status = true and r.refevnt != '#') order by r.evenement asc";

        //    emf = Persistence.createEntityManagerFactory("ArrubaguestPU");
        em = emf.createEntityManager();
        em.getTransaction().begin();

        Query q = em.createQuery(ReqQ);
        q.setParameter("search", "%" + s.toUpperCase() + "%");
        List<Radadmin> l = (List<Radadmin>) q.getResultList();

        em.getTransaction().commit();

        em.close();
        //   emf.close();
        return l;

    }

    public int Ncount(String uid) {

        int nb = 0;

        em = emf.createEntityManager();
        em.getTransaction().begin();

        Query q = em.createNativeQuery("select count (*) from radadmin where createur = '" + uid + "' and expiration > now()");

        q.setMaxResults(1);
        nb = Integer.parseInt(q.getResultList().get(0).toString());

        em.getTransaction().commit();
        em.close();

        return nb;
    }

    public String TestAdmin() {

        String res;

        //     emf = Persistence.createEntityManagerFactory("ArrubaguestPU");
        em = emf.createEntityManager();
        em.getTransaction().begin();

        Query q = em.createNativeQuery("select count (*) from radadmin where refevnt != '#'");

        q.setMaxResults(1);
        res = q.getResultList().get(0).toString();

        em.getTransaction().commit();

        if (res.equals("0")) {
            em.close();
            //         emf.close();
            return res;

        } else {

            em.getTransaction().begin();

            Query q2 = em.createNativeQuery("select refevnt from radadmin where (refevnt != '#') order by id desc limit 1");

            res = q2.getSingleResult().toString();

            if (res.equals("Z")) {
                res = "+";
            }

            em.getTransaction().commit();
            em.close();
            //            emf.close();
            return res;
        }

        /*{

         em.getTransaction().begin();

         Query q1 = em.createNativeQuery("select refevnt from radadmin order by refevnt desc limit 1");
         res = q1.getSingleResult().toString();

         if (!res.equals("Z")) {

         em.getTransaction().commit();
         em.close();
         emf.close();
         return res;

         }  else 
         {
                
         Query q2 = em.createNativeQuery("select refevnt from radadmin order by id desc limit 1");
         res = q2.getSingleResult().toString();
                
         if (res.equals("Z")) res = "A";
                
         em.getTransaction().commit();
         em.close();
         emf.close();
         return res ;

         }
         } */
    }

    public void Change_EvntNB(int n, String id) {

        int lid = Integer.parseInt(id);

        //   emf = Persistence.createEntityManagerFactory("ArrubaguestPU");
        em = emf.createEntityManager();

        try {

            em.getTransaction().begin();

            Query q = em.createNativeQuery("update radadmin set nbcompte = " + n + " where id = " + lid);
            q.executeUpdate();

            em.getTransaction().commit();
        } catch (RuntimeException r) {
            if (em.getTransaction().isActive()) {
                try {
                    em.getTransaction().rollback();
                } catch (RuntimeException e) {
                    // Log eventuellement
                }
            }
            throw r;
        } finally {
            em.close();
        }

    //    emf.close();
    }

    public void WriteAdmin(String evt, Integer nb, String fc, Date datedeb, Date datefin, String nom, boolean b, char refevnt, String infos) {

        // emf = Persistence.createEntityManagerFactory("ArrubaguestPU");
        em = emf.createEntityManager();

        try {

            em.getTransaction().begin();

            Radadmin rad = new Radadmin();

            rad.setEvenement(evt);
            rad.setNbcompte(nb);
            rad.setFirstaccount(fc);
            rad.setFirstlogin(datedeb);
            rad.setExpiration(datefin);
            rad.setCreateur(nom);
            rad.setStatus(b);
            rad.setRefevnt(refevnt);
            rad.setInfos(infos);

            em.persist(rad);
            em.getTransaction().commit();
        } catch (RuntimeException r) {
            if (em.getTransaction().isActive()) {
                try {
                    em.getTransaction().rollback();
                } catch (RuntimeException e) {
                    // Log eventuellement
                }
            }
            throw r;
        } finally {
            em.close();
        }
 //   emf.close();

    }

    public void WriteUsers(List<Radcheck> l, boolean type) {

        //   emf = Persistence.createEntityManagerFactory("ArrubaguestPU");
        em = emf.createEntityManager();

        Query q = em.createNativeQuery("insert into radcheck (username ,attribute ,op ,value)"
                + " values ( ?,?,?,MD5(?))");

        Query q1 = em.createNativeQuery("insert into radcheck (username ,attribute ,op ,value)"
                + " values ( ?,?,?,?)");

        try {

            em.getTransaction().begin();

            for (int i = 0; i < l.size(); i++) {

                if (type == false) {

                    q.setParameter(1, l.get(i).getUsername());
                    q.setParameter(2, "MD5-Password");
                    q.setParameter(3, ":=");
                    q.setParameter(4, l.get(i).getValue());

                    q.executeUpdate();

                    q1.setParameter(1, l.get(i).getUsername());
                    q1.setParameter(2, "Expire-After");
                    q1.setParameter(3, ":=");
                    q1.setParameter(4, ExpireAfter);

                    q1.executeUpdate();

                } else {

                    q.setParameter(1, l.get(i).getUsername());
                    q.setParameter(2, "MD5-Password");
                    q.setParameter(3, ":=");
                    q.setParameter(4, l.get(i).getValue());

                    q.executeUpdate();

                    q1.setParameter(1, l.get(i).getUsername());
                    q1.setParameter(2, "Expiration");
                    q1.setParameter(3, ":=");
                    q1.setParameter(4, Expiration);

                    q1.executeUpdate();

                    q1.setParameter(1, l.get(i).getUsername());
                    q1.setParameter(2, "First-Login");
                    q1.setParameter(3, ":=");
                    q1.setParameter(4, ExpireAfter);

                    q1.executeUpdate();

                }    
            } 
            em.getTransaction().commit();
            } catch (RuntimeException r) {
            if (em.getTransaction().isActive()) {
                try {
                    em.getTransaction().rollback();
                } catch (RuntimeException e) {
                    // Log eventuellement
                }
            }
            throw r;
        } finally {
            em.close();
        }
    }
}
