/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.davmoslav.web.zrna;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.davmoslav.klijenti.MeteoServis;
import org.foi.nwtis.davmoslav.web.podaci.WeatherData;

/**
 *
 * @author Davor
 */
@ManagedBean
@SessionScoped
public class PregledAdresa {

    @EJB
    private MeteoServis meteoServis;

    private HashMap<String, String> popisAdrese;

    private String odabranaAdrese;
    private List<String> adrese;
    private int zadnjihNPodataka = 0;
    private WeatherData wd;
    private List<WeatherData> sviPodaci;
    private boolean ulogiran;

    private String vrijemeOd;
    private String vrijemeDo;
    private Date date;
    private Date date2;

    /**
     * Creates a new instance of nesto
     */
    public PregledAdresa() {
    }

    public boolean isUlogiran() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Object korisnik = session.getAttribute("korisnik");
            ulogiran = korisnik != null;
        } else {
            ulogiran = false;
        }
        return ulogiran;
    }

    public void setUlogiran(boolean ulogiran) {
        this.ulogiran = ulogiran;
    }

    public MeteoServis getMeteoServis() {
        return meteoServis;
    }

    public void setMeteoServis(MeteoServis meteoServis) {
        this.meteoServis = meteoServis;
    }

    public HashMap<String, String> getPopisAdrese() {
        return popisAdrese;
    }

    public void setPopisAdrese(HashMap<String, String> popisAdrese) {
        this.popisAdrese = popisAdrese;
    }

    public String getOdabranaAdrese() {
        return odabranaAdrese;
    }

    public void setOdabranaAdrese(String odabranaAdrese) {
        this.odabranaAdrese = odabranaAdrese;
    }

    public List<String> getAdrese() {
        popisAdrese = new HashMap<>();
        adrese = meteoServis.dajSveAdrese();
        int i = 0;
        if (adrese != null) {
            for (String adr : adrese) {
                popisAdrese.put(adr, Integer.toString(i));
                i++;
            }
        }
        return adrese;
    }

    public void setAdrese(List<String> adrese) {
        this.adrese = adrese;
    }

    public int getZadnjihNPodataka() {
        return zadnjihNPodataka;
    }

    public void setZadnjihNPodataka(int zadnjihNPodataka) {
        this.zadnjihNPodataka = zadnjihNPodataka;
    }

    public String getVrijemeOd() {
        return vrijemeOd;
    }

    public void setVrijemeOd(String vrijemeOd) {
        this.vrijemeOd = vrijemeOd;
    }

    public String getVrijemeDo() {
        return vrijemeDo;
    }

    public void setVrijemeDo(String vrijemeDo) {
        this.vrijemeDo = vrijemeDo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public WeatherData getWd() {
        return wd;
    }

    public void setWd(WeatherData wd) {
        this.wd = wd;
    }

    public List<WeatherData> getSviPodaci() {
        return sviPodaci;
    }

    public void setSviPodaci(List<WeatherData> sviPodaci) {
        this.sviPodaci = sviPodaci;
    }

    public String preuzmiMeteoPodatke() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (odabranaAdrese == null || odabranaAdrese.isEmpty()) {
            return "";
        }
        sviPodaci = new ArrayList<>();
        if (zadnjihNPodataka != 0) {
            wd = new WeatherData();
            String meteoPodatak = meteoServis.dajPosljednjePodatkeZaAdresu(zadnjihNPodataka, odabranaAdrese).toString();
            wd.setVrijeme(meteoPodatak.split(":")[0].trim());
            wd.setHumidity(Float.parseFloat(meteoPodatak.split(":")[1].trim()));
            wd.setRainDaily(Float.parseFloat(meteoPodatak.split(":")[2].trim()));
            wd.setTemperature(Float.parseFloat(meteoPodatak.split(":")[3].trim()));
            wd.setWindSpeed(Float.parseFloat(meteoPodatak.split(":")[4].trim()));
            wd.setSnowDaily(Float.parseFloat(meteoPodatak.split(":")[5].trim()));
            wd.setLatitude(meteoPodatak.split(":")[6].trim());
            wd.setLongitude(meteoPodatak.split(":")[7].trim());
            sviPodaci.add(wd);
        } else if (date != null && date2 != null) {
            vrijemeOd = format.format(date).toString();
            vrijemeDo = format.format(date2).toString();
            List<String> meteoPodatak = meteoServis.dajPodatkeZaAdresuInterval(odabranaAdrese, vrijemeOd, vrijemeDo);
            for (int i = 0; i < meteoPodatak.size(); i++) {
                wd = new WeatherData();
                wd.setVrijeme(meteoPodatak.get(i).split(":")[0].trim());
                wd.setHumidity(Float.parseFloat(meteoPodatak.get(i).split(":")[1].trim()));
                wd.setRainDaily(Float.parseFloat(meteoPodatak.get(i).split(":")[2].trim()));
                wd.setTemperature(Float.parseFloat(meteoPodatak.get(i).split(":")[3].trim()));
                wd.setWindSpeed(Float.parseFloat(meteoPodatak.get(i).split(":")[4].trim()));
                wd.setSnowDaily(Float.parseFloat(meteoPodatak.get(i).split(":")[5].trim()));
                wd.setLatitude(meteoPodatak.get(i).split(":")[6].trim());
                wd.setLongitude(meteoPodatak.get(i).split(":")[7].trim());
                sviPodaci.add(wd);
            }
        }
        return "";
    }
}
