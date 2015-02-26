/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.davmoslav.ws.serveri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
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
@WebService(serviceName = "GeoMeteoWS")
public class GeoMeteoWS {

    Konfiguracija konfig;
    BP_Konfiguracija bpKonfig;
    String sql = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /* 
     String cKey = konfig.dajPostavku("cKey");
     String sKey = konfig.dajPostavku("sKey");
     */

    /**
     * Web service operation
     *
     * @return
     */
    @WebMethod(operationName = "dajSveAdrese")
    public List<String> dajSveAdrese() {
        ResultSet rs = dohvatiPodatke("SELECT * FROM davmoslav_adrese");
        List<String> adrese = new ArrayList<>();
        try {
            while (rs.next()) {
                adrese.add(rs.getString("adresa"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }

        return adrese;
    }

    /**
     * Web service operation
     *
     * @param adresa
     * @return
     */
    @WebMethod(operationName = "dajVazeceMeteoPodatkeZaAdresu")
    public WeatherData dajVazeceMeteoPodatkeZaAdres(@WebParam(name = "adresa") final String adresa) {
        this.konfig = SlusacAplikacije.konfiguracija;
        if (adresa != null && adresa.length() > 0) {
            GoogleMapsKlijent gmk = new GoogleMapsKlijent();
            Location loc = gmk.getGeoLocation(adresa);

            String cKey = konfig.dajPostavku("cKey");
            String sKey = konfig.dajPostavku("sKey");

            WeatherBugKlijent wbk = new WeatherBugKlijent(cKey, sKey);
            WeatherData wd = wbk.getRealTimeWeather(loc.getLatitude(), loc.getLongitude());
            return wd;
        }

        return null;
    }

    private ResultSet dohvatiPodatke(String sql) {
        this.bpKonfig = SlusacAplikacije.konfiguracija_baza;

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

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
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("Greška prilikom spajanja na bazu: " + ex.getMessage());
        }
        return rs;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "dajRangListuAdresa")
    public List<String> dajRangListuAdresa(@WebParam(name = "brojAdresa") Integer brojAdresa) {
        ResultSet rs = dohvatiPodatke("SELECT adresa, COUNT(*) FROM davmoslav_meteo GROUP BY 1 ORDER BY 2 DESC LIMIT " + brojAdresa);
        List<String> adrese = new ArrayList<>();
        try {
            while (rs.next()) {
                adrese.add(rs.getString("adresa"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return adrese;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "dajPosljednjePodatkeZaAdresu")
    public List<String> dajPosljednjePodatkeZaAdresu(@WebParam(name = "broj") Integer broj, @WebParam(name = "adresa") String adresa) {

        ResultSet rs = dohvatiPodatke("SELECT * FROM davmoslav_meteo WHERE adresa='" + adresa + "' ORDER BY vrijeme DESC LIMIT " + broj);
        List<String> meteoPodaci = new ArrayList<>();
        try {
            while (rs.next()) {
                String meteoPodatak;
                meteoPodatak = sdf.format(Integer.valueOf(rs.getString("vrijeme"))) + ":"
                        + rs.getString("vlaga") + ":"
                        + rs.getString("kisa") + ":"
                        + rs.getString("temperatura") + ":"
                        + rs.getString("vjetar") + ":"
                        + rs.getString("snijeg") + ":"
                        + rs.getString("latitude") + ":"
                        + rs.getString("longitude") + ":";
                meteoPodaci.add(meteoPodatak);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return meteoPodaci;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "dajPodatkeZaAdresuInterval")
    public List<String> dajPodatkeZaAdresuInterval(@WebParam(name = "adresa") String adresa, @WebParam(name = "vrijemeOd") String vrijemeOd, @WebParam(name = "vrijemeDo") String vrijemeDo) {
        Date parseVrijemeOd = null;
        Date parseVrijemeDo = null;
        try {
            parseVrijemeOd = sdf.parse(vrijemeOd);
            parseVrijemeDo = sdf.parse(vrijemeDo);
        } catch (ParseException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<String> meteoPodaci = new ArrayList<>();
        ResultSet rs = dohvatiPodatke("SELECT * FROM davmoslav_meteo WHERE adresa='" + adresa + "' AND vrijeme>'" + parseVrijemeOd.getTime() + "' AND vrijeme<'" + parseVrijemeDo.getTime() + "'");
        try {
            while (rs.next()) {
                String meteoPodatak;
                meteoPodatak = (rs.getString("vrijeme")) + ":"
                        + rs.getString("vlaga") + ":"
                        + rs.getString("kisa") + ":"
                        + rs.getString("temperatura") + ":"
                        + rs.getString("vjetar") + ":"
                        + rs.getString("snijeg") + ":"
                        + rs.getString("latitude") + ":"
                        + rs.getString("longitude") + ":";
                meteoPodaci.add(meteoPodatak);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return meteoPodaci;
    }
}
