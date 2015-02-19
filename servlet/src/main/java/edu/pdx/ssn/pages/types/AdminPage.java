package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;

public class AdminPage implements ServerPage {
    @Override
    public String getTitle() {
        return "Administration Panel";
    }

    @Override
    public void setRequestAttributes(HttpServletRequest req) {
        //TODO
    }
}
