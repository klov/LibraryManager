/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivc.libraryweb.repositories;

import com.ivc.libraryweb.entities.Book;
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
@Repository("jpaBookRepository")
public class BookRepositoryImpl implements BookRepository {

    //-------------------Logger---------------------------------------------------
    //-------------------Constants------------------------------------------------
    //-------------------Fields---------------------------------------------------
    @PersistenceContext
    private EntityManager em;

    //-------------------Constructors---------------------------------------------
    //-------------------Getters and setters--------------------------------------
    //-------------------Methods--------------------------------------------------
    public List<Book> findAll() {
        return em.createNamedQuery("Book.findAll", Book.class).getResultList();
    }

    public Book find(Book book) {
        return em.find(Book.class, book.getId());
    }

    public Book findWithDetail(Book book) {
        return em.createNamedQuery("Book.findWithDetail", Book.class)
                .setParameter("id", book.getId()).getSingleResult();
    }

    public Book create(Book book) {
        em.persist(book);
        return book;
    }

    public Book update(Book book) {
        int version = find(book).getVersion();
        book.setVersion(version);
        return em.merge(book);
    }

    public void delete(Book book) {
        em.remove(find(book));
    }

    public Delivery findLastDelivery(Book book) {
        List<Delivery> list = em.createNamedQuery("Book.findLastDelivery", Delivery.class)
                .setParameter("id", book.getId()).getResultList();
         return  ((list.isEmpty()) ? null : list.get(0));
    }

}
