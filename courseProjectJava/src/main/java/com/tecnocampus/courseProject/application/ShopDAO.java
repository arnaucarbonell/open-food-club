package com.tecnocampus.courseProject.application;

import java.util.Date;
import java.util.List;

import com.tecnocampus.courseProject.application.dtos.OrderDTO;
import com.tecnocampus.courseProject.application.dtos.ProductDTO;
import com.tecnocampus.courseProject.application.dtos.SubscriptionDTO;
import com.tecnocampus.courseProject.application.dtos.UserDTO;

public interface ShopDAO {

	public List<String> getUsersId();

    public List<OrderDTO> getAllOrdersFromUser(String userId);

	public List<SubscriptionDTO> getAllSubscriptionsFromUserOrder(String orderId, String userId);

	public UserDTO getUser(String userId);

	public UserDTO getOnlyUser(String userId);

	public List<ProductDTO> getStock();

	public ProductDTO getProduct(String productId);

	public ProductDTO getProductPrice(String productId);

	public void updateSubscription(String userId, String subscriptionId, int amount);

	public void deleteSubscription(String userId, String subscriptionId);

	public void deleteSubscriptionAdmin(String userId, String subscriptionId);

	public void postSubscription(String userId,String productId, int amount);

	public void postSubscription(String userId,String productId, int amount, Date initial_date, String subscription_order);

	public OrderDTO postOrder(String userId);

    public List<ProductDTO> getCategoriesList();

    public List<ProductDTO> getStockByCategory(String categoryName);

	public List<OrderDTO> getAllStateOrdersFromUser(String state, String userId);

	public List<SubscriptionDTO> getSubscriptionsFromOpenOrder(String userId);

	public List<SubscriptionDTO> getSubscriptionsFromClosedOrder(String userId);

	public void updateSubscriptionState(String newState, String previousState);

	public List<ProductDTO> getAllProducts();

	public void updatePriceProduct(String id, double price);

	public void updatePriceSubscription(String id, double price);

	public List<SubscriptionDTO> getAllSubscriptionsFromOpenOrders();

	public void updateAdminSubscription(String userId, String subscriptionId, int amount);
   
}
