/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.davmoslav;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.foi.nwtis.davmoslav.kontrole.JMSPoruka;

/**
 *
 * @author Davor
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/NWTiS_davmoslav_1")
})
public class MeteoPostar implements MessageListener {

    public static List<JMSPoruka> poruke = null;

    public MeteoPostar() {
        if (poruke == null) {
            poruke = new ArrayList<>();
        }
    }

    @Override
    public void onMessage(Message message) {

        if (message instanceof ObjectMessage) {
            ObjectMessage om = (ObjectMessage) message;

            JMSPoruka poruka;
            try {
                poruka = (JMSPoruka) om.getObject();
                poruke.add(poruka);
            } catch (JMSException ex) {
                System.out.println("Pogreška kod pretvaranja" + ex);
            }
        }
    }

}
