package com.ivc.libraryweb.repositories;

import com.ivc.libraryweb.entities.Category;
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
@Repository("jpaCategoryRepository")
public class CategoryRepositoryImpl implements CategoryRepository {

    //-------------------Logger---------------------------------------------------
    //-------------------Constants------------------------------------------------
    //-------------------Fields---------------------------------------------------
    @PersistenceContext
    private EntityManager em;
  //-------------------Constructors---------------------------------------------

    //-------------------Getters and setters--------------------------------------
    //-------------------Methods--------------------------------------------------
    public Category create(Category category) {
        em.persist(category);
        return category;
    }

    public void delete(Category category) {
        em.remove(find(category));
    }

    public Category update(Category category) {
        int version = find(category).getVersion();
        category.setVersion(version);
        return em.merge(category);
    }

    public List<Category> findAll() {
        return em.createNamedQuery("Category.findAll", Category.class).getResultList();
    }

    public Category find(Category category) {
        return em.find(Category.class, category.getId());
    }

    public Category findWithDetail(Category category) {
        return em.createNamedQuery("Category.findWithDetail", Category.class).setParameter("id", category.getId()).getSingleResult();
    }
}
