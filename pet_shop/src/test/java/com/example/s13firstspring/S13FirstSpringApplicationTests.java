package com.example.s13firstspring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class S13FirstSpringApplicationTests {

    private Calculator underTest = new Calculator();

    @Test  //this is junit annotation for test
    void itShouldAddTwoNumbers() {
        // given
        int numberOne = 20;
        int numberTwo = 30;

        //when
        int result = underTest.add(numberOne, numberTwo);

        //then
        assertThat(result).isEqualTo(50);
    }

    class Calculator {
        int add(int a, int b) {
            return a + b;
        }
    }

}
