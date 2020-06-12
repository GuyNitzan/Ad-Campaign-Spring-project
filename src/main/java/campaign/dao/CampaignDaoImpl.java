package campaign.dao;

import campaign.controller.CampaignParams;
import campaign.global.Constants;
import campaign.model.Campaign;
import campaign.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.FileReader;
import java.util.Iterator;

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
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/main/resources/products.json"));
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
        logger.info("DB initiated successfully");
    }

    @Override
    public Campaign createCampaign(CampaignParams params) {
        if (isSellerUnknown(params) || isCategoryPromotedBySeller(params)) {
            return null;
        }
        Campaign newCampaign = new Campaign();
        newCampaign.setName(params.getName());
        newCampaign.setBid(params.getBid());
        newCampaign.setSellerId(params.getSellerId());
        newCampaign.setStatus(Constants.CampainStatus.ACTIVE);
        campaignSession.saveOrUpdate(newCampaign);
        logger.info("saved campaign: " + campaignSession.get(Campaign.class, newCampaign.getSellerId()));

        return newCampaign;
    }

    private boolean isCategoryPromotedBySeller(CampaignParams params) {
        Transaction transaction = this.campaignSession.beginTransaction();
        String query = String.format("SELECT * FROM Campaign WHERE sellerId='%s'", params.getSellerId());
        NativeQuery qu = this.campaignSession.createSQLQuery(query).addEntity(Product.class);
        transaction.commit();
        return qu.list().isEmpty();
    }

    private boolean isSellerUnknown(CampaignParams params) {
        Transaction transaction = this.productSession.beginTransaction();
        String query = String.format("SELECT * FROM Product WHERE sellerId='%s'", params.getSellerId());
        NativeQuery qu = this.productSession.createSQLQuery(query).addEntity(Product.class);
        transaction.commit();
        return qu.list().isEmpty();
    }

}
