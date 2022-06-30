package com.example.s13firstspring.modelsTests.repositories;

import com.example.s13firstspring.modelsTests.entities.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository underTest;

    @Test
    void itShouldFindByName() {
        //given
        String name="coffins";
        Category category = new Category();
        underTest.save(category);

        //when
        Optional<Category> brand1= Optional.ofNullable(underTest.findByName(name));
        Boolean expected= brand1.isPresent();
        //then
        assertTrue(expected);
    }
}