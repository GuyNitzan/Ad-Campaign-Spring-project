package me.guy;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {

    private @Id @GeneratedValue @Getter @Setter int serialNumber;
    private @Getter @Setter String title;
    private @Getter @Setter String category;
    private @Getter @Setter int price;
    private @Getter @Setter int sellerId;

    Product() {}

    Product(int serialNumber, String title, String category, int price, int sellerId) {
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