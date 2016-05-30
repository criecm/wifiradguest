/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ecm.arrubaguest.server;

import java.io.Serializable;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jlf
 */
@Entity
@Table(name = "radacct")
@SequenceGenerator(name="radacct_radacctid_seq", sequenceName="radacct_radacctid_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Radacct.findAll", query = "SELECT r FROM Radacct r"),
    @NamedQuery(name = "Radacct.findByRadacctid", query = "SELECT r FROM Radacct r WHERE r.radacctid = :radacctid"),
    @NamedQuery(name = "Radacct.findByAcctsessionid", query = "SELECT r FROM Radacct r WHERE r.acctsessionid = :acctsessionid"),
    @NamedQuery(name = "Radacct.findByAcctuniqueid", query = "SELECT r FROM Radacct r WHERE r.acctuniqueid = :acctuniqueid"),
    @NamedQuery(name = "Radacct.findByUsername", query = "SELECT r FROM Radacct r WHERE r.username = :username"),
    @NamedQuery(name = "Radacct.findByGroupname", query = "SELECT r FROM Radacct r WHERE r.groupname = :groupname"),
    @NamedQuery(name = "Radacct.findByRealm", query = "SELECT r FROM Radacct r WHERE r.realm = :realm"),
    @NamedQuery(name = "Radacct.findByNasportid", query = "SELECT r FROM Radacct r WHERE r.nasportid = :nasportid"),
    @NamedQuery(name = "Radacct.findByNasporttype", query = "SELECT r FROM Radacct r WHERE r.nasporttype = :nasporttype"),
    @NamedQuery(name = "Radacct.findByAcctstarttime", query = "SELECT r FROM Radacct r WHERE r.acctstarttime = :acctstarttime"),
    @NamedQuery(name = "Radacct.findByAcctstoptime", query = "SELECT r FROM Radacct r WHERE r.acctstoptime = :acctstoptime"),
    @NamedQuery(name = "Radacct.findByAcctsessiontime", query = "SELECT r FROM Radacct r WHERE r.acctsessiontime = :acctsessiontime"),
    @NamedQuery(name = "Radacct.findByAcctauthentic", query = "SELECT r FROM Radacct r WHERE r.acctauthentic = :acctauthentic"),
    @NamedQuery(name = "Radacct.findByConnectinfoStart", query = "SELECT r FROM Radacct r WHERE r.connectinfoStart = :connectinfoStart"),
    @NamedQuery(name = "Radacct.findByConnectinfoStop", query = "SELECT r FROM Radacct r WHERE r.connectinfoStop = :connectinfoStop"),
    @NamedQuery(name = "Radacct.findByAcctinputoctets", query = "SELECT r FROM Radacct r WHERE r.acctinputoctets = :acctinputoctets"),
    @NamedQuery(name = "Radacct.findByAcctoutputoctets", query = "SELECT r FROM Radacct r WHERE r.acctoutputoctets = :acctoutputoctets"),
    @NamedQuery(name = "Radacct.findByCalledstationid", query = "SELECT r FROM Radacct r WHERE r.calledstationid = :calledstationid"),
    @NamedQuery(name = "Radacct.findByCallingstationid", query = "SELECT r FROM Radacct r WHERE r.callingstationid = :callingstationid"),
    @NamedQuery(name = "Radacct.findByAcctterminatecause", query = "SELECT r FROM Radacct r WHERE r.acctterminatecause = :acctterminatecause"),
    @NamedQuery(name = "Radacct.findByServicetype", query = "SELECT r FROM Radacct r WHERE r.servicetype = :servicetype"),
    @NamedQuery(name = "Radacct.findByXascendsessionsvrkey", query = "SELECT r FROM Radacct r WHERE r.xascendsessionsvrkey = :xascendsessionsvrkey"),
    @NamedQuery(name = "Radacct.findByFramedprotocol", query = "SELECT r FROM Radacct r WHERE r.framedprotocol = :framedprotocol"),
    @NamedQuery(name = "Radacct.findByAcctstartdelay", query = "SELECT r FROM Radacct r WHERE r.acctstartdelay = :acctstartdelay"),
    @NamedQuery(name = "Radacct.findByAcctstopdelay", query = "SELECT r FROM Radacct r WHERE r.acctstopdelay = :acctstopdelay")})
