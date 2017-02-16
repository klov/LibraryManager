/*
 Информационно-вычислительный центр космодрома Байконур
 */
package com.ivc.libraryweb.controllers;

import static com.ivc.libraryweb.controllers.DeliveryRestController.DELIVERY;
import com.ivc.libraryweb.entities.Book;
import com.ivc.libraryweb.entities.Delivery;
import com.ivc.libraryweb.repositories.DeliveryRepository;
import com.ivc.libraryweb.services.BookService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Администратор
 */
@RestController
@RequestMapping(path = DELIVERY, produces = MediaType.APPLICATION_JSON_VALUE)
public class DeliveryRestController {
  //-------------------Logger---------------------------------------------------

    //-------------------Constants------------------------------------------------
    public static final String DELIVERY = "/delivery";
    public static final String ITEM = "/item";
    //-------------------Fields---------------------------------------------------
    private DeliveryRepository deliveryRepository;
    private BookService bookService;
    //-------------------Constructors---------------------------------------------

    public DeliveryRestController() {
    }

    //-------------------Getters and setters--------------------------------------
    public DeliveryRepository getDeliveryRepository() {
        return deliveryRepository;
    }

    @Autowired(required = false)
    public void setDeliveryRepository(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public BookService getBookService() {
        return bookService;
    }

    @Autowired(required = false)
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
    
    
    //-------------------Methods--------------------------------------------------

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Delivery createDelivery(@RequestBody Delivery delivery) {
        return deliveryRepository.create(delivery);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Delivery closeDelivery(@RequestBody Delivery delivery) {
        return deliveryRepository.update(delivery);
    }

    @RequestMapping(method = RequestMethod.GET, path = ITEM)
    public Set<Delivery> find(@RequestParam("bookId") Long bookId) {
        Book book = new Book();
        book.setId(bookId);
        return bookService.findBookWithDetail(book).getDeliveries();
    }
}
