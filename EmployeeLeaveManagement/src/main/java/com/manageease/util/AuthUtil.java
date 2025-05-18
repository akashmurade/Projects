package com.manageease.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthUtil {
    public static boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("employee") != null;
    }

    public static boolean isAdmin(HttpServletRequest request) {
        return hasRole(request, "Admin");
    }

    public static boolean isHR(HttpServletRequest request) {
        return hasRole(request, "HR");
    }

    public static boolean isManager(HttpServletRequest request) {
        return hasRole(request, "Manager");
    }

    private static boolean hasRole(HttpServletRequest request, String role) {
        HttpSession session = request.getSession(false);
        return session != null && role.equals(session.getAttribute("role"));
    }
}