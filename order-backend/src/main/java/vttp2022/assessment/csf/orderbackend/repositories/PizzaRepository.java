package vttp2022.assessment.csf.orderbackend.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.assessment.csf.orderbackend.models.Order;

@Repository
public class PizzaRepository {
    
    private static final String SQL_INSERT_ORDER = "insert into orders (name, email, pizza_size, thick_crust, sauce, toppings, comments) values (?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_GET_ORDERS_BY_EMAIL = "select * from orders where email = ?;";

    @Autowired
    private JdbcTemplate template;

    public void createOrder(Order order) {
        String toppingsJoined = String.join(",", order.getToppings());
        template.update(SQL_INSERT_ORDER, order.getName(), order.getEmail(), order.getSize(), order.isThickCrust(), order.getSauce(), toppingsJoined, order.getComments());
    }

    public List<Order> getOrdersByEmail(String email) {
        final SqlRowSet rs = template.queryForRowSet(SQL_GET_ORDERS_BY_EMAIL, email);
        List<Order> orders = new ArrayList<>();
        while(rs.next()) {
            orders.add(Order.create(rs));
        }
        return orders;
    }
}
