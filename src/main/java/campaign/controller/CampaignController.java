package campaign.controller;

import campaign.model.Campaign;
import campaign.model.Product;
import campaign.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class CampaignController {

    private CampaignService campaignService;

    @Autowired
    public CampaignController(@Qualifier("campaignService") CampaignService campaignService){
        this.campaignService = campaignService;
    }

    /*
    Create a new campaign for a seller's products of some category
    Body parameter has to be of type 'CampaignParams'
    */
    @PostMapping("/create-campaign")
    public @ResponseBody ResponseEntity createCampaign(@Valid @RequestBody CampaignParams params) {
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

    /*
    Return a promoted product of the given category, promoted in the highest bid campaign
     */
    @GetMapping("/serve-ad")
    public @ResponseBody ResponseEntity serveAd(@RequestParam("category") String category) {
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
    public ResponseEntity changeStatus(@RequestBody UpdateCampaignParams params, @PathVariable("id") Long id) {
        boolean updatedSuccessfully = this.campaignService.updateCampaign(params, id);
        if (updatedSuccessfully) {
            return ResponseEntity.ok(String.format("Campaign '%d' updated successfully", id));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/campaigns/{id}")
        public ResponseEntity deleteCampaign(@PathVariable("id") Long id) {
        boolean campaignDeleted = this.campaignService.deleteCampaign(id);
        if (campaignDeleted) {
            return ResponseEntity.ok(String.format("Campaign '%d' deleted successfully", id));
        }
        return ResponseEntity.notFound().build();
    }

}
