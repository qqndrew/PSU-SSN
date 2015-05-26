package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexPage implements ServerPage {

    public static final String PAGE_KEY = "idx";

    @Override
    public boolean processRequest(HttpServletRequest req, HttpServletResponse resp) {
        return false;
    }


    @Override
    public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("title", "Main Page");
        req.setAttribute("app", PAGE_KEY);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        return;
    }
}
