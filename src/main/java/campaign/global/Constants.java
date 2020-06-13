package campaign.global;

public class Constants {

    public static final String SELLER_NOT_FOUND_MESSAGE = "No products by the given seller ID were found";
    public static final String CATEGORY_NOT_FOUND_MESSAGE = "No products of the given category by the given seller ID  were found";
    public static final String EXISTING_CAMPAIGN_MESSAGE = "The requested campaign was already created for the given seller";
    public static final String NO_CAMPAIGNS_MESSAGE = "No campaigns found";

    public static final String PRODUCTS_JSON_PATH = "src/main/resources/products.json";
    public enum CampaignStatus {ACTIVE, INACTIVE}

}
