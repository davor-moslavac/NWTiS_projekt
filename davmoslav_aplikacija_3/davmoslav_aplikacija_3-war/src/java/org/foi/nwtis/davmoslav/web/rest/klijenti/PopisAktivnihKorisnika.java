/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.davmoslav.web.rest.klijenti;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST
 * resource:PopisAktivnihKorisnikaResource<br>
 * USAGE:
 * <pre>
 *        PopisAktivnihKorisnika client = new PopisAktivnihKorisnika();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Davor
 */
public class PopisAktivnihKorisnika {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/davmoslav_aplikacija_2-war/webresources";

    public PopisAktivnihKorisnika(String id) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        String resourcePath = java.text.MessageFormat.format("popisAktivnihKorisnikas/{0}", new Object[]{id});
        webTarget = client.target(BASE_URI).path(resourcePath);
    }

    public void setResourcePath(String id) {
        String resourcePath = java.text.MessageFormat.format("popisAktivnihKorisnikas/{0}", new Object[]{id});
        webTarget = client.target(BASE_URI).path(resourcePath);
    }

    public void delete() throws ClientErrorException {
        webTarget.request().delete();
    }

    public String getHtml() throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.TEXT_HTML).get(String.class);
    }

    public void putHtml(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.TEXT_HTML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.TEXT_HTML));
    }

    public void close() {
        client.close();
    }

    public String dajPopisPopisAktivnihKorisnika() {
        PopisAktivnihKorisnika klijent = new PopisAktivnihKorisnika(BASE_URI);
        return klijent.getHtml();
    }

}
