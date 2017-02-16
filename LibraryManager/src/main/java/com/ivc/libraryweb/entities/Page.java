
package com.ivc.libraryweb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Version;


/**
 * РљР»Р°СЃСЃ РѕРїРёСЃС‹РІР°РµС‚ РёРЅС„РѕСЂРјР°С†РёСЋ Рѕ СЃС‚СЂР°РЅРёС†Р°С… РєРЅРёРіРё.
 * @author 
 */
@Entity
@Table(name = "page")
@NamedQueries({
    @NamedQuery(name = "Page.clearTable",
            query = "DELETE FROM Page"),
    @NamedQuery(name = "Page.findAll",
            query = "SELECT p FROM Page p"),
    @NamedQuery(name = "Page.findWithDetail",
            query = "SELECT DISTINCT p FROM Page p LEFT JOIN FETCH p.document LEFT JOIN FETCH p.pageData WHERE p.id = :id"
           ),
    @NamedQuery(name = "Page.findPagesForDocument",
            query = "SELECT DISTINCT p FROM Page p "
            + "LEFT JOIN FETCH p.document  "
            + "WHERE p.document = :id")
})
public class Page implements Serializable {

       /**
        * РџРµСЂРµС‡РµСЃР»РµРЅРёРµ СЃРѕСЃС‚РѕСЏРЅРёР№ СЃС‚СЂР°РЅРёС†С‹. Added - СЃС‚СЂР°РЅРёС†Р° РґРѕР±Р°РІР»РµРЅРЅР° РІ РєРЅРёРіСѓ Рё РёСЃРїРѕР»СЊР·СѓРµС‚СЃСЏ РїСЂРё 
        * С„РѕСЂРјРёСЂРѕРІР°РЅРёРё СЃРѕРґРµСЂР¶РёРјРѕРіРѕ РґРѕРєСѓРјРµРЅС‚Р°. Delete - РїРѕРјРµС‡Р°РµС‚ СЃС‚СЂР°РЅРёС†Сѓ РєР°Рє Р°РЅРЅСѓР»РёСЂРѕРІР°РЅРЅСѓСЋ. 
        */
       public static enum PageState {

        ADDED("Added"), DELETE("Delete"), NULL("null");

        private final String name;

        PageState(String in) {
            this.name = in;
        }

        public String getName() {
            return this.name;
        }

        static public PageState find(String name) {
            for (PageState i : values()) {
                if (i.name.equals(name)) {
                    return i;
                }
            }
            return NULL;
        }

    }
    //-------------------Constants------------------------------------------------
    public static final String ID_PROPERTY = "id";
    public static final String NAME_PROPERTY = "name";
    public static final String DOCUMENT_PROPERTY = "document";
    public static final String PAGE_STATE = "pageState";
    //-------------------Fields---------------------------------------------------

    @JsonProperty(ID_PROPERTY)
    private Long id;

    @JsonIgnore
    private int version;

    /**
     * РџРѕР»Рµ СЃРѕРґРµСЂР¶РёС‚ РЅРѕРјРµСЂ СЃС‚СЂР°РЅРёС†С‹.
     */
    @JsonProperty(NAME_PROPERTY)
    private String name;

    @JsonIgnore
    private PageData pageData;

    @JsonProperty(DOCUMENT_PROPERTY)
    private Document document;

    @JsonProperty(PAGE_STATE)
    private PageState state;

    //-------------------Constructors---------------------------------------------
    public Page(String name) {
        this.name = name;
    }

    public Page() {
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

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Р’РѕР·РІСЂР°С‰Р°РµС‚ РїРѕР»Рµ document С…СЂРѕРЅСЏС‰РµРµ РѕР±СЉРµРєС‚ РґРѕРєСѓРјРµРЅС‚Р° РєРѕС‚РѕСЂРѕРјСѓ РїСЂРµРЅР°РґР»РµР¶РёС‚ СЃС‚СЂР°РЅРёС†Р°.
     * @return 
     */
    @ManyToOne
    @JoinColumn(name = "DOCUMENT_ID")
    public Document getDocument() {
        return document;
    }

    public void setDocument(Document DocumentId) {
        this.document = DocumentId;
    }
  
    /**
     * Р’РѕР·РІСЂР°С‰Р°РµС‚ РїРѕР»Рµ pageData, С…СЂРѕРЅСЏС‰РµРµ СЃРѕРґРµСЂР¶РёРјРѕРµ СЃС‚СЂР°РЅРёС†С‹.
     * @return
     */
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="PAGE_DATA_ID")
    public PageData getPageData() {
        return pageData;
    }

    public void setPageData(PageData pageData) {

        this.pageData = pageData;
    }
    
    public void setPageDataByte(byte[] pageData) {
        PageData pd = new PageData();
        pd.setContent(pageData);
        this.pageData = pd;
    }

    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    public PageState getState() {
        return state;
    }

    public void setState(PageState state) {
        this.state = state;
    }
    //-------------------Methods--------------------------------------------------
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 79 * hash + (this.name != null ? this.name.hashCode() : 0);
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
        final Page other = (Page) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
