package ecm.arrubaguest.server;

import java.io.Serializable;

public class UserData implements Serializable {

    private String uid;
    private String sn;
    private String givenName;
    private String mailLocalAddress;
    private int supannEmpId;
    private String supannAffectation;
    private String eduPersonAffiliation;
    
    public UserData() {       
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getsupannEmpId() {
        return supannEmpId;
    }

    public void setsupannEmpId(String supannEmpId) {
        this.supannEmpId = Integer.parseInt(supannEmpId);
    }

     public String getSupannAffectation() {
        return supannAffectation;
    }

    public void setSupannAffectation(String supannAffectation) {
        this.supannAffectation = supannAffectation;
    }
    
    public String getEduPersonAffiliation() {
        return eduPersonAffiliation;
    }

    public void setEduPersonAffiliation(String eduPersonAffiliation) {
        this.eduPersonAffiliation = eduPersonAffiliation;
    }
    
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;;
    }

     public String getmail() {
        return mailLocalAddress;
    }

    public void setmail(String mailLocalAddress) {
        this.mailLocalAddress = mailLocalAddress;
    }
   
}
