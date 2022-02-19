package com.example.s13firstspring.controllers;


import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.exceptions.UnauthorizedException;
import com.example.s13firstspring.managers.SessionManager;
import com.example.s13firstspring.models.dtos.*;
import com.example.s13firstspring.models.entities.*;
import com.example.s13firstspring.models.repositories.OrderRepository;
import com.example.s13firstspring.models.repositories.ProductRepository;
import com.example.s13firstspring.services.OrderService;
import com.example.s13firstspring.services.utilities.LoginUtility;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;


    @PostMapping("/orders/add")
    public ResponseEntity<OrderAddDTO> add(@RequestBody HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        return ResponseEntity.ok(orderService.add());
    }

    //-edit order
    @PutMapping("/orders/edit")
    public ResponseEntity<Order> edit(@RequestBody Order order, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        return ResponseEntity.ok(orderService.edit(order));
    }

    //-remove order
    @DeleteMapping("/orders/delete/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        orderService.delete(id);
    }

    @GetMapping(value = "viewOrder/{id}")
    public ArrayList<Product> view(@PathVariable("id") long orderId, HttpSession session, HttpServletResponse response) throws SQLException {
        if (!SessionManager.isLogged(session)) {
            throw new UnauthorizedException("You have to log in first");
        }
        User user = (User) session.getAttribute(SessionManager.USER_LOGGED);
        if (!(orderRepository.existsById((int) orderId))){
            throw new NotFoundException("Order not found!");
        }
        Order order = orderRepository.getById((int) orderId);

            ArrayList <Product> pfc= orderService.getProductsFromOrder(order);
            ArrayList<Product> products = new ArrayList<>();
            double totalPrice = 0;
            for (Product p: pfc) {
                Optional<Product> product = Optional.ofNullable(productRepository.findByName(p.getName()));
                if(product.isPresent()){
                    totalPrice += p.getPrice();
                    Product pr = product.get();
                }
                else{
                    throw new NotFoundException("Product not found");
                }
            }
            response.setHeader("total price", String.valueOf(totalPrice));
            return products;

    }


}
