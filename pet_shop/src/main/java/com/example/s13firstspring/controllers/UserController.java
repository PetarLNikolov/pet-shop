package com.example.s13firstspring.controllers;

import com.example.s13firstspring.modelsTests.dtos.users.UserLoginDTO;
import com.example.s13firstspring.modelsTests.dtos.users.UserRegisterDTO;
import com.example.s13firstspring.modelsTests.dtos.users.UserResponseDTO;
import com.example.s13firstspring.modelsTests.entities.User;
import com.example.s13firstspring.services.OrderService;
import com.example.s13firstspring.services.UserService;
import com.example.s13firstspring.services.utilities.SessionUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    OrderService orderService;


    @PostMapping("/users/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody UserLoginDTO user, HttpServletRequest request) {
        User u = userService.login(user.getUsername(), user.getPassword(), request);
        return ResponseEntity.ok().body(modelMapper.map(u, UserResponseDTO.class));
    }

    @PostMapping("/users/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterDTO user, HttpServletRequest request) {
        User u = userService.register(user, request);
        return ResponseEntity.ok(modelMapper.map(u, UserResponseDTO.class));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping("/users/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SessionUtility.validateLogin(request);
        userService.logout(request, (Integer) request.getSession().getAttribute(SessionUtility.ORDER_ID));
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
    }


    @DeleteMapping("/users/delete/{id}")
    private void deleteUser(@PathVariable int id, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        userService.delete(id);
    }


    @PutMapping("/users" )
    public ResponseEntity<UserResponseDTO> edit(@RequestBody UserRegisterDTO user, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok(userService.edit(user,request));
    }


}


