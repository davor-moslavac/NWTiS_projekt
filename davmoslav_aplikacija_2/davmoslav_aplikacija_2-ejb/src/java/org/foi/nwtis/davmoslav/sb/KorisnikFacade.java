/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.davmoslav.sb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.foi.nwtis.davmoslav.ejb.Korisnik;
import org.foi.nwtis.davmoslav.ejb.Korisnik_;

/**
 *
 * @author Davor
 */
@Stateless
public class KorisnikFacade extends AbstractFacade<Korisnik> {

    @PersistenceContext(unitName = "davmoslav_aplikacija_2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public KorisnikFacade() {
        super(Korisnik.class);
    }

    public Korisnik findUser(String korisnickoIme, String lozinka) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Korisnik> criteria = builder.createQuery(Korisnik.class);
        Root<Korisnik> u = criteria.from(Korisnik.class);
        TypedQuery<Korisnik> query = em.createQuery(
                criteria.select(u).where(builder.and(
                                builder.equal(u.get(Korisnik_.korisnickoIme), korisnickoIme),
                                builder.equal(u.get(Korisnik_.lozinka), lozinka))));

        return query.getSingleResult();
    }

}
