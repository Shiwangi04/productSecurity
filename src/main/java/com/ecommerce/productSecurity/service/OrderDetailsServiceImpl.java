package com.ecommerce.productSecurity.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.productSecurity.dao.CartDAO;
import com.ecommerce.productSecurity.dao.OrderDetailsDAO;
import com.ecommerce.productSecurity.dao.ProductDAO;
import com.ecommerce.productSecurity.dao.UserDAO;
import com.ecommerce.productSecurity.entity.Cart;
import com.ecommerce.productSecurity.entity.OrderDetails;
import com.ecommerce.productSecurity.entity.OrderInput;
import com.ecommerce.productSecurity.entity.OrderProductQuantity;
import com.ecommerce.productSecurity.entity.Product;
import com.ecommerce.productSecurity.entity.TransactionDetails;
import com.ecommerce.productSecurity.entity.User;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

	private static final String ORDER_PLACED = "Placed";
	@Autowired
	private OrderDetailsDAO dao;

	@Autowired
	private ProductDAO prodDao;

	@Autowired
	private UserDAO usrDao;
	
	@Autowired
	private CartDAO cartDao;
	
	private static final String KEY = "rzp_test_1XYXyNizmB7mqO";
	private static final String SECRET= "EkPD05tNGDkRIDUorhUtIsDw";
	private static final String CURRENCY= "USD";

	@Override
	public void placeOrder(OrderInput orderInput, boolean isCartCheckout) {
		List<OrderProductQuantity> quantityList = orderInput.getProductList();
		for (OrderProductQuantity o: quantityList) {
			Product prodDetail = prodDao.findById(o.getProductId()).get();
			String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
			User usr = usrDao.findById(currentUser).get();
			OrderDetails od = new OrderDetails(orderInput.getFullName(),
					orderInput.getFullAddress(), orderInput.getContactNo(), orderInput.getAltContactNo(), 
					ORDER_PLACED, prodDetail.getProductDiscountedPrice() * o.getQuantity(), prodDetail, usr, orderInput.getTransactionId());
			
			if (!isCartCheckout) {
				List<Cart> carts = cartDao.findByUser(usr);
				carts.stream().forEach(x -> cartDao.deleteById(x.getCartId()));
			}
			dao.save(od);	
		}

	}

	@Override
	public List<OrderDetails> getOrderDetails() {
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		User usr = usrDao.findById(currentUser).get();
		
		return dao.findByUser(usr);
	}

	@Override
	public void deleteOrder(Integer orderId) {
		dao.deleteById(orderId);
	}

	@Override
	public List<OrderDetails> getAllOrders(String status) {
		List<OrderDetails> orList = new ArrayList<>();
		if (status.equals("All")) {
			dao.findAll().forEach(
					t -> orList.add(t)
				);
		} else {
			dao.findByOrderStatus(status).forEach(
					t -> orList.add(t)
			);
		}
		
		return orList;
	}

	@Override
	public void markOrderStatus(Integer orderId) {
		OrderDetails order = dao.findById(orderId).get();
		if (order != null) {
			order.setOrderStatus("Delivered");
			dao.save(order);
		}
	}

	@Override
	public TransactionDetails createTransaction(Double amount) {
		// amount , currency, key, secretKey
		try {
			JSONObject json = new JSONObject();
			json.put("amount", (amount * 100));
			json.put("currency", CURRENCY);
			RazorpayClient client = new RazorpayClient(KEY, SECRET);
			Order order = client.orders.create(json);
			return prepareTransactionDetails(order);
		} catch (RazorpayException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	private TransactionDetails prepareTransactionDetails(Order order) {
		String orderId = order.get("id");
		Integer amount = order.get("amount");
		String currency = order.get("currency");
		TransactionDetails transaction = new TransactionDetails(orderId,amount, currency, KEY);
		return transaction;
		
	}
}
