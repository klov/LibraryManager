/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivc.libraryweb.services.test.integrated;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.ivc.libraryweb.entities.Category;
import com.ivc.libraryweb.integration.config.TestConfig;
import com.ivc.libraryweb.repositories.CategoryRepositoryImpl;
import com.ivc.libraryweb.services.BookPropertiesService;
import com.ivc.libraryweb.services.BookPropertiesServiceImpl;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.assertj.core.api.AssertionsForClassTypes.extractProperty;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

/**
 *
 * @author Vitaliy Denisov
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
        classes = {TestConfig.class,
            CategoryRepositoryImpl.class,
            BookPropertiesServiceImpl.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
public class BookPropertiesImplIT extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String[] ALL_CATEGORY_NAME = new String[]{"8A82KM", "14A14"};
    private static Category validCategory = new Category("8A82KM");

    @Autowired
    BookPropertiesService bookProperties;

    @Autowired
    ApplicationContext applicationContext;

    @PersistenceContext
    private EntityManager em;

    @Test
    @DatabaseSetup("classpath:dataBase.xml")
    public void testFindAllCategories() {
        List<Category> findList = bookProperties.findAllCategories();
        assertThat(extractProperty(Category.NAME_PROPERTY).from(findList))
                .hasSize(ALL_CATEGORY_NAME.length)
                .containsOnly((Object[]) ALL_CATEGORY_NAME);
    }

//    @Test
//    @DatabaseSetup("classpath:category_data.xml")
//    @ExpectedDatabase("classpath:expectedDataBaseForUpdateCategory.xml")
//    public void testUpdateCategory() throws MalformedURLException, DataSetException, URISyntaxException, Exception {
//        Set<Book> setBook = new HashSet<Book>();
//        Book b = new Book();
//        b.setName("myBook");
//        b.setId(1l);
//        b.setListCount(30);
//        b.setCategory(validCategory);
//        setBook.add(b);
//        validCategory.setId(1l);
//        validCategory = bookProperties.findCategory(validCategory);
//        validCategory.setBooks(setBook);
//        bookProperties.updateCategory(validCategory);
//        URI  uri = getClass().getClassLoader().getResource("expectedDataBaseForUpdateCategory.xml").toURI();
//        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File(uri));
//        IDataSet dataSet = new QueryDataSet(dbConnection);
//        ITable expectedTable = expectedDataSet.getTable("book");
//        ITable table = dataSet.getTable("book");
//        assertEquals(expectedTable,table);
//    }
}
