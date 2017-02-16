package com.ivc.libraryweb.repositories;

import com.ivc.libraryweb.entities.Document;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author VitaliyDenisov
 */
@Transactional
@Repository("jpaDocumentRepository")
public class DocumentRepositoryImpl implements DocumentRepository {

    //-------------------Logger---------------------------------------------------
    //-------------------Constants------------------------------------------------
    //-------------------Fields---------------------------------------------------
    @PersistenceContext
    private EntityManager em;

    //-------------------Constructors---------------------------------------------
    //-------------------Getters and setters--------------------------------------
    //-------------------Methods--------------------------------------------------
    public List<Document> findAll() {
        return em.createNamedQuery("Document.findAll", Document.class).getResultList();
    }

    public Document find(Document document) {
        return em.find(Document.class, document.getId());
    }

    public Document findWithDetail(Document document) {
        return em.createNamedQuery("Document.findWithDetail", Document.class).setParameter("id", document.getId()).getSingleResult();
    }

    public Document create(Document document) {
        em.persist(document);
        return document;
    }

    public Document update(Document document) {
        int version = find(document).getVersion();
        document.setVersion(version);
        return em.merge(document);
    }

    public void delete(Document document) {
        em.remove(find(document));
    }
}
