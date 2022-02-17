package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.exceptions.UnauthorizedException;
import com.example.s13firstspring.models.entities.User;
import com.example.s13firstspring.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public User login(String username, String password) {
        if (username == null || username.isBlank()) {
            throw new BadRequestException("Username is mandatory");
        }
        if (password == null || password.isBlank()) {
            throw new BadRequestException("Password is mandatory");
        }
        User u = userRepository.findByUsername(username);
        if (u == null) {
            throw new UnauthorizedException("Wrong credentials");
        }
        if (!passwordEncoder.matches(password, u.getPassword())) {
            throw new UnauthorizedException("Wrong credentials");
        }
        return u;
    }

    public User register(String username, String password, String confirmPassword) {
        if (username == null || username.isBlank()) {
            throw new BadRequestException("Username is mandatory");
        }
        if (username.length() <= 5) {
            throw new BadRequestException("Username should be at least 6 symbols");
        }
        if (password == null || password.isBlank()) {
            throw new BadRequestException("Password is mandatory");
        }
        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
            throw new BadRequestException("Password must be hairy");
        }
        if (!password.equals(confirmPassword)) {
            throw new BadRequestException("Passwords mismatch");
        }
        if (userRepository.findByUsername(username) != null) {
            throw new BadRequestException("User already exists");
        }
        User u = new User();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(password));
        userRepository.save(u);
        return u;
    }

    public User getById(long id) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new NotFoundException("User not found");
        }
    }

    @Transactional
    public User edit(User user) {
        Optional<User> opt = userRepository.findById(user.getId());
        if (opt.isPresent()) {
            userRepository.save(user);//update users set ..... where id = user.getId
            //commentRepository.deleteAll(user.getComments());
            return user;
        } else {
            throw new NotFoundException("User not found");
        }
    }
}
