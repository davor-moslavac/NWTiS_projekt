/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.davmoslav.web.zrna;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.foi.nwtis.davmoslav.web.kontrole.JMSPoruka;


/**
 *
 * @author Davor
 */
@ManagedBean
@RequestScoped
public class JMSPoruke {

    private List<JMSPoruka> listaPoruka = new ArrayList<JMSPoruka>();
    private List<JMSPoruka> filtriranaListaPoruka = new ArrayList<JMSPoruka>();
    /**
     * Creates a new instance of JMSPoruke
     */
    public JMSPoruke() {
    }

    void preuzmiPoruke(){
        
    }
    
    public void obrisi(JMSPoruka obrisi){
        System.out.println("Brisem");
        
        
    }
    
    public List<JMSPoruka> getListaPoruka() {
        return listaPoruka;
    }

    public void setListaPoruka(List<JMSPoruka> listaPoruka) {
        this.listaPoruka = listaPoruka;
    }

    public List<JMSPoruka> getFiltriranaListaPoruka() {
        return filtriranaListaPoruka;
    }

    public void setFiltriranaListaPoruka(List<JMSPoruka> filtriranaListaPoruka) {
        this.filtriranaListaPoruka = filtriranaListaPoruka;
    }
}
