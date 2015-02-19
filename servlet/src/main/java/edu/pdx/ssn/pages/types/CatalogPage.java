package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.application.Library;
import edu.pdx.ssn.pages.ServerPage;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class CatalogPage implements ServerPage {

    public static final String UID_QUERY_STRING = "uid";
    public static final String ISBN_QUERY_STRING = "isbn";
    public static final String TITLE_QUERY_STRING = "title";
    public static final String AUTHOR_LAST_QUERY_STRING = "last";
    public static final String AUTHOR_FIRST_QUERY_STRING = "first";
    public static final String SUBJECT_QUERY_STRING = "subj";
    public static final String COURSE_QUERY_STRING = "courseno";

    Library library = null;

    @Override
    public String getTitle() {
        return "Catalog";
    }

    @Override
    public void setRequestAttributes(HttpServletRequest req) {
        Map<String, String[]> params = req.getParameterMap();
        Long uid = params.containsKey(UID_QUERY_STRING) ? Long.valueOf(params.get(UID_QUERY_STRING)[0]) : null;
        Long isbn = params.containsKey(ISBN_QUERY_STRING) ? Long.valueOf(params.get(ISBN_QUERY_STRING)[0]) : null;
        String title = params.containsKey(TITLE_QUERY_STRING) ? params.get(TITLE_QUERY_STRING)[0] : null;
        String last = params.containsKey(AUTHOR_LAST_QUERY_STRING) ? params.get(AUTHOR_LAST_QUERY_STRING)[0] : null;
        String first = params.containsKey(AUTHOR_FIRST_QUERY_STRING) ? params.get(AUTHOR_FIRST_QUERY_STRING)[0] : null;
        String subj = params.containsKey(SUBJECT_QUERY_STRING) ? params.get(SUBJECT_QUERY_STRING)[0] : null;
        int courseno = params.containsKey(COURSE_QUERY_STRING) ? Integer.valueOf(params.get(COURSE_QUERY_STRING)[0]) : null;
        req.setAttribute("books", library.getCatalog(uid, isbn, title, last, first, subj, courseno));
    }
}
