
package com.ivc.libraryweb.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ivc.libraryweb.config.CustomLocalDateTimeSerializer;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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


@Entity
@Table(name = "delivery")
@NamedQueries({
    @NamedQuery(name = "Delivery.clearTable",
            query = "DELETE FROM Delivery"),
    @NamedQuery(name = "Delivery.findAll",
            query = "SELECT d FROM Delivery d"),
    @NamedQuery(name = "Delivery.findWithDetail",
            query = "SELECT DISTINCT d FROM Delivery d "
            + "LEFT JOIN FETCH d.book b "
            + "WHERE d.id = :id")
})
public class Delivery implements Serializable {
    //-------------------Logger---------------------------------------------------

    //-------------------Constants------------------------------------------------
    public static final String ID_PROPERTY = "id";
    public static final String DELIVERY_DATE_PROPERTY = "deliverydate";
    public static final String SURRENDER_DATE_PROPERTY = "surrenderdate";
    public static final String BOOK_PROPERTY = "book";
    public static final String EMPLOYEE_ID_PROPERTY = "employeeid";

    //-------------------Fields---------------------------------------------------
    @JsonProperty(ID_PROPERTY)
    private Long id;

    @JsonIgnore
    private int version;

    @JsonProperty(DELIVERY_DATE_PROPERTY)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date deliveryDate;

    @JsonProperty(SURRENDER_DATE_PROPERTY)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date surrenderDate;

    @JsonProperty(BOOK_PROPERTY)
    private Book book;

    @JsonProperty(EMPLOYEE_ID_PROPERTY)
    private Long employeId;

    //-------------------Constructors---------------------------------------------
    public Delivery() {
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

    @Temporal(TemporalType.DATE)
    @Column(name = "DELIVERY_DATE")
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "SURRENDER_DATE")
    public Date getSurrenderDate() {
        return surrenderDate;
    }

    public void setSurrenderDate(Date surrenderDate) {
        this.surrenderDate = surrenderDate;
    }

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Column(name = "EMPLOYE_ID")
    public Long getEmployeId() {
        return employeId;
    }

    public void setEmployeId(Long employeId) {
        this.employeId = employeId;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    //-------------------Methods--------------------------------------------------
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 79 * hash + (int) (this.employeId ^ (this.employeId >>> 32));
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
        final Delivery other = (Delivery) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if (!Objects.equals(this.employeId, other.employeId)) {
            return false;
        }
        return true;
    }

   

   

}
