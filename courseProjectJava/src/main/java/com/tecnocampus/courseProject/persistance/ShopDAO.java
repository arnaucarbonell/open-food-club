package com.tecnocampus.courseProject.persistance;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.tecnocampus.courseProject.application.dtos.OrderDTO;
import com.tecnocampus.courseProject.application.dtos.ProductDTO;
import com.tecnocampus.courseProject.application.dtos.SubscriptionDTO;
import com.tecnocampus.courseProject.application.dtos.UserDTO;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.simpleflatmapper.jdbc.spring.RowMapperImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository 
public class ShopDAO implements com.tecnocampus.courseProject.application.ShopDAO{

    private JdbcTemplate jdbcTemplate;

	ResultSetExtractorImpl<UserDTO> usersRowMapper =
			JdbcTemplateMapperFactory
			.newInstance()
			.addKeys("id")
			.newResultSetExtractor(UserDTO.class);

	RowMapperImpl<UserDTO> userRowMapper =
			JdbcTemplateMapperFactory
			.newInstance()
			.addKeys("id")
			.newRowMapper(UserDTO.class);

	ResultSetExtractorImpl<OrderDTO> ordersRowMapper =
			JdbcTemplateMapperFactory
			.newInstance()
			.addKeys("id","orders_id")
			.newResultSetExtractor(OrderDTO.class);

	RowMapperImpl<OrderDTO> orderRowMapper =
			JdbcTemplateMapperFactory
			.newInstance()
			.addKeys("id","orders_id")
			.newRowMapper(OrderDTO.class);

    ResultSetExtractorImpl<ProductDTO> productsRowMapper =
			JdbcTemplateMapperFactory
			.newInstance()
			.addKeys("id","orders_subscriptions_product_id")
			.newResultSetExtractor(ProductDTO.class);

	RowMapperImpl<ProductDTO> productRowMapper =
			JdbcTemplateMapperFactory
			.newInstance()
			.addKeys("id","orders_subscriptions_product_id")
			.newRowMapper(ProductDTO.class);

	ResultSetExtractorImpl<SubscriptionDTO> subscriptionsRowMapper =
			JdbcTemplateMapperFactory
			.newInstance()
			.addKeys("id","orders_subscriptions_id")
			.newResultSetExtractor(SubscriptionDTO.class);

	RowMapperImpl<SubscriptionDTO> subscriptionRowMapper =
			JdbcTemplateMapperFactory
			.newInstance()
			.addKeys("id","orders_subscriptions_id")
			.newRowMapper(SubscriptionDTO.class);

	public ShopDAO(JdbcTemplate jdbcTemplate) throws Exception {
		this.jdbcTemplate=jdbcTemplate;
	}

	@Override
	public List<String> getUsersId(){
		final String queryUsersId = "SELECT id FROM user";
		return jdbcTemplate.queryForList(queryUsersId,String.class);
	}

    @Override
    public List<OrderDTO> getAllOrdersFromUser(String userId) {
    	final String queryOrders = "select o.id, o.end_date as date, o.actual_state as state, " + 
    			"s.id as subscriptions_id, s.amount as subscriptions_amount, s.price as subscriptions_price, s.initial_date as subscriptions_initialDate, "+
    			"p.id as subscriptions_product_id, p.name as subscriptions_product_name, p.price as subscriptions_product_price,"+
    			"p.provider as subscriptions_product_provider, p.measure_unit as subscriptions_product_unidadDeMedida,p.vat_type as subscriptions_product_iva,"+
    			"p.category as subscriptions_product_category, p.num_of_periods as subscription_product_num_period, p.period as subscriptions_product_periodicityType,p.image as subscriptions_product_img "+
    			"from orders o "+
    			"left join subscriptions s on o.id=s.subscription_order "+
    			"left join product p on s.product=p.id where o.user= ?";
    	
		return jdbcTemplate.query(queryOrders, ordersRowMapper,userId);
    }

	@Override
	public List<OrderDTO> getAllStateOrdersFromUser(String state, String userId) {
		final String queryOrders = "select o.id, o.end_date as date, o.actual_state as state, " + 
    			"s.id as subscriptions_id, s.amount as subscriptions_amount, s.price as subscriptions_price, s.initial_date as subscriptions_initialDate, "+
    			"p.id as subscriptions_product_id, p.name as subscriptions_product_name, p.price as subscriptions_product_price,"+
    			"p.provider as subscriptions_product_provider, p.measure_unit as subscriptions_product_unidadDeMedida,p.vat_type as subscriptions_product_iva,"+
    			"p.category as subscriptions_product_category, p.num_of_periods as subscription_product_num_period, p.period as subscriptions_product_periodicityType,p.image as subscriptions_product_img "+
    			"from orders o "+
    			"left join subscriptions s on o.id=s.subscription_order "+
    			"left join product p on s.product=p.id where o.user= ? and o.actual_state=?";
    	
		return jdbcTemplate.query(queryOrders, ordersRowMapper,userId,state);
	}

