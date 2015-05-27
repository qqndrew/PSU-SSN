package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.pages.ServerPage;
import edu.pdx.ssn.pages.types.admin.AdminCheckin;
import edu.pdx.ssn.pages.types.admin.AdminCreateNew;
import edu.pdx.ssn.pages.types.admin.AdminEditBook;
import edu.pdx.ssn.pages.types.admin.AdminNewMember;
import edu.pdx.ssn.pages.types.admin.AdminNewRecordPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class AdminPage implements ServerPage {

    public static String PAGE_KEY = "admin";

    private static HashMap<String, ServerPage> pages;

    static {
        pages = new HashMap<>();
        pages.put("idx", null);
        pages.put("members", null);
        pages.put("books", null);
        pages.put("create_new", new AdminCreateNew());
        pages.put("new_record", new AdminNewRecordPage());
        pages.put(AdminCheckin.PAGE_KEY, new AdminCheckin());
        pages.put("editbook", new AdminEditBook());
        pages.put(AdminNewMember.PAGE_KEY, new AdminNewMember());
    }

    @Override
    public boolean processRequest(HttpServletRequest req, HttpServletResponse resp) {
        String page = req.getParameterMap().containsKey("page") ? req.getParameter("page").toLowerCase() : "idx";
        if (!pages.containsKey(page)) {
            page = "idx";
        }
        ServerPage action = pages.get(page);
        boolean ret = false;
        if (action != null) {
            ret = action.processRequest(req, resp);
        }
        req.setAttribute("admpage", page);
        return ret;
    }

    @Override
     public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("title", "Administration Panel");
        req.setAttribute("app", PAGE_KEY);
        String page = req.getParameterMap().containsKey("page") ? req.getParameter("page").toLowerCase() : "idx";
        if (!pages.containsKey(page)) {
            page = "idx";
        }
        ServerPage action = pages.get(page);
        if (action != null) {
            action.setMetaAttributes(req);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) { // Forward to relevant page
        String page = req.getParameterMap().containsKey("page") ? req.getParameter("page").toLowerCase() : "idx";
        if (!pages.containsKey(page)) {
            page = "idx";
        }
        ServerPage action = pages.get(page);
        if (action != null) {
            action.doPost(req, resp);
        }
        req.setAttribute("admpage", page);
    }
}
