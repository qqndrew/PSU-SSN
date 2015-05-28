package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.Server;
import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class CheckoutPage implements ServerPage {

    public static final String PAGE_KEY = "checkout";

    private static final long WEEK = 604800000;
    private static final long ELEVEN_WEEKS = 6652800000L;

    @Override
    public boolean processRequest(HttpServletRequest req, HttpServletResponse resp) {
        int barcode = Integer.valueOf(req.getParameter("uid"));
        req.setAttribute("record", Server.getLibrary().getRecord(barcode));
        req.setAttribute("book", Server.getLibrary().getRecord(barcode).getBook());
        req.setAttribute("library", Server.getLibrary());
        req.setAttribute("due_date", new Date(System.currentTimeMillis() + WEEK));
        req.setAttribute("confirm", req.getParameter("confirm") == null ? false : Boolean.valueOf(req.getParameter("confirm")));
        return false;
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("title", "Checkout");
        req.setAttribute("app", PAGE_KEY);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {

    }
}
