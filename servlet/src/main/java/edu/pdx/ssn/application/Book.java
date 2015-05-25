package edu.pdx.ssn.application;

import edu.pdx.ssn.sql.Schema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

public class Book {

    // Generic Book Information
    private long isbn;
    private String title;
    private String authorLast;
    private String authorFirst;




    // Class information
    private String subject;
    private Integer number;
    private Collection<String> professors;

    public Book(ResultSet result) {
        try {
            isbn = result.getLong(Schema.BOOK_ISBN);
            title = result.getString(Schema.BOOK_TITLE);
            authorLast = result.getString(Schema.BOOK_AUTHOR_LAST);
            authorFirst = result.getString(Schema.BOOK_AUTHOR_FIRST);
            subject = result.getString(Schema.BOOK_SUBJECT);
            number = result.getInt(Schema.BOOK_COURSE_NUMBER);
            if (number == 0) {
                number = null;
            }

            professors = Arrays.asList(result.getString(Schema.BOOK_ASSIGNING_PROFESSORS).split("::"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Book(long barcode, long isbn, String title, String last, String first, String profs, String subj, int num, long donor, long ret) {
        this.isbn = isbn;
        this.title = title;
        this.authorLast = last;
        this.authorFirst = first;
        this.professors = Arrays.asList(profs.split("::"));
        this.subject = subj;
        this.number = num;
    }

    public long getISBN() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorLast() {
        return authorLast;
    }

    public String getAuthorFirst() {
        return authorFirst;
    }

    public String getSubject() {
        return subject;
    }

    public Integer getNumber() {
        return number;
    }

    public Collection<String> getProfessors() {
        return professors;
    }

}
