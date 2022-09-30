package vttp2022.assessment.csf.orderbackend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;
import vttp2022.assessment.csf.orderbackend.repositories.PizzaRepository;

@Service
public class OrderService {

	@Autowired
	private PricingService priceSvc;

	@Autowired
	private PizzaRepository pizzaRepo;

	// POST /api/order
	// Create a new order by inserting into orders table in pizzafactory database
	// IMPORTANT: Do not change the method's signature
	public void createOrder(Order order) {
		pizzaRepo.createOrder(order);
	}

	// GET /api/order/<email>/all
	// Get a list of orders for email from orders table in pizzafactory database
	// IMPORTANT: Do not change the method's signature
	public List<OrderSummary> getOrdersByEmail(String email) {
		// Use priceSvc to calculate the total cost of an order
		List<Order> orders = pizzaRepo.getOrdersByEmail(email);
		List<OrderSummary> orderSummary = new ArrayList<>();
		for(int i = 0; i < orders.size(); i++) {
			Order o = orders.get(i);
			OrderSummary os = new OrderSummary();
			os.setOrderId(o.getOrderId());
			os.setName(o.getName());
			os.setEmail(o.getEmail());
			Float amount = priceSvc.sauce(o.getSauce()) + priceSvc.size(o.getSize()) + (o.isThickCrust() ? priceSvc.thickCrust() : priceSvc.thinCrust());
			for(int j = 0; j < o.getToppings().size(); j++){
				amount += priceSvc.topping(o.getToppings().get(j));
			}
			os.setAmount(amount);
			orderSummary.add(os);
		}
		return orderSummary;
	}
}
