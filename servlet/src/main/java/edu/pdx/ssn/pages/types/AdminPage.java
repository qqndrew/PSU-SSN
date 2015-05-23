package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.pages.ServerPage;
import edu.pdx.ssn.pages.types.admin.AdminCreateNew;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class AdminPage implements ServerPage {

    public static String PAGE_KEY = "admin";

    private static HashMap<String, ServerPage> pages;

    static {
        pages = new HashMap<>();
        pages.put("create_new", new AdminCreateNew());
    }

    @Override
    public void processRequest(HttpServletRequest req) {
        String page = req.getParameterMap().containsKey("page") ? req.getParameter("page").toLowerCase() : "idx";
        if (!pages.containsKey(page)) {
            page = "idx";
        }
        ServerPage action = pages.get(page);
        if (action != null) {
            action.processRequest(req);
        }
        req.setAttribute("page", page);

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
    public void doPost(HttpServletRequest req) { // Forward to relevant page
        System.out.println("Processing admin post: " + req.getParameter("page").toLowerCase());

        String page = req.getParameterMap().containsKey("page") ? req.getParameter("page").toLowerCase() : "idx";
        if (!pages.containsKey(page)) {
            page = "idx";
        }
        ServerPage action = pages.get(page);
        if (action != null) {
            System.out.println("forwarding action to " + action.toString());
            action.doPost(req);
        } else {
            System.out.println("action null: " + page);

        }
        req.setAttribute("page", page);
    }
}
