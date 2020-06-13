package campaign.exceptions;

import campaign.global.Constants;

public class SellerNotFoundException extends Exception{
    public SellerNotFoundException () {
        super(Constants.SELLER_NOT_FOUND_MESSAGE);
    }
}
