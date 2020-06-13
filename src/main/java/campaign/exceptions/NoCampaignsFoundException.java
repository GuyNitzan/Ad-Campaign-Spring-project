package campaign.exceptions;

import campaign.global.Constants;

public class NoCampaignsFoundException extends Exception{
    public NoCampaignsFoundException () {
        super(Constants.NO_CAMPAIGNS_MESSAGE);
    }
}
