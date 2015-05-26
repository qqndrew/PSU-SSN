package edu.pdx.ssn.pages.types.admin;

import edu.pdx.ssn.Server;
import edu.pdx.ssn.application.Record;
import edu.pdx.ssn.pages.PageManager;
import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        if (req.getAttribute("confirm") == null || req.getAttribute("confirm").equals(false)) {
            String temp = req.getParameter("code").replaceAll("[^0-9]", "");
            if (temp.equals("")) {
                temp = "0";
            }
            long barcode = Long.valueOf(temp);
            if (Server.getLibrary().getRecord(barcode) != null) {
                req.setAttribute("err", "A record with this barcode already exists!");
                req.setAttribute("confirm", false);
                forwardRequest(req,resp);
                return;
            }
            temp = req.getParameter("isbn").replaceAll("[^0-9]", "");
            if (temp.equals("")) {
                temp = "0";
            }
            long isbn = Long.valueOf(temp);
            if (Server.getLibrary().getBook(isbn) == null) {
                req.setAttribute("err", "The book with the provided isbn " + isbn + " does not exist in this system");
                req.setAttribute("confirm", false);
                forwardRequest(req,resp);
                return;
            }
            temp = req.getParameter("donor");
            Long donorUid = null;
            if (!temp.equals("")) {
                donorUid = Long.valueOf(temp.replaceAll("[^0-9]", ""));
            }
            temp = req.getParameter("return");
            Long retDate = null;
            if (!temp.equals("")) {
                retDate = Long.valueOf(temp);
            }
            Record record = Server.getLibrary().createRecord(barcode, isbn, donorUid, retDate);
            req.setAttribute("record", record);
            req.setAttribute("book", record.getBook());
            req.setAttribute("confirm", true);
            forwardRequest(req, resp);
            return;
        }
    }

    private void forwardRequest(HttpServletRequest req, HttpServletResponse resp) {
        try {
            PageManager.getPage("admin").setMetaAttributes(req);
            req.setAttribute("admpage", "new_record");
            req.getRequestDispatcher("/WEB-INF/index.jsp?app=admin&page=new_record").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
