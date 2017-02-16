/*
   Информационно-вычислительный центр космодрома Байконур
*/

package com.ivc.libraryweb.services.test.integrated;

import static org.junit.Assert.assertEquals;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;
import com.ivc.libraryweb.entities.Book;
import com.ivc.libraryweb.entities.Category;
import com.ivc.libraryweb.entities.Delivery;
import com.ivc.libraryweb.entities.Document;
import com.ivc.libraryweb.entities.Organization;
import com.ivc.libraryweb.integration.config.BookIdFilter;
import com.ivc.libraryweb.integration.config.IdFilter;
import com.ivc.libraryweb.integration.config.TestConfig;
import com.ivc.libraryweb.repositories.BookRepositoryImpl;
import com.ivc.libraryweb.repositories.DocumentRepositoryImpl;
import com.ivc.libraryweb.repositories.OrganizationRepositoryImpl;
import com.ivc.libraryweb.repositories.PageRepositoryImpl;
import com.ivc.libraryweb.services.BookService;
import com.ivc.libraryweb.services.BookServiceImpl;
import com.ivc.libraryweb.services.OrganizationServiceImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
        classes = {TestConfig.class,
            BookRepositoryImpl.class,
            BookServiceImpl.class,
            OrganizationRepositoryImpl.class,
            DocumentRepositoryImpl.class,
            PageRepositoryImpl.class,
            OrganizationServiceImpl.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    DbUnitTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
public class BookServiceIT {
  //-------------------Logger---------------------------------------------------
  
  //-------------------Constants------------------------------------------------

  //-------------------Fields---------------------------------------------------
 
    @Autowired
    BookService bookService;
    
    @PersistenceContext
    private EntityManager em;
    
    Organization organization = new Organization();
    Category category = new Category();
    Book book = new Book();
  //-------------------Constructors---------------------------------------------

  //-------------------Getters and setters--------------------------------------

  //-------------------Methods--------------------------------------------------

      @Before
    public void before() {
        organization.setId(1l);
        category.setId(1l);
        book.setId(1l);
    }
    
    @Test
    @DatabaseSetup("classpath:bookService/testGetLastDeliveryShouldReturnNull.xml")
    public void testGetLastDeliveryShouldReturnNull() throws ParseException{
        Delivery delivery = bookService.getLastActiveDelivery(book.getId());
        assertNull(delivery);
    }
    
    @Test
    @DatabaseSetup("classpath:bookService/testGetLastDeliveryShouldReturnDelivery.xml")
    public void testGetLastDeliveryShouldReturnDelivery() throws ParseException{
        SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd");
        Delivery expectedDelivery = new Delivery();
        expectedDelivery.setBook(book);
        expectedDelivery.setId(3l);
        expectedDelivery.setVersion(1);
        expectedDelivery.setDeliveryDate(formatter.parse("2016-08-23"));
        expectedDelivery.setEmployeId(1l);
        Delivery delivery = bookService.getLastActiveDelivery(book.getId());
        assertEquals(expectedDelivery, delivery);
    }
    
    @Test
    @DatabaseSetup("classpath:bookService/testCreateBookSetupData.xml")
    @ExpectedDatabase(value = "classpath:bookService/testCreateBookExpectedData.xml",assertionMode = DatabaseAssertionMode.NON_STRICT,columnFilters={IdFilter.class,BookIdFilter.class})
    public void testCreateBook(){
        Document document = new Document();
        document.setNotice("notice");
        document.setListCount(0);
        Book newBook = new Book();
        newBook.setOrganization(organization);
        newBook.addCategory(category);
        newBook.setName("newBook");
        newBook.setCopyNumber("copy one");
        newBook.setBookNumber("12345");
        newBook.setDeclimalNumber("ABCD");
        newBook.setOriginalInventoryNumber("originalInventoryNumber");
        newBook.setInventoryNumber("inventoryNumber");
        newBook.addDocument(document);
        bookService.createBook(newBook);
    }
    
//    @Test
//    @DatabaseSetup("classpath:bookService/testUpdateSetupData.xml")
//    @ExpectedDatabase(value = "classpath:bookService/testUpdateExpectedData.xml",assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
//    public void testUpdate(){
//        Category twoCategory = new Category();
//        twoCategory.setId(2l);
//        Book book = new Book();
//        book.setId(1l);
//        book.addCategory(twoCategory);
//        book.setOrganization(organization);
//        book.addCategory(category);
//        book.setName("updateBook");
//        book.setCopyNumber("copy one");
//        book.setBookNumber("12345");
//        book.setDeclimalNumber("DCBA");
//        book.setOriginalInventoryNumber("originalInventoryNumber");
//        book.setInventoryNumber("inventoryNumber");
//        book.addDocument(document);
//        bookService.update(book);
//    }
}