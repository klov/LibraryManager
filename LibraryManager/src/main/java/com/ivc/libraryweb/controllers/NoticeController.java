/*
 Информационно-вычислительный центр космодрома Байконур
 */
package com.ivc.libraryweb.controllers;

import static com.ivc.libraryweb.controllers.NoticeController.NOTICE_PATH;
import com.ivc.libraryweb.entities.Document;
import com.ivc.libraryweb.entities.Notice;
import com.ivc.libraryweb.services.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Администратор
 */
@RestController
@RequestMapping(path = NOTICE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class NoticeController {
  //-------------------Logger---------------------------------------------------

    //-------------------Constants------------------------------------------------
    public static final String NOTICE_PATH = "/notice";
    //-------------------Fields---------------------------------------------------
    private NoticeService noticeService;
  //-------------------Constructors---------------------------------------------

    //-------------------Getters and setters--------------------------------------
    @Autowired(required = false)
    public void setNoticeService(NoticeService noticeService) {
        this.noticeService = noticeService;
    }
  //-------------------Methods--------------------------------------------------

    @RequestMapping(method = RequestMethod.POST,path = "/{documentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Notice create(@PathVariable("documentId") long docuentId,@RequestBody Notice notice){
        Document document = new Document();
        document.setId(docuentId);
        return noticeService.createNotice(notice, document);
    }
    
    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@RequestBody Notice notice) {
        noticeService.deleteNotice(notice);
    }
}
