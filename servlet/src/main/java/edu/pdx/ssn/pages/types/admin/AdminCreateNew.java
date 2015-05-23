package edu.pdx.ssn.pages.types.admin;

import edu.pdx.ssn.Server;
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
        if (req.getAttribute("confirm") == null) {
            long barcode = Long.valueOf(req.getParameter("code"));
            long isbn = Long.valueOf(req.getParameter("isbn"));
            String title = req.getParameter("title");
            String last = req.getParameter("last");
            String first = req.getParameter("first");
            String profs = req.getParameter("profs");
            profs = profs.replaceAll(",", "::");
            String subj = req.getParameter("subj");
            int num = Integer.valueOf(req.getParameter("num") == "" ? "0" : req.getParameter("num"));
            long donor = Long.valueOf(req.getParameter("donor") == "" ? "0" : req.getParameter("donor"));
            long ret = Long.valueOf(req.getParameter("return") == "" ? "0" : req.getParameter("return"));
            req.setAttribute("book", Server.getLibrary().createNew(barcode, isbn, title, last, first, profs, subj, num, donor, ret));
            req.setAttribute("confirm", true);
        }
    }
}
