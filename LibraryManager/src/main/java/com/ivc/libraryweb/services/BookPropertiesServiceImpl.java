
package com.ivc.libraryweb.services;

import com.ivc.libraryweb.entities.Category;
import com.ivc.libraryweb.repositories.CategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.util.Assert.notNull;

/**
 *
 * @author VitaliyDenisov Service for work with properties of books. Provides functions of creation,
 * search, removal, updating.
 */
@Service
public class BookPropertiesServiceImpl implements BookPropertiesService {

    private final static String INVALID_REPOSITORY = "null categoryRepository";
    private final static String INVALID_PARAMETRE = "invalid parametre";

    CategoryRepository categoryRepository;

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    @Autowired(required = false)
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        notNull(categoryRepository, INVALID_REPOSITORY);
        this.categoryRepository = categoryRepository;
    }

    //----------------------------Method----------------------------------------------
    public Category createCategory(Category category) {
        notNull(category, INVALID_PARAMETRE);
        return categoryRepository.create(category);
    }

    public void deleteCategory(Category category) {
        notNull(category, INVALID_PARAMETRE);
        categoryRepository.delete(category);
    }

    public Category updateCategory(Category category) {
        notNull(category, INVALID_PARAMETRE);
        return categoryRepository.update(category);
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findCategory(Category category) {
        notNull(category, INVALID_PARAMETRE);
        return categoryRepository.find(category);

    }

    public Category findWithDetailCategory(Category category) {
        notNull(category, INVALID_PARAMETRE);
        return categoryRepository.findWithDetail(category);
    }

}
