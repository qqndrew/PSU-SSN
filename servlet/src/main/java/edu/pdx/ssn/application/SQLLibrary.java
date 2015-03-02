package edu.pdx.ssn.application;

import java.util.List;

public class SQLLibrary implements Library {
    @Override
    public Book lookupBookByUid(long uid) {
        return null;
    }

    @Override
    public List<Book> getCatalog(Long uid, Long isbn, String title, String last, String first, String subj, Integer courseno) {
        return null;
    }
}
