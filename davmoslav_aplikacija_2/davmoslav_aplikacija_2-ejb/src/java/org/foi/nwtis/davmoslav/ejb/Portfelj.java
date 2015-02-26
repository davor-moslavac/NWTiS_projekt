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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "PORTFELJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Portfelj.findAll", query = "SELECT p FROM Portfelj p"),
    @NamedQuery(name = "Portfelj.findById", query = "SELECT p FROM Portfelj p WHERE p.id = :id"),
    @NamedQuery(name = "Portfelj.findByNaziv", query = "SELECT p FROM Portfelj p WHERE p.naziv = :naziv")})
public class Portfelj implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NAZIV")
    private String naziv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "portfeljId")
    private List<PortfeljKorisnika> portfeljKorisnikaList;
    @JoinColumn(name = "KORISNIK_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Korisnik korisnikId;

    public Portfelj() {
    }

    public Portfelj(Integer id) {
        this.id = id;
    }

    public Portfelj(Integer id, String naziv) {
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
    public List<PortfeljKorisnika> getPortfeljKorisnikaList() {
        return portfeljKorisnikaList;
    }

    public void setPortfeljKorisnikaList(List<PortfeljKorisnika> portfeljKorisnikaList) {
        this.portfeljKorisnikaList = portfeljKorisnikaList;
    }

    public Korisnik getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Korisnik korisnikId) {
        this.korisnikId = korisnikId;
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
        if (!(object instanceof Portfelj)) {
            return false;
        }
        Portfelj other = (Portfelj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.davmoslav.ejb.Portfelj[ id=" + id + " ]";
    }
    
}
