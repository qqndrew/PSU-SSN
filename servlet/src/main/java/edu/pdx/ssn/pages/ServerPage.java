package edu.pdx.ssn.pages;

import javax.servlet.http.HttpServletRequest;

public interface ServerPage {

    void processRequest(HttpServletRequest req);

    void setMetaAttributes(HttpServletRequest req);

    void doPost(HttpServletRequest req);
}
