package campaign.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class CampaignParams {

    @Getter
    @NotNull(message = "Please provide a category")
    private String category;

    @Getter
    @NotNull(message = "Please provide a bid")
    private Long bid;

    @Getter
    @NotNull(message = "Please provide a name")
    private String name;

    @Getter
    @NotNull(message = "Please provide a sellerId")
    private String sellerId;

    public CampaignParams(@JsonProperty("category") String category,
                   @JsonProperty("bid") Long bid,
                   @JsonProperty("name") String name,
                   @JsonProperty("sellerId") String sellerId) {
        this.category = category;
        this.bid = bid;
        this.name = name;
        this.sellerId = sellerId;
    }

}