package edu.pdx.ssn.application;

import edu.pdx.ssn.sql.MySQLConnection;
import edu.pdx.ssn.sql.Schema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SQLLibrary implements Library {

    private MySQLConnection conn;

    public SQLLibrary(MySQLConnection connection) {
        this.conn = connection;
    }

    private static final String CATALOG_RETRIEVAL_QUERY = new StringBuilder().append("Select * FROM ")
            .append(Schema.BOOKS_TABLE).append(" WHERE ((").append(Schema.BOOK_BARCODE).append("=?) AND (")
            .append(Schema.BOOK_ISBN).append("=?) AND (").append(Schema.BOOK_TITLE).append( "LIKE ?) AND (")
            .append(Schema.BOOK_AUTHOR_LAST).append("=?) AND (").append(Schema.BOOK_AUTHOR_FIRST).append("=?) AND (")
            .append(Schema.BOOK_SUBJECT).append("=?) AND (").append(Schema.BOOK_COURSE_NUMBER).append("=?))").toString();

    @Override
    public List<Book> getCatalog(Long uid, Long isbn, String title, String last, String first, String subj, Integer courseno) {
        ResultSet result = conn.executeQuery("catalog_retrieval", true, CATALOG_RETRIEVAL_QUERY, uid, isbn,
                title.toLowerCase(), last.toLowerCase(), first.toLowerCase(), subj.toLowerCase(), courseno);
        try {
            List<Book> ret = new LinkedList<>();
            while (result.next()) {
                // TODO: Cache books
                Book entry = new Book(result);
                ret.add(entry);
            }
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
