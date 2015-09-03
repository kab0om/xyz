package xyz;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by kab00m on 03.09.15.
 */
@Entity
@Table(name = "MyTable")
public class Row implements Serializable {

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
    private String creationDate;

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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Row{" +
                "id=" + id +
                ", creationDate='" + creationDate + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
