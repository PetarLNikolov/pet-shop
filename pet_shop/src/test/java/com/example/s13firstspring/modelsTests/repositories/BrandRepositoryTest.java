package com.example.s13firstspring.modelsTests.repositories;

import com.example.s13firstspring.modelsTests.entities.Brand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BrandRepositoryTest {

    @Autowired
    private BrandRepository underTest;

    @Test
    void itShouldFindByName() {
        //given
        String name="GoatLike";
        Brand brand = new Brand();
        brand.setName(name);
        underTest.save(brand);

        //when
        Optional<Brand> brand1= Optional.ofNullable(underTest.findByName(name));
        Boolean expected= brand1.isPresent();
        //then
        assertThat(expected).isTrue();
    }
}