
package com.ivc.libraryweb.controllers;

import static com.ivc.libraryweb.controllers.DocumentRestController.DOCUMENT_PATH;
import com.ivc.libraryweb.entities.Document;
import com.ivc.libraryweb.entities.Page;
import static com.ivc.libraryweb.entities.Page.PAGE_STATE;
import com.ivc.libraryweb.services.BookService;
import com.ivc.libraryweb.services.DocumentService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author VitaliyDenisov
 */
@RestController
@RequestMapping(path = DOCUMENT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentRestController {
  //-------------------Logger---------------------------------------------------

    //-------------------Constants------------------------------------------------
    public static final String DOCUMENT_PATH = "/document";
    public static final String UPLOAD_PAGE = "/uploadPage";
    public static final String PAGE_PATH = "/page";
    public static final String GROUP_PAGE_PATH = "/groupPage";
    //-------------------Fields---------------------------------------------------
    private DocumentService documentService;
    private BookService bookService;
  //-------------------Constructors---------------------------------------------
    //-------------------Getters and setters--------------------------------------

    //-------------------Methods--------------------------------------------------
    @Autowired(required = false)
    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Autowired(required = false)
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Document findDocument(@RequestParam("documentId") Long documentId) {
        Document document = new Document();
        document.setId(documentId);
        return documentService.findDocument(document);
    }

    @RequestMapping(method = RequestMethod.GET, path = UPLOAD_PAGE)
    public List<Page> uploadPage(@RequestParam("documentId") Long documentId) {
        Document document = new Document();
        document.setId(documentId);      
        return documentService.uploadPage(document);
    }

    @RequestMapping(method = RequestMethod.GET, path = PAGE_PATH)
    public void loadPage(@RequestParam("id") Long pageId, HttpServletResponse response) throws IOException {
        documentService.pageFormation(pageId, response);
    }

    @RequestMapping(method = RequestMethod.PUT, path = PAGE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Page updatePage(@RequestBody Page page) {
        return documentService.updatePage(page);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Document updateDocument(@RequestBody Document document) {
        return documentService.updateDocument(document);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = PAGE_PATH)
    public void createPage(@RequestParam("fileData") MultipartFile data,
            @RequestParam("name") String name, @RequestParam("documentId") Long documentId, @RequestParam(PAGE_STATE) String status)
            throws IOException {
        if(Page.PageState.find(status) == Page.PageState.ADDED){
            documentService.addPageInTheDocument(name, data, documentId, Page.PageState.find(status));
        } else if(Page.PageState.find(status) == Page.PageState.DELETE){
            documentService.addRepealedPage(name, data, documentId, Page.PageState.find(status));
        }
    }
    
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = GROUP_PAGE_PATH)
    public void createPagesFromFile(@RequestParam("fileData") MultipartFile data,@RequestParam("documentId") Long documentId) throws IOException{
        try(InputStream is = new ByteArrayInputStream(data.getBytes())){
        documentService.createPagesFromFile(is,documentId);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = PAGE_PATH)
    public void deletePage(@RequestBody Page page) {
        documentService.deletePage(page);
    }
    
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteDocument(@RequestBody Document document){
        documentService.deleteDocument(document);  
    }
  }
