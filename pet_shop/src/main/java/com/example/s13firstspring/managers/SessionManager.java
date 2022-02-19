package com.example.s13firstspring.managers;

import com.example.s13firstspring.models.entities.User;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpSession;

@NoArgsConstructor
public class SessionManager {

        public static final String USER_LOGGED = "user_logged";

        public static void logInUser(HttpSession session, User user) {
            session.setAttribute(USER_LOGGED, user);
        }

        public static boolean isLogged(HttpSession session) {
            return session.getAttribute(USER_LOGGED) != null;
        }
}


