package edu.pdx.ssn.pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ServerPage {

    boolean processRequest(HttpServletRequest req, HttpServletResponse resp);

    void setMetaAttributes(HttpServletRequest req);

    void doPost(HttpServletRequest req, HttpServletResponse resp);
}
