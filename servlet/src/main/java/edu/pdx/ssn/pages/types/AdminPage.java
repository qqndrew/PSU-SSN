package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;

public class AdminPage implements ServerPage {

    public static String PAGE_KEY = "admin";

    @Override
    public void setRequestAttributes(HttpServletRequest req) {
        //TODO
    }

    @Override
     public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("title", "Administration Panel");
        req.setAttribute("app", PAGE_KEY);
    }
}