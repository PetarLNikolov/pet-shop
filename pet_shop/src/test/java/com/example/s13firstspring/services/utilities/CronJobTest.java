package com.example.s13firstspring.services.utilities;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CronJobTest {

    SimpleDateFormat sdf =
            new SimpleDateFormat("\"yyyy-MM-dd HH:mm:ss\"");
    @Test
    void logOutInactiveUsers() {
        //given

        //when

        //then
    }
}