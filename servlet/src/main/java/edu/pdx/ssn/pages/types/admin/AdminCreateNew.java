package edu.pdx.ssn.pages.types.admin;

import edu.pdx.ssn.Server;
import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;

public class AdminCreateNew implements ServerPage {


    @Override
    public void processRequest(HttpServletRequest req) {
        if (!(req.getParameter("confirm") != null && Boolean.valueOf(req.getParameter("confirm")))) {
            req.setAttribute("confirm", false);
            if (req.getParameterMap().containsKey("barcode")){
                long barcode = Long.valueOf(req.getParameter("barcode"));
                long isbn = Long.valueOf(req.getParameter("isbn"));
                String title = req.getParameter("title");
                String last = req.getParameter("last");
                String first = req.getParameter("first");
                String profs = req.getParameter("profs");
                String subj = req.getParameter("subj");
                int num = Integer.valueOf(req.getParameter("num"));
                long donor =Long.valueOf(req.getParameter("donor") == null ? "0" : req.getParameter("donor"));
                long ret = Long.valueOf(req.getParameter("return") == null ? "0" : req.getParameter("return"));
                Server.getLibrary().createNew(barcode, isbn, title, last, first, profs, subj, num, donor, ret);
            }
        }
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {

    }
}
