package campaign.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import javax.persistence.*;

@Entity
@Table
public class Product {

    @Id
    @Getter
    @Setter
    private String serialNumber;

    @Column
    @Getter
    @Setter
    private String title;

    @Column
    @Getter
    @Setter
    private String category;

    @Column
    @Getter
    @Setter
    private int price;

    @Column
    @Getter
    @Setter
    private String sellerId;

    public Product(String serialNumber, String title, String category, int price, String sellerId) {
        this.serialNumber = serialNumber;
        this.title = title;
        this.category = category;
        this.price = price;
        this.sellerId = sellerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Product))
            return false;
        Product product = (Product) o;
        return Objects.equals(this.serialNumber, product.serialNumber) && Objects.equals(this.title, product.title)
                && Objects.equals(this.category, product.category) && Objects.equals(this.price, product.price)
                && Objects.equals(this.sellerId, product.sellerId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "serialNumber=" + this.serialNumber +
                ", title='" + this.title +
                ", category='" + this.category +
                ", price='" + this.price +
                ", sellerId='" + this.sellerId+ '}';
    }

}