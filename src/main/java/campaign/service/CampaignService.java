package campaign.service;

import campaign.controller.CampaignParams;
import campaign.controller.UpdateCampaignParams;
import campaign.exceptions.CategoryNotFoundException;
import campaign.exceptions.ExistingCampaignException;
import campaign.exceptions.NoCampaignsFoundException;
import campaign.exceptions.SellerNotFoundException;
import campaign.model.Campaign;
import campaign.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;

import java.util.List;

public interface CampaignService {
    Campaign createCampaign(CampaignParams params) throws JsonProcessingException, ParseException, ExistingCampaignException, CategoryNotFoundException, SellerNotFoundException;

    Product serveAd(String category) throws NoCampaignsFoundException;

    List<Campaign> getCampaigns();

    Campaign getCampaign(Long id);

    boolean updateCampaign(UpdateCampaignParams params, Long id);

    boolean deleteCampaign(Long id);
}
