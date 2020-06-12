package campaign.dao;

import campaign.controller.CampaignParams;
import campaign.model.Campaign;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;

public interface CampaignDao {

    void initDB();

    Campaign createCampaign(CampaignParams params) throws JsonProcessingException, ParseException;
}
