package edu.pdx.ssn.pages;

import javax.servlet.http.HttpServletRequest;

public interface ServerPage {
    String getTitle();
    void setRequestAttributes(HttpServletRequest req);
}
