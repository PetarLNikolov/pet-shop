package com.example.s13firstspring.modelsTests.repositories;

import com.example.s13firstspring.modelsTests.entities.Discount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DiscountRepositoryTest {

    @Autowired
    private DiscountRepository underTest;

    @Test
    void itShouldFindByName() {
        //given
        String name = "BlackLivesMatterDiscount";
        Discount discount = new Discount();
        discount.setName(name);
        underTest.save(discount);

        //when
        Optional<Discount> discount1 = Optional.ofNullable(underTest.findByName(name));
        Boolean expected = discount1.isPresent();
        //then
        assertThat(expected).isTrue();
    }

    @Test
    void deleteByEndOfOfferBefore() {
        //given
        Discount discount = new Discount();
        discount.setEndOfOffer(LocalDateTime.now().minusMinutes(3));
        int id = 3;
        discount.setId(id);

        //when
        underTest.deleteByEndOfOfferBefore(LocalDateTime.now());

        Optional<Discount> discount1 = underTest.findById(id);
        Boolean expected=discount1.isPresent();

        //then
        assertThat(expected).isFalse();
    }
}