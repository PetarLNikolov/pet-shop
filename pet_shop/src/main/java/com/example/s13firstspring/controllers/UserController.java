package com.example.s13firstspring.controllers;

import com.example.s13firstspring.exceptions.UnauthorizedException;
import com.example.s13firstspring.models.dtos.UserRegisterDTO;
import com.example.s13firstspring.models.dtos.UserResponseDTO;

import com.example.s13firstspring.models.entities.User;
import com.example.s13firstspring.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    public static final String LOGGED = "logged";
    public static final String LOGGED_FROM = "logged_from";

    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/users/login")
    public UserResponseDTO login(@RequestBody User user, HttpSession session, HttpServletRequest request) {
        String username = user.getUsername();
        String password = user.getPassword();
        User u = userService.login(username, password);
        session.setAttribute(LOGGED, true);
        session.setAttribute(LOGGED_FROM, request.getRemoteAddr());
        UserResponseDTO dto = modelMapper.map(u, UserResponseDTO.class);
        return dto;
    }

    @PostMapping("/users/reg")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterDTO user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String confirmPassword = user.getPassword2();
        User u = userService.register(username, password, confirmPassword);
        UserResponseDTO dto = modelMapper.map(u, UserResponseDTO.class);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable int id) {
        User u = userService.getById(id);
        UserResponseDTO dto = modelMapper.map(u, UserResponseDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/users")
    public ResponseEntity<UserResponseDTO> edit(@RequestBody User user, HttpSession session, HttpServletRequest request) {
        validateLogin(session, request);
        User u = userService.edit(user);
        UserResponseDTO dto = modelMapper.map(u, UserResponseDTO.class);
        return ResponseEntity.ok(dto);

    }




    public static void validateLogin(HttpSession session, HttpServletRequest request) {
        if (session.isNew() ||
                (!(Boolean) session.getAttribute(LOGGED)) ||
                (!request.getRemoteAddr().equals(session.getAttribute(LOGGED_FROM)
                ))) {
            throw new UnauthorizedException("You have to login!");
        }
    }
}


