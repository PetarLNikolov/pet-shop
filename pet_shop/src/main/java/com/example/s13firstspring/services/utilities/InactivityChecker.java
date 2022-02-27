package com.example.s13firstspring.services.utilities;

import com.example.s13firstspring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;


@Component

public class InactivityChecker {

    @Autowired
    UserService userService;
    @Autowired
    JdbcTemplate jdbcTemplate;

    SimpleDateFormat sdf =
            new SimpleDateFormat("\"yyyy-MM-dd HH:mm:ss\"");

    @Scheduled(fixedRate = 1000*30)
    public void logOutInactiveUsers() {
        Date dt = Date.from(LocalDateTime.now().minusMinutes(60).toInstant(ZoneOffset.UTC));
        String currentTime = sdf.format(dt);
        String statement = "SELECT u.id FROM users AS u WHERE u.last_action<=" + currentTime;
        List<Integer> userIds = jdbcTemplate.queryForList(statement, Integer.class);
        for (Integer userId : userIds) {
            userService.restoreProductsOriginal(userId);
        }

        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("logs"+ File.separator +"inactivity",true);
            myWriter.write("Inactive users: "+userIds.size());
            myWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
