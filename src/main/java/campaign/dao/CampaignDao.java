package campaign.dao;

import campaign.controller.CampaignParams;
import campaign.controller.ChangeStatusParams;
import campaign.exceptions.CategoryNotFoundException;
import campaign.exceptions.ExistingCampaignException;
import campaign.exceptions.NoCampaignsFoundException;
import campaign.exceptions.SellerNotFoundException;
import campaign.model.Campaign;
import campaign.model.Product;

import java.util.List;

public interface CampaignDao {

    void initDB();

    Campaign createCampaign(CampaignParams params) throws SellerNotFoundException, ExistingCampaignException, CategoryNotFoundException;

    Product serveAd(String category) throws NoCampaignsFoundException;

    List<Campaign> getCampaigns();

    Campaign getCampaign(Long id);

    boolean changeStatus(ChangeStatusParams newStatus, Long id);

    boolean deleteCampaign(Long id);

}
