/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivc.libraryweb.repositories;

import com.ivc.libraryweb.entities.Document;
import java.util.List;

/**
 *
 * @author VitalyDenisov
 */
public interface DocumentRepository {

    List<Document> findAll();

    Document find(Document document);

    Document findWithDetail(Document document);

    Document create(Document document);

    Document update(Document document);

    void delete(Document document);
}
