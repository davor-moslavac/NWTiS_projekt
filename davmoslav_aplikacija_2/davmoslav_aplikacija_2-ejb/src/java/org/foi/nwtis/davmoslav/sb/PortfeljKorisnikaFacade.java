/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.davmoslav.sb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.foi.nwtis.davmoslav.ejb.PortfeljKorisnika;

/**
 *
 * @author Davor
 */
@Stateless
public class PortfeljKorisnikaFacade extends AbstractFacade<PortfeljKorisnika> {
    @PersistenceContext(unitName = "davmoslav_aplikacija_2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PortfeljKorisnikaFacade() {
        super(PortfeljKorisnika.class);
    }
    
}
