package edu.pdx.ssn.application;

import java.util.List;

public interface Library {

    List<Book> getCatalog(Long uid, Long isbn, String title, String last, String first, String subj, Integer courseno);

    boolean checkout(Long bookUid, Object userUid, String dueDate);

}
