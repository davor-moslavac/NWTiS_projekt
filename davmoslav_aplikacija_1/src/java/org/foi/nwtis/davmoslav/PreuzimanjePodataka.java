/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.davmoslav;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.foi.nwtis.davmoslav.klijenti.GoogleMapsKlijent;
import org.foi.nwtis.davmoslav.klijenti.WeatherBugKlijent;
import org.foi.nwtis.davmoslav.konfiguracija.Konfiguracija;
import org.foi.nwtis.davmoslav.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.davmoslav.podaci.Location;
import org.foi.nwtis.davmoslav.podaci.WeatherData;
import org.foi.nwtis.davmoslav.slusaci.SlusacAplikacije;

/**
 *
 * @author Davor
 */
public class PreuzimanjePodataka extends Thread {

    Konfiguracija konfig;
    BP_Konfiguracija bpKonfig;
    private int interval;
    private String cKey;
    private String sKey;
    String adresa;
    String latitude;
    String longitude;

    long vrijemeKraj = 0;
    long vrijemeRada = 0;
    long vrijemeStart = 0;

    public static Boolean pokretanjeDretve = true;
    public static Boolean pauza = false;

    public PreuzimanjePodataka() {
        this.bpKonfig = SlusacAplikacije.konfiguracija_baza;
        this.konfig = SlusacAplikacije.konfiguracija;
        this.interval = Integer.parseInt(konfig.dajPostavku("interval"));
        this.cKey = konfig.dajPostavku("cKey");
        this.sKey = konfig.dajPostavku("sKey");
    }

    @Override
    public void run() {
        System.out.println("Dretva za preuzimanje podatka sa meteo servisa!");

        while (pokretanjeDretve) {
            if (!pauza) {
                System.out.println("Dretva krece u novi ciklus");
                vrijemeStart = (new Date()).getTime();

                Connection conn = null;
                Statement stmt = null;

                try {
                    Class.forName(bpKonfig.getDriver_database());
                } catch (ClassNotFoundException ex) {
                    System.out.println("Greška kod učitavanja drivera: " + ex.getMessage());
                }
                String connUrl = bpKonfig.getServer_database() + bpKonfig.getUser_database();
                try {
                    //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                    conn = DriverManager.getConnection(connUrl, bpKonfig.getUser_username(), bpKonfig.getUser_password());
                    stmt = conn.createStatement();

                    ResultSet rs = stmt.executeQuery("SELECT adresa, latitude, longitude FROM davmoslav_adrese");
                    WeatherBugKlijent wbk = new WeatherBugKlijent(cKey, sKey);
                    while (rs.next()) {
                        adresa = rs.getString("adresa");
                        latitude = rs.getString("latitude");
                        longitude = rs.getString("longitude");

                        WeatherData wd = wbk.getRealTimeWeather(latitude, longitude);
                        float temperatura = wd.getTemperature();
                        float vlaga = wd.getHumidity();
                        float kisa;
                        if (wd.getRainDaily() == null) {
                            kisa = 0;
                        } else {
                            kisa = wd.getRainDaily();
                        }
                        float snijeg;
                        if (wd.getSnowDaily() == null) {
                            snijeg = 0;
                        } else {
                            snijeg = wd.getSnowDaily();
                        }
                        float vjetar = wd.getWindSpeed();

                        stmt = conn.createStatement();
                        stmt.executeUpdate("INSERT INTO davmoslav_meteo (adresa, vlaga, kisa, temperatura, vjetar, snijeg, vrijeme, latitude, longitude) VALUES ('"
                                + new String(adresa).trim() + "'," + vlaga + "," + kisa + "," + temperatura + "," + vjetar + "," + snijeg
                                + ",'" + (System.currentTimeMillis() / 1000L) + "','" + new String(latitude).trim() + "','" + new String(longitude).trim() + "')");

                    }

                    System.out.println("Upisani podaci!");
                    stmt.close();
                    conn.close();

                } catch (SQLException ex) {
                    System.out.println("Greška prilikom spajanja na bazu: " + ex.getMessage());
                }

            } else {
                vrijemeStart = (new Date()).getTime();
                System.out.println("Preuzimanje meteo podataka je pauzirano.");
            }
            vrijemeKraj = (new Date()).getTime();
            vrijemeRada = vrijemeKraj - vrijemeStart;
            try {
                sleep(((long) interval * 1000) - vrijemeRada);
            } catch (InterruptedException ex) {
                System.out.println("Pogreska kod spavanja dretve");
            }
        }
    }

    //DRETVA Stop a ne interupt (napravis jos jednu petlju)
    
    @Override
    public void interrupt() {
        System.out.println("Dretva za preuzimanje podatka sa meteo servisa prekida sa radom");
        pokretanjeDretve = false;
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

}
