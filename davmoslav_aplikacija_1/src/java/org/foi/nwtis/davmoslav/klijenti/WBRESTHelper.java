/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.davmoslav.klijenti;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author dkermek
 */
public class WBRESTHelper {
    private static final String WB_BASE_URI = "https://thepulseapi.earthnetworks.com/";    
    private String cKey;
    private String sKey;
    private Authentication autentikacija;

    public WBRESTHelper(String cKey, String sKey) {
        this.cKey = cKey;
        this.sKey = sKey;
    }

    public static String getWB_BASE_URI() {
        return WB_BASE_URI;
    }
    
    /**
     * Creates a new instance of WeatherBugStrarnoVrijeme
     */
    public void autenticiraj() {
        autentikacija = ClientBuilder.newClient()
                .target(WB_BASE_URI + "oauth20/token?grant_type=client_credentials&client_id=" 
                        + cKey + "&client_secret=" + sKey)
                .request(MediaType.APPLICATION_XML)
                .get(Authentication.class);
    } 
    
    // to do pripremi autentikaciju na bazi json-a

    public Authentication getAutentikacija() {
        return autentikacija;
    }

    public void setAautentikacija(Authentication autentikacija) {
        this.autentikacija = autentikacija;
    }
    
    
}
