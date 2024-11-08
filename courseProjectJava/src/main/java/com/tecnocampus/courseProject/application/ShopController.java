package com.tecnocampus.courseProject.application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tecnocampus.courseProject.application.dtos.OrderDTO;
import com.tecnocampus.courseProject.application.dtos.ProductDTO;
import com.tecnocampus.courseProject.application.dtos.SubscriptionDTO;
import com.tecnocampus.courseProject.application.dtos.UserDTO;

@Service
public class ShopController{

	private ShopDAO shopDAO;

	public ShopController(ShopDAO shopDAO){
		this.shopDAO=shopDAO;
	}

	public List<OrderDTO> getAllOrdersFromUser(String userId) {
		return shopDAO.getAllOrdersFromUser(userId);
	}

	public List<OrderDTO> getAllStateOrdersFromUser(String state,String userId) {
        return shopDAO.getAllStateOrdersFromUser(state,userId);
    }

	public List<SubscriptionDTO> getAllSubscriptionsFromUserOrder(String orderId, String userId){
		return shopDAO.getAllSubscriptionsFromUserOrder(orderId, userId);
	}

	public UserDTO getUser(String userId){
		return shopDAO.getUser(userId);
	}

	public List<ProductDTO> getStock() {
		return shopDAO.getStock();
	}

	public List<ProductDTO> getStockByCategory(String categoryName) {
        return shopDAO.getStockByCategory(categoryName);
    }

	public List<String> getCategoriesList() {
		List<ProductDTO> list = shopDAO.getCategoriesList();
		List<String> result = new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			result.add(list.get(i).getCategory());
		}
		return result;
    }

	public ProductDTO getProduct(String productId) {
		return shopDAO.getProduct(productId);
	}

	public double getProductPrice(String productId) {
		return shopDAO.getProductPrice(productId).getPrice();
	}

	public void updateSubscription(String userId, String subscriptionId, int amount){
		if (amount == 0){
			shopDAO.deleteSubscription(userId, subscriptionId);
		}
		else{
			shopDAO.updateSubscription(userId, subscriptionId, amount);
		}
	}

	public void postSubscription(String userId,String productId, int amount) {
		for(int i=0;i<getSubscriptionsFromOpenOrder(userId).size();i++){
			if(getSubscriptionsFromOpenOrder(userId).get(i).getProduct().getId().equals(productId)) return; //should throw an error
		}
		if(getAllStateOrdersFromUser("Open",userId).size()!=0){
			shopDAO.postSubscription(userId, productId, amount);
		}
	}

	public void postOrder(String userId){
		shopDAO.postOrder(userId);
	}

	public List<SubscriptionDTO> getSubscriptionsFromOpenOrder(String userId){
		return shopDAO.getSubscriptionsFromOpenOrder(userId);
	}

	public UserDTO getOnlyUser(String userId) {
		return shopDAO.getOnlyUser(userId);
	}

	public void updateSubscriptionAdmin(String userId, String subscriptionId, int amount) {
		if (amount == 0){
			shopDAO.deleteSubscription(userId, subscriptionId);
		}
		else{
			shopDAO.updateAdminSubscription(userId, subscriptionId, amount);
		}
	}

	public void deleteSubscriptionAdmin(String userId, String subscriptionId) {
		shopDAO.deleteSubscriptionAdmin(userId, subscriptionId);
	}

    public void deleteSubscription(String userId, String subscriptionId) {
		shopDAO.deleteSubscription(userId, subscriptionId);
    }

	@Scheduled(cron = "0 45 23 * * FRI")
	//@Scheduled(cron = "0/20 * * * * *")
	public void fridayTask(){
		shopDAO.updateSubscriptionState("Closed","Open");
	}
	@Scheduled(cron = "0 0 2 * * MON")
	//@Scheduled(cron = "10/20 * * * * *")
	public void mondayTask(){
		List<String> usersId = shopDAO.getUsersId();
		for(int i=0;i<usersId.size();i++){
			OrderDTO o = shopDAO.postOrder(usersId.get(i));
			List<SubscriptionDTO> subs = shopDAO.getSubscriptionsFromClosedOrder(usersId.get(i));
			List<ProductDTO> products = new ArrayList<ProductDTO>();
			for(int j=0;j<subs.size();j++){

				Calendar actualCal = Calendar.getInstance();
				actualCal.setTime(new java.util.Date()); 
				int actualWeek = actualCal.get(Calendar.WEEK_OF_YEAR);
				System.out.println(actualWeek);

				Calendar productCal = Calendar.getInstance();
				productCal.setTime(subs.get(j).getInitialDate().getTime()); 
				int productWeek = productCal.get(Calendar.WEEK_OF_YEAR);

				if((actualWeek+(52*((actualCal.get(Calendar.YEAR))-(productCal.get(Calendar.YEAR))))-productWeek) % subs.get(j).getProduct().getNumPeriod()==0){
					shopDAO.postSubscription(usersId.get(i), subs.get(j).getProduct().getId(), subs.get(j).getAmount(), subs.get(j).getInitialDate().getTime(), o.getId());
					products.add(subs.get(j).getProduct());
				}
			}
			sendEmails(shopDAO.getOnlyUser(usersId.get(i)).getEmail(),products);
		}
        shopDAO.updateSubscriptionState("Delivered","Closed");
	}
	
	public void sendEmails(String email, List<ProductDTO> products){
       
		String to = email;

        String from = "theopenfoodclub@gmail.com";

        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("theopenfoodclub@gmail.com", "Open_food0");
            }
        });

        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("The Open Food Club");
            message.setText("New orders are generated!!!");
			for(int i=0; i<products.size();i++){
				message.setText(products.get(i).getName()+" ");
			}
            System.out.println("sending...");
            Transport.send(message);
            System.out.println("Sent message successfully....");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
	}

}


