/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivc.libraryweb.repositories;

import com.ivc.libraryweb.entities.Category;
import java.util.List;

/**
 *
 * @author VitaliyDenisov
 */
public interface CategoryRepository {

    Category create(Category category);

    void delete(Category category);

    Category update(Category category);

    List<Category> findAll();

    Category find(Category category);

    Category findWithDetail(Category category);
}
