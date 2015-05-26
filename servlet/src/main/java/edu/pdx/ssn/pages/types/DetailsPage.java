package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.Params;
import edu.pdx.ssn.Server;
import edu.pdx.ssn.application.Book;
import edu.pdx.ssn.application.BookRegistry;
import edu.pdx.ssn.application.Record;
import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

public class DetailsPage implements ServerPage {

    public static final String PAGE_KEY = "details";

    @Override
    public boolean processRequest(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String[]> params = req.getParameterMap();
        Long isbn = params.containsKey(Params.ISBN.getKey()) ? Long.valueOf(params.get(Params.ISBN.getKey())[0]) : null;
        Collection<Record> catalog = Server.getLibrary().getRecords(isbn);
        req.setAttribute("books", catalog);
        req.setAttribute("book", BookRegistry.getBook(isbn));
        return false;
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("title", req.getAttribute("book") != null ? "Book Details - "
                + ((Book)req.getAttribute("book")).getTitle() : "Book Details - Not Found!");
        req.setAttribute("app", PAGE_KEY);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {

    }
}
