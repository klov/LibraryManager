
package com.ivc.libraryweb.services;

import com.ivc.libraryweb.entities.Category;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vitaliy Denisov
 */
@Service
public interface BookPropertiesService {

    Category createCategory(Category category);

    void deleteCategory(Category category);

    Category updateCategory(Category category);

    List<Category> findAllCategories();

    Category findCategory(Category category);

    Category findWithDetailCategory(Category category);

}
