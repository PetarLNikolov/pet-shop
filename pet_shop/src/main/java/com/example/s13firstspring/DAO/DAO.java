package com.example.s13firstspring.DAO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public abstract class DAO {
    @Autowired
    protected
    JdbcTemplate jdbcTemplate;
}