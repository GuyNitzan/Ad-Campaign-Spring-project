package campaign.controller;

import campaign.global.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class UpdateCampaignParams {

    @Getter
    @Enumerated(EnumType.STRING)
    private Constants.CampaignStatus status;

    @Getter
    private Long bid;

    UpdateCampaignParams(@JsonProperty("status") Constants.CampaignStatus status,
                         @JsonProperty("bid") Long bid) {
        this.status = status;
        this.bid = bid;
    }

}