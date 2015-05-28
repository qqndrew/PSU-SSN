package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.Sessions;
import edu.pdx.ssn.application.Member;
import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MemberPage implements ServerPage, Sessions {
    public static String PAGE_KEY = "member";
    @Override
    public boolean processRequest(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        if ((session == null) || !((Boolean)session.getAttribute(IS_LOGGED_IN))) {
            return false;
        }
        req.setAttribute("user", Member.getMember((Long) session.getAttribute(USER_ID)));
        return false;
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("title", "Member Overview");
        req.setAttribute("app", PAGE_KEY);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {

    }
}
