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
    private Long loanerUid;

    // Class information
    private String subject;
    private Integer number;
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
            if (number == 0) {
                number = null;
            }
            checked_out = result.getBoolean(Schema.BOOK_CHECKED_OUT);
            checkoutUid = result.getLong(Schema.BOOK_BORROW_UID);
            long temp = result.getLong(Schema.BOOK_DUE_DATE);
            dueDate = temp == 0 ? null : new Date(temp);
            loaned = result.getBoolean(Schema.BOOK_LOANED);
            loanerUid = result.getLong(Schema.BOOK_LOANER_UID);
            temp = result.getLong(Schema.BOOK_LOAN_END);
            loan_end = temp == 0 ? null : new Date(temp);
            professors = Arrays.asList(result.getString(Schema.BOOK_ASSIGNING_PROFESSORS).split("::"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Book(long barcode, long isbn, String title, String last, String first, String profs, String subj, int num, long donor, long ret) {
        this.barcode = barcode;
        this.isbn = isbn;
        this.title = title;
        this.authorLast = last;
        this.authorFirst = first;
        this.professors = Arrays.asList(profs.split("::"));
        this.subject = subj;
        this.number = num;
        if (donor != 0) {
            this.loaned = true;
            this.loanerUid = donor;
            this.loan_end = new Date(ret);
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

    public Integer getNumber() {
        return number;
    }

    public Collection<String> getProfessors() {
        return professors;
    }

    public Date getLoanEnd() {
        return loan_end;
    }

    public Long getLoanerUid() {
        return loanerUid;
    }
}
