package edu.pdx.ssn.application;

import edu.pdx.ssn.sql.Schema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

public class Book {

    // Generic Book Information
    private long isbn;
    private String title;
    private String authorLast;
    private String authorFirst;

    // Circulation information
    private boolean checked_out;
    private long checkoutUid;
    private Date dueDate;
    private long barcode;

    // Book loans
    private boolean loaned;
    private Date loan_end;
    private long loanerUid;

    // Class information
    private String subject;
    private int number;
    private Collection<String> professors;

    public Book(ResultSet result) {
        try {
            barcode = result.getLong(Schema.BOOK_BARCODE);
            isbn = result.getLong(Schema.BOOK_ISBN);
            title = result.getString(Schema.BOOK_TITLE);
            authorLast = result.getString(Schema.BOOK_AUTHOR_LAST);
            authorFirst = result.getString(Schema.BOOK_AUTHOR_FIRST);
            subject = result.getString(Schema.BOOK_SUBJECT);
            number = result.getInt(Schema.BOOK_COURSE_NUMBER);
            checked_out = result.getBoolean(Schema.BOOK_CHECKED_OUT);
            checkoutUid = result.getLong(Schema.BOOK_BORROW_UID);
            dueDate = result.getDate(Schema.BOOK_DUE_DATE);
            loaned = result.getBoolean(Schema.BOOK_LOANED);
            loanerUid = result.getLong(Schema.BOOK_LOANER_UID);
            loan_end = result.getDate(Schema.BOOK_LOAN_END);
            professors = Arrays.asList(result.getString(Schema.BOOK_ASSIGNING_PROFESSORS).split("::"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean in_circulation() {
        return !checked_out && (!loaned || new Date(System.currentTimeMillis()).before(loan_end));
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

    public Date getDueDate() {
        return dueDate;
    }

    public long getBarcode() {
        return barcode;
    }

    public String getSubject() {
        return subject;
    }

    public int getNumber() {
        return number;
    }

    public Collection<String> getProfessors() {
        return professors;
    }

    public Date getLoanEnd() {
        return loan_end;
    }

    public long getLoanerUid() {
        return loanerUid;
    }
}
