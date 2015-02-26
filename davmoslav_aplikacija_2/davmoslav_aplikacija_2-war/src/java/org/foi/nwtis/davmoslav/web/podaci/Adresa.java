/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.davmoslav.web.podaci;

/**
 *
 * @author dkermek
 */
public class Adresa {
    private long idadresa;
    private String adresa;
    private Location geoloc;

    public Adresa() {
    }

    public Adresa(long idadresa, String adresa, Location geoloc) {
        this.idadresa = idadresa;
        this.adresa = adresa;
        this.geoloc = geoloc;
    }

    public Location getGeoloc() {
        return geoloc;
    }

    public void setGeoloc(Location geoloc) {
        this.geoloc = geoloc;
    }

    public long getIdadresa() {
        return idadresa;
    }

    public void setIdadresa(long idadresa) {
        this.idadresa = idadresa;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
    
    
}
