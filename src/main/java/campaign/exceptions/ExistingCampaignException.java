package campaign.exceptions;

import campaign.global.Constants;

public class ExistingCampaignException extends Exception{
    public ExistingCampaignException() {
        super(Constants.EXISTING_CAMPAIGN_MESSAGE);
    }
}
