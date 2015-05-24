package edu.pdx.ssn.pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ServerPage {

    void processRequest(HttpServletRequest req);

    void setMetaAttributes(HttpServletRequest req);

    void doPost(HttpServletRequest req, HttpServletResponse resp);
}
