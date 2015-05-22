package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.Sessions;
import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutPage implements ServerPage, Sessions {

    public static final String PAGE_KEY = "logout";


    @Override
    public void processRequest(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            // Reset defaults
            session.setAttribute(IS_LOGGED_IN, false);
            session.setAttribute(ADMIN, false);
            // Remove login-time attributes
            session.removeAttribute(USER_ID);
            session.removeAttribute(FIRST_NAME);
            session.removeAttribute(LAST_NAME);
            session.removeAttribute(EMAIL);
            session.removeAttribute(PHONE);
        }
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("title", "Logout");
        req.setAttribute("app", PAGE_KEY);
    }

    @Override
    public void doPost(HttpServletRequest req) {

    }
}
