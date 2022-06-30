package com.example.s13firstspring.modelsTests.repositories;

import com.example.s13firstspring.modelsTests.entities.Animal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AnimalRepositoryTest {

    @Autowired
    private AnimalRepository underTest;

    @Test
    void itShouldFindByName() {
        //given
        String name="koza";
        Animal animal = new Animal(19, name, new HashSet<>());
        underTest.save(animal);

        //when
        Optional<Animal> animal1= Optional.ofNullable(underTest.findByName(name));
        Boolean expected= animal1.isPresent();
        //then
        assertThat(expected).isTrue();
    }
}