package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;

public class LoginPage implements ServerPage {

    public static final String PAGE_KEY = "login";

    @Override
    public void setRequestAttributes(HttpServletRequest req) {
        //TODO
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("title", "Login");
        req.setAttribute("app", PAGE_KEY);
    }
}
