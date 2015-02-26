/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.davmoslav.web.kontrole;

import java.io.Serializable;

/**
 * Klasa koja se koristi za generiranja objekta koji sadrži atribute za opis
 poruke o adresa kodovima koja se šalje na JMS
 *
 * @author Davor
 */
public class JMSPorukaAdresa implements Serializable {

    private String adresa;
    private String korisnik;

    public JMSPorukaAdresa(String zip, String korisnik) {
        this.adresa = zip;
        this.korisnik = korisnik;
    }

    public String getZip() {
        return adresa;
    }

    public void setZip(String zip) {
        this.adresa = zip;
    }

    public String getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
    }
}
