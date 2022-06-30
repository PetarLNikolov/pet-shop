package com.example.s13firstspring.modelsTests.repositories;

import com.example.s13firstspring.modelsTests.entities.Delivery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DeliveryRepositoryTest {

    @Autowired
    private DeliveryRepository underTest;

    @Test
    void itShouldFindByName() {
        //given
        int id= 999;
        Delivery delivery = new Delivery();
        delivery.setId(id);
        underTest.save(delivery);

        //when
        Optional<Delivery> delivery1= Optional.ofNullable(underTest.getById(id));
        Boolean expected= delivery1.isPresent();
        //then
        assertThat(expected).isTrue();
    }
}