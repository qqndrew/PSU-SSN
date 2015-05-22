package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.Server;
import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class CheckoutPage implements ServerPage {

    public static final String PAGE_KEY = "checkout";


    @Override
    public void processRequest(HttpServletRequest req) {
        int barcode = Integer.valueOf(req.getParameter("uid"));
        req.setAttribute("book", Server.getLibrary().getBook(barcode));
        req.setAttribute("library", Server.getLibrary());
        req.setAttribute("due_date", new Date(System.currentTimeMillis() + 6652800000L));
        req.setAttribute("confirm", req.getParameter("confirm") == null ? false : Boolean.valueOf(req.getParameter("confirm")));
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("title", "Checkout");
        req.setAttribute("app", PAGE_KEY);
    }

    @Override
    public void doPost(HttpServletRequest req) {

    }
}
