package com.example.s13firstspring.modelsTests.repositories;

import com.example.s13firstspring.modelsTests.entities.City;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CityRepositoryTest {

    @Autowired
    private CityRepository underTest;

    @Test
    void itShouldFindByName() {
        //given
        String name="coffins";
        City city = new City();
        city.setName(name);
        underTest.save(city);

        //when
        Optional<City> brand1= Optional.ofNullable(underTest.findByName(name));
        Boolean expected= brand1.isPresent();
        //then
        assertThat(expected).isTrue();
    }
}