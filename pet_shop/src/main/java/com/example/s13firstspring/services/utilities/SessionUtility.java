package com.example.s13firstspring.services.utilities;

import com.example.s13firstspring.exceptions.SessionTimeoutException;
import com.example.s13firstspring.exceptions.UnauthorizedException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;

@Data
public class SessionUtility {

    @Autowired
    JdbcTemplate jdbcTemplate;
    public static final String IS_ADMIN = "is_admin";
    public static final String LOGGED = "logged";
    public static final String LOGGED_FROM = "logged_from";
    public static final String USER_ID = "user_id";
    public static final String ORDER_FINAL_PRICE="order_final_price";
    public static final String ORDER_ID = "order_id";
    public static final String LAST_LOGIN = "last_login";

    public static void validateLogin(HttpServletRequest request) {
        checkForInactivity(request);

        request.getSession().setAttribute(LAST_LOGIN, LocalDateTime.now());
        HttpSession s = request.getSession();
        if (s.isNew() ||
                (!(Boolean) s.getAttribute(LOGGED)) ||
                (!request.getRemoteAddr().equals(s.getAttribute(LOGGED_FROM)
                ))) {
            throw new UnauthorizedException("You have to login!");
        }
    }

    private static void checkForInactivity(HttpServletRequest request) {
        if(LocalDateTime.now().minusHours(1).isBefore((ChronoLocalDateTime<?>) request.getSession().getAttribute(LAST_LOGIN))){
            request.getSession().invalidate();
            //TODO ask krasi it wants deleteUserOrders to be static and jdbctemplate doesnotlikeit(maybe autowire to u.repo or u.service)
            //deleteUserOrders(request.getSession());
            throw new SessionTimeoutException("You have been inactive for 1 hour, please log in again");
        }
    }
//    private void deleteUserOrders(HttpSession session) {
//        int userId= (int) session.getAttribute(SessionUtility.USER_ID);
//        jdbcTemplate.update("DELETE ohp FROM orders_have_products AS ohp JOIN orders AS o ON o.id=ohp.order_id WHERE o.user_id=(?);", userId);
//    }

    public static void isAdmin(HttpServletRequest request) {
        if(!(boolean) request.getSession().getAttribute(IS_ADMIN)){
            throw new UnauthorizedException("You are not an admin");
        }
    }
}