public class Radacct implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="radacct_radacctid_seq")
    @Basic(optional = false)
    @Column(name = "radacctid")
    private Long radacctid;
    @Basic(optional = false)
    @Column(name = "acctsessionid")
    private String acctsessionid;
    @Basic(optional = false)
    @Column(name = "acctuniqueid")
    private String acctuniqueid;
    @Column(name = "username")
    private String username;
    @Column(name = "groupname")
    private String groupname;
    @Column(name = "realm")
    private String realm;
    @Basic(optional = false)
    @Lob
    @Column(name = "nasipaddress")
    private InetAddress nasipaddress;
    @Column(name = "nasportid")
    private String nasportid;
    @Column(name = "nasporttype")
    private String nasporttype;
    @Column(name = "acctstarttime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date acctstarttime;
    @Column(name = "acctstoptime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date acctstoptime;
    @Column(name = "acctsessiontime")
    private BigInteger acctsessiontime;
    @Column(name = "acctauthentic")
    private String acctauthentic;
    @Column(name = "connectinfo_start")
    private String connectinfoStart;
    @Column(name = "connectinfo_stop")
    private String connectinfoStop;
    @Column(name = "acctinputoctets")
    private BigInteger acctinputoctets;
    @Column(name = "acctoutputoctets")
    private BigInteger acctoutputoctets;
    @Column(name = "calledstationid")
    private String calledstationid;
    @Column(name = "callingstationid")
    private String callingstationid;
    @Column(name = "acctterminatecause")
    private String acctterminatecause;
    @Column(name = "servicetype")
    private String servicetype;
    @Column(name = "xascendsessionsvrkey")
    private String xascendsessionsvrkey;
    @Column(name = "framedprotocol")
    private String framedprotocol;
    @Lob
    @Column(name = "framedipaddress")
    private InetAddress framedipaddress;
    @Column(name = "acctstartdelay")
    private Integer acctstartdelay;
    @Column(name = "acctstopdelay")
    private Integer acctstopdelay;

    public Radacct() {
    }

    public Radacct(Long radacctid) {
        this.radacctid = radacctid;
    }

    public Radacct(Long radacctid, String acctsessionid, String acctuniqueid, InetAddress nasipaddress) {
        this.radacctid = radacctid;
        this.acctsessionid = acctsessionid;
        this.acctuniqueid = acctuniqueid;
        this.nasipaddress = nasipaddress;
    }

    public Long getRadacctid() {
        return radacctid;
    }

    public void setRadacctid(Long radacctid) {
        this.radacctid = radacctid;
    }

    public String getAcctsessionid() {
        return acctsessionid;
    }

    public void setAcctsessionid(String acctsessionid) {
        this.acctsessionid = acctsessionid;
    }

    public String getAcctuniqueid() {
        return acctuniqueid;
    }

    public void setAcctuniqueid(String acctuniqueid) {
        this.acctuniqueid = acctuniqueid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public Object getNasipaddress() {
        return nasipaddress;
    }

    public void setNasipaddress(InetAddress nasipaddress) {
        this.nasipaddress = nasipaddress;
    }

    public String getNasportid() {
        return nasportid;
    }

    public void setNasportid(String nasportid) {
        this.nasportid = nasportid;
    }

    public String getNasporttype() {
        return nasporttype;
    }

    public void setNasporttype(String nasporttype) {
        this.nasporttype = nasporttype;
    }

    public Date getAcctstarttime() {
        return acctstarttime;
    }

    public void setAcctstarttime(Date acctstarttime) {
        this.acctstarttime = acctstarttime;
    }

    public Date getAcctstoptime() {
        return acctstoptime;
    }

    public void setAcctstoptime(Date acctstoptime) {
        this.acctstoptime = acctstoptime;
    }

    public BigInteger getAcctsessiontime() {
        return acctsessiontime;
    }

    public void setAcctsessiontime(BigInteger acctsessiontime) {
        this.acctsessiontime = acctsessiontime;
    }

    public String getAcctauthentic() {
        return acctauthentic;
    }

    public void setAcctauthentic(String acctauthentic) {
        this.acctauthentic = acctauthentic;
    }

    public String getConnectinfoStart() {
        return connectinfoStart;
    }

    public void setConnectinfoStart(String connectinfoStart) {
        this.connectinfoStart = connectinfoStart;
    }

    public String getConnectinfoStop() {
        return connectinfoStop;
    }

    public void setConnectinfoStop(String connectinfoStop) {
        this.connectinfoStop = connectinfoStop;
    }

    public BigInteger getAcctinputoctets() {
        return acctinputoctets;
    }

    public void setAcctinputoctets(BigInteger acctinputoctets) {
        this.acctinputoctets = acctinputoctets;
    }

    public BigInteger getAcctoutputoctets() {
        return acctoutputoctets;
    }

    public void setAcctoutputoctets(BigInteger acctoutputoctets) {
        this.acctoutputoctets = acctoutputoctets;
    }

    public String getCalledstationid() {
        return calledstationid;
    }

    public void setCalledstationid(String calledstationid) {
        this.calledstationid = calledstationid;
    }

    public String getCallingstationid() {
        return callingstationid;
    }

    public void setCallingstationid(String callingstationid) {
        this.callingstationid = callingstationid;
    }

    public String getAcctterminatecause() {
        return acctterminatecause;
    }

    public void setAcctterminatecause(String acctterminatecause) {
        this.acctterminatecause = acctterminatecause;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public String getXascendsessionsvrkey() {
        return xascendsessionsvrkey;
    }

    public void setXascendsessionsvrkey(String xascendsessionsvrkey) {
        this.xascendsessionsvrkey = xascendsessionsvrkey;
    }

    public String getFramedprotocol() {
        return framedprotocol;
    }

    public void setFramedprotocol(String framedprotocol) {
        this.framedprotocol = framedprotocol;
    }

    public Object getFramedipaddress() {
        return framedipaddress;
    }

    public void setFramedipaddress(InetAddress framedipaddress) {
        this.framedipaddress = framedipaddress;
    }

    public Integer getAcctstartdelay() {
        return acctstartdelay;
    }

    public void setAcctstartdelay(Integer acctstartdelay) {
        this.acctstartdelay = acctstartdelay;
    }

    public Integer getAcctstopdelay() {
        return acctstopdelay;
    }

    public void setAcctstopdelay(Integer acctstopdelay) {
        this.acctstopdelay = acctstopdelay;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (radacctid != null ? radacctid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Radacct)) {
            return false;
        }
        Radacct other = (Radacct) object;
        if ((this.radacctid == null && other.radacctid != null) || (this.radacctid != null && !this.radacctid.equals(other.radacctid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ecm.arrubaguest.server.Radacct[ radacctid=" + radacctid + " ]";
    }
    
}
