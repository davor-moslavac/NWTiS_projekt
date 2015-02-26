/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.davmoslav.web.slusaci;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.foi.nwtis.davmoslav.ejb.Korisnik;

/**
 * Web application lifecycle listener.
 *
 * @author Davor
 */
@WebListener()
public class SlusacSesije implements HttpSessionListener, HttpSessionAttributeListener {

    public static List<Korisnik> poljeKorisnika = new ArrayList<Korisnik>();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Kreirana je sesija: " + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("Brise se sesija: " + se.getSession().getId());
        Korisnik odjavljeniKorisnik = null;
        odjavljeniKorisnik = (Korisnik) se.getSession().getAttribute("korisnik");
        poljeKorisnika.remove(odjavljeniKorisnik);
        se.getSession().getServletContext().setAttribute("popisKorisnika", poljeKorisnika);
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        Korisnik prijavljeniKorisnik = null;
        if (event.getName().contentEquals("korisnik")) {

            //popisKorisnika = (List<Korisnik>) event.getSession().getServletContext().getAttribute("popisKorisnika");
            prijavljeniKorisnik = (Korisnik) event.getValue();
            poljeKorisnika.add(prijavljeniKorisnik);
            event.getSession().getServletContext().setAttribute("popisKorisnika", poljeKorisnika);
            System.out.println("Polje registiranih korisnika je " + poljeKorisnika.size());
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        System.out.println("Brise se atribut.");

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        System.out.println("Atribut zamijenjen.");
    }
}
