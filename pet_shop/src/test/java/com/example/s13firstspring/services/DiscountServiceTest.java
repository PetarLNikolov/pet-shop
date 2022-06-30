package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.modelsTests.dtos.discounts.DiscountAddDTO;
import com.example.s13firstspring.modelsTests.entities.Discount;
import com.example.s13firstspring.modelsTests.repositories.DiscountRepository;
import com.example.s13firstspring.modelsTests.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DiscountServiceTest {


    public static final String DISCOUNT_NAME = "Discount";
    public static final LocalDateTime END_DATE = LocalDateTime.now().plusDays(3);
    public static final LocalDateTime START_DATE = LocalDateTime.now();
    public static final int PERCENT_DISCOUNT = -3;
    @Mock
    private DiscountRepository repository;
    @Mock
    ModelMapper mapper;
    @Mock
    UserRepository userRepository;
    @Mock
    JdbcTemplate jdbcTemplate;
    @Mock
    private JavaMailSender emailSender;
    @Mock
    EntityManager entityManager;
    @InjectMocks
    DiscountService discountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void shouldThrowWhenNameExists() {
        DiscountAddDTO discount = new DiscountAddDTO();
        discount.setName(DISCOUNT_NAME);
        when(repository.findByName(DISCOUNT_NAME)).thenReturn(new Discount());
        Throwable exception = assertThrows(BadRequestException.class, () -> discountService.validation(discount));
        assertEquals(exception.getMessage(), "Discount name is taken");
    }

    @Test
    void shouldThrowWhenDateIsInvalid() {
        DiscountAddDTO discount = new DiscountAddDTO();
        discount.setStartOfOffer(START_DATE);
        discount.setEndOfOffer(END_DATE.minusYears(1));
        Throwable exception = assertThrows(BadRequestException.class, () -> discountService.validation(discount));
        assertEquals(exception.getMessage(),"Discount dates are invalid");
    }

    @Test
    void shouldNotThrowWhenPercentIsInvalid() {
        DiscountAddDTO discount = new DiscountAddDTO();
        discount.setPercentDiscount(PERCENT_DISCOUNT);
        discount.setStartOfOffer(START_DATE);
        discount.setEndOfOffer(END_DATE);
        discount.setName(DISCOUNT_NAME);

        Throwable exception = assertThrows(BadRequestException.class, () -> discountService.validation(discount));
        assertEquals(exception.getMessage(),"Discount percentage is invalid");
    }



}