    @Override
    public List<SubscriptionDTO> getAllSubscriptionsFromUserOrder(String orderId, String userId) {
    	final String queryOrders = "select s.id, s.amount, s.price, s.initial_date as initialDate, "+
    			"p.id as product_id, p.name as product_name, p.price as product_price,"+
    			"p.provider as product_provider, p.measure_unit as product_unidadDeMedida,p.vat_type as product_iva,"+
    			"p.category as product_category, p.num_of_periods as product_num_period, p.period as product_periodicityType, p.image as product_img "+
    			"from subscriptions s "+
    			"left join product p on s.product=p.id where s.subscription_order= ? and (select user from orders where id=?)= ?";
    	
		return jdbcTemplate.query(queryOrders, subscriptionsRowMapper,orderId,orderId,userId);
    }

	@Override
	public List<SubscriptionDTO> getSubscriptionsFromOpenOrder(String userId) {
    	final String queryOrders = "select s.id, s.amount, s.price, s.initial_date as initialDate, "+
    			"p.id as product_id, p.name as product_name, p.price as product_price,"+
    			"p.provider as product_provider, p.measure_unit as product_unidadDeMedida,p.vat_type as product_iva,"+
    			"p.category as product_category, p.num_of_periods as product_num_period, p.period as product_periodicityType, p.image as product_img "+
    			"from subscriptions s "+
    			"left join product p on s.product=p.id where user=? and (select id from orders where (actual_state='Closed' or actual_state='Open') and user=?)=s.subscription_order";
				
		return jdbcTemplate.query(queryOrders, subscriptionsRowMapper,userId,userId);
    }


    @Override
    public UserDTO getUser(String userId) {
    	final String queryUser = "select u.id, u.name, u.secondname as secondName, u.email, o.id as orders_id, o.end_date as orders_date, o.actual_state as orders_state, " + 
    			"s.id as orders_subscriptions_id, s.amount as orders_subscriptions_amount, s.price as orders_subscriptions_price, s.initial_date as orders_subscriptions_initialDate, "+
    			"p.id as orders_subscriptions_product_id, p.name as orders_subscriptions_product_name, p.price as orders_subscriptions_product_price,"+
    			"p.provider as orders_subscriptions_product_provider, p.measure_unit as orders_subscriptions_product_unidadDeMedida,p.vat_type as orders_subscriptions_product_iva,"+
    			"p.category as orders_subscriptions_product_category, p.num_of_periods as orders_subscriptions_product_num_period, p.period as orders_subscriptions_product_periodicityType, p.image as orders_subscriptions_product_img "+
    			"from user u "+
    			"left join orders o on u.id=o.user "+
    			"left join subscriptions s on o.id=s.subscription_order "+
    			"left join product p on s.product=p.id where u.id = ?";
    	
		return jdbcTemplate.queryForObject(queryUser, userRowMapper,userId);
    }

    @Override
    public List<ProductDTO> getStock() {
        final String queryStock = "select id, name, price, provider, measure_unit as unidadDeMedida,vat_type as iva,category,period as periodicityType, image as img from product";
		return jdbcTemplate.query(queryStock, productsRowMapper);
    }

	@Override
	public List<ProductDTO> getStockByCategory(String categoryName) {
		final String queryStock = "select id, name, price, provider, measure_unit as unidadDeMedida,vat_type as iva,category,period as periodicityType, image as img from product where category=?";
		return jdbcTemplate.query(queryStock, productsRowMapper,categoryName);
	}

	@Override
	public List<ProductDTO> getCategoriesList() {
		final String queryStock = "select DISTINCT(category) from product";
		return jdbcTemplate.query(queryStock, productsRowMapper);
	}

    @Override
    public ProductDTO getProduct(String productId) {
    	final String queryProduct = "select id, name, price, provider, measure_unit as unidadDeMedida,vat_type as iva,category,period as periodicityType, image as img from product where id=?";
    	return jdbcTemplate.queryForObject(queryProduct, productRowMapper,productId);
    }

	@Override
    public List<ProductDTO> getAllProducts() {
    	final String queryProduct = "select id, name, price, provider, measure_unit as unidadDeMedida,vat_type as iva,category,period as periodicityType, image as img from product";
    	return jdbcTemplate.query(queryProduct, productsRowMapper);
    }

    @Override
    public ProductDTO getProductPrice(String productId) {
    	final String queryProduct = "select id, name, price, provider, measure_unit as unidadDeMedida,vat_type as iva,category,period as periodicityType from product where id=?";
    	return jdbcTemplate.queryForObject(queryProduct, productRowMapper,productId);
    }

