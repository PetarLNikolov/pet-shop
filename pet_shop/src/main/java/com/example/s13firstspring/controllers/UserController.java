package com.example.s13firstspring.controllers;

import com.example.s13firstspring.models.dtos.UserLoginDTO;
import com.example.s13firstspring.models.dtos.UserRegisterDTO;
import com.example.s13firstspring.models.dtos.UserResponseDTO;

import com.example.s13firstspring.models.entities.Discount;
import com.example.s13firstspring.models.entities.User;
import com.example.s13firstspring.services.UserService;
import com.example.s13firstspring.services.utilities.LoginUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

//    public static ResponseEntity<String> notifyDiscountChange(Discount discount) {
//        return ResponseEntity.status(418).body("Your product is on sale  " + discount.getPercentDiscount() + " % OFF");
//    }


    @PostMapping("/users/login")
    public UserResponseDTO login(@RequestBody UserLoginDTO user, HttpServletRequest request) {
        User u = userService.login(user.getUsername(), user.getPassword());
        HttpSession session=request.getSession();
        session.setAttribute(LoginUtility.LOGGED, true);
        session.setAttribute(LoginUtility.LOGGED_FROM, request.getRemoteAddr());
        session.setAttribute(LoginUtility.IS_ADMIN, u.isAdmin());
        return modelMapper.map(u, UserResponseDTO.class);
    }

    @PostMapping("/users/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterDTO user, HttpServletRequest request) {
        User u = userService.register(user, request);
        HttpSession session=request.getSession();
        session.setAttribute(LoginUtility.LOGGED, true);
        session.setAttribute(LoginUtility.LOGGED_FROM, request.getRemoteAddr());
        session.setAttribute(LoginUtility.IS_ADMIN, u.isAdmin());
        return ResponseEntity.ok(modelMapper.map(u, UserResponseDTO.class));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/users")
    public ResponseEntity<UserResponseDTO> edit(@RequestBody UserRegisterDTO user, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        return ResponseEntity.ok(userService.edit(user));
    }

    @PostMapping("/users/logout")
    public void logout(HttpSession session, HttpServletResponse response) {
        session.invalidate();
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
    }


}


