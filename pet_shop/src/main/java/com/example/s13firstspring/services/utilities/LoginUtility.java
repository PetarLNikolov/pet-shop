package com.example.s13firstspring.services.utilities;

import com.example.s13firstspring.exceptions.UnauthorizedException;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Data
public class LoginUtility {

    public static final String IS_ADMIN = "is_admin";
    public static final String LOGGED = "logged";
    public static final String LOGGED_FROM = "logged_from";

    public static void validateLogin(HttpServletRequest request) {

        HttpSession s = request.getSession();
        if (s.isNew() ||
                (!(Boolean) s.getAttribute(LOGGED)) ||
                (!request.getRemoteAddr().equals(s.getAttribute(LOGGED_FROM)
                ))) {
            throw new UnauthorizedException("You have to login!");
        }
    }

    public static void isAdmin(HttpServletRequest request) {
        if(!(boolean) request.getSession().getAttribute(IS_ADMIN)){
            throw new UnauthorizedException("You are not an admin");
        }
    }
}
