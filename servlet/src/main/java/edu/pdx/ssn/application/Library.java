package edu.pdx.ssn.application;

import java.util.List;

public interface Library {
    Book lookupBookByUid(long uid);

    List<Book> getCatalog(Long uid, Long isbn, String title, String last, String first, String subj, int courseno);
}
