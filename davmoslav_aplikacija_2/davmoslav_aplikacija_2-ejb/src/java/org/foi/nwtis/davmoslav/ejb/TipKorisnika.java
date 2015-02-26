/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.davmoslav.ejb;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Davor
 */
@Entity
@Table(name = "TIP_KORISNIKA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipKorisnika.findAll", query = "SELECT t FROM TipKorisnika t"),
    @NamedQuery(name = "TipKorisnika.findById", query = "SELECT t FROM TipKorisnika t WHERE t.id = :id"),
    @NamedQuery(name = "TipKorisnika.findByNaziv", query = "SELECT t FROM TipKorisnika t WHERE t.naziv = :naziv")})
public class TipKorisnika implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NAZIV")
    private String naziv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipKorisnikaId")
    private List<Korisnik> korisnikList;

    public TipKorisnika() {
    }

    public TipKorisnika(Integer id) {
        this.id = id;
    }

    public TipKorisnika(Integer id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @XmlTransient
    public List<Korisnik> getKorisnikList() {
        return korisnikList;
    }

    public void setKorisnikList(List<Korisnik> korisnikList) {
        this.korisnikList = korisnikList;
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
        if (!(object instanceof TipKorisnika)) {
            return false;
        }
        TipKorisnika other = (TipKorisnika) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.davmoslav.ejb.TipKorisnika[ id=" + id + " ]";
    }
    
}
