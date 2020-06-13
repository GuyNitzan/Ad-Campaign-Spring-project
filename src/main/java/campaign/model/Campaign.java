package campaign.model;

import campaign.global.Constants;
import lombok.Getter;
import lombok.Setter;
import java.util.Objects;
import javax.persistence.*;

@Entity
public class Campaign {

    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Constants.CampaignStatus status;

    @Getter
    @Setter
    private Long bid;

    @Getter
    @Setter
    private String category;

    @Getter
    @Setter
    private String sellerId;

    public Campaign() {}

    public Campaign(String name, Constants.CampaignStatus status, Long bid, String sellerId, String category) {
        this.name = name;
        this.status = status;
        this.bid = bid;
        this.sellerId = sellerId;
        this.category = category;
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
                && Objects.equals(this.category, campaign.category);
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "name=" + this.name +
                ", status='" + this.status +
                ", bid='" + this.bid +
                ", sellerId='" + this.sellerId+
                ", category='" + this.category+'}';
    }
}
