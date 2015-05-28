package edu.pdx.ssn.pages.types.admin;

import edu.pdx.ssn.Server;
import edu.pdx.ssn.pages.PageManager;
import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminCreateNew implements ServerPage {



    @Override
    public boolean processRequest(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("err", "");
        req.setAttribute("confirm", false);
        return false;
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getAttribute("confirm") == null || req.getAttribute("confirm").equals(false)) {
            long isbn = 0;
            try {
                isbn = Long.valueOf(req.getParameter("isbn").replaceAll("[^0-9]", ""));
            } catch (NumberFormatException e) {
                req.setAttribute("err", "This is not a valid isbn!");
                req.setAttribute("confirm", false);
                forward(req, resp);
                return;
            }
            String title = req.getParameter("title");
            String last = req.getParameter("last");
            String first = req.getParameter("first");
            String profs = req.getParameter("profs");
            profs = profs.replaceAll(",", "::");
            String subj = req.getParameter("subj");
            int num = Integer.valueOf(req.getParameter("num").equals("") ? "0" : req.getParameter("num"));
            if (Server.getLibrary().getBook(isbn) != null) {
                req.setAttribute("err", "This book is already registered!");
                req.setAttribute("confirm", false);
            } else {
                req.setAttribute("book", Server.getLibrary().createNewBook(isbn, title, last, first, profs, subj, num));
                req.setAttribute("confirm", true);
                req.setAttribute("err", "");
                req.setAttribute("forward", "Book successfully created!");
            }
            forward(req, resp);
        }
    }

    private void forward(HttpServletRequest req, HttpServletResponse resp) {
        try {
            PageManager.getPage("admin").setMetaAttributes(req);
            req.setAttribute("admpage", "create_new");
            req.getRequestDispatcher("/WEB-INF/index.jsp?app=admin&page=create_new").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
