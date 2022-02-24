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
import javax.swing.text.Utilities;
import javax.transaction.Transactional;
import java.net.http.HttpRequest;
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


    public int add(OrderAddDTO order, HttpServletRequest request) {
        User u = userRepository.findById(order.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));
        Order o = new Order();
        o.setCreatedAt(LocalDateTime.now());
        o.setUser(u);
        request.getSession().setAttribute(SessionUtility.ORDER_FINAL_PRICE, 0.00);
        return orderRepository.save(o).getId();
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
        User u = o.getUser();
        d.setFirstName(u.getFirstName());
        d.setEmail(u.getEmail());
        d.setLastName(u.getLastName());
        d.setPhoneNumber(u.getPhoneNumber());
        o.setDelivery(d);
        orderRepository.save(o);
        deliveryRepository.save(d);
        //TODO pitai zashto ne vrushta orederite pri wkarwane w delivery (wremeto za save e poveche ot vremeto za getbyID????)
        return mapper.map(deliveryRepository.getById(d.getId()), DeliveryResponseDTO.class);
    }


    public OrderWithProductAndUnitsDTO addProduct(int productId, HttpServletRequest request) {
        return editProduct(productId, (Integer) request.getSession().getAttribute(SessionUtility.ORDER_ID), request, "add");
    }

    public OrderWithProductAndUnitsDTO removeProduct(int productId, HttpServletRequest request) {
        return editProduct(productId, (Integer) request.getSession().getAttribute(SessionUtility.ORDER_ID), request, "remove");
    }

    public OrderWithProductAndUnitsDTO editProduct(int productId, int orderId, HttpServletRequest request, String addOrRemove) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));

        Optional<Order> o = orderRepository.findById(orderId);
        if (!o.isPresent()) {
            int userId = (int) request.getSession().getAttribute(SessionUtility.USER_ID);
            OrderAddDTO order = new OrderAddDTO();
            order.setUserId(userId);
            orderId = add(order, request);
        }
        OrdersHaveProductsKey ohpk = new OrdersHaveProductsKey(productId, orderId);

        int units = 1;
        Optional<OrdersHaveProducts> o2 = ordersHaveProductsRepository.findById(ohpk);
        Product p1 = productRepository.getById(productId);
        if (o2.isPresent()) {
            int helpUnits = o2.get().getUnits();
            int unitsInStock = p1.getUnitsInStock();
            if (addOrRemove.equals("add")) {
                if (unitsInStock == 0) {
                    throw new BadRequestException("No units of product left in store");
                }
                units = helpUnits + 1;
                p1.setUnitsInStock(unitsInStock - 1);
            } else {
                if (helpUnits == 0) {
                    throw new BadRequestException("No units of product left in order");
                }
                units = helpUnits - 1;
                p1.setUnitsInStock(unitsInStock + 1);
            }
        }

        OrdersHaveProducts ohp = new OrdersHaveProducts(ohpk, product, o.get(), units);
        ordersHaveProductsRepository.save(ohp);
        OrderWithProductAndUnitsDTO orderResponse = new OrderWithProductAndUnitsDTO();
        orderResponse.setProductsInOrder(getOrder(orderId));

        Double price = product.getDiscountPrice();
        if (request.getSession().getAttribute(SessionUtility.ORDER_FINAL_PRICE) != null) {
            if (addOrRemove.equals("add")) {
                price = (Double) request.getSession().getAttribute(SessionUtility.ORDER_FINAL_PRICE) + price;
            } else {
                price = (Double) request.getSession().getAttribute(SessionUtility.ORDER_FINAL_PRICE) - price;
            }
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
