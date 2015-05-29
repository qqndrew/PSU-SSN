package edu.pdx.ssn.application;

import edu.pdx.ssn.sql.Schema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Record {
    // Identifier
    private long isbn;

    // Book loans
    private boolean loaned;
    private Date loan_end;
    private Long loanerUid;

    // Circulation information
    private int checked_out;
    private long checkoutUid;
    private Date dueDate;
    private long barcode;

    public Record(ResultSet result) {
        try {
            barcode = result.getLong(Schema.RECORD_BARCODE);
            isbn = result.getLong(Schema.RECORD_ISBN);
            checked_out = result.getInt(Schema.RECORD_CHECKED_OUT);
            checkoutUid = result.getLong(Schema.RECORD_BORROW_UID);
            long temp = result.getLong(Schema.RECORD_DUE_DATE);
            dueDate = temp == 0 ? null : new Date(temp);
            loaned = result.getBoolean(Schema.RECORD_LOANED);
            loanerUid = result.getLong(Schema.RECORD_LOANER_UID);
            temp = result.getLong(Schema.RECORD_LOAN_END);
            loan_end = temp == 0 ? null : new Date(temp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean in_circulation() {
        return (checked_out == 0) && (!loaned || new Date(System.currentTimeMillis()).before(loan_end));
    }

    public Date getDueDate() {
        return dueDate;
    }

    public long getBarcode() {
        return barcode;
    }

    public Date getLoanEnd() {
        return loan_end;
    }

    public Long getLoanerUid() {
        return loanerUid;
    }

    public Book getBook() {
        return BookRegistry.getBook(isbn);
    }

    public int getCheckoutState() {
        return checked_out;
    }

    public long getCheckoutUid() {
        return checkoutUid;
    }

    public Member getCheckoutMember() {
        if (checkoutUid != 0) {
            return Member.getMember(checkoutUid);
        } else {
            return null;
        }
    }
}
