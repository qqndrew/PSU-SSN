package edu.pdx.ssn.pages.types.admin;

import edu.pdx.ssn.Server;
import edu.pdx.ssn.application.Record;
import edu.pdx.ssn.pages.PageManager;
import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminCheckin implements ServerPage {

    public static String PAGE_KEY = "checkin";

    @Override
    public boolean processRequest(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("err", "");
        if (Boolean.valueOf(req.getParameter("confirm"))) {
            if (Boolean.valueOf(req.getParameter("continue"))) {
                long barcode = Long.getLong(req.getParameter("code"));
                Server.getLibrary().checkin(barcode);
                PageManager.getPage("admin").setMetaAttributes(req);
                req.setAttribute("admpage", "books");
                try {
                    req.getRequestDispatcher("/WEB-INF/index.jsp?app=admin&page=books").forward(req, resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            } else { // Should not be reached
                long barcode = Long.getLong(req.getParameter("code"));
                req.setAttribute("record", Server.getLibrary().getRecord(barcode));
                forwardRequest(req, resp, true);
                return true;
            }
        } else {
            return false; // Do nothing
        }
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("err", "");
        if (!Boolean.valueOf(req.getParameter("confirm"))) {
            long barcode = 0;
            try {
                barcode = Long.getLong(req.getParameter("code"));
            } catch (NumberFormatException e) {
                req.setAttribute("err", "This is not a valid barcode!");
                forwardRequest(req, resp, false);
                return;
            }
            Record record = Server.getLibrary().getRecord(barcode);
            if (record == null) {
                req.setAttribute("err", "A record matching this barcode was not found!");
                forwardRequest(req, resp, false);
                return;
            }
            req.setAttribute("record", record);
            forwardRequest(req, resp, true);
        }
    }

    private void forwardRequest(HttpServletRequest req, HttpServletResponse resp, boolean confirm) {

        try {
            PageManager.getPage("admin").setMetaAttributes(req);
            req.setAttribute("admpage", "checkin");
            req.getRequestDispatcher("/WEB-INF/index.jsp?app=admin&page=checkin&confirm=" + confirm).forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
