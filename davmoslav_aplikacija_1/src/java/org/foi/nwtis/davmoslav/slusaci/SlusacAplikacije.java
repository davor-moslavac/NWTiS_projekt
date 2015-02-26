/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.davmoslav.slusaci;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.foi.nwtis.davmoslav.PreuzimanjePodataka;
import org.foi.nwtis.davmoslav.SocketServer;
import org.foi.nwtis.davmoslav.konfiguracija.Konfiguracija;
import org.foi.nwtis.davmoslav.konfiguracija.KonfiguracijaApstraktna;
import org.foi.nwtis.davmoslav.konfiguracija.NemaKonfiguracije;
import org.foi.nwtis.davmoslav.konfiguracije.bp.BP_Konfiguracija;

/**
 * Web application lifecycle listener.
 *
 * @author Davor
 */
@WebListener()
public class SlusacAplikacije implements ServletContextListener {

    public static Konfiguracija konfiguracija = null;
    public static BP_Konfiguracija konfiguracija_baza = null;
    PreuzimanjePodataka pruzimanjePodataka = null;
    SocketServer socketServer = null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        String path = sc.getRealPath("WEB-INF");
        String datotekaKonfiguracije = path + java.io.File.separator + sc.getInitParameter("konfiguracija");
        String datotekaKonfiguracijeBaze = path + java.io.File.separator + sc.getInitParameter("konfiguracija_baza");
        try {
            konfiguracija = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datotekaKonfiguracije);
            konfiguracija_baza = new BP_Konfiguracija(datotekaKonfiguracijeBaze);
            sc.setAttribute("konfiguracija", konfiguracija);
            sc.setAttribute("konfiguracija_baza", konfiguracija_baza);
            System.out.println("Konfiguracijski podaci uspjesno ucitani");
            
            pruzimanjePodataka = new PreuzimanjePodataka();
            pruzimanjePodataka.start();
            
            socketServer = new SocketServer();
            socketServer.start();
            
        } catch (NemaKonfiguracije ex) {
            System.out.println("Nema konfiguracije!  " + ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (pruzimanjePodataka != null && pruzimanjePodataka.isAlive()) {
            pruzimanjePodataka.interrupt();
        }
        if (socketServer != null && socketServer.isAlive()) {
            socketServer.interrupt();
        }
    }
}
