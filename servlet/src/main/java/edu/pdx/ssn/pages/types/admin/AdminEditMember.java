package edu.pdx.ssn.pages.types.admin;

import edu.pdx.ssn.Server;
import edu.pdx.ssn.application.Member;
import edu.pdx.ssn.pages.PageManager;
import edu.pdx.ssn.pages.ServerPage;
import edu.pdx.ssn.sql.Schema;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminEditMember implements ServerPage {

    public static final String PAGE_KEY = "edit_member";
    private static final String EDIT_QUERY = "UPDATE `" + Schema.USERS_TABLE + "` SET `"
            + Schema.USER_EMAIL + "`=?, `" + Schema.USER_PHONE + "`=?, `"
            + Schema.USER_LAST_NAME + "`=?, `" + Schema.USER_FIRST_NAME + "`=?, `"
            + Schema.USER_IS_ADMIN + "`=? WHERE (`" + Schema.USER_UID + "`=?)";

    @Override
    public boolean processRequest(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("errmessage", "");
        return false;
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("title", "Edit Member");
        req.setAttribute("admpage", PAGE_KEY);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if (Boolean.valueOf(req.getParameter("confirm"))) {
            String user = req.getParameter("user").toLowerCase();
            long uid = Long.valueOf(req.getParameter("uid"));
            if (!user.matches("(?i)\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b")) {
                req.setAttribute("errmessage", "This is not a valid email!");
                PageManager.getPage("admin").setMetaAttributes(req);
                req.setAttribute("admpage", PAGE_KEY);
                try {
                    req.getRequestDispatcher("/WEB-INF/index.jsp?app=admin&page=" + PAGE_KEY).forward(req, resp);
                    return;
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            int admin = req.getParameter("admin") != null ? 1 : 0;
            String phone = req.getParameter("phone");
            String firstName = req.getParameter("first");
            String lastName = req.getParameter("last");
            Server.getConnection().executeQuery("edit_admin", false, EDIT_QUERY, user, phone, lastName, firstName, admin, uid);
            try {
                PageManager.getPage("admin").setMetaAttributes(req);
                req.setAttribute("admpage", "members");
                req.setAttribute("forward", "Member " + user + " edited successfully");
                req.getRequestDispatcher("/WEB-INF/index.jsp?app=admin&page=members").forward(req, resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String email = req.getParameter("email").toLowerCase();
            Member user = Member.getMember(email);
            if (user == null) {
                req.setAttribute("errmessage", "Member with matching e-mail not found!");
                try {
                    PageManager.getPage("admin").setMetaAttributes(req);
                    req.setAttribute("admpage", PAGE_KEY);
                    req.getRequestDispatcher("/WEB-INF/index.jsp?app=admin&confirm=false&page=" + PAGE_KEY).forward(req, resp);
                    return;
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

        }
    }
}
