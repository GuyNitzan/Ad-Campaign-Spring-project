package campaign.service;

import campaign.controller.CampaignController;
import campaign.controller.CampaignParams;
import campaign.dao.CampaignDao;
import campaign.model.Campaign;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service("campaignService")
public class CampaignServiceImpl implements  CampaignService{

    private static final Logger logger = LoggerFactory.getLogger(CampaignServiceImpl.class);
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
    public Campaign createCampaign(CampaignParams params) throws JsonProcessingException, ParseException {
        return this.campaignDao.createCampaign(params);
    }

}
