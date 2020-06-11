package me.guy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Iterator;
@SpringBootApplication
public class MainAppClass {

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        Session session = buildSessionFactory(Product.class).openSession();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/main/resources/products.json"));
            JSONArray productsList = (JSONArray) jsonObject.get("products");
            Iterator<JSONObject> iterator = productsList.iterator();
            while (iterator.hasNext()) {
                JSONObject productJson = iterator.next();
                Product product = new Product((String) productJson.get("serialNumber"),
                        (String) productJson.get("title"),
                        (String) productJson.get("category"),
                        (int) (long) productJson.get("price"),
                        (String) productJson.get("sellerId"));
                session.save(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SpringApplication.run(MainAppClass.class, args);
    }

    private static SessionFactory buildSessionFactory(Class cl) {
        return new Configuration().configure().addAnnotatedClass(cl).buildSessionFactory();
    }
}