package xyz;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by kab00m on 03.09.15.
 */
@Entity
@Table(name = "MyTable")
public class Row implements Serializable {

    @Column(length = 128)
    private String filter;
    @Id
    @Column(length = 50)
    private BigInteger id;
    @Column(length = 255)
    private String title;
    @Column(length = 500)
    private String description;
    @Column(length = 255)
    private String link;
    @Column(length = 128)
    private String price;
    @Column(length = 128)
    private String creationDateString;
    @Column(length = 255)
    private String categoryLocation;
    private Date creationDate;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreationDateString() {
        return creationDateString;
    }

    public void setCreationDateString(String creationDateString) {
        this.creationDateString = creationDateString;
    }

    public String getCategoryLocation() {
        return categoryLocation;
    }

    public void setCategoryLocation(String categoryLocation) {
        this.categoryLocation = categoryLocation;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Row{" +
                "id=" + id +
                "\n filter=" + filter +
                ", creationDateString='" + creationDateString + '\'' +
                ", price='" + price + '\'' +
                ", categoryLocation='" + categoryLocation + '\'' +
                "\n, title='" + title + '\'' +
                "\n, description='" + description + '\'' +
                "\n, link='" + link + '\'' +
                '}';
    }
}
