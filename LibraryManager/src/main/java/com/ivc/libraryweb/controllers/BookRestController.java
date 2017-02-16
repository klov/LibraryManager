/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivc.libraryweb.controllers;

import static com.ivc.libraryweb.controllers.BookRestController.BOOK_PATH;
import com.ivc.libraryweb.entities.Book;
import com.ivc.libraryweb.entities.Delivery;
import com.ivc.libraryweb.entities.Document;
import com.ivc.libraryweb.services.BookService;
import com.ivc.libraryweb.services.BookService.TypeOfFormation;
import com.ivc.libraryweb.services.DocumentService;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author VitaliyDenisov
 */
@RestController
@RequestMapping(path = BOOK_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class BookRestController {

    public static final String BOOK_PATH = "/books";
    public static final String CREATE_DOKUMENT_PATH = "/create_document/{bookId}";
    public static final String ADD_PAGE = "/add_page";
    public static final String GET_FILE_PATH = "/fileFormation";
    public static final String DELIVERY_PATH = "/delivery";
    public static final String ITEM = "/item";

    private DocumentService documentService;
    private BookService bookService;

    @Autowired(required = false)
    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Autowired(required = false)
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Book> findAllBook() {
        return bookService.findAllBook();
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@RequestBody Book book) {
        bookService.deleteBook(book);
    }

    @RequestMapping(method = RequestMethod.GET, path = GET_FILE_PATH)
    public void fileFormation(@RequestParam("bookId") Long bookId,
            @RequestParam("modification") int modification,
            HttpServletResponse response,
            @RequestParam(name = "type",defaultValue = "ACTUAL") String type) throws IOException {
        bookService.fileFormation(bookId, modification, response,TypeOfFormation.find(type));
    }

    @RequestMapping(method = RequestMethod.POST, path = CREATE_DOKUMENT_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Document createDocument(@RequestBody Document document, @PathVariable("bookId") long bookId) {
        Book book = new Book();
        book.setId(bookId);
        return documentService.addDocumentToBook(book, document);
    }

    @RequestMapping(method = RequestMethod.GET, path = DELIVERY_PATH)
    public Delivery findLastDelivery(@RequestParam("id") Long book_id) {
        return bookService.getLastActiveDelivery(book_id);
    }

    @RequestMapping(method = RequestMethod.GET, path = ITEM)
    public Book find(@RequestParam("bookId") Long bookId) {
        Book book = new Book();
        book.setId(bookId);
        return bookService.findBookWithDetail(book);
    }
    
    @RequestMapping(method = RequestMethod.PUT, path = ITEM)
    public Book update(@RequestBody Book book){
        return bookService.update(book);
    }
}
