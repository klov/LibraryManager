/*
 пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ-пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
 */
package com.ivc.libraryweb.services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.ivc.libraryweb.entities.Page;
import com.ivc.libraryweb.repositories.DocumentRepository;
import com.ivc.libraryweb.repositories.PageRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.util.Assert.notNull;
import org.springframework.web.multipart.MultipartFile;
import com.ivc.libraryweb.entities.*;
import com.ivc.libraryweb.repositories.BookRepository;
import static com.ivc.libraryweb.services.BookServiceImpl.FILE_NAME_HEADER;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author VitaliyDenisom
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    //-------------------Logger---------------------------------------------------
    //-------------------Constants------------------------------------------------
    public final static String INVALID_PARAMETRE_DOCUMENT = "The document contains inadmissible value:";
    public final static String INVALID_PARAMETRE = "contains inadmissible value:";
    public final static String INVALID_PAGE_DATA = "Incorrect value of the data of page";
    private final static String FORMAT = ".pdf";
    //-------------------Fields---------------------------------------------------
    private DocumentRepository documentRepository;
    private PageRepository pageRepository;
    private BookRepository bookRepository;

    //-------------------Constructors---------------------------------------------
    public DocumentServiceImpl() {
    }
    //-------------------Getters and setters--------------------------------------

    @Autowired(required = false)
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public DocumentRepository getDocumentRepository() {
        return documentRepository;
    }

    @Autowired(required = false)
    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public PageRepository getPageRepository() {
        return pageRepository;
    }

    @Autowired(required = false)
    public void setPageRepository(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    //-------------------Methods--------------------------------------------------
    public Document createDocument(Document document) {
        notNull(document, INVALID_PARAMETRE_DOCUMENT + document);
        return documentRepository.create(document);
    }

    @Transactional
    public void deleteDocument(Document document) {
        notNull(document, INVALID_PARAMETRE_DOCUMENT + document);
        Document oldDocument = findDocument(document);
        if( oldDocument.getBook()!=null){
            oldDocument.getBook().getDocuments().remove(oldDocument);
        }
        documentRepository.delete(document);

    }

    @Transactional
    public Document addPageInTheDocument(Document document, Page page) {
        document = documentRepository.findWithDetail(document);
        page.setDocument(document);
        document.addPage(page);
        if (page.getId() == null) {
            page = pageRepository.create(page);
        } else {
            page = pageRepository.update(page);
        }
        return document;
    }

    public Document removePageFromDocument(Document document, Page page) {
        document = documentRepository.findWithDetail(document);
        document.removePage(page);
        pageRepository.delete(pageRepository.find(page));
        return document;
    }

    /**
     * Проверяет коректност входных параметров. Импортирует входные данные страницы в байтовый
     * массив.
     *
     * @param namePage
     * @param pdfData
     * @param documentId
     * @return
     * @throws IOException - возникает при ошибке чтения данных страницы.
     * @throws IllegalArgumentException - возбуждаеться если данных в pdfData содержится больше чем
     * одна страница.
     *
     */
    public Document addPageInTheDocument(String namePage, MultipartFile pdfData, Long documentId, Page.PageState status) throws IOException {
        notNull(namePage, INVALID_PARAMETRE);
        notNull(pdfData, INVALID_PARAMETRE);
        notNull(documentId, INVALID_PARAMETRE);
        Page page = new Page(namePage);
        Document document = new Document();
        document.setId(documentId);
        InputStream is = null;
        try {
            is = new ByteArrayInputStream(pdfData.getBytes());
            PdfReader reader = new PdfReader(is);
            PdfDocument pdfDocument = new PdfDocument(reader);
            PageData pd = new PageData();
            pd.setContent(pdfData.getBytes());
            page.setPageData(pd);
            page.setState(status);
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return addPageInTheDocument(document, page);
    }

    public Document findDocument(Document document) {
        return documentRepository.findWithDetail(document);
    }

    public List<Page> uploadPage(Document document) {
        document = documentRepository.findWithDetail(document);
        return new LinkedList<Page>(document.getPages());
    }

    /**
     * Отправляет содержимое страницы в выходной поток response клиенту.
     *
     * @param pageId
     * @param response
     * @throws IOException
     */
    public void pageFormation(Long pageId, HttpServletResponse response) throws IOException {
        Page page = new Page();
        page.setId(pageId);
        page = pageRepository.findWithDetail(page);
        response.setContentType("application/pdf");
        response.setHeader(FILE_NAME_HEADER, page.getName() + FORMAT);
        OutputStream os = null;
        InputStream is = null;
        PdfDocument tempDocuments = null;
        PdfDocument resultDocument = null;
        try {
            os = response.getOutputStream();
            PdfWriter writer = new PdfWriter(os);
            writer.setSmartMode(true);
            resultDocument = new PdfDocument(writer);
            resultDocument.initializeOutlines();
            is = new ByteArrayInputStream(page.getPageData().getContent());
            tempDocuments = new PdfDocument(new PdfReader(is));
            tempDocuments.copyPagesTo(1, 1, resultDocument);
        } finally {
            if (is != null) {
                is.close();
            }
            if (tempDocuments != null) {
                tempDocuments.close();
            }
            if (resultDocument != null) {
                resultDocument.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }

    public Document updateDocument(Document document) {
        Document oldDocument = documentRepository.findWithDetail(document);
        document.setPages(oldDocument.getPages());
        document.setBook(oldDocument.getBook());
        for (Notice notice : document.getComment()) {
            notice.setDocument(document);
        }
        return documentRepository.update(document);
    }

    public void deletePage(Page page) {
        pageRepository.delete(page);
    }

    public Page updatePage(Page page) {
        Page oldPage = pageRepository.find(page);
        oldPage.setName(page.getName());
        return pageRepository.update(oldPage);
    }

    /**
     * Добавляет к документу страницу с аннулированным состоянием. Ее статус учитываться при
     * формировании документа.
     *
     * @param name - имя новой страницы.
     * @param data - содержание страницы.
     * @param documentId - id документа к которому будет добавлена страница.
     * @param status - статус документа.
     * @return
     */
    public Document addRepealedPage(String name, MultipartFile data, Long documentId, Page.PageState status) {
        Page page = new Page(name);
        page.setState(status);
        Document document = new Document();
        document.setId(documentId);
        page.setDocument(null);
        return addPageInTheDocument(document, page);
    }

    /**
     * Функция предназначена для сохранения страниц дакумента в базу данных. Файл передается в
     * переменную data. Функция по средствам библиотеки itext разделяет файл на отдельные страницы,
     * помещает байтовое содержимое страниц в объекты класса Page и записывает их в базу данных. Все
     * происходит в рамках одной транзакции.
     *
     * @param data
     * @param documentId
     * @throws IOException
     */
    @Transactional
    public void createPagesFromFile(InputStream data, Long documentId) throws IOException {
        notNull(data, INVALID_PARAMETRE);
        notNull(documentId, INVALID_PARAMETRE);
        Document document = new Document();
        document.setId(documentId);
        PdfReader reader = new PdfReader(data);
        ByteArrayOutputStream os = null;
        PdfDocument pdfDocument = new PdfDocument(reader);
        try {
            os = new ByteArrayOutputStream();
            for (int i = 1; i < pdfDocument.getNumberOfPages() + 1; i++) {
                Page page = new Page();
                PdfWriter writer = new PdfWriter(os);
                writer.setSmartMode(true);
                PdfDocument nPdfDocument = new PdfDocument(writer);
                nPdfDocument.initializeOutlines();
                pdfDocument.copyPagesTo(i, i, nPdfDocument);
                nPdfDocument.close();
                page.setPageDataByte(os.toByteArray());
                page.setState(Page.PageState.ADDED);
                addPageInTheDocument(document, page);
                nPdfDocument.close();
                os.reset();
            }
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * Функция добавляет новое изменение к книге.
     *
     * @param book
     * @param document - новое изменение.
     * @return сохраненный документ.
     */
    public Document addDocumentToBook(Book book, Document document) {
        document.setBook(bookRepository.findWithDetail(book));
        for (Notice notice : document.getComment()) {
            notice.setDocument(document);
            notice.setDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        }
        return documentRepository.create(document);
    }

}
