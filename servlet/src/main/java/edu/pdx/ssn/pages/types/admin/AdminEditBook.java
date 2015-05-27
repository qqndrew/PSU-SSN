package edu.pdx.ssn.pages.types.admin;

import edu.pdx.ssn.Server;
import edu.pdx.ssn.application.Book;
import edu.pdx.ssn.application.BookRegistry;
import edu.pdx.ssn.pages.PageManager;
import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminEditBook implements ServerPage {
    @Override
    public boolean processRequest(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("err", "");
        if (req.getAttribute("confirm") == null) {
            req.setAttribute("confirm", false);
        }
        return false;
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("admpage", "editbook");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("err", "");
        if (req.getParameter("confirm").equals(true)) {
            long isbn = 0;
            try {
                isbn = Long.valueOf(req.getParameter("isbn").replaceAll("[^0-9]", ""));
            } catch (NumberFormatException e) {
                req.setAttribute("err", "This is not a valid isbn!");
                forwardRequest(req, resp);
                return;
            }
            String title = req.getParameter("title");
            String last = req.getParameter("last");
            String first = req.getParameter("first");
            String profs = req.getParameter("profs");
            profs = profs.replaceAll(",", "::");
            String subj = req.getParameter("subj");
            int num = Integer.valueOf(req.getParameter("num").equals("") ? "0" : req.getParameter("num"));
            req.setAttribute("book", Server.getLibrary().updateBook(isbn, title, last, first, profs, subj, num));
            req.setAttribute("forward", "Book record for " + isbn + " successfully edited!");
            try {
                PageManager.getPage("admin").setMetaAttributes(req);
                req.setAttribute("admpage", "idx");
                req.getRequestDispatcher("/WEB-INF/index.jsp?app=admin").forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        } else {
            long isbn = 0;
            try {
                isbn = Long.valueOf(req.getParameter("code"));
            } catch (NumberFormatException e) {
                req.setAttribute("err", "This is not a valid isbn!");
                forwardRequest(req, resp);
                return;
            }
            Book book = BookRegistry.getBook(isbn);
            if (book == null) {
                req.setAttribute("err", "A book record matching this isbn does not exist");
                forwardRequest(req, resp);
                return;
            }
            req.setAttribute("book", book);
            forwardRequest(req, resp, true);
        }
    }

    private void forwardRequest(HttpServletRequest req, HttpServletResponse resp) {
        forwardRequest(req, resp, false);
    }

    private void forwardRequest(HttpServletRequest req, HttpServletResponse resp, boolean confirm) {
        try {
            PageManager.getPage("admin").setMetaAttributes(req);
            req.setAttribute("admpage", "editbook");
            req.getRequestDispatcher("/WEB-INF/index.jsp?app=admin&page=editbook&confirm="+confirm).forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
