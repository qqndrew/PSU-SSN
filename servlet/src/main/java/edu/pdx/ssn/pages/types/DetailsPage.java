package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.Params;
import edu.pdx.ssn.application.Book;
import edu.pdx.ssn.application.Library;
import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class DetailsPage implements ServerPage {

    public static final String PAGE_KEY = "details";

    Library library = null;


    @Override
    public void setRequestAttributes(HttpServletRequest req) {
        Map<String, String[]> params = req.getParameterMap();
        Long isbn = params.containsKey(Params.ISBN) ? Long.valueOf(params.get(Params.ISBN)[0]) : null;
        req.setAttribute("books", library.getCatalog(null, isbn, null, null, null, null, null));
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("title", "Book Details - " + ((Book)req.getAttribute("book")).getTitle());
        req.setAttribute("app", PAGE_KEY);
    }
}
