/*
 �������������-�������������� ����� ���������� ��������
 */
package com.ivc.libraryweb.services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.ivc.libraryweb.entities.Book;
import com.ivc.libraryweb.entities.Category;
import com.ivc.libraryweb.entities.Delivery;
import com.ivc.libraryweb.entities.Document;
import com.ivc.libraryweb.entities.Notice;
import com.ivc.libraryweb.entities.Page;
import com.ivc.libraryweb.repositories.BookRepository;
import com.ivc.libraryweb.repositories.DocumentRepository;
import com.ivc.libraryweb.repositories.PageRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.regex.*;

/**
 * Реализация сервиса для выполнения операцый над книгами.
 *
 * @author VitaliyDenisov
 */
@Service
public class BookServiceImpl implements BookService {

    private final static String FILE_PATH = "documantetion\\";
    private final static String FORMAT = ".pdf";
    public final static String FILE_NAME_HEADER = "Content-Disposition";

    private BookRepository bookRepository;
    private OrganizationService organizationService;
    private DocumentRepository documentRepository;
    private PageRepository pageRepository;

    @Autowired()
    public void setPageRepository(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @Autowired()
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Autowired()
    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Autowired()
    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    /**
     * Создает объект книги в базе данных предварительно производжит установку внешних ключей
     * связей.
     *
     * @param book - сохроняемая в базе книга.
     * @return
     */
    @Transactional
    public Book createBook(Book book) {
        for (Document document : book.getDocuments()) {
            document.setBook(book);
            for (Notice notice : document.getComment()) {
                notice.setDocument(document);
                notice.setDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
            }
        }
        for (Delivery delivery : book.getDeliveries()) {
            delivery.setBook(book);
        }
        for (Category category : book.getCategories()) {
            category.addBook(book);
        }
        organizationService.findWithDetailOrganization(book.getOrganization()).addBook(book);
        return bookRepository.create(book);
    }

    public List<Book> findAllBook() {
        return bookRepository.findAll();
    }

    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    /**
     * Формирует книгу на момент указанной версии и отправляет ее клиенту.
     *
     * @param bookId- id книги для которой происходит формирование .
     * @param modification - № изменения на момент которого формируется документ.
     * @param response - Объект HttpServletResponse в котороый происходит запись готового
     * результата.
     * @throws IOException
     */
    public void fileFormation(Long bookId, int modification, HttpServletResponse response, TypeOfFormation type) throws IOException {
        Book book = new Book();
        book.setId(bookId);
        book = bookRepository.findWithDetail(book);
        response.setContentType("application/pdf");
        OutputStream os = null;
        PdfDocument resultDocument = null;
        try {
            os = response.getOutputStream();
            PdfWriter writer = new PdfWriter(os);
            writer.setSmartMode(true);
            resultDocument = new PdfDocument(writer);
            resultDocument.initializeOutlines();
            response.setHeader(FILE_NAME_HEADER, book.getName() + "_v_" + modification + FORMAT);
            for (Map.Entry<String, Page> page : generateBookSheets(bookId, modification, type)) {
                PdfDocument tempDocuments = new PdfDocument(new PdfReader(new ByteArrayInputStream(
                        pageRepository.findWithDetail(page.getValue())
                        .getPageData()
                        .getContent()))
                );
                tempDocuments.copyPagesTo(1, 1, resultDocument);
                tempDocuments.close();
            }
        } finally {
            if (resultDocument != null) {
                resultDocument.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }

    private List<Entry<String, Page>> generateBookSheets(Long bookId, int modification, TypeOfFormation type) {
        switch (type) {
            case ACTUAL:
                return generateBookSheets(bookId, modification);
            case PREVIW:
                return generatePreviwBookSheets(bookId, modification);
            default:
                throw new IllegalArgumentException("Incorrect type of formation of the book");
        }
    }

    /**
     * Создает коллекцию страниц книги актуальную на момент изменения. Сохраняет результат в
     * LinkedHashMap для сохранения порядка следования.
     *
     * @param bookId - id книги.
     * @param modification - номер изменения.
     * @return
     */
    private List<Entry<String, Page>> generateBookSheets(Long bookId, int modification) {
        Book book = new Book();
        book.setId(bookId);
        book = bookRepository.findWithDetail(book);
        Map<Integer, Document> documents = new HashMap<Integer, Document>();
        for (Document document : book.getDocuments()) {
            documents.put(document.getModification(), document);
        }
        Map<String, Page> pages = new LinkedHashMap<String, Page>();
        for (int i = 0; i <= modification; i++) {
            if (documents.containsKey(i)) {
                Document document;
                document = documentRepository.findWithDetail(documents.get(i));
                for (Page page : document.getPages()) {
                    if (page.getState() == Page.PageState.DELETE) {
                        pages.remove(page.getName());
                    } else if (page.getState() == Page.PageState.ADDED) {
                        if (pages.containsKey(page.getName())) {
                            pages.replace(page.getName(), page);
                        } else {
                            if (!page.getName().equals("")) {
                                pages.put(page.getName(), page);
                            }
                        }
                    }
                }
            }
        }
        return sortList(pages);
    }

    /**
     * Производит преобразование отображения страниц в список станиц. Список отсортирован в порядке
     * следовании страниц в книги.
     *
     * @param mapPages
     * @return
     */
    private List<Entry<String, Page>> sortList(Map<String, Page> mapPages) {
        List<Entry<String, Page>> listPage = new LinkedList<Map.Entry<String, Page>>(mapPages.entrySet());
        final Pattern p = Pattern.compile("\\D");
        Collections.sort(listPage, new Comparator<Map.Entry<String, Page>>() {
            public int compare(Map.Entry<String, Page> o1, Map.Entry<String, Page> o2) {
                Integer o1IntPart = 0, o2IntPart = 0;
                String o1ChartPart = "", o2ChartPart = "";
                Matcher m1 = p.matcher(o1.getKey());
                Matcher m2 = p.matcher(o2.getKey());
                if (m1.find()) {
                    o1IntPart = Integer.valueOf(o1.getKey().substring(0, m1.start()));
                    o1ChartPart = o1.getKey().substring(m1.start(), o1.getKey().length());
                } else {
                    o1IntPart = Integer.valueOf(o1.getKey());
                }
                if (m2.find()) {
                    o2IntPart = Integer.valueOf(o2.getKey().substring(0, m2.start()));
                    o2ChartPart = o2.getKey().substring(m2.start(), o2.getKey().length());
                } else {
                    o2IntPart = Integer.valueOf(o2.getKey());
                }
                if (o1IntPart == o2IntPart) {
                    return o1ChartPart.compareTo(o2ChartPart);
                }
                return o1IntPart - o2IntPart;
            }
        });
        return listPage;
    }

    public Book findBook(Book book) {
        return bookRepository.find(book);
    }

    /**
     * Возвращает книгу с полным содержимым связей.
     *
     * @param book
     * @return
     */
    public Book findBookWithDetail(Book book) {
        return bookRepository.findWithDetail(book);
    }

    public Delivery getLastActiveDelivery(Long book_id) {
        Book book = new Book();
        book.setId(book_id);
        return bookRepository.findLastDelivery(book);
    }

    /**
     * Обновляет состояние объекта книги в базе данных. Из за настроек сериализации, перед вызовом
     * метода, производит установку связей сущностей.
     *
     * @param book
     * @return
     */
    public Book update(Book book) {
        Book oldBook = bookRepository.findWithDetail(book);
        for (Document d : book.getDocuments()) {
            d.setBook(book);
        }
        for (Delivery d : book.getDeliveries()) {
            d.setBook(book);
        }
        for (Document document : book.getDocuments()) {
            document.setBook(book);
            for (Notice notice : document.getComment()) {
                notice.setDocument(document);
                notice.setDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
            }
        }

        return bookRepository.update(book);
    }

    
    /**
     * Альтернативная функция формирование книги. Осталась в процессе изменений тз. Решил оставить заглушкой на будущее.
     * @param bookId
     * @param modification
     * @return 
     */
    private List<Entry<String, Page>> generatePreviwBookSheets(Long bookId, int modification) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
