package com.tecnocampus.courseProject.application;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.tecnocampus.courseProject.application.dtos.SubscriptionDTO;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductController {

    ObjectMapper om = new ObjectMapper();

    private ShopDAO shopDAO;

	public ProductController(ShopDAO shopDAO){
		this.shopDAO=shopDAO;
	}

    @Scheduled(cron = "* * 0 * * *")
    public void updateProductPrices() throws Exception {
        System.out.println("Update done");
		String url= "http://localhost:80/api/v1/products/price";
		String jsonString = new RestTemplate().getForObject(url, String.class);

		JsonNode rootNode=om.readTree(jsonString);

		ArrayNode arrayNode = (ArrayNode) rootNode;

		Iterator<JsonNode> itr = arrayNode.elements();

		DecimalFormat df = new DecimalFormat("#.##");
        
		while(itr.hasNext()) {
			JsonNode temp=itr.next();
            shopDAO.updatePriceProduct(temp.path("id").textValue(),Double.parseDouble(df.format(temp.path("price").doubleValue()).replace(",", ".")));
		}

		List<SubscriptionDTO> subsFromOpenOrder = shopDAO.getAllSubscriptionsFromOpenOrders();
			for(int i=0; i<subsFromOpenOrder.size(); i++){
				shopDAO.updatePriceSubscription(subsFromOpenOrder.get(i).getId(), subsFromOpenOrder.get(i).getProduct().getPrice());
			}
	}
    
}
