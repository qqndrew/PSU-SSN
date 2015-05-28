package edu.pdx.ssn.application;

import edu.pdx.ssn.sql.MySQLConnection;
import edu.pdx.ssn.sql.Schema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class SQLLibrary implements Library {

    private MySQLConnection conn;

    public SQLLibrary(MySQLConnection connection) {
        this.conn = connection;
        this.conn.connect();
    }

    private static final String RECORDS_HOLD = "UPDATE `" + Schema.RECORDS_TABLE + "` SET `"
            + Schema.RECORD_CHECKED_OUT + "`=1, `" + Schema.RECORD_BORROW_UID + "`=?, `" + Schema.RECORD_DUE_DATE
            + "`=? WHERE ((`" + Schema.RECORD_CHECKED_OUT + "`=0) AND (`" + Schema.RECORD_BARCODE + "`=?))";

    private static final String RECORDS_CHECKOUT = "UPDATE `" + Schema.RECORDS_TABLE + "` SET `"
            + Schema.RECORD_CHECKED_OUT + "`=2, `" + Schema.RECORD_DUE_DATE
            + "`=? WHERE ((`" + Schema.RECORD_CHECKED_OUT + "`=1) AND (`" + Schema.RECORD_BARCODE + "`=?))";

    private static final String RECORDS_CHECKIN = "UPDATE `" + Schema.RECORDS_TABLE + "` SET `"
            + Schema.RECORD_CHECKED_OUT + "`=0, `" + Schema.RECORD_LOANER_UID + "`=NULL WHERE (`" + Schema.RECORD_BARCODE + "`=?)";

    private static final String CATALOG_RETRIEVAL_QUERY = "Select * FROM `" + Schema.BOOKS_TABLE + "` WHERE ((`"
            + Schema.BOOK_ISBN + "` LIKE ?) AND (`" + Schema.BOOK_TITLE + "` LIKE ?) AND (`" + Schema.BOOK_AUTHOR_LAST
            + "` LIKE ?) AND (`" + Schema.BOOK_AUTHOR_FIRST + "` LIKE ?) AND (`" + Schema.BOOK_SUBJECT
            + "` LIKE ?) AND (`" + Schema.BOOK_COURSE_NUMBER + "` LIKE ?))";

    private static final String RECORDS_RETRIEVE_ISBN = "Select * FROM `" + Schema.RECORDS_TABLE + "` WHERE `"
            + Schema.RECORD_ISBN + "`=?";

    private static final String RECORDS_RETRIEVE_BARCODE = "SELECT * FROM `" + Schema.RECORDS_TABLE + "` WHERE `"
            + Schema.RECORD_BARCODE + "`=?";

    private static final String CREATE_NEW_BOOK = "INSERT INTO `" + Schema.BOOKS_TABLE + "` (" + Schema.BOOK_ISBN + ","
            + Schema.BOOK_TITLE + "," + Schema.BOOK_AUTHOR_LAST + "," + Schema.BOOK_AUTHOR_FIRST + ","
            + Schema.BOOK_ASSIGNING_PROFESSORS + "," + Schema.BOOK_SUBJECT + "," + Schema.BOOK_COURSE_NUMBER
            + ") VALUES (" + "?,?,?,?,?,?,?)";

    private static final String UPDATE_BOOK = "UPDATE `" + Schema.BOOKS_TABLE + "` SET `"
            + Schema.BOOK_TITLE + "`=?,`" + Schema.BOOK_AUTHOR_LAST + "`=?,`" + Schema.BOOK_AUTHOR_FIRST + "`=?,`"
            + Schema.BOOK_ASSIGNING_PROFESSORS + "`=?,`" + Schema.BOOK_SUBJECT + "`=?,`" + Schema.BOOK_COURSE_NUMBER
            + "`=? WHERE (`" + Schema.BOOK_ISBN + "`=?)";

    private static final String CREATE_NEW_RECORD = "INSERT INTO `" + Schema.RECORDS_TABLE + "` ("
            + Schema.RECORD_BARCODE + "," + Schema.RECORD_ISBN + "," + Schema.RECORD_LOANED + ","
            + Schema.RECORD_LOANER_UID + "," + Schema.RECORD_LOAN_END + ","
            + Schema.RECORD_CHECKED_OUT + ") VALUES (?,?,?,?,?, 0)";

    private static final String RECORDS_BORROWED = "SELECT * FROM `" + Schema.RECORDS_TABLE + "` WHERE `" + Schema.RECORD_BORROW_UID + "`=?";
    private static final String RECORDS_LOANED = "SELECT * FROM `" + Schema.RECORDS_TABLE + "` WHERE `" + Schema.RECORD_LOANER_UID + "`=?";

    @Override
    public Book createNewBook(long isbn, String title, String last, String first, String profs, String subj, int num) {
        conn.executeQuery("create_new_book", false, CREATE_NEW_BOOK, isbn, title, last, first, profs, subj, num == 0 ? null : num);
        BookRegistry.updateBook(isbn);
        return getBook(isbn);
    }

    @Override
    public Collection<Record> getRecords(long isbn) {
        ResultSet rs = conn.executeQuery("records_retrieve_isbn", false, RECORDS_RETRIEVE_ISBN, isbn);
        Collection<Record> ret = new LinkedList<>();
        try {
            while (rs.next()) {
                ret.add(new Record(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public Record createRecord(long barcode, long isbn, Long donorUid, Long retDate) {
        conn.executeQuery("record_create", false, CREATE_NEW_RECORD, barcode, isbn, donorUid != null ? 1 : 0, donorUid, retDate);
        return getRecord(barcode);
    }

    @Override
    public void checkin(long barcode) {
        conn.executeQuery("record_checkin", false, RECORDS_CHECKIN, barcode);
    }

    @Override
    public Book updateBook(long isbn, String title, String last, String first, String profs, String subj, int num) {
        conn.executeQuery("update_book", false, UPDATE_BOOK, title, last, first, profs, subj, num, isbn);
        BookRegistry.updateBook(isbn);
        return getBook(isbn);
    }

    @Override
    public boolean checkout(long barcode, long date) {
        conn.executeQuery("records_checkout", true, RECORDS_CHECKOUT, date, barcode);
        return true;
    }

    @Override
    public List<Record> getBorrowedRecords(long uid) {
        ResultSet rs = conn.executeQuery("get_borrowed", false, RECORDS_BORROWED, uid);
        List<Record> ret = new LinkedList<>();
        try {
            while (rs.next()) {
                ret.add(new Record(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public List<Record> getLoanedRecords(long uid) {
        ResultSet rs = conn.executeQuery("get_loaned", false, RECORDS_LOANED, uid);
        List<Record> ret = new LinkedList<>();
        try {
            while (rs.next()) {
                ret.add(new Record(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public Record getRecord(long barcode) {
        ResultSet rs = conn.executeQuery("records_retrieve_barcode", false, RECORDS_RETRIEVE_BARCODE, barcode);
        try {
            if (rs.next()) {
                return new Record(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getCatalog(Long isbn, String title, String last, String first, String subj, Integer courseno) {
        ResultSet result = conn.executeQuery("catalog_retrieval", true, CATALOG_RETRIEVAL_QUERY, isbn,
                title, last, first, subj, courseno);
        try {
            List<Book> ret = new LinkedList<>();
            while (result.next()) {
                Book entry = new Book(result);
                ret.add(entry);
            }
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean hold(Long bookUid, long userUid, Date dueDate) {
        conn.executeQuery("records_hold", true, RECORDS_HOLD, userUid, dueDate.getTime(), bookUid);
        ResultSet result = conn.executeQuery("records_retrieve_barcode", true, RECORDS_RETRIEVE_BARCODE, bookUid);
        try {
            if (result.next()) {
                return result.getLong(Schema.RECORD_BORROW_UID) == userUid;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public Book getBook(long isbn) {
        return BookRegistry.getBook(isbn);
    }


}
