package edu.pdx.ssn.pages.types.admin;

import edu.pdx.ssn.Server;
import edu.pdx.ssn.application.Record;
import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminNewRecordPage implements ServerPage {
    @Override
    public void processRequest(HttpServletRequest req) {
        req.setAttribute("err", "");
        req.setAttribute("confirm", false);
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getAttribute("confirm") == null || req.getAttribute("confirm") == false) {
            long barcode = Long.valueOf(req.getParameter("code").replaceAll("[^0-9]", ""));
            if (Server.getLibrary().getRecord(barcode) != null) {
                req.setAttribute("err", "A record with this barcode already exists!");
                req.setAttribute("confirm", false);
            }
            long isbn = Long.valueOf(req.getParameter("isbn").replaceAll("[^0-9]", ""));
            if (Server.getLibrary().getBook(isbn) == null) {
                req.setAttribute("err", "The book with the provided isbn " + isbn + " does not exist in this system");
                req.setAttribute("confirm", false);
            }
            String temp = req.getParameter("donor");
            Long donorUid = null;
            if (temp != null) {
                donorUid = Long.valueOf(temp.replaceAll("[^0-9]", ""));
            }
            String ret = req.getParameter("return");
            Long retDate = null;
            if (ret != null) {
                retDate = Long.valueOf(ret);
            }
            Record record = Server.getLibrary().createRecord(barcode, isbn, donorUid, retDate);
            req.setAttribute("record", record);
            req.setAttribute("confirm", true);
        }
    }
}
