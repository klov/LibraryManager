/*
 Информационно-вычислительный центр космодрома Байконур
 */
package com.ivc.libraryweb.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ivc.libraryweb.config.CustomLocalDateTimeSerializer;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *Класс содержит информацию о замечаниях к документам. Имеет отношение с документом один ко многим.
 * Хранит в себе дату добавления замечания и его содержимое.
 * @author Vitaliy Denisov
 */
@Entity
@Table(name = "notice")
@NamedQueries({
    @NamedQuery(name = "Notice.findAll",
            query = "SELECT n FROM Notice n")})
public class Notice implements Serializable {
  //-------------------Logger---------------------------------------------------

    //-------------------Constants------------------------------------------------
    public static final String ID_PROPERTY = "id";
    public static final String CONTENT_PROPERTY = "content";
    public static final String CRIATION_DATA_PROPERTY = "date";
    public static final String DOCUMENT_PROPERTY = "document";
    //-------------------Fields---------------------------------------------------
    @JsonProperty(ID_PROPERTY)
    private Long id;
    
    @JsonProperty(CONTENT_PROPERTY)
    private String content;
    
    @JsonProperty(CRIATION_DATA_PROPERTY)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date date;
    
    @JsonIgnore
    private int version;
    
    @JsonIgnore
    private Document document;

    //-------------------Constructors---------------------------------------------
    public Notice() {
    }

    //-------------------Getters and setters--------------------------------------
    //-------------------Methods--------------------------------------------------
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

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "CONTENT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "CRIATION_DATE")
    @Temporal(TemporalType.DATE)
    public Date getDate() {
        return date;
    }

    public void setDate(Date criationDate) {
        this.date = criationDate;
    }

    @ManyToOne
    @JoinColumn(name = "DOCUMENT_ID")
    public Document getDocument() {
        return document;
    }

    public void setDocument(Document DocumentId) {
        this.document = DocumentId;
    }

}
