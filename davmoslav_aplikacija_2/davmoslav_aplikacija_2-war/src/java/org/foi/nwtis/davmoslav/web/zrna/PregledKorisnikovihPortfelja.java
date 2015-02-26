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
import org.foi.nwtis.davmoslav.ejb.Portfelj;
import org.foi.nwtis.davmoslav.klijenti.MeteoServis;
import org.foi.nwtis.davmoslav.sb.KorisnikFacade;
import org.foi.nwtis.davmoslav.sb.PortfeljFacade;
import org.foi.nwtis.davmoslav.web.podaci.Location;
import org.foi.nwtis.davmoslav.web.podaci.WeatherData;

/**
 *
 * @author Davor
 */
@ManagedBean
@RequestScoped
public class PregledKorisnikovihPortfelja {

    @EJB
    private PortfeljFacade portfeljFacade;
    @EJB
    private KorisnikFacade korisnikFacade;
    @EJB
    private MeteoServis meteoServis;
    
    private String novaAdresa;
    private String noviPortfelj;
    private String odabranaAdresa;
    private String odabranaAdresaZaBrisanje;

    private Map<String, Integer> popisPortfelja;
    private Map<String, String> adreseAktivne;
    private Map<String, String> adreseZaMeteo;

    private List<WeatherData> meteoZaPrikaz;

    /**
     * Creates a new instance of PregledKorisnikovihPortfelja
     */
    public PregledKorisnikovihPortfelja() {
    }

    /*
     TODO povuci korisnika iz baze
     Stavi u listu sve portfelje za tog korisnika (select one list box)
     Pritiskom na "Odaberi" ispisi adrese u drugom list boxu
     */
    public String getNoviPortfelj() {
        return noviPortfelj;
    }

    public void setNoviPortfelj(String noviPortfelj) {
        this.noviPortfelj = noviPortfelj;
    }

    public Map<String, Integer> getPopisPortfelja() {
        List<Portfelj> portfelji = portfeljFacade.findAll();
        popisPortfelja = new HashMap<>();
        for (Portfelj p : portfelji) {
            if (p.getKorisnikId().equals("sesija")) {

            }

        }
        return popisPortfelja;
    }

    public String emailPostavke() {
        return "emailPostavke";
    }
    
    public PortfeljFacade getPortfeljFacade() {

        return portfeljFacade;
    }

    public void setPortfeljFacade(PortfeljFacade portfeljFacade) {
        this.portfeljFacade = portfeljFacade;
    }

    public KorisnikFacade getKorisnikFacade() {
        return korisnikFacade;
    }

    public void setKorisnikFacade(KorisnikFacade korisnikFacade) {
        this.korisnikFacade = korisnikFacade;
    }

    public MeteoServis getMeteoServis() {
        return meteoServis;
    }

    public void setMeteoServis(MeteoServis meteoServis) {
        this.meteoServis = meteoServis;
    }

    public String getNovaAdresa() {
        return novaAdresa;
    }

    public void setNovaAdresa(String novaAdresa) {
        this.novaAdresa = novaAdresa;
    }

    public String getOdabranaAdresa() {
        return odabranaAdresa;
    }

    public void setOdabranaAdresa(String odabranaAdresa) {
        this.odabranaAdresa = odabranaAdresa;
    }

    public String getOdabranaAdresaZaBrisanje() {
        return odabranaAdresaZaBrisanje;
    }

    public void setOdabranaAdresaZaBrisanje(String odabranaAdresaZaBrisanje) {
        this.odabranaAdresaZaBrisanje = odabranaAdresaZaBrisanje;
    }

    public Map<String, String> getAdreseAktivne() {
        return adreseAktivne;
    }

    public void setAdreseAktivne(Map<String, String> adreseAktivne) {
        this.adreseAktivne = adreseAktivne;
    }

    public Map<String, String> getAdreseZaMeteo() {
        return adreseZaMeteo;
    }

    public void setAdreseZaMeteo(Map<String, String> adreseZaMeteo) {
        this.adreseZaMeteo = adreseZaMeteo;
    }

    public List<WeatherData> getMeteoZaPrikaz() {
        return meteoZaPrikaz;
    }

    public void setMeteoZaPrikaz(List<WeatherData> meteoZaPrikaz) {
        this.meteoZaPrikaz = meteoZaPrikaz;
    }

}
