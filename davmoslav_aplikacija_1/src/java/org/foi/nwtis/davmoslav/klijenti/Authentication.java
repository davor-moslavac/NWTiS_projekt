/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.davmoslav.klijenti;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dkermek
 */

@XmlRootElement(name="OAuth20")
public class Authentication {
    
    private AccessToken access_token;

    public AccessToken getAccess_token() {
        return access_token;
    }

    @XmlElement
    public void setAccess_token(AccessToken access_token) {
        this.access_token = access_token;
    }    
}
