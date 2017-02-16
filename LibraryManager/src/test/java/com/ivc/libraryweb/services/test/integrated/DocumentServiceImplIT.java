/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivc.libraryweb.services.test.integrated;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;
import com.ivc.libraryweb.entities.Book;
import com.ivc.libraryweb.entities.Category;
import com.ivc.libraryweb.entities.Document;
import com.ivc.libraryweb.entities.Notice;
import com.ivc.libraryweb.entities.Page;
import com.ivc.libraryweb.entities.PageData;
import com.ivc.libraryweb.integration.config.TestConfig;
import com.ivc.libraryweb.repositories.BookRepositoryImpl;
import com.ivc.libraryweb.repositories.DocumentRepositoryImpl;
import com.ivc.libraryweb.repositories.PageRepositoryImpl;
import com.ivc.libraryweb.services.DocumentService;
import com.ivc.libraryweb.services.DocumentServiceImpl;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import static org.assertj.core.api.AssertionsForClassTypes.extractProperty;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import org.dbunit.dataset.DataSetException;
import static org.junit.Assert.assertTrue;
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

/**
 *
 *
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
        classes = {TestConfig.class,
            DocumentRepositoryImpl.class,
            DocumentServiceImpl.class,
            PageRepositoryImpl.class,
            BookRepositoryImpl.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    DbUnitTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
public class DocumentServiceImplIT {

    private static final String[] ALL_PAGE_NAME = new String[]{"newPage", "pageName","pageName2"};

    @Autowired
    DocumentService documentService;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    DataSource dataSource;

    private Document newDocument = new Document();
    private Document  validDocument = new Document();
    private Page page = new Page("newPage");
    private Page removePage = new Page("pageName");
    private Book book = new Book();

    @Before
    public void before() {
        
        validDocument.setId(1l);
        removePage.setId(1l);
        book.setId(1l);
    }

    @Test
    @DatabaseSetup("classpath:documentService/documentServiceTestData.xml")
    public void testPersistAndAddPageInTheDocument() {
        Document document = em.createNamedQuery("Document.findWithDetail", Document.class).setParameter("id", validDocument.getId()).getSingleResult();
        Set<Page> listPage1 = document.getPages();
        documentService.addPageInTheDocument(validDocument, page);
        Set<Page> listPage = em.createNamedQuery("Document.findWithDetail",Document.class).setParameter("id", validDocument.getId()).getSingleResult().getPages();
        assertThat(extractProperty(Category.NAME_PROPERTY).from(listPage))
                .hasSize(ALL_PAGE_NAME.length)
                .containsOnly((Object[]) ALL_PAGE_NAME);
    }

    @Test
    @DatabaseSetup("classpath:documentService/documentServiceTestData.xml")
    @ExpectedDatabase(value = "classpath:documentService/testRemovePageFromDocument.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testRemovePageFromDocument() {
        documentService.removePageFromDocument(validDocument, removePage);
        List<Page> listPage = em.createNamedQuery("Page.findAll", Page.class).getResultList();
        List<PageData> listPageData = em.createNamedQuery("PageData.findAll",PageData.class).getResultList();
    }

    @Test
    @DatabaseSetup("classpath:documentService/documentServiceTestData.xml")
    @ExpectedDatabase(value = "classpath:documentService/testCreateDocument.xml", assertionMode =DatabaseAssertionMode.NON_STRICT_UNORDERED )
    public void testShouldCreateDocument() {
        Page page = new Page("newPage");
        page.setDocument(newDocument);
        page.setState(Page.PageState.ADDED);
        Document document = documentService.createDocument(newDocument);
        documentService.addPageInTheDocument(document, page);
        List<Page> listPage = em.createNamedQuery("Page.findAll", Page.class).getResultList();
    }

    @Test
    @DatabaseSetup("classpath:documentService/documentServiceTestData.xml")
    @ExpectedDatabase("classpath:documentService/testDocumentDelete.xml")
    public void testShouldDeleteDocument() throws DataSetException {
        documentService.deleteDocument(validDocument);
    }
    
    @Test
    @DatabaseSetup("classpath:documentService/testAddDocumentToTheBookDataSetup.xml")
    @ExpectedDatabase(value = "classpath:documentService/testAddDocumentToTheBookExtendData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testShouldAddTheDocumentToTheBook(){         
       newDocument.setListCount(0);
       newDocument.setModification(34);
       newDocument.setIncomeNumber("666");
       newDocument.setOutcomeNumber("666");
       Notice notice = new Notice();
       notice.setContent("notice");
       newDocument.getComment().add(notice);
       Document rezultDocument =  documentService.addDocumentToBook(book, newDocument);
       List<Document> list = em.createNamedQuery("Document.findAll", Document.class).getResultList();
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
       Date d1 = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
       Date d2 = rezultDocument.getComment().get(0).getDate();
       assertTrue(sdf.format(d1).equals(sdf.format(d2)));
    }
    
    
    @Test
    @DatabaseSetup("classpath:documentService/testUpdateDocumentDataBefore.xml")
    @ExpectedDatabase("classpath:documentService/testUpdateDocumentExpectedData.xml")
    public void testUpdateDocument(){
        validDocument.setNotice("newNotice");
        validDocument.setOutcomeNumber("15r");
        validDocument.setIncomeNumber("12");
        validDocument.setModification(34);
        validDocument.setListCount(2);
        documentService.updateDocument(validDocument);
        Document dc = documentService.findDocument(validDocument);
    }
}
