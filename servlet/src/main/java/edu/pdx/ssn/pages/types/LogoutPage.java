package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;

public class LogoutPage implements ServerPage {
    @Override
    public String getTitle() {
        return "Logout";
    }

    @Override
    public void setRequestAttributes(HttpServletRequest req) {
        //TODO
    }
}
