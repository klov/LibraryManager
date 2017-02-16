/*
 Информационно-вычислительный центр космодрома Байконур
 */
package com.ivc.libraryweb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Класс предназначен для хранения содержимого страниц документа.
 *
 * @author Vitaliy Denisov
 */
@Entity
@Table(name = "pageData")
@NamedQueries({
    @NamedQuery(name = "PageData.findAll",
            query = "SELECT p FROM PageData p")})
public class PageData implements Serializable {

    public PageData() {
    }
  //-------------------Logger---------------------------------------------------

  //-------------------Constants------------------------------------------------
  //-------------------Fields---------------------------------------------------
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private Page page;
    @JsonIgnore
    private int version;
    @JsonIgnore
    private byte[] content;
  //-------------------Constructors---------------------------------------------

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

    @Column(name = "CONTENT")
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @OneToOne(mappedBy = "pageData")
    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    
}
