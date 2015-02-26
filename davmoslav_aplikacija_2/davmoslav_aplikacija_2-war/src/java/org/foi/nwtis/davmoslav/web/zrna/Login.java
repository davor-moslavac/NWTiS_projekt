/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.davmoslav.web.zrna;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.foi.nwtis.davmoslav.ejb.Korisnik;
import org.foi.nwtis.davmoslav.sb.KorisnikFacade;

/**
 *
 * @author Denis
 */
@ManagedBean(name="login")
@SessionScoped
public class Login {
    
    private String korisnicko_ime = "";
    private String korisnicka_lozinka = "";
    
    @EJB
    private KorisnikFacade korisnikFacade;
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

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
    
    public String prijava() {
        if(korisnicko_ime != null && korisnicko_ime.length() > 0 && korisnicka_lozinka != null && korisnicka_lozinka.length() > 0)
        {
            Korisnik kor = korisnikFacade.findUser(korisnicko_ime, korisnicka_lozinka);
            if(kor != null)
            {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("korisnik", kor);
                return "OK";
            }
        }
        return "NOTOK";
    }
}
