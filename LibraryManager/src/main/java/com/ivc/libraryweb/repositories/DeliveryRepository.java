/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivc.libraryweb.repositories;

import com.ivc.libraryweb.entities.Delivery;
import java.util.List;

/**
 *
 * @author Sokolov@ivc.org
 */
public interface DeliveryRepository {

    List<Delivery> findAll();

    Delivery find(Delivery delivery);

    Delivery findWithDetail(Delivery delivery);

    Delivery create(Delivery delivery);

    Delivery update(Delivery delivery);

    void delete(Delivery delivery);

}
