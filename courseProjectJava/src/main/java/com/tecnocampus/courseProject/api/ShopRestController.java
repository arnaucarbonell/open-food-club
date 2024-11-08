package com.tecnocampus.courseProject.api;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecnocampus.courseProject.application.ShopController;
import com.tecnocampus.courseProject.application.dtos.OrderDTO;
import com.tecnocampus.courseProject.application.dtos.ProductDTO;
import com.tecnocampus.courseProject.application.dtos.SubscriptionDTO;
import com.tecnocampus.courseProject.application.dtos.UserDTO;


@RestController
@CrossOrigin(origins = "*")
public class ShopRestController {

	ShopController shopController;

	public ShopRestController(ShopController shopController) {
		this.shopController = shopController;
	}

	@GetMapping("user/me")
	public UserDTO getMyProfile(Principal principal) {
    	return shopController.getOnlyUser(principal.getName());
	}

	@GetMapping("/orders/{userId}")
	public List<OrderDTO> getAllOrdersFromUser(@PathVariable String userId){
		return shopController.getAllOrdersFromUser(userId);
	}

	@GetMapping("/orders/{state}/{userId}")
	public List<OrderDTO> getAllStateOrdersFromUser(@PathVariable String state,@PathVariable String userId){
		return shopController.getAllStateOrdersFromUser(state,userId);
	}

	@GetMapping("/subscriptions/{orderId}/{userId}")
	public List<SubscriptionDTO> getAllSubscriptionsFromUserOrder(@PathVariable String orderId,@PathVariable String userId) throws Exception{
		return shopController.getAllSubscriptionsFromUserOrder(orderId,userId);
	}

	@GetMapping("/subscriptions/{userId}")
	public List<SubscriptionDTO> getSubscriptionsFromOpenOrder(@PathVariable String userId){
		return shopController.getSubscriptionsFromOpenOrder(userId);
	}

	@GetMapping("/users/{userId}")
	public UserDTO getUser(@PathVariable String userId) throws Exception{
		return shopController.getUser(userId);
	}
	@GetMapping("/stock")
	public List<ProductDTO> getStock(){
		return shopController.getStock();
	}
	@GetMapping("/stock/categories/{categoryName}")
    public List<ProductDTO> getStockByCategory(@PathVariable String categoryName){
        return shopController.getStockByCategory(categoryName);
    }
	@GetMapping("/stock/categories")
	public List<String> getCategoriesList(){
		return shopController.getCategoriesList();
	}

	@GetMapping("/stock/{productId}")
	public ProductDTO getProduct(@PathVariable String productId){
		return shopController.getProduct(productId);
	}

	@GetMapping("/stock/{productId}/price")
	public double getProductPrice(@PathVariable String productId){
		return shopController.getProductPrice(productId);
	}

	@PutMapping("/subscription/{userId}/{subscriptionId}/{amount}")
	public void putSubscription(@PathVariable String userId,@PathVariable String subscriptionId,@PathVariable int amount) throws Exception {
		shopController.updateSubscription(userId,subscriptionId,amount);
	}

	@PutMapping("/subscriptionAdmin/{userId}/{subscriptionId}/{amount}")
	public void putSubscriptionAdmin(@PathVariable String userId,@PathVariable String subscriptionId,@PathVariable int amount) throws Exception {
		shopController.updateSubscriptionAdmin(userId,subscriptionId,amount);
	}

	@DeleteMapping("/subscription/{userId}/{subscriptionId}")
	public void deleteSubscription(@PathVariable String userId,@PathVariable String subscriptionId) throws Exception {
		shopController.deleteSubscription(userId,subscriptionId);
	}

	@DeleteMapping("/subscriptionAdmin/{userId}/{subscriptionId}")
	public void deleteSubscriptionAdmin(@PathVariable String userId,@PathVariable String subscriptionId) throws Exception {
		shopController.deleteSubscriptionAdmin(userId,subscriptionId);
	}

	@PostMapping("/subscription/{userId}/{productId}/{amount}")
	public void postSubscription(@PathVariable String userId,@PathVariable String productId,@PathVariable int amount) throws Exception {
		shopController.postSubscription(userId,productId,amount);
	}

	@PostMapping("/orders/{userId}")
	public void postOrder(@PathVariable String userId) throws Exception {
		shopController.postOrder(userId);
	}

}