    @Override
    public void updateSubscription(String userId, String subscriptionId, int amount) {
		final String updateSubscription = "UPDATE subscriptions SET amount=? where id=? and (select id from orders where (actual_state='Open') and user=?)= subscription_order";
		jdbcTemplate.update(updateSubscription,amount,subscriptionId,userId);
    }

	
    @Override
    public void deleteSubscription(String userId, String subscriptionId) {
		final String deleteSubscription = "DELETE FROM subscriptions where id=? and (select id from orders where (actual_state='Open') and user=?)= subscription_order";
		jdbcTemplate.update(deleteSubscription,subscriptionId,userId);
    }

    @Override
    public void postSubscription(String userId, String productId, int amount) {
    	final String allocateSubscription = "INSERT INTO subscriptions (id, amount, price, initial_date, subscription_order, product,user) VALUES (?, ?, ?, ?, ?, ?, ?)";
		SubscriptionDTO s=new SubscriptionDTO();
		jdbcTemplate.update(allocateSubscription, s.getId(), amount, getProduct(productId).getPrice(), s.getInitialDate().getTime(),getAllStateOrdersFromUser("Open",userId).get(0).getId(),productId,userId);
    }

    @Override
    public OrderDTO postOrder(String userId) {
    	final String postOrders = "INSERT INTO orders (id, end_date, actual_state, user) VALUES (?, ?, ?,?)";
		OrderDTO o=new OrderDTO();
		jdbcTemplate.update(postOrders, o.getId(), o.getDate().getTime(), "Open" ,userId);
		return o;  
    }

	@Override
	public List<SubscriptionDTO> getSubscriptionsFromClosedOrder(String userId) {
    	final String queryOrders = "select s.id, s.amount, s.price, s.initial_date as initialDate, "+
    			"p.id as product_id, p.name as product_name, p.price as product_price,"+
    			"p.provider as product_provider, p.measure_unit as product_unidadDeMedida,p.vat_type as product_iva,"+
    			"p.category as product_category, p.num_of_periods as product_num_period, p.period as product_periodicityType, p.image as product_img "+
    			"from subscriptions s "+
    			"left join product p on s.product=p.id where s.user= ? and (select id from orders where (actual_state='Closed') and user=?)=s.subscription_order";
		return jdbcTemplate.query(queryOrders, subscriptionsRowMapper,userId,userId);
    }

	@Override
	public void updateSubscriptionState(String newState, String previousState) {
		final String updateOrder = "UPDATE orders SET actual_state=? where actual_state=?";
		jdbcTemplate.update(updateOrder, newState, previousState);
	}

	@Override
	public void postSubscription(String userId, String productId, int amount, Date initial_date,String subscription_order) {
		final String allocateSubscription = "INSERT INTO subscriptions (id, amount, price, initial_date, subscription_order, product, user) VALUES (?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(allocateSubscription,UUID.randomUUID().toString(), amount, getProduct(productId).getPrice(), initial_date, subscription_order, productId, userId); 
	}

	@Override
	public void updatePriceProduct(String id, double price) {
		final String updateProduct = "UPDATE product SET price=? where id=?";
		jdbcTemplate.update(updateProduct, price ,id);
	}

	@Override
	public void updatePriceSubscription(String id, double price) {
		final String updateSubscriptions = "UPDATE subscriptions SET price = ? where id = ?";
		jdbcTemplate.update(updateSubscriptions, price, id);
	}

	@Override
	public UserDTO getOnlyUser(String userId) {
		final String queryUser = "select u.id, u.name, u.secondname as secondName, u.email from user u where id=?";
		return jdbcTemplate.queryForObject(queryUser, userRowMapper,userId);
	}

	@Override
    public List<SubscriptionDTO> getAllSubscriptionsFromOpenOrders() {
    	final String queryOrders = "select s.id, s.amount, s.price, s.initial_date as initialDate, "+
    			"p.id as product_id, p.name as product_name, p.price as product_price,"+
    			"p.provider as product_provider, p.measure_unit as product_unidadDeMedida,p.vat_type as product_iva,"+
    			"p.category as product_category, p.num_of_periods as product_num_period, p.period as product_periodicityType, p.image as product_img "+
    			"from subscriptions s "+
    			"left join product p on s.product=p.id where (select actual_state from orders where id=s.subscription_order)= 'Open'";
    	
		return jdbcTemplate.query(queryOrders, subscriptionsRowMapper);
    }

	@Override
	public void updateAdminSubscription(String userId, String subscriptionId, int amount) {
		final String updateSubscription = "UPDATE subscriptions SET amount=? where id=? and (select id from orders where (actual_state='Open' or actual_state='Closed') and user=?)= subscription_order";
		jdbcTemplate.update(updateSubscription,amount,subscriptionId,userId);
	}

	@Override
	public void deleteSubscriptionAdmin(String userId, String subscriptionId) {
		final String deleteSubscription = "DELETE FROM subscriptions where id=? and (select id from orders where (actual_state='Open' or actual_state='Closed') and user=?)= subscription_order";
		jdbcTemplate.update(deleteSubscription,subscriptionId,userId);
	}

}