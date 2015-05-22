package edu.pdx.ssn.pages.types.admin;

import edu.pdx.ssn.Server;
import edu.pdx.ssn.application.Book;
import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;

public class AdminCreateNew implements ServerPage {


    @Override
    public void processRequest(HttpServletRequest req) {

    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {

    }

    @Override
    public void doPost(HttpServletRequest req) {
        if (req.getParameter("confirm") != null) {

        } else {
            if (Boolean.valueOf(req.getParameter("confirm"))) { // Confirmed
                long barcode = Long.valueOf(req.getParameter("code"));
                long isbn = Long.valueOf(req.getParameter("isbn"));
                String title = req.getParameter("title");
                String last = req.getParameter("last");
                String first = req.getParameter("first");
                String profs = req.getParameter("profs");
                String subj = req.getParameter("subj");
                int num = Integer.valueOf(req.getParameter("num"));
                long donor = Long.valueOf(req.getParameter("donor") == null ? "0" : req.getParameter("donor"));
                long ret = Long.valueOf(req.getParameter("return") == null ? "0" : req.getParameter("return"));
                new Book(barcode, isbn, title, last, first, profs, subj, num, donor, ret);
                req.setAttribute("book", Server.getLibrary().createNew(barcode, isbn, title, last, first, profs, subj, num, donor, ret));
            } else {
                return;
            }
        }
    }
}
