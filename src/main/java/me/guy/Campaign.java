package me.guy;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Campaign {
    private @Id @GeneratedValue @Getter @Setter int name;
    private @Getter @Setter CampainStatusEnum status;
    private @Getter @Setter int bid;
    private @Getter @Setter int sellerId;
    private @Getter @Setter Set<Product> products;

    Campaign() {}

    Campaign(int name, CampainStatusEnum status, int bid, int sellerId, Set<Product> products) {
        this.name = name;
        this.status = status;
        this.bid = bid;
        this.sellerId = sellerId;
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Campaign))
            return false;
        Campaign campaign = (Campaign) o;
        return Objects.equals(this.name, campaign.name) && Objects.equals(this.status, campaign.status)
                && Objects.equals(this.bid, campaign.bid) && Objects.equals(this.sellerId, campaign.sellerId)
                && Objects.equals(this.products, campaign.products);
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "name=" + this.name +
                ", status='" + this.status +
                ", bid='" + this.bid +
                ", sellerId='" + this.sellerId+ '}'; //products
    }
}
