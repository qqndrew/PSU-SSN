package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.Params;
import edu.pdx.ssn.application.Library;
import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class CatalogPage implements ServerPage {

    public static final String PAGE_KEY = "catalog";

    Library library = null;


    @Override
    public void setRequestAttributes(HttpServletRequest req) {
        Map<String, String[]> params = req.getParameterMap();
        Long uid = params.containsKey(Params.UID) ? Long.valueOf(params.get(Params.UID)[0]) : null;
        Long isbn = params.containsKey(Params.ISBN) ? Long.valueOf(params.get(Params.ISBN)[0]) : null;
        String title = params.containsKey(Params.TITLE) ? params.get(Params.TITLE)[0] : null;
        String last = params.containsKey(Params.AUTHOR_LAST) ? params.get(Params.AUTHOR_LAST)[0] : null;
        String first = params.containsKey(Params.AUTHOR_FIRST) ? params.get(Params.AUTHOR_FIRST)[0] : null;
        String subj = params.containsKey(Params.SUBJECT) ? params.get(Params.SUBJECT)[0] : null;
        int courseno = params.containsKey(Params.COURSE) ? Integer.valueOf(params.get(Params.COURSE)[0]) : null;
        req.setAttribute("books", library.getCatalog(uid, isbn, title, last, first, subj, courseno));
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("title", "Catalog");
        req.setAttribute("app", PAGE_KEY);
    }
}
