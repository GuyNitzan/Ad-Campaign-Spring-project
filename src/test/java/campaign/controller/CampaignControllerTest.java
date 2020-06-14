package campaign.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import campaign.AbstractTest;
import campaign.global.Constants;
import campaign.model.Campaign;
import campaign.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class CampaignControllerTest extends AbstractTest {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void serveAdTest() throws Exception {
        //Create a new Campaign
        CampaignParams params = new CampaignParams("Sport", 60L, "Campaign_1", "seller_1");
        this.createCampaign(params);
        //Create another Campaign
        params = new CampaignParams("Sport", 100L, "Campaign_2", "seller_2");
        this.createCampaign(params);

        // Call serve-ad
        MockHttpServletResponse serveAdResponse = this.serveAd("Sport");
        Product product = super.mapFromJson(serveAdResponse.getContentAsString(), Product.class);
        assertEquals("Sport", product.getCategory());
        assertEquals("seller_2", product.getSellerId());
    }

    @Test
    public void getCampaignTest() throws Exception{
        //Create a new Campaign
        CampaignParams params = new CampaignParams("Sport", 40L, "Campaign_1", "seller_1");
        this.createCampaign(params);
        //Call getCampaigns
        MockHttpServletResponse getCampaignsResponse = this.getCampaigns();
        assertEquals(200, getCampaignsResponse.getStatus());
        Campaign[] campaigns = super.mapFromJson(getCampaignsResponse.getContentAsString(), Campaign[].class);
        assertTrue(campaigns.length == 1);
        assertEquals("Campaign_1", campaigns[0].getName());
        assertEquals((Long) 40L, campaigns[0].getBid());
        assertEquals("Sport", campaigns[0].getCategory());
        assertEquals("seller_1", campaigns[0].getSellerId());

        //Create another Campaign
        params = new CampaignParams("Dogs", 6L, "Campaign_2", "seller_2");
        this.createCampaign(params);
        //Call getCampaigns
        getCampaignsResponse = this.getCampaigns();
        assertEquals(200, getCampaignsResponse.getStatus());
        campaigns = super.mapFromJson(getCampaignsResponse.getContentAsString(), Campaign[].class);
        assertTrue(campaigns.length == 2);
        assertEquals("Campaign_2", campaigns[1].getName());
        assertEquals((Long) 6L, campaigns[1].getBid());
        assertEquals("Dogs", campaigns[1].getCategory());
        assertEquals("seller_2", campaigns[1].getSellerId());
    }

    @Test
    public void createCampaignTest() throws Exception {
        //Create a new Campaign
        CampaignParams params = new CampaignParams("Sport", 60L, "Campaign_1", "seller_1");
        MockHttpServletResponse response = this.createCampaign(params);
        assertEquals(201, response.getStatus());
        String expectedReturnString = "{\"id\":1,\"name\":\"Campaign_1\",\"status\":\"ACTIVE\",\"bid\":60,\"category\":\"Sport\",\"sellerId\":\"seller_1\"}";
        assertEquals(expectedReturnString, response.getContentAsString());
    }

    @Test
    public void updateCampaignTest() throws Exception {
        //Create a new Campaign
        CampaignParams params = new CampaignParams("Sport", 60L, "Campaign_1", "seller_1");
        this.createCampaign(params);
        //get id of new campaign
        MockHttpServletResponse getCampaignsResponse = this.getCampaigns();
        Campaign[] campaigns = super.mapFromJson(getCampaignsResponse.getContentAsString(), Campaign[].class);
        Long id = campaigns[0].getId();
        //Change status to 'INACTIVE' and bid to 40
        MockHttpServletResponse response = this.updateCampaign(id, new UpdateCampaignParams(Constants.CampaignStatus.INACTIVE, 40L));
        assertEquals(200, response.getStatus());
        //get campaign again for validation
        getCampaignsResponse = this.getCampaigns();
        campaigns = super.mapFromJson(getCampaignsResponse.getContentAsString(), Campaign[].class);
        assertEquals((Long) 40L, campaigns[0].getBid());
        assertEquals(Constants.CampaignStatus.INACTIVE, campaigns[0].getStatus());
    }

    @Test
    public void deleteCampaignTest() throws Exception {
        //Create a new Campaign
        CampaignParams params = new CampaignParams("Sport", 60L, "Campaign_1", "seller_1");
        this.createCampaign(params);
        //get id of new campaign
        MockHttpServletResponse getCampaignsResponse = this.getCampaigns();
        Campaign[] campaigns = super.mapFromJson(getCampaignsResponse.getContentAsString(), Campaign[].class);
        Long id = campaigns[0].getId();
        assertTrue(id instanceof Long);
        MockHttpServletResponse response = this.deleteCampaign(id);
        assertEquals(200, response.getStatus());
        //get campaign again for validation
        getCampaignsResponse = this.getCampaigns();
        campaigns = super.mapFromJson(getCampaignsResponse.getContentAsString(), Campaign[].class);
        assertTrue(campaigns.length == 0);
    }

    // getCampaigns HTTP Get request
    public MockHttpServletResponse getCampaigns() throws Exception {
        String uri = "/campaigns";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        return mvcResult.getResponse();
    }

    // getCampaign HTTP GET request
    public MockHttpServletResponse getCampaign(Long id) throws Exception {
        String uri = String.format("/campaigns/%d", id);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        return mvcResult.getResponse();
    }

    // serveAd HTTP GET request
    public MockHttpServletResponse serveAd(String category) throws Exception {
        String uri = String.format("/serve-ad?category=", category);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        return mvcResult.getResponse();
    }

    // createCampaign HTTP POST request
    public MockHttpServletResponse createCampaign(CampaignParams campaignParams) throws Exception {
        String uri = "/create-campaign";
        String inputJson = super.mapToJson(campaignParams);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        return mvcResult.getResponse();
    }

    // updateCampaign HTTP PATCH request
    public MockHttpServletResponse updateCampaign(Long id, UpdateCampaignParams params) throws Exception {
        String uri = String.format("/campaigns/%d", id);
        String inputJson = super.mapToJson(params);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.patch(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        return mvcResult.getResponse();
    }

    // deleteCampaign HTTP DELETE request
    public MockHttpServletResponse deleteCampaign(Long id) throws Exception {
        String uri = String.format("/campaigns/%d", id);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        return mvcResult.getResponse();
    }

    // deleteCampaign HTTP DELETE request
    @After
    public void clearDB () throws Exception {
        String campaignsUri = "/campaigns";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(campaignsUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Campaign[] campaigns = super.mapFromJson(response, Campaign[].class);
        for (Campaign campaign : campaigns) {
            String uri = String.format("/campaigns/%d", campaign.getId());
            mvc.perform(MockMvcRequestBuilders.delete(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        }
    }
}