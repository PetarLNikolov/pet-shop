package com.example.s13firstspring.services.utilities;

import com.example.s13firstspring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.transaction.Transactional;
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

public class CronJob {

    @Autowired
    UserService userService;
    @Autowired
    JdbcTemplate jdbcTemplate;

    SimpleDateFormat sdf =
            new SimpleDateFormat("\"yyyy-MM-dd HH:mm:ss\"");

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void deleteExpiredDiscounts() {
        Date dt = Date.from(LocalDateTime.now().minusMinutes(60).toInstant(ZoneOffset.UTC));
        String currentTime = sdf.format(dt);
        //set product discount price to price
        jdbcTemplate.update("DELETE FROM discounts AS d WHERE d.end_of_offer<= "+ currentTime);

        //TODO add logging here
    }

    @Scheduled(fixedRate = 1000 * 60)
    public void logOutInactiveUsers() {
        Date dt = Date.from(LocalDateTime.now().minusMinutes(60).toInstant(ZoneOffset.UTC));
        String currentTime = sdf.format(dt);
        String statement = "SELECT u.id FROM users AS u WHERE u.last_action<=" + currentTime;
        List<Integer> userIds = jdbcTemplate.queryForList(statement, Integer.class);
        for (Integer userId : userIds) {
            userService.restoreProductsOriginal(userId);
        }

        //TODO Add logging here
    }
}
