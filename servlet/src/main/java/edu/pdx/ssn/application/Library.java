package edu.pdx.ssn.application;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface Library {

    Record getRecord(long barcode);

    List<Book> getCatalog(Long isbn, String title, String last, String first, String subj, Integer courseno);

    boolean checkout(Long bookUid, UUID userUid, String dueDate);

    Book getBook(long barcode);

    Book createNewBook(long isbn, String title, String last, String first, String profs, String subj, int num);

    Collection<Record> getRecords(long isbn);

    Record createRecord(long barcode, long isbn, Long donorUid, Long retDate);
}
