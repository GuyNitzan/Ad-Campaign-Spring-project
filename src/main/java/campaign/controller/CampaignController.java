package campaign.controller;

import campaign.model.Campaign;
import campaign.model.Product;
import campaign.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
public class CampaignController {

    private CampaignService campaignService;

    @Autowired
    public CampaignController(@Qualifier("campaignService") CampaignService campaignService){
        this.campaignService = campaignService;
    }

    @PostMapping("/create-campaign")
    public @ResponseBody ResponseEntity createCampaign(@RequestBody CampaignParams params) {
        Campaign createdCampaign;
        try {
            createdCampaign = this.campaignService.createCampaign(params);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(createdCampaign)
                .toUri();

        return ResponseEntity.created(uri)
                .body(createdCampaign);
    }

    @GetMapping("/serve-ad")
    public @ResponseBody ResponseEntity serveAdd(@RequestParam("category") String category) {
        Product product;
        try {
            product = this.campaignService.serveAd(category);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(product.getSerialNumber())
                .toUri();

        return ResponseEntity.created(uri)
                .body(product);
    }

    @GetMapping("/campaigns")
    public @ResponseBody ResponseEntity<List<Campaign>> getCampaigns() {
        return ResponseEntity.ok().body(this.campaignService.getCampaigns());
    }

    @GetMapping("/campaigns/{id}")
    public @ResponseBody ResponseEntity<Campaign> getCampaign(@PathVariable("id") Long id) {
        Campaign campaign = this.campaignService.getCampaign(id);
        return campaign != null ? ResponseEntity.ok().body(campaign) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/campaigns/{id}")
    public ResponseEntity changeStatus(@RequestBody ChangeStatusParams newStatus, @PathVariable("id") Long id) {
        boolean statusChanged = this.campaignService.changeStatus(newStatus, id);
        if (statusChanged) {
            return ResponseEntity.ok(String.format("Campaign '%d' status changed to '%s'", id, newStatus.getStatus()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/campaigns/{id}")
        public ResponseEntity deleteCampaign(@PathVariable("id") Long id) {
        boolean campaignDeleted = this.campaignService.deleteCampaign(id);
        if (campaignDeleted) {
            return ResponseEntity.ok(String.format("Campaign '%d' deleted", id));
        }
        return ResponseEntity.notFound().build();
    }

}
