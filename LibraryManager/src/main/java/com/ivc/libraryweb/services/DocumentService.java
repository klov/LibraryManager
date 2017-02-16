/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivc.libraryweb.services;

import com.ivc.libraryweb.entities.Book;
import com.ivc.libraryweb.entities.Document;
import com.ivc.libraryweb.entities.Page;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author VitaliyDenisov
 */
public interface DocumentService {

    Document createDocument(Document document);

    void deleteDocument(Document document);

    /**
     * Добавляет страницу к изменению.
     *
     * @param document - изменение к которому производится добавление.
     * @param page - добавляемая страница.
     * @return
     */
    Document addPageInTheDocument(Document document, Page page);

    /**
     * Добавляет страницу к изменению. Создает страницу из входного файла с именем namePage и 
     * дабавляет к изменению с id documentId. Устанавливает статус страницы status.  
     *
     * @param namePage - Имя создаваемой страницы.
     * @param pdfData - источник с содержимым страницы.
     * @param documentId - Id изменения в который будет добавлена страница.
     * @return
     * @throws IOException
     */
    Document addPageInTheDocument(String namePage, MultipartFile pdfData, Long documentId,Page.PageState status) throws IOException;

    /**
     * Удаляет страницу из изменения.
     *
     * @param document -  Изменение из которого будет удалена страница.
     * @param page - Объект страницы которую нужно удалить из книги.
     * @return
     */
    Document removePageFromDocument(Document document, Page page);
    
    Document findDocument(Document document);
    
    List<Page> uploadPage(Document document);

    void pageFormation(Long pageId, HttpServletResponse response)  throws IOException;

    Document updateDocument(Document document);

    void deletePage(Page page);

    Page updatePage(Page page);

    Document addRepealedPage(String name, MultipartFile data, Long documentId, Page.PageState status);

    void createPagesFromFile(InputStream data, Long documentId) throws IOException;

    Document addDocumentToBook(Book book, Document document);
}
