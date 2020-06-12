package campaign.controller;

import campaign.global.Constants;
import campaign.model.Campaign;
import campaign.service.CampaignService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
public class CampaignController {

    private static final Logger logger = LoggerFactory.getLogger(CampaignController.class);
    private CampaignService campaignService;

    @Autowired
    public CampaignController(@Qualifier("campaignService") CampaignService campaignService){
        this.campaignService = campaignService;
    }

    @PostMapping("/create-campaign")
    public @ResponseBody ResponseEntity createCampaign(@RequestBody CampaignParams params) throws JsonProcessingException, ParseException {
        Campaign createdCampaign = this.campaignService.createCampaign(params);
        if (createdCampaign == null) {
            return ResponseEntity.badRequest().body(Constants.SELLER_NOT_FOUND_MESSAGE);
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{sellerId}")
                .buildAndExpand(createdCampaign.getSellerId())
                .toUri();

        return ResponseEntity.created(uri)
                .body(createdCampaign);
    }

    @GetMapping("/serve-add")
    public @ResponseBody ResponseEntity serveAdd(@RequestParam("category") String category) {

        return ResponseEntity.ok().body("OK"+category);
    }

}
