/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.davmoslav.kontrole;

import java.io.Serializable;

/**
 * Klasa koja se koristi za generiranja objekta koji sadrži atribute za opis
 * email statistike koja se šalje na JMS
 *
 * @author Davor
 */
public class JMSPoruka implements Serializable {

    private String vrijemePocetka;
    private String vrijemeKraja;
    private int brojProcitanihPoruka;
    private int brojIspravnihPoruka;
    private int brojOstalihPoruka;

    public JMSPoruka(String vrijemePocetka, String vrijemeKraja, int brojProcitanihPoruka, int brojIspravnihPoruka, int brojOstalihPoruka) {
        this.vrijemePocetka = vrijemePocetka;
        this.vrijemeKraja = vrijemeKraja;
        this.brojProcitanihPoruka = brojProcitanihPoruka;
        this.brojIspravnihPoruka = brojIspravnihPoruka;
        this.brojOstalihPoruka = brojOstalihPoruka;
    }

    public String getVrijemePocetka() {
        return vrijemePocetka;
    }

    public void setVrijemePocetka(String vrijemePocetka) {
        this.vrijemePocetka = vrijemePocetka;
    }

    public String getVrijemeKraja() {
        return vrijemeKraja;
    }

    public void setVrijemeKraja(String vrijemeKraja) {
        this.vrijemeKraja = vrijemeKraja;
    }

    public int getBrojProcitanihPoruka() {
        return brojProcitanihPoruka;
    }

    public void setBrojProcitanihPoruka(int brojProcitanihPoruka) {
        this.brojProcitanihPoruka = brojProcitanihPoruka;
    }

    public int getBrojIspravnihPoruka() {
        return brojIspravnihPoruka;
    }

    public void setBrojIspravnihPoruka(int brojIspravnihPoruka) {
        this.brojIspravnihPoruka = brojIspravnihPoruka;
    }

    public int getBrojOstalihPoruka() {
        return brojOstalihPoruka;
    }

    public void setBrojOstalihPoruka(int brojOstalihPoruka) {
        this.brojOstalihPoruka = brojOstalihPoruka;
    }
}
