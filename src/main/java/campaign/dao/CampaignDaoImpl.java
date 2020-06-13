package campaign.dao;

import campaign.controller.CampaignParams;
import campaign.controller.ChangeStatusParams;
import campaign.exceptions.CategoryNotFoundException;
import campaign.exceptions.ExistingCampaignException;
import campaign.exceptions.NoCampaignsFoundException;
import campaign.exceptions.SellerNotFoundException;
import campaign.global.Constants;
import campaign.model.Campaign;
import campaign.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileReader;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Repository("campaignDao")
public class CampaignDaoImpl implements CampaignDao{

    private static final Logger logger = LoggerFactory.getLogger(CampaignDaoImpl.class);

    private Session campaignSession = buildSessionFactory(Campaign.class).openSession();
    private Session productSession = buildSessionFactory(Product.class).openSession();

    private static SessionFactory buildSessionFactory(Class cl) {
        return new Configuration().configure().addAnnotatedClass(cl).buildSessionFactory();
    }

    @Override
    public void initDB() {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(Constants.PRODUCTS_JSON_PATH));
            JSONArray productsList = (JSONArray) jsonObject.get("products");
            Iterator<JSONObject> iterator = productsList.iterator();
            Transaction transaction = this.productSession.beginTransaction();
            while (iterator.hasNext()) {
                JSONObject productJson = iterator.next();
                Product product = new Product((String) productJson.get("serialNumber"),
                        (String) productJson.get("title"),
                        (String) productJson.get("category"),
                        (int) (long) productJson.get("price"),
                        (String) productJson.get("sellerId"));
                this.productSession.save(product);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("DB initiated successfully with products");
    }

    @Override
    public Campaign createCampaign(CampaignParams params) throws SellerNotFoundException, ExistingCampaignException, CategoryNotFoundException {
        // Check parameters
        checkExistingSellerAndCampaign(params);
        CheckExistingCampaign(params);

        Campaign newCampaign = new Campaign();
        newCampaign.setName(params.getName());
        newCampaign.setBid(params.getBid());
        newCampaign.setCategory(params.getCategory());
        newCampaign.setSellerId(params.getSellerId());
        newCampaign.setStatus(Constants.CampaignStatus.ACTIVE);
        campaignSession.saveOrUpdate(newCampaign);
        logger.info("created campaign: " + newCampaign.getName());

        return newCampaign;
    }

    // Checks if the given seller has any products of the given category
    private void checkExistingSellerAndCampaign(CampaignParams params) throws SellerNotFoundException, CategoryNotFoundException {
        Transaction transaction = this.productSession.beginTransaction();
        String queryString = String.format(
                "SELECT * FROM Product WHERE sellerId='%s'",
                params.getSellerId());
        List<Product> sellerProducts = this.productSession.createSQLQuery(queryString).addEntity(Product.class).list();
        transaction.commit();
        if (sellerProducts.isEmpty()) {
            throw new SellerNotFoundException();
        }
        else if (sellerProducts.stream().noneMatch(product -> product.getCategory().equals(params.getCategory()))) {
            throw new CategoryNotFoundException();
        }
    }

    // Checks if the given seller already has an existing campaign for the given category
    private void CheckExistingCampaign(CampaignParams params) throws ExistingCampaignException {
        Transaction transaction = this.campaignSession.beginTransaction();
        String query = String.format(
                "SELECT * FROM Campaign WHERE sellerId='%s' AND category='%s'",
                params.getSellerId(),
                params.getCategory());
        List<Campaign> sellerCampaigns = this.campaignSession.createSQLQuery(query).addEntity(Campaign.class).list();
        transaction.commit();
        if (!sellerCampaigns.isEmpty()) {
            throw new ExistingCampaignException();
        }
    }

    @Override
    public Product serveAd(String category) throws NoCampaignsFoundException {
        // Get all active campaigns
        Transaction campaignsTransaction = this.campaignSession.beginTransaction();
        String campaignsQuery = String.format("SELECT * FROM Campaign WHERE status='%s'", Constants.CampaignStatus.ACTIVE);
        List<Campaign> allActiveCampaigns = this.campaignSession.createSQLQuery(campaignsQuery).addEntity(Campaign.class).list();
        campaignsTransaction.commit();
        if (allActiveCampaigns.isEmpty()) {
            throw new NoCampaignsFoundException();
        }
        // Get all active campaigns of the requested category
        List<Campaign> activeCategoryCampaigns = allActiveCampaigns.stream().filter(campaign ->
                campaign.getCategory().equals(category)).collect(Collectors.toList());
        // If there are no promoted products of the given category, find a product of any active campaign
        return getProductPromotedByHighestBidSeller(activeCategoryCampaigns.isEmpty() ? allActiveCampaigns : activeCategoryCampaigns);
    }

    // Find a product (most expensive) of the highest bid campaign, from the given list of campaigns
    private Product getProductPromotedByHighestBidSeller(List<Campaign> campaigns) {
        // Find the campaign with the highest bid
        Campaign highestBidCampaign = campaigns.stream().max(Comparator.comparing(Campaign::getBid)).get();
        // Get all products
        Transaction productsTransaction = this.productSession.beginTransaction();
        List<Product> allProducts = this.productSession.createSQLQuery("SELECT * FROM Product").addEntity(Product.class).list();
        productsTransaction.commit();
        // Find all products of the found campaign
        List<Product> productsByHighestBidSeller = allProducts.stream().filter(product ->
                (product.getSellerId().equals(highestBidCampaign.getSellerId()) &&
                        product.getCategory().equals(highestBidCampaign.getCategory()))).collect(Collectors.toList());
        // return the most expensive promoted product
        return productsByHighestBidSeller.stream().max(Comparator.comparing(Product::getPrice)).get();
    }

    @Override
    public List<Campaign> getCampaigns() {
        Transaction campaignsTransaction = this.campaignSession.beginTransaction();
        List<Campaign> campaigns = this.campaignSession.createSQLQuery("SELECT * FROM Campaign").addEntity(Campaign.class).list();
        campaignsTransaction.commit();
        return campaigns;
    }

    @Override
    public Campaign getCampaign(Long id) {
        Transaction campaignsTransaction = this.campaignSession.beginTransaction();
        Campaign campaign = this.campaignSession.get(Campaign.class, id);
        campaignsTransaction.commit();
        return campaign;
    }

    @Override
    public boolean changeStatus(ChangeStatusParams newStatus, Long id) {
        boolean statusUpdated = false;
        Transaction campaignsTransaction = this.campaignSession.beginTransaction();
        Campaign campaign = this.campaignSession.get(Campaign.class, id);
        if (campaign != null) {
            campaign.setStatus(newStatus.getStatus());
            this.campaignSession.save(campaign);
            statusUpdated = true;
        }
        campaignsTransaction.commit();
        return statusUpdated;
    }

    @Override
    public boolean deleteCampaign(Long id) {
        boolean campaignDeleted = false;
        Transaction campaignsTransaction = this.campaignSession.beginTransaction();
        Campaign campaign = this.campaignSession.get(Campaign.class, id);
        if (campaign != null) {
            this.campaignSession.delete(campaign);
            this.campaignSession.flush();
            campaignDeleted = true;
        }
        campaignsTransaction.commit();
    return campaignDeleted;
    }
}
