package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.Params;
import edu.pdx.ssn.Server;
import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class CatalogPage implements ServerPage {

    public static final String PAGE_KEY = "catalog";



    @Override
    public void setRequestAttributes(HttpServletRequest req) {
        Map<String, String[]> params = req.getParameterMap();
        Long uid = params.containsKey(Params.UID.getKey()) ? Long.valueOf(params.get(Params.UID.getKey())[0]) : null;
        Long isbn = params.containsKey(Params.ISBN.getKey()) ? Long.valueOf(params.get(Params.ISBN.getKey())[0]) : null;
        String title = params.containsKey(Params.TITLE.getKey()) ? params.get(Params.TITLE.getKey())[0] : null;
        String last = params.containsKey(Params.AUTHOR_LAST.getKey()) ? params.get(Params.AUTHOR_LAST.getKey())[0] : null;
        String first = params.containsKey(Params.AUTHOR_FIRST.getKey()) ? params.get(Params.AUTHOR_FIRST.getKey())[0] : null;
        String subj = params.containsKey(Params.SUBJECT.getKey()) ? params.get(Params.SUBJECT.getKey())[0] : null;
        Integer courseno;
        if (params.containsKey(Params.COURSE.getKey()))
            courseno = Integer.valueOf(params.get(Params.COURSE.getKey())[0]);
        else {
            courseno = null;
        }
        req.setAttribute("books", Server.getLibrary().getCatalog(uid, isbn, title, last, first, subj, courseno));
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("title", "Catalog");
        req.setAttribute("app", PAGE_KEY);
    }
}
