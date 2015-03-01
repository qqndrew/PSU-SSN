package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;

public class CheckoutPage implements ServerPage {

    public static final String PAGE_KEY = "checkout";


    @Override
    public void setRequestAttributes(HttpServletRequest req) {
        //TODO
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("title", "Checkout");
        req.setAttribute("app", PAGE_KEY);
    }
}
