package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;

public class IndexPage implements ServerPage {
    @Override
    public String getTitle() {
        return "Main Page";
    }

    @Override
    public void setRequestAttributes(HttpServletRequest req) {
        return;
    }
}
