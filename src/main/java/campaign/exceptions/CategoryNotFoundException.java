package campaign.exceptions;

import campaign.global.Constants;

public class CategoryNotFoundException extends Exception{
    public CategoryNotFoundException () {
        super(Constants.CATEGORY_NOT_FOUND_MESSAGE);
    }
}
