
package com.ivc.libraryweb.controllers;

import static com.ivc.libraryweb.controllers.CategoryRestController.CATEGORY_PATH;
import com.ivc.libraryweb.entities.Category;
import com.ivc.libraryweb.services.BookPropertiesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Vitaliy Denisov
 */
@RestController
@RequestMapping(path = CATEGORY_PATH,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryRestController {

    public static final String CATEGORY_PATH = "/categories";

    BookPropertiesService bookProperties;

    public CategoryRestController() {
    }

    @Autowired(required = false)
    public void setBookProperties(BookPropertiesService bookProperties) {
        this.bookProperties = bookProperties;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Category> getAllCategory() {
        return bookProperties.findAllCategories();
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Category createCategory(@RequestBody Category category) {
        return bookProperties.createCategory(category);
    }

    @RequestMapping(method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Category updateCategory(@RequestBody Category category) {
        return bookProperties.updateCategory(category);
    }

    @RequestMapping(method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteCategory(@RequestBody Category category) {
        bookProperties.deleteCategory(category);
    }

}
