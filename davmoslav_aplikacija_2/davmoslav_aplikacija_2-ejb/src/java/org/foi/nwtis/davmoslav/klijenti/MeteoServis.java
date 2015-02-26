/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.davmoslav.klijenti;

import javax.ejb.Stateless;
import javax.xml.ws.WebServiceRef;
import org.foi.nwtis.davmoslav.ws.serveri.GeoMeteoWS_Service;
import org.foi.nwtis.davmoslav.ws.serveri.WeatherData;

/**
 *
 * @author Davor
 */
@Stateless
public class MeteoServis {

    @WebServiceRef(wsdlLocation = "META-INF/wsdl/localhost_8084/davmoslav_aplikacija_1/GeoMeteoWS.wsdl")
    private GeoMeteoWS_Service service;

    public java.util.List<java.lang.String> dajPodatkeZaAdresuInterval(java.lang.String adresa, java.lang.String vrijemeOd, java.lang.String vrijemeDo) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.foi.nwtis.davmoslav.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajPodatkeZaAdresuInterval(adresa, vrijemeOd, vrijemeDo);
    }

    public java.util.List<java.lang.String> dajPosljednjePodatkeZaAdresu(java.lang.Integer broj, java.lang.String adresa) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.foi.nwtis.davmoslav.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajPosljednjePodatkeZaAdresu(broj, adresa);
    }

    public java.util.List<java.lang.String> dajRangListuAdresa(java.lang.Integer brojAdresa) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.foi.nwtis.davmoslav.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajRangListuAdresa(brojAdresa);
    }

    public java.util.List<java.lang.String> dajSveAdrese() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.foi.nwtis.davmoslav.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveAdrese();
    }

    public WeatherData dajVazeceMeteoPodatkeZaAdresu(java.lang.String adresa) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.foi.nwtis.davmoslav.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajVazeceMeteoPodatkeZaAdresu(adresa);
    }

}
