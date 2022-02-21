package com.example.s13firstspring.services;

import com.example.s13firstspring.DAO.DAO;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.dtos.OrderAddDTO;
import com.example.s13firstspring.models.entities.Order;
import com.example.s13firstspring.models.entities.Product;
import com.example.s13firstspring.models.repositories.OrderRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcAccessor;
import org.springframework.stereotype.Service;


import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.*;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;


@Service
public class OrderService {

    private static final String PRODUCTS_FROM_ORDER_SQL = "SELECT o.date as date, o.id as order_id, p.id as product_id, p.name, \n" +
            "IF((p.discount_id is not null AND d.date_from<o.created_on \n" +
            "AND d.date_to>o.created_on), p.price*(1-d.amount/100) ,p.price)\n" +
            "AS price, p.description, s.name as subcategory, c.name, s.id, c.id\n" +
            "FROM orders AS o\n" +
            "JOIN order_has_product AS op \n" +
            "ON order_id = o.id \n" +
            "JOIN products AS p \n" +
            "ON product_id = p.id\n" +
            "JOIN subcategories as s\n" +
            "ON s.id = p.subcategory_id\n" +
            "JOIN categories as c\n" +
            "ON c.id = s.category_id\n" +
            "LEFT OUTER JOIN discounts AS d\n" +
            "ON d.id = p.discount_id\n" +
            "WHERE order_id = ?\n" +
            "GROUP BY product_id";

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ModelMapper mapper;
    private SessionFactory sessionFactory;

    @Autowired
    DAO dao;

    public OrderAddDTO add() {

        Order o=new Order();
        o.setCreatedAt(LocalDateTime.now());
        orderRepository.save(o);
        return mapper.map(o, OrderAddDTO.class);
    }

    @Transactional
    public Order edit(Order order) {
        Optional<Order> opt = orderRepository.findById((order.getId()));
        if (opt.isPresent()) {
            orderRepository.save(order);
            return order;
        } else {
            throw new NotFoundException("Order not found");
        }
    }

    public void delete(int id) {
        if (orderRepository.getById(id) == null) {
            throw new NotFoundException("Order not found");
        }
        orderRepository.deleteById(id);
    }
    @Transactional
    public void saveOrder(Order order) {
        Session session = sessionFactory.getCurrentSession();

        Order o = new Order();

        o.setCreatedAt(LocalDateTime.now());
        orderRepository.save(o);;

        session.persist(o);

        session.flush();
    }

    public ArrayList<Product> getProductsFromOrder(Order order) throws SQLException {
        Connection connection = dao.getJdbcTemplate().getDataSource().getConnection();
        ArrayList<Product> products = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(PRODUCTS_FROM_ORDER_SQL)) {
            ps.setLong(1, order.getId());
            ResultSet rows = ps.executeQuery();
            while (rows.next()) {
                Product product = new Product();
                product.setId((int) rows.getInt("product_id"));;
                product.setName(rows.getString("p.name"));
                double price = rows.getDouble("price");
                DecimalFormat dF = new DecimalFormat("#.##");
                price = Double.parseDouble(dF.format(price));
                product.setPrice(price);
                product.setDescription(rows.getString("description"));
                product.setSubCategory(rows.getInt("subcategory_id"));
                products.add(product);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return products;
    }
}
