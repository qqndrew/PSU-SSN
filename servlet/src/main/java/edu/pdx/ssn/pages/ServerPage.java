package edu.pdx.ssn.pages;

import javax.servlet.http.HttpServletRequest;

public interface ServerPage {

    void setRequestAttributes(HttpServletRequest req);

    void setMetaAttributes(HttpServletRequest req);
}
