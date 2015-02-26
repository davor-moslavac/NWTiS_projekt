/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.davmoslav.web.zrna;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.foi.nwtis.davmoslav.konfiguracije.Konfiguracija;
import org.foi.nwtis.davmoslav.web.slusaci.SlusacAplikacije;

/**
 *
 * @author Davor
 */
@ManagedBean(name = "emailPovezivanje")
@SessionScoped
public class EmailPovezivanje implements Serializable {

    private String email_posluzitelj = "127.0.0.1";
    private String korisnicko_ime = "servis@nwtis.nastava.foi.hr";
    private String korisnicka_lozinka = "";

    /**
     * Creates a new instance of EmailPovezivanje
     */
    public EmailPovezivanje() {
    }

    public String getEmail_posluzitelj() {
        return email_posluzitelj;
    }

    public void setEmail_posluzitelj(String email_posluzitelj) {
        this.email_posluzitelj = email_posluzitelj;
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

    public String saljiPoruku() {
        return "OK";
    }

    public String citajPoruke() {
        return "OK";
    }
}
