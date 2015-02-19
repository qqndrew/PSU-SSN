package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;

public class CheckoutPage implements ServerPage {
    @Override
    public String getTitle() {
        return "Checkout";
    }

    @Override
    public void setRequestAttributes(HttpServletRequest req) {
        //TODO
    }
}
