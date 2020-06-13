package campaign.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class CampaignParams {

    @Getter
    private String category;

    @Getter
    private Long bid;

    @Getter
    private String name;

    @Getter
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