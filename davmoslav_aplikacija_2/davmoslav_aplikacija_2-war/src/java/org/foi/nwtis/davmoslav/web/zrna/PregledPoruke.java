/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.davmoslav.web.zrna;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.foi.nwtis.davmoslav.web.kontrole.Poruka;

/**
 *
 * @author Davor
 */
@ManagedBean
@RequestScoped
public class PregledPoruke {

    private Poruka poruka;

    /**
     * Creates a new instance of PregledPoruke
     */
    public PregledPoruke() {
    }

    public Poruka getPoruka() {
        PregledSvihPoruka psp = (PregledSvihPoruka) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pregledSvihPoruka");
        poruka = psp.getOdabranaPoruka();
        return poruka;
    }

    public void setPoruka(Poruka poruka) {
        this.poruka = poruka;
    }
}
