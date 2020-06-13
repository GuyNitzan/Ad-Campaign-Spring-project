package campaign.controller;

import campaign.global.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class ChangeStatusParams {

    @Getter
    @Enumerated(EnumType.STRING)
    private Constants.CampaignStatus status;

    ChangeStatusParams(@JsonProperty("status") Constants.CampaignStatus status) {
        this.status = status;
    }

}