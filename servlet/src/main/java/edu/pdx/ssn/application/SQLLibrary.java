package edu.pdx.ssn.application;

import edu.pdx.ssn.sql.MySQLConnection;
import edu.pdx.ssn.sql.Schema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SQLLibrary implements Library {

    private MySQLConnection conn;

    public SQLLibrary(MySQLConnection connection) {
        this.conn = connection;
        this.conn.connect();
    }

    private static final String RECORDS_CHECKOUT = "UPDATE `" + Schema.RECORDS_TABLE + "` SET `"
            + Schema.RECORD_CHECKED_OUT + "`=true, `" + Schema.RECORD_BORROW_UID + "`=?, `" + Schema.RECORD_DUE_DATE
            + "`=? WHERE ((`" + Schema.RECORD_CHECKED_OUT + "`=false) AND (`" + Schema.RECORD_BARCODE + "`=?))";

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

    private static final String CREATE_NEW_RECORD = "INSERT INTO `" + Schema.RECORDS_TABLE + "` ("
            + Schema.RECORD_BARCODE + "," + Schema.RECORD_ISBN + "," + Schema.RECORD_LOANED + ","
            + Schema.RECORD_LOANER_UID + "," + Schema.RECORD_LOAN_END + ","
            + Schema.RECORD_CHECKED_OUT + ") VALUES (?,?,?,?,?, false)";

    @Override
    public Book createNewBook(long isbn, String title, String last, String first, String profs, String subj, int num) {
        conn.executeQuery("create_new_book", false, CREATE_NEW_BOOK, isbn, title, last, first, profs, subj, num == 0 ? null : num);
        ResultSet result = conn.executeQuery("catalog_retrieval", true, CATALOG_RETRIEVAL_QUERY, isbn, null, null, null, null, null);
        try {
            result.next();
            Book book = new Book(result);
            BookRegistry.addBook(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public boolean checkout(Long bookUid, long userUid, String dueDate) {
        conn.executeQuery("records", true, RECORDS_CHECKOUT, userUid, dueDate, bookUid);
        ResultSet result = conn.executeQuery("records_retrieve_barcode", true, RECORDS_RETRIEVE_BARCODE, bookUid);
        try {
            if (result.next()) {
                String res = result.getString(Schema.RECORD_BORROW_UID);
                return res != null && Long.valueOf(res).equals(userUid);
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
