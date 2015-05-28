package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FourOhFourPage implements ServerPage {
    @Override
    public boolean processRequest(HttpServletRequest req, HttpServletResponse resp) {
        return false;
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("app", "404");
        req.setAttribute("title", "Error: Page not found");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {

    }
}
