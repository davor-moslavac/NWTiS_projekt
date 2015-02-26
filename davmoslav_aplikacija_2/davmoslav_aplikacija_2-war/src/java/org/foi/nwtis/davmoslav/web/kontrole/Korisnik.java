/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.davmoslav.web.kontrole;

/**
 * Klasa Korisnik služi za spremanje prijavljenog korisnika u listu prijavljenih korisnika
 * @author Davor
 */
public class Korisnik {

    String korisnik;
    String prezime;
    String ime;
    String ses_ID;
    String email;
    int vrsta;
    
    /**
     * Konstruktor klase Korisnik
     * @param korisnik korisničko ime korisnika aplikacije
     * @param prezime prezime korisnika
     * @param ime ime korisnika
     * @param ses_ID id sesije prijavljenog korisnika
     * @param email email adresa korisnika
     * @param vrsta određuje da li je korisnik administrator ili obični korisnik
     */
    public Korisnik(String korisnik, String prezime, String ime, String ses_ID, String email,int vrsta) {
        this.korisnik = korisnik;
        this.prezime = prezime;
        this.ime = ime;
        this.ses_ID = ses_ID;
        this.email = email;
        this.vrsta = vrsta;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getSes_ID() {
        return ses_ID;
    }

    public void setSes_ID(String ses_ID) {
        this.ses_ID = ses_ID;
    }

    public int getVrsta() {
        return vrsta;
    }

    public void setVrsta(int vrsta) {
        this.vrsta = vrsta;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
