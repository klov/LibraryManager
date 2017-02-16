package com.ivc.libraryweb.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ivc.libraryweb.config.CustomLocalDateTimeSerializer;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author �������������
 */
@Entity
@Table(name = "document")
@NamedQueries({
    @NamedQuery(name = "Document.findAll",
            query = "SELECT b FROM Document b"),
    @NamedQuery(name = "Document.findWithDetail",
            query = "SELECT DISTINCT d FROM Document d LEFT JOIN FETCH d.pages p LEFT JOIN FETCH d.book b WHERE d.id = :id")
})
public class Document implements Serializable {

    //-------------------Constants------------------------------------------------

    public static final String ID_PROPERTY = "id";
    public static final String MODIFICATION_PROPERTY = "modification";
    public static final String INCOME_NUMBER_PROPERTY = "incomenumber";
    public static final String OUTCOME_NUMBER_PROPERTY = "outcomenumber";
    public static final String INCOME_DATE_PROPERTY = "incomedate";
    public static final String OUTCOME_DATE_PROPERTY = "outcomedate";
    public static final String BOOK_PROPERTY = "book";
    public static final String NOTICE_PROPERTY = "notice";
    public static final String LIST_COUNT_PROPERTY = "listcount";
    public static final String DOCUMENT_STATE_PROPERTY = "state";
    public static final String COMMENT_PROPERTY = "comments";
    public static final String NOTIC_DATE = "noticedate";

    //-------------------Fields---------------------------------------------------
    @JsonProperty(ID_PROPERTY)
    private Long id;

    @JsonProperty(MODIFICATION_PROPERTY)
    private int modification;

    @JsonProperty(NOTICE_PROPERTY)
    private String notice;

    @JsonIgnore
    private int version;

    @JsonProperty(LIST_COUNT_PROPERTY)
    private int listCount;

    @JsonProperty(INCOME_NUMBER_PROPERTY)
    private String incomeNumber;

    @JsonProperty(OUTCOME_NUMBER_PROPERTY)
    private String outcomeNumber;

    @JsonProperty(INCOME_DATE_PROPERTY)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date incomeDate;

    @JsonProperty(NOTIC_DATE)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date noticeDate;

    @JsonProperty(OUTCOME_DATE_PROPERTY)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date outcomeDate;

    @JsonIgnore
    private Book book;

    @JsonIgnore
    private Set<Page> pages = new HashSet<Page>();

    @JsonProperty(COMMENT_PROPERTY)
    private List<Notice> comment = new LinkedList<Notice>();

    //-------------------Constructors---------------------------------------------
    public Document(int modification, String notice, int version, String incomeNumber, String outcomeNumber, Date incomeDate, Date outcomeDate, int listcount) {
        this.modification = modification;
        this.notice = notice;
        this.version = version;
        this.incomeNumber = incomeNumber;
        this.outcomeNumber = outcomeNumber;
        this.incomeDate = incomeDate;
        this.outcomeDate = outcomeDate;
        this.listCount = listcount;
    }

    public Document() {
    }
    //-------------------Getters and setters--------------------------------------

    @Column(name = "LIST_COUNT")
    public int getListCount() {
        return listCount;
    }

    public void setListCount(int listCount) {
        this.listCount = listCount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "NOTICE_DATE")
    public Date getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(Date noticeDate) {
        this.noticeDate = noticeDate;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "INCOME_NUMBER")
    public String getIncomeNumber() {
        return incomeNumber;
    }

    public void setIncomeNumber(String incomeNumber) {
        this.incomeNumber = incomeNumber;
    }

    @Column(name = "OUTCOME_NUMBER")
    public String getOutcomeNumber() {
        return outcomeNumber;
    }

    public void setOutcomeNumber(String outcomeNumber) {
        this.outcomeNumber = outcomeNumber;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "INCOME_DATE")
    public Date getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(Date incomeDate) {
        this.incomeDate = incomeDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "OUTCOME_DATE")
    public Date getOutcomeDate() {
        return outcomeDate;
    }

    public void setOutcomeDate(Date outcomeDate) {
        this.outcomeDate = outcomeDate;
    }

    @Column(name = "MODIFICATION")
    public int getModification() {
        return modification;
    }

    public void setModification(int modification) {
        this.modification = modification;
    }

    @Column(name = "NOTICE")
    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    @OneToMany(mappedBy = "document", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    public Set<Page> getPages() {
        return pages;
    }

    public void setPages(Set<Page> pages) {
        this.pages = pages;
    }

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    public List<Notice> getComment() {
        return comment;
    }

    public void setComment(List<Notice> comment) {
        this.comment = comment;
    }

//-------------------Methods--------------------------------------------------
    public void addPage(Page page) {
        this.pages.add(page);
    }

    public boolean removePage(Page page) {
        return this.pages.remove(page);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 97 * hash + this.modification;
        hash = 97 * hash + this.version;
        hash = 97 * hash + (this.incomeNumber != null ? this.incomeNumber.hashCode() : 0);
        hash = 97 * hash + (this.outcomeNumber != null ? this.outcomeNumber.hashCode() : 0);
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
        final Document other = (Document) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.modification != other.modification) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if ((this.incomeNumber == null) ? (other.incomeNumber != null) : !this.incomeNumber.equals(other.incomeNumber)) {
            return false;
        }
        if ((this.outcomeNumber == null) ? (other.outcomeNumber != null) : !this.outcomeNumber.equals(other.outcomeNumber)) {
            return false;
        }
        return true;
    }

}
