/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ecm.arrubaguest.server;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "radpostauth")
@SequenceGenerator(name="radpostauth_id_seq", sequenceName="radpostauth_id_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Radpostauth.findAll", query = "SELECT r FROM Radpostauth r"),
    @NamedQuery(name = "Radpostauth.findById", query = "SELECT r FROM Radpostauth r WHERE r.id = :id"),
    @NamedQuery(name = "Radpostauth.findByUsername", query = "SELECT r FROM Radpostauth r WHERE r.username = :username"),
    @NamedQuery(name = "Radpostauth.findByPass", query = "SELECT r FROM Radpostauth r WHERE r.pass = :pass"),
    @NamedQuery(name = "Radpostauth.findByReply", query = "SELECT r FROM Radpostauth r WHERE r.reply = :reply"),
    @NamedQuery(name = "Radpostauth.findByCalledstationid", query = "SELECT r FROM Radpostauth r WHERE r.calledstationid = :calledstationid"),
    @NamedQuery(name = "Radpostauth.findByCallingstationid", query = "SELECT r FROM Radpostauth r WHERE r.callingstationid = :callingstationid"),
    @NamedQuery(name = "Radpostauth.findByAuthdate", query = "SELECT r FROM Radpostauth r WHERE r.authdate = :authdate")})
public class Radpostauth implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="radpostauth_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Column(name = "pass")
    private String pass;
    @Column(name = "reply")
    private String reply;
    @Column(name = "calledstationid")
    private String calledstationid;
    @Column(name = "callingstationid")
    private String callingstationid;
    @Basic(optional = false)
    @Column(name = "authdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date authdate;

    public Radpostauth() {
    }

    public Radpostauth(Long id) {
        this.id = id;
    }

    public Radpostauth(Long id, String username, Date authdate) {
        this.id = id;
        this.username = username;
        this.authdate = authdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
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

    public Date getAuthdate() {
        return authdate;
    }

    public void setAuthdate(Date authdate) {
        this.authdate = authdate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Radpostauth)) {
            return false;
        }
        Radpostauth other = (Radpostauth) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ecm.arrubaguest.server.Radpostauth[ id=" + id + " ]";
    }
    
}
