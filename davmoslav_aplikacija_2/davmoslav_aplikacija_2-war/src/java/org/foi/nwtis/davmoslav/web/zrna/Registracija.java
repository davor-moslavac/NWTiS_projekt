/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.davmoslav.web.zrna;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.foi.nwtis.davmoslav.ejb.Korisnik;
import org.foi.nwtis.davmoslav.ejb.TipKorisnika;
import org.foi.nwtis.davmoslav.sb.KorisnikFacade;
import org.foi.nwtis.davmoslav.sb.TipKorisnikaFacade;

/**
 *
 * @author Davor
 */
@ManagedBean
@RequestScoped
public class Registracija {

    private String korisnicko_ime = "";
    private String korisnicka_lozinka = "";
    private Map<String, String> registracija_vrsta_korisnika;
    private Integer odabranaVrsta;

    @EJB
    private KorisnikFacade korisnikFacade;
    @EJB
    private TipKorisnikaFacade tipKorisnikaFacade;

    public Integer getOdabranaVrsta() {
        return odabranaVrsta;
    }

    public void setOdabranaVrsta(Integer odabranaVrsta) {
        this.odabranaVrsta = odabranaVrsta;
    }

    public Map<String, String> getRegistracija_vrsta_korisnika() {
        List<TipKorisnika> tipovi_korisnika = tipKorisnikaFacade.findAll();
        registracija_vrsta_korisnika = new HashMap<>();
        for (TipKorisnika tip : tipovi_korisnika) {
            registracija_vrsta_korisnika.put(tip.getNaziv(), tip.getId().toString());
        }
        return registracija_vrsta_korisnika;
    }

    public void setRegistracija_vrsta_korisnika(Map<String, String> registracija_vrsta_korisnika) {
        this.registracija_vrsta_korisnika = registracija_vrsta_korisnika;
    }

    public KorisnikFacade getKorisnikFacade() {
        return korisnikFacade;
    }

    public void setKorisnikFacade(KorisnikFacade korisnikFacade) {
        this.korisnikFacade = korisnikFacade;
    }

    public String getKorisnicko_ime() {
        return korisnicko_ime;
    }

    public void setKorisnicko_ime(String korisnicko_ime) {
        this.korisnicko_ime = korisnicko_ime;
    }

    public String getKorisnicka_lozinka() {
        return korisnicka_lozinka;
    }

    public void setKorisnicka_lozinka(String korisnicka_lozinka) {
        this.korisnicka_lozinka = korisnicka_lozinka;
    }

    public String registriraj() {
        if (korisnicko_ime != null && korisnicko_ime.length() > 0 && 
            korisnicka_lozinka != null &&  korisnicka_lozinka.length() > 0 &&
            odabranaVrsta != null) {
            Korisnik korisnik = new Korisnik(Integer.SIZE, korisnicko_ime, korisnicka_lozinka);
            korisnik.setTipKorisnikaId(tipKorisnikaFacade.find(odabranaVrsta));

            korisnikFacade.create(korisnik);
            return "OK";
        }
        return "NOTOK";
    }
}
