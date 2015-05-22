package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;

public class IndexPage implements ServerPage {

    public static final String PAGE_KEY = "idx";

    @Override
    public void processRequest(HttpServletRequest req) {
        return;
    }


    @Override
    public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("title", "Main Page");
        req.setAttribute("app", PAGE_KEY);
    }

    @Override
    public void doPost(HttpServletRequest req) {
        return;
    }
}
