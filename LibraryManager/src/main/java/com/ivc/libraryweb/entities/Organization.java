
package com.ivc.libraryweb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;


/**
 * Класс описывает сущность хранящую информацию об организации к которой относятся документы.
 * @author Vitaliy Denisov
 */
@Entity
@Table(name = "organization")
@NamedQueries({
    @NamedQuery(name = "Organization.findAll",
            query = "SELECT b FROM Organization b"),
    @NamedQuery(name = "Organization.findWithDetail",
            query = "SELECT DISTINCT o FROM Organization o "
            + "LEFT JOIN FETCH o.books b "
            + "WHERE o.id = :id")
})
public class Organization implements Serializable {
    //-------------------Logger---------------------------------------------------

    //-------------------Constants------------------------------------------------
    public static final String ID_PROPERTY = "id";
    public static final String NAME_PROPERTY = "name";
    public static final String ADDRESS_PROPERTY = "address";

    //-------------------Fields---------------------------------------------------
    @JsonProperty(ID_PROPERTY)
    private Long id;

    @JsonIgnore
    private int version;

    @JsonProperty(NAME_PROPERTY)
    private String name;

    @JsonProperty(ADDRESS_PROPERTY)
    private String address;

    @JsonIgnore
    private Set<Book> books = new TreeSet<Book>();

    //-------------------Constructors---------------------------------------------
    /**
     * Create organization object.
     */
    public Organization() {
    }

    /**
     * Create organization object.
     *
     * @param name - name of new organization.
     * @param address - address of new organization.
     */
    public Organization(String name, String address) {
        this.name = name;
        this.address = address;
    }

    /**
     * Create organization object.
     *
     * @param version - object version.
     * @param name - name of new organization.
     * @param address - address of new organization.
     * @param books - books concerning the organization.
     */
    public Organization(int version, String name, String address, Set<Book> books) {
        this.version = version;
        this.name = name;
        this.address = address;
        this.books = books;
    }

    //-------------------Getters and setters--------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     *
     * @return the object version .
     */
    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    /**
     * 
     *
     * @param version the object version .
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * Возвращает название организации.
     *
     * @return the distinguished name.
     */
    @Column(name = "NAME", unique = true)
    public String getName() {
        return name;
    }

    /**
     * 
     *
     * @param name object distinguished name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает адрес организации.
     *
     * @return the organization address.
     */
    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    /**
     * 
     *
     * @param address the organization address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     *
     * @return the books of organization.
     */
    @Column(name = "BOOKS")
    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    public Set<Book> getBooks() {
        return books;
    }

    /**
     * 
     *
     * @param setBooks the books of organization.
     */
    public void setBooks(Set<Book> setBooks) {
        this.books = setBooks;
    }

    //-------------------Methods-------------------------------------------------
    /**
     * Добавляет книгу для организации.
     *
     * @param book - book object.
     */
    public void addBook(Book book) {
        this.books.add(book);
    }

    /**
     * Удаляет книгу из списка книг относящихся к организации.
     *
     * @param book book The book belonging to the organization.
     * @return result of success of removal of the book.
     */
    public boolean removeBook(Book book) {
        return this.books.remove(book);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 29 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 29 * hash + (this.address != null ? this.address.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Organization other = (Organization) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.address == null) ? (other.address != null) : !this.address.equals(other.address)) {
            return false;
        }
        return true;
    }
}
