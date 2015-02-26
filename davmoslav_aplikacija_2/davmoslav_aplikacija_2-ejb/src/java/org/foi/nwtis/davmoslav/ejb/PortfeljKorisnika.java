/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.davmoslav.ejb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Davor
 */
@Entity
@Table(name = "PORTFELJ_KORISNIKA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PortfeljKorisnika.findAll", query = "SELECT p FROM PortfeljKorisnika p"),
    @NamedQuery(name = "PortfeljKorisnika.findById", query = "SELECT p FROM PortfeljKorisnika p WHERE p.id = :id"),
    @NamedQuery(name = "PortfeljKorisnika.findByAdresa", query = "SELECT p FROM PortfeljKorisnika p WHERE p.adresa = :adresa")})
public class PortfeljKorisnika implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "ADRESA")
    private String adresa;
    @JoinColumn(name = "PORTFELJ_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Portfelj portfeljId;

    public PortfeljKorisnika() {
    }

    public PortfeljKorisnika(Integer id) {
        this.id = id;
    }

    public PortfeljKorisnika(Integer id, String adresa) {
        this.id = id;
        this.adresa = adresa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Portfelj getPortfeljId() {
        return portfeljId;
    }

    public void setPortfeljId(Portfelj portfeljId) {
        this.portfeljId = portfeljId;
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
        if (!(object instanceof PortfeljKorisnika)) {
            return false;
        }
        PortfeljKorisnika other = (PortfeljKorisnika) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.davmoslav.ejb.PortfeljKorisnika[ id=" + id + " ]";
    }
    
}
