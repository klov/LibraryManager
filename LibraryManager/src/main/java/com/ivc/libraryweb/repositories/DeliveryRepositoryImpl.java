/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivc.libraryweb.repositories;

import com.ivc.libraryweb.entities.Delivery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Sokolov@ivc.org
 */
@Transactional
@Repository("jpaDeliveryRepository")
public class DeliveryRepositoryImpl implements DeliveryRepository {

    //-------------------Logger---------------------------------------------------
    //-------------------Constants------------------------------------------------
    //-------------------Fields---------------------------------------------------
    @PersistenceContext
    private EntityManager em;

    //-------------------Constructors---------------------------------------------
    //-------------------Getters and setters--------------------------------------
    //-------------------Methods--------------------------------------------------
    public List<Delivery> findAll() {
        return em.createNamedQuery("Delivery.findAll", Delivery.class)
                .getResultList();
    }

    public Delivery find(Delivery delivery) {
        return em.find(Delivery.class, delivery.getId());
    }

    public Delivery findWithDetail(Delivery delivery) {
        return em.createNamedQuery("Delivery.findWithDetail", Delivery.class)
                .setParameter("id", delivery.getId()).getSingleResult();
    }

    public Delivery create(Delivery delivery) {
        em.persist(delivery);
        return delivery;
    }

    public Delivery update(Delivery delivery) {
        int version = find(delivery).getVersion();
        delivery.setVersion(version);
        return em.merge(delivery);
    }

    public void delete(Delivery delivery) {
        em.remove(find(delivery));
    }

}
