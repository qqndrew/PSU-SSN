package edu.pdx.ssn.application;

import edu.pdx.ssn.sql.MySQLConnection;
import edu.pdx.ssn.sql.Schema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class SQLLibrary implements Library {


    private MySQLConnection conn;

    public SQLLibrary(MySQLConnection connection) {
        this.conn = connection;
        this.conn.connect();
    }

    private static final String CATALOG_CHECKOUT_QUERY = new StringBuilder().append("UPDATE `").append(Schema.BOOKS_TABLE)
            .append("` SET `").append(Schema.BOOK_CHECKED_OUT).append("`=true, `").append(Schema.BOOK_BORROW_UID)
            .append("`=?, `").append(Schema.BOOK_DUE_DATE).append("`=? WHERE ((`").append(Schema.BOOK_CHECKED_OUT)
            .append("`=false) AND (`").append(Schema.BOOK_BARCODE).append("`=?))").toString();

    private static final String CATALOG_RETRIEVAL_QUERY = new StringBuilder().append("Select * FROM `")
            .append(Schema.BOOKS_TABLE).append("` WHERE ((`").append(Schema.BOOK_BARCODE).append("` LIKE ?) AND (`")
            .append(Schema.BOOK_ISBN).append("` LIKE ?) AND (`").append(Schema.BOOK_TITLE).append("` LIKE ?) AND (`")
            .append(Schema.BOOK_AUTHOR_LAST).append("` LIKE ?) AND (`").append(Schema.BOOK_AUTHOR_FIRST).append("` LIKE ?) AND (`")
            .append(Schema.BOOK_SUBJECT).append("` LIKE ?) AND (`").append(Schema.BOOK_COURSE_NUMBER).append("` LIKE ?))").toString();

    private static final String CATALOG_RETRIEVE_UID = new StringBuilder().append("Select * FROM `")
            .append(Schema.BOOKS_TABLE).append("` WHERE `").append(Schema.BOOK_BARCODE).append("`=?").toString();

    private static final String CREATE_NEW_BOOK = new StringBuilder().append("INSERT INTO `").append(Schema.BOOKS_TABLE)
            .append("` (").append(Schema.BOOK_BARCODE).append(",").append(Schema.BOOK_ISBN).append(",").append(Schema.BOOK_TITLE).append(",")
            .append(Schema.BOOK_AUTHOR_LAST).append(",").append(Schema.BOOK_AUTHOR_FIRST).append(Schema.BOOK_ASSIGNING_PROFESSORS).append(",")
            .append(",").append(Schema.BOOK_SUBJECT).append(",").append(Schema.BOOK_COURSE_NUMBER).append(",").append(Schema.BOOK_LOANER_UID)
            .append(",").append(Schema.BOOK_LOAN_END).append(") VALUES (").append("?,?,?,?,?,?,?,?,?,?)").toString();


    @Override
    public void createNew(long barcode, long isbn, String title, String last, String first, String profs, String subj, int num, long donor, long ret) {
        conn.executeQuery("create_new", false, CREATE_NEW_BOOK, barcode, isbn, title, last, first, profs, subj, num, donor);
    }

    @Override
    public List<Book> getCatalog(Long uid, Long isbn, String title, String last, String first, String subj, Integer courseno) {
        ResultSet result = conn.executeQuery("catalog_retrieval", true, CATALOG_RETRIEVAL_QUERY, uid, isbn,
                title, last, first, subj, courseno);
        try {
            List<Book> ret = new LinkedList<>();
            while (result.next()) {
                // TODO: Cache books
                Book entry = new Book(result);
                ret.add(entry);
            }
            System.out.println("Found books: " + ret.size());
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean checkout(Long bookUid, UUID userUid, String dueDate) {
        conn.executeQuery("catalog_checkout", true, CATALOG_CHECKOUT_QUERY, userUid, dueDate, bookUid);
        ResultSet result = conn.executeQuery("retrieve_barcode", true, CATALOG_RETRIEVE_UID, bookUid);
        try {
            if (result.next()) {
                String res = result.getString(Schema.BOOK_BORROW_UID);
                if (res == null) {
                    return false;
                } else {
                    return UUID.fromString(res).equals(userUid);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Book getBook(long barcode) {
        ResultSet result = conn.executeQuery("retrieve_barcode", true, CATALOG_RETRIEVE_UID, barcode);
        try {
            if (result.next()) {
                return new Book(result);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
