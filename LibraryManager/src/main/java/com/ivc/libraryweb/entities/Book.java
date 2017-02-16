
package com.ivc.libraryweb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import static org.springframework.util.Assert.notNull;

/**
 *
 * @author VitaliyDenisov
 */
@Entity
@Table(name = "book")
@NamedQueries({
    @NamedQuery(name = "Book.clearTable",
            query = "DELETE FROM Book"),
    @NamedQuery(name = "Book.findAll",
            query = "SELECT b FROM Book b"),
    @NamedQuery(name = "Book.findWithDetail",
            query = "SELECT DISTINCT b FROM Book b "
            + "LEFT JOIN FETCH b.documents d "
            + "LEFT JOIN FETCH b.deliveries l "
            + "WHERE b.id = :id"),
    @NamedQuery(name = "Book.findLastDelivery",
            query = "SELECT d FROM Delivery d WHERE d.book.id = :id AND (d.surrenderDate IS NULL)")
})
public class Book implements Serializable, Comparable {
    //-------------------Logger---------------------------------------------------

    //-------------------Constants------------------------------------------------
    public static final String ID_PROPERTY = "id";
    public static final String NAME_PROPERTY = "name";
    public static final String INVENTORY_NUMBER_PROPERTY = "inventorynumber";
    public static final String DECIMAL_NUMBER_PROPERTY = "decimalnumber";
    public static final String BOOK_NUMBER_PROPERTY = "booknumber";
    public static final String BOOK_PART_PROPERTY = "bookpart";
    public static final String COPY_NUMBER_PROPERTY = "copynumber";
    public static final String CATEGORY_PROPERTY = "category";
    public static final String ORGANIZATION_PROPERTY = "organization";
    public static final String BOOK_TYPE_PROPERTY = "booktype";
    public static final String DOCUMENTS_PROPERTY = "documents";
    public static final String ORIGINAL_INVENTORY_NUMBER= "originalinventorynumber";

    //-------------------Fields---------------------------------------------------
    @JsonProperty(ID_PROPERTY)
    private Long id;

    @JsonIgnore
    private int version;

    @JsonProperty(NAME_PROPERTY)
    private String name;

    @JsonProperty(INVENTORY_NUMBER_PROPERTY)
    private String inventoryNumber;
    
    @JsonProperty(ORIGINAL_INVENTORY_NUMBER)
    private String originalInventoryNumber;

    @JsonProperty(DECIMAL_NUMBER_PROPERTY)
    private String declimalNumber;

    @JsonProperty(BOOK_NUMBER_PROPERTY)
    private String bookNumber;

    @JsonProperty(BOOK_PART_PROPERTY)
    private String bookPart;

    @JsonProperty(COPY_NUMBER_PROPERTY)
    private String copyNumber;

    @JsonProperty(CATEGORY_PROPERTY)
    private Set<Category> categories;

    @JsonProperty(ORGANIZATION_PROPERTY)
    private Organization organization;

    @JsonProperty(DOCUMENTS_PROPERTY)
    private Set<Document> documents = new HashSet<Document>();

    @JsonIgnore
    private Set<Delivery> deliveries = new HashSet<Delivery>();

    //-------------------Constructors---------------------------------------------
    public Book() {

    }

    public Book(String name, String inventoryNumber, String decimalNumber,
            String bookNumber, String bookPart, String copyNumber) {
        this.name = name;
        this.inventoryNumber = inventoryNumber;
        this.declimalNumber = decimalNumber;
        this.bookNumber = bookNumber;
        this.bookPart = bookPart;
        this.copyNumber = copyNumber;
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

    @Column(name = "ORIGINAL_INVENTORY_NUMBER")
    public String getOriginalInventoryNumber() {
        return originalInventoryNumber;
    }

    public void setOriginalInventoryNumber(String originalInventoryNumber) {
        this.originalInventoryNumber = originalInventoryNumber;
    }
    
    

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    /**
     *
     * @return
     */
    @Column(name = "NAME",length = 1000)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "INVENTORY_NUMBER")
    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    @Column(name = "DECLIMAL_NUMBER")
    public String getDeclimalNumber() {
        return declimalNumber;
    }

    public void setDeclimalNumber(String declimalNumber) {
        this.declimalNumber = declimalNumber;
    }

    @Column(name = "BOOK_NUMBER")
    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }

    @Column(name = "BOOK_PART")
    public String getBookPart() {
        return bookPart;
    }

    public void setBookPart(String bookPart) {
        this.bookPart = bookPart;
    }

    @Column(name = "COPY_NUMBER")
    public String getCopyNumber() {
        return copyNumber;
    }

    public void setCopyNumber(String copyNumber) {
        this.copyNumber = copyNumber;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_label_detail", inverseJoinColumns  = @JoinColumn(name = "CATEGORY_ID"),
        joinColumns   = @JoinColumn(name = "BOOK_ID"))
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> category) {
        this.categories = category;
    }

    public void addCategory(Category category) {
        if(categories == null){
            categories = new HashSet<>();
        }
        categories.add(category);
    }

    public void removeCategory(Category category) {
        categories.remove(id);
    }

    @ManyToOne
    @JoinColumn(name = "ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    public Set<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public Set<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(Set<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    //-------------------Methods--------------------------------------------------
    public void addDocument(@NotNull Document document) {
        documents.add(document);
    }

    public void removeDocument(@NotNull Document document) {
        documents.remove(document);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 17 * hash + this.version;
        hash = 17 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (this.version != other.version) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object o) {
        notNull(o);
        Book comparingBook = (Book) o;
        return version - comparingBook.version;
    }

}
