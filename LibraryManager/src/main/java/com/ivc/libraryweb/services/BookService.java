
package com.ivc.libraryweb.services;

import com.ivc.libraryweb.entities.Book;
import com.ivc.libraryweb.entities.Delivery;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author VitaliyDenisov
 */
public interface BookService {

    public static enum TypeOfFormation {

        ACTUAL("ACTUAL"),PREVIW("PREVIW"), NULL(null);

        private final String name;

        TypeOfFormation(String in) {
            this.name = in;
        }

        public String getName() {
            return name;
        }

        static public TypeOfFormation find(String name) {
            for (TypeOfFormation i : values()) {
                if (i.name.equals(name)) {
                    return i;
                }
            }
            return NULL;
        }
    }

    /**
     * Создает новый экземпляр книги.
     *
     * @param book - the created book
     * @return
     */
    Book createBook(Book book);

    /**
     * Возвращает список всех книг которые есть в базе данных.
     *
     * @return
     */
    List<Book> findAllBook();

    /**
     * Удаляет книгу из базы данных.
     *
     * @param book
     */
    void deleteBook(Book book);

    /**
     * ��������� ������ ������ ����� � ��������� �� �� �����. ���������� ����� ������������ �����.
     *
     * @param bookId
     * @return
     */
    void fileFormation(Long bookId, int modification, HttpServletResponse response,TypeOfFormation type) throws IOException;

    Book findBook(Book book);

    Book findBookWithDetail(Book book);

    public Delivery getLastActiveDelivery(Long book_id);

    public Book update(Book book);

}
