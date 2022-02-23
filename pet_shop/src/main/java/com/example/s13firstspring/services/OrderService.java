package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.dtos.*;
import com.example.s13firstspring.models.entities.*;
import com.example.s13firstspring.models.repositories.*;
import com.example.s13firstspring.services.utilities.SessionUtility;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    OrdersHaveProductsRepository ordersHaveProductsRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ModelMapper mapper;
    private SessionFactory sessionFactory;


    public OrderResponseDTO add(OrderAddDTO order, HttpServletRequest request) {
        User u = userRepository.findById(order.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));
        Order o = new Order();
        o.setCreatedAt(LocalDateTime.now());
        o.setUser(u);
        request.getSession().setAttribute(SessionUtility.ORDER_FINAL_PRICE, 0.00);
        return mapper.map(orderRepository.save(o), OrderResponseDTO.class);
    }


    public void delete(int id) {
        orderRepository.delete(orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found")));
    }

    @Transactional
    public DeliveryResponseDTO finalizeOrder(int orderId, int deliveryId) {
        Order o = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found"));
        Delivery d = new Delivery();
        if (deliveryRepository.findById(deliveryId).isPresent()) {
            d = deliveryRepository.getById(deliveryId);
        }
        User u=o.getUser();
        d.getOrders().add(o);
        d.setFirstName(u.getFirstName());
        d.setLastName(u.getLastName());
        d.setPhoneNumber(u.getPhoneNumber());
        deliveryRepository.save(d);
        //o.setDelivery(d);
        return mapper.map(d, DeliveryResponseDTO.class);
    }

    public OrderWithProductAndUnitsDTO addProduct(int productId, int orderId, HttpServletRequest request) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
        if (product.getUnitsInStock() == 0) {
            throw new BadRequestException("Out of stock for product: " + product.getName());
        }
        Order o = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found"));


        OrdersHaveProductsKey ohpk = new OrdersHaveProductsKey(productId, orderId);
        int units = 1;

        Optional<OrdersHaveProducts> o2 = ordersHaveProductsRepository.findById(ohpk);
        if (o2.isPresent()) {
            units += ordersHaveProductsRepository.getById(ohpk).getUnits();
        } else {
            OrdersHaveProducts ordersHaveProducts = new OrdersHaveProducts(ohpk, product, o, 0);
            ordersHaveProductsRepository.save(ordersHaveProducts);
        }
        OrdersHaveProducts ohp = new OrdersHaveProducts(ohpk, product, o, units);
        ordersHaveProductsRepository.save(ohp);
        OrderWithProductAndUnitsDTO orderResponse = new OrderWithProductAndUnitsDTO();
        orderResponse.setProductsInOrder(getOrder(orderId));

        Double price = product.getDiscountPrice();
        if (request.getSession().getAttribute(SessionUtility.ORDER_FINAL_PRICE) != null) {
            price += (Double) request.getSession().getAttribute(SessionUtility.ORDER_FINAL_PRICE);
        }
        request.getSession().setAttribute(SessionUtility.ORDER_FINAL_PRICE, price);
        orderResponse.setCost(price);
        orderResponse.setOrderId(orderId);
        return orderResponse;
    }


    private Map<String, Integer> getOrder(int orderId) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT p.id AS productId,p.name AS product_name,ohp.units AS units FROM products AS p INNER JOIN orders_have_products AS ohp ON p.id=ohp.product_id WHERE ohp.order_id=(?)", orderId);
        Map<String, Integer> order = new HashMap<>();
        while (rowSet.next()) {
            order.put(rowSet.getString("product_name"), rowSet.getInt("units"));
        }
        return order;
    }


}
