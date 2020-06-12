package campaign.model;

import campaign.global.Constants;
import lombok.Getter;
import lombok.Setter;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Campaign {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Constants.CampainStatus status;

    @Getter
    @Setter
    private Long bid;

    @Id
    @Getter
    @Setter
    private String sellerId;

    public Campaign() {}

    public Campaign(String name, Constants.CampainStatus status, Long bid, String sellerId) {
        this.name = name;
        this.status = status;
        this.bid = bid;
        this.sellerId = sellerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Campaign))
            return false;
        Campaign campaign = (Campaign) o;
        return Objects.equals(this.name, campaign.name) && Objects.equals(this.status, campaign.status)
                && Objects.equals(this.bid, campaign.bid) && Objects.equals(this.sellerId, campaign.sellerId);
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "name=" + this.name +
                ", status='" + this.status +
                ", bid='" + this.bid +
                ", sellerId='" + this.sellerId+ '}';
    }
}
