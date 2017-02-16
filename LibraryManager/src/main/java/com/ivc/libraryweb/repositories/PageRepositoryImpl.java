/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivc.libraryweb.repositories;

import com.ivc.libraryweb.entities.Page;
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
@Repository("jpaPageRepository")
public class PageRepositoryImpl implements PageRepository {

    //-------------------Logger---------------------------------------------------
    //-------------------Constants------------------------------------------------
    //-------------------Fields---------------------------------------------------
    @PersistenceContext
    private EntityManager em;

    //-------------------Constructors---------------------------------------------
    //-------------------Getters and setters--------------------------------------
    //-------------------Methods--------------------------------------------------
    public List<Page> findAll() {
        return em.createNamedQuery("Page.findAll", Page.class).getResultList();
    }

    public Page find(Page page) {
        return em.find(Page.class, page.getId());
    }

    public Page findWithDetail(Page page) {
        return em.createNamedQuery("Page.findWithDetail", Page.class)
                .setParameter("id", page.getId()).getSingleResult();
    }

    public Page create(Page page) {
        em.persist(page);
        return page;
    }

    public Page update(Page page) {
        int version = find(page).getVersion();
        page.setVersion(version);
        return em.merge(page);
    }

    public void delete(Page page) {
        Page p = find(page);
        em.remove(p);
    }

    public List<Page> findPagesForDocument(int documentId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
