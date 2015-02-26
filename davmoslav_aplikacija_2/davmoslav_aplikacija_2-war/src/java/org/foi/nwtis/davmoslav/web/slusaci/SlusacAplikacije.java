/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.davmoslav.web.slusaci;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.foi.nwtis.davmoslav.konfiguracije.Konfiguracija;
import org.foi.nwtis.davmoslav.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.davmoslav.web.ObradaPoruka;

/**
 * Web application lifecycle listener.
 *
 * @author Davor 
 Preuzima podatke iz konteksta i šalje ih dretvi, te pokreće tu dretvu
 */
@WebListener()
public class SlusacAplikacije implements ServletContextListener {
    private Thread dretva = null;
    private ObradaPoruka obradaPoruka = null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        javax.servlet.ServletContext kontekst = sce.getServletContext();
        String path = kontekst.getRealPath("WEB-INF") + java.io.File.separator;
        String datoteka = path + sce.getServletContext().getInitParameter("datotekaKonfiguracijeBaze");
        
        BP_Konfiguracija konfig = new BP_Konfiguracija(datoteka);
        sce.getServletContext().setAttribute("BP_Konfiguracija", konfig);

        System.out.println(kontekst.getContextPath() + ": Konfiguracija je ucitana.");

        dretva = new Thread(obradaPoruka);
        dretva.start();
        System.out.println("Dretva uspješno pokrenuta");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
         if (dretva != null) {
            obradaPoruka.ugasi();
            System.out.println("Dretva ugašena");
        }
    }
}