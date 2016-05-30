/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ecm.arrubaguest.server;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jlf
 */
@Entity
@Table(name = "radcheck")
@SequenceGenerator(name="radcheck_id_seq", sequenceName="radcheck_id_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Radcheck.findAll", query = "SELECT r FROM Radcheck r"),
    @NamedQuery(name = "Radcheck.findById", query = "SELECT r FROM Radcheck r WHERE r.id = :id"),
    @NamedQuery(name = "Radcheck.findByUsername", query = "SELECT r FROM Radcheck r WHERE r.username = :username"),
    @NamedQuery(name = "Radcheck.findByAttribute", query = "SELECT r FROM Radcheck r WHERE r.attribute = :attribute"),
    @NamedQuery(name = "Radcheck.findByOp", query = "SELECT r FROM Radcheck r WHERE r.op = :op"),
    @NamedQuery(name = "Radcheck.findByValue", query = "SELECT r FROM Radcheck r WHERE r.value = :value")})
public class Radcheck implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="radcheck_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "attribute")
    private String attribute;
    @Basic(optional = false)
    @Column(name = "op")
    private String op;
    @Basic(optional = false)
    @Column(name = "value")
    private String value;

    public Radcheck() {
    }

    public Radcheck(Integer id) {
        this.id = id;
    }

    public Radcheck(Integer id, String username, String attribute, String op, String value) {
        this.id = id;
        this.username = username;
        this.attribute = attribute;
        this.op = op;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
        if (!(object instanceof Radcheck)) {
            return false;
        }
        Radcheck other = (Radcheck) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ecm.arrubaguest.server.Radcheck[ id=" + id + " ]";
    }
    
}
