package com.example.s13firstspring.services;

import com.example.s13firstspring.controllers.ImageController;
import com.example.s13firstspring.controllers.UserController;
import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.exceptions.UnauthorizedException;
import com.example.s13firstspring.models.dtos.UserRegisterDTO;
import com.example.s13firstspring.models.entities.Image;
import com.example.s13firstspring.models.entities.User;
import com.example.s13firstspring.models.repositories.UserRepository;
import com.example.s13firstspring.services.utilities.LoginUtility;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ImageController imageController;
    @Autowired
    private ModelMapper mapper;


    public User login(String username, String password) {
        if (username == null || username.isBlank()) {
            throw new BadRequestException("Username is mandatory");
        }
        if (password == null || password.isBlank()) {
            throw new BadRequestException("Password is mandatory");
        }
        User u = repository.findByUsername(username);
        if (u == null) {
            throw new UnauthorizedException("Wrong credentials");
        }
        if (!passwordEncoder.matches(password, u.getPassword())) {
            throw new UnauthorizedException("Wrong credentials");
        }
        return u;
    }

    public User register(UserRegisterDTO user) {
        String username= user.getUsername();
        String password=user.getPassword();
        String confirmPassword=user.getPassword2();
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
            throw new BadRequestException("Password must contain:" +
                    " a digit must occur at least once" +
                    "a lower case letter must occur at least once" +
                    "an upper case letter must occur at least once" +
                    "a special character must occur at least once" +
                    "no whitespace allowed in the entire string" +
                    "anything, at least eight places though");
        }
        if (!password.equals(confirmPassword)) {
            throw new BadRequestException("Passwords mismatch");
        }
        if (repository.findByUsername(username) != null) {
            throw new BadRequestException("User already exists");
        }
        User u = mapper.map(user,User.class);
        u.setPassword(passwordEncoder.encode(password));
        repository.save(u);
        return u;
    }

    public User getById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional
    public User edit(User user) {
        return repository.save(getById(user.getId()));
    }


}

