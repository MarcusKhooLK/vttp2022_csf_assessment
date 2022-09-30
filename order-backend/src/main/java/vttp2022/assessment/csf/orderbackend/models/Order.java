package vttp2022.assessment.csf.orderbackend.models;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

// IMPORTANT: You can add to this class, but you cannot delete its original content

public class Order {

	private Integer orderId;
	private String name;
	private String email;
	private Integer size;
	private String sauce;
	private Boolean thickCrust;
	private List<String> toppings = new LinkedList<>();
	private String comments;

	public void setOrderId(Integer orderId) { this.orderId = orderId; }
	public Integer getOrderId() { return this.orderId; }

	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }

	public void setEmail(String email) { this.email = email; }
	public String getEmail() { return this.email; }

	public void setSize(Integer size) { this.size = size; }
	public Integer getSize() { return this.size; }

	public void setSauce(String sauce) { this.sauce = sauce; }
	public String getSauce() { return this.sauce; }

	public void setThickCrust(Boolean thickCrust) { this.thickCrust = thickCrust; }
	public Boolean isThickCrust() { return this.thickCrust; }

	public void setToppings(List<String> toppings) { this.toppings = toppings; }
	public List<String> getToppings() { return this.toppings; }
	public void addTopping(String topping) { this.toppings.add(topping); }

	public void setComments(String comments) { this.comments = comments; }
	public String getComments() { return this.comments; }

	public static Order create(String json) {
		Order o = new Order();
		JsonReader reader = Json.createReader(new StringReader(json));
		JsonObject obj = reader.readObject();
		o.setName(obj.getString("name"));
		o.setEmail(obj.getString("email"));
		o.setSize(obj.getInt("size"));
		o.setSauce(obj.getString("sauce"));
		o.setThickCrust(obj.getBoolean("base"));
		JsonArray toppingsArray = obj.getJsonArray("toppings");
		List<String> toppings = new ArrayList<>();
		for(int i = 0; i < toppingsArray.size(); i++) {
			toppings.add(toppingsArray.getString(i));
		}
		o.setToppings(toppings);
		o.setComments(obj.getString("comments"));
		return o;
	}

	public static Order create(final SqlRowSet rs) {
		Order o = new Order();
		o.setOrderId(rs.getInt("order_id"));
		o.setName(rs.getString("name"));
		o.setEmail(rs.getString("email"));
		o.setSize(rs.getInt("pizza_size"));
		o.setSauce(rs.getString("sauce"));
		o.setThickCrust(rs.getBoolean("thick_crust"));
		String toppings = rs.getString("toppings");
		List<String> toppingsList = Arrays.asList(toppings.split(","));
		o.setToppings(toppingsList);
		o.setComments(rs.getString("comments"));
		return o;
	}

	@Override
	public String toString() {
		return "order_id: %d | name: %s | email: %s | pizza_size: %d | sauce: %s | comments: %s"
		.formatted(orderId, name, email, size, sauce, comments);
	}
}
