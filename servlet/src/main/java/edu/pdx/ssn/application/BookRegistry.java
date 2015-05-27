package edu.pdx.ssn.application;

import edu.pdx.ssn.Server;
import edu.pdx.ssn.sql.Schema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class BookRegistry {
    private static BookRegistry instance;

    private HashMap<Long, Book> registry;

    private static final String CATALOG_RETRIEVAL_QUERY = new StringBuilder().append("Select * FROM `")
            .append(Schema.BOOKS_TABLE).append("` WHERE ((`").append(Schema.BOOK_ISBN).append("` LIKE ?) AND (`")
            .append(Schema.BOOK_TITLE).append("` LIKE ?) AND (`")
            .append(Schema.BOOK_AUTHOR_LAST).append("` LIKE ?) AND (`")
            .append(Schema.BOOK_AUTHOR_FIRST).append("` LIKE ?) AND (`")
            .append(Schema.BOOK_SUBJECT).append("` LIKE ?) AND (`")
            .append(Schema.BOOK_COURSE_NUMBER).append("` LIKE ?))").toString();

    BookRegistry() throws SQLException {
        ResultSet result = Server.getConnection().executeQuery("registry_init", true, CATALOG_RETRIEVAL_QUERY, null, null, null, null, null, null);
        registry = new HashMap<>();
        while (result.next()) {
            Book book = new Book(result);
            registry.put(book.getISBN(), book);
        }
    }

    public static Book getBook(long isbn) {
        if (instance == null) {
            try {
                instance = new BookRegistry();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return instance.registry.get(isbn);
    }

    public static void addBook(Book book) {
        instance.registry.put(book.getISBN(), book);
    }

    public static void updateBook(long isbn) {
        if (instance.registry.containsKey(isbn)) {
            instance.registry.remove(isbn);
        }
        ResultSet result = Server.getConnection().executeQuery("registry_init", true, CATALOG_RETRIEVAL_QUERY, isbn, null, null, null, null, null);
        try {
            while (result.next()) {
                Book book = new Book(result);
                instance.registry.put(book.getISBN(), book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
