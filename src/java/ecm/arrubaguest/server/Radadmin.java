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
@Table(name = "radadmin")
@SequenceGenerator(name="radadmin_id_seq", sequenceName="radadmin_id_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Radadmin.findAll", query = "SELECT r FROM Radadmin r"),
    @NamedQuery(name = "Radadmin.findById", query = "SELECT r FROM Radadmin r WHERE r.id = :id"),
    @NamedQuery(name = "Radadmin.findByEvenement", query = "SELECT r FROM Radadmin r WHERE r.evenement = :evenement"),
    @NamedQuery(name = "Radadmin.findByNbcompte", query = "SELECT r FROM Radadmin r WHERE r.nbcompte = :nbcompte"),
    @NamedQuery(name = "Radadmin.findByFirstaccount", query = "SELECT r FROM Radadmin r WHERE r.firstaccount = :firstaccount"),
    @NamedQuery(name = "Radadmin.findByExpiration", query = "SELECT r FROM Radadmin r WHERE r.expiration = :expiration"),
    @NamedQuery(name = "Radadmin.findByCreateur", query = "SELECT r FROM Radadmin r WHERE r.createur = :createur"),
    @NamedQuery(name = "Radadmin.findByStatus", query = "SELECT r FROM Radadmin r WHERE r.status = :status"),
    @NamedQuery(name = "Radadmin.findByRefevnt", query = "SELECT r FROM Radadmin r WHERE r.refevnt = :refevnt"),
    @NamedQuery(name = "Radadmin.findByFirstlogin", query = "SELECT r FROM Radadmin r WHERE r.firstlogin = :firstlogin"),
    @NamedQuery(name = "Radadmin.findByInfos", query = "SELECT r FROM Radadmin r WHERE r.infos = :infos")})

public class Radadmin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="radadmin_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "evenement")
    private String evenement;
    @Basic(optional = false)
    @Column(name = "nbcompte")
    private int nbcompte;
    @Basic(optional = false)
    @Column(name = "firstaccount")
    private String firstaccount;
    @Column(name = "expiration")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiration;
    @Basic(optional = false)
    @Column(name = "createur")
    private String createur;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "refevnt")
    private Character refevnt;
    @Column(name = "firstlogin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firstlogin;
    @Basic(optional = false)
    @Column(name = "infos")
    private String infos;

    public Radadmin() {
    }

    public Radadmin(Integer id) {
        this.id = id;
    }

    public Radadmin(Integer id, String evenement, int nbcompte, String firstaccount, String createur) {
        this.id = id;
        this.evenement = evenement;
        this.nbcompte = nbcompte;
        this.firstaccount = firstaccount;
        this.createur = createur;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEvenement() {
        return evenement;
    }

    public void setEvenement(String evenement) {
        this.evenement = evenement;
    }

    public int getNbcompte() {
        return nbcompte;
    }

    public void setNbcompte(int nbcompte) {
        this.nbcompte = nbcompte;
    }

    public String getFirstaccount() {
        return firstaccount;
    }

    public void setFirstaccount(String firstaccount) {
        this.firstaccount = firstaccount;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getCreateur() {
        return createur;
    }

    public void setCreateur(String createur) {
        this.createur = createur;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Character getRefevnt() {
        return refevnt;
    }

    public void setRefevnt(Character refevnt) {
        this.refevnt = refevnt;
    }

    public Date getFirstlogin() {
        return firstlogin;
    }

    public void setFirstlogin(Date firstlogin) {
        this.firstlogin = firstlogin;
    }

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
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
        if (!(object instanceof Radadmin)) {
            return false;
        }
        Radadmin other = (Radadmin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ecm.arrubaguest.server.Radadmin[ id=" + id + " ]";
    }

}
