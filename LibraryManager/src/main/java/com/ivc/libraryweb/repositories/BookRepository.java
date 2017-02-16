/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivc.libraryweb.repositories;

import com.ivc.libraryweb.entities.Book;
import com.ivc.libraryweb.entities.Delivery;
import java.util.List;

/**
 *
 * @author Sokolov@ivc.org
 */
public interface BookRepository {

   List<Book> findAll();

    Book find(Book book);

    Book findWithDetail(Book book);

    Book create(Book book);

    Book update(Book book);

    void delete(Book book);
    
    Delivery findLastDelivery(Book book);
}
