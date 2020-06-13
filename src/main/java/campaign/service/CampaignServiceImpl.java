package campaign.service;

import campaign.controller.CampaignParams;
import campaign.controller.ChangeStatusParams;
import campaign.dao.CampaignDao;
import campaign.exceptions.CategoryNotFoundException;
import campaign.exceptions.ExistingCampaignException;
import campaign.exceptions.NoCampaignsFoundException;
import campaign.exceptions.SellerNotFoundException;
import campaign.model.Campaign;
import campaign.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service("campaignService")
public class CampaignServiceImpl implements  CampaignService{

    private CampaignDao campaignDao;

    @Autowired
    public CampaignServiceImpl(@Qualifier("campaignDao") CampaignDao campaignDao) {
        this.campaignDao = campaignDao;
    }

    @PostConstruct
    public void initDB() {
        this.campaignDao.initDB();
    }

    @Override
    public Campaign createCampaign(CampaignParams params) throws ExistingCampaignException, CategoryNotFoundException, SellerNotFoundException {
        return this.campaignDao.createCampaign(params);
    }

    @Override
    public Product serveAd(String category) throws NoCampaignsFoundException {
        return this.campaignDao.serveAd(category);
    }

    @Override
    public List<Campaign> getCampaigns() {
        return this.campaignDao.getCampaigns();
    }

    @Override
    public Campaign getCampaign(Long id) {
        return this.campaignDao.getCampaign(id);
    }

    @Override
    public void changeStatus(ChangeStatusParams newStatus, Long id) {
        this.campaignDao.changeStatus(newStatus, id);
    }

    @Override
    public void deleteCampaign(Long id) {
        this.campaignDao.deleteCampaign(id);
    }

}
