package edu.pdx.ssn.application;

import java.util.Date;

public class Book {

    // Generic Book Information
    private long isbn;
    private String title;
    private String authorLast;
    private String authorFirst;

    // Circulation information
    private boolean checked_out;
    long checkoutUid;
    private Date due_date;
    private long barcode;

    // Book loans
    private boolean loaned;
    private Date loan_end;
    private long loanerUid;

    // Class information
    private String subject;
    private int number;
    private String professor;

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
        return due_date;
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

    public String getProfessor() {
        return professor;
    }

    public Date getLoanEnd() {
        return loan_end;
    }
}
