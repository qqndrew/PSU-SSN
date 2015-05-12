package edu.pdx.ssn.sql;

public final class Schema {

    /* Users Table */
    public static final String USERS_TABLE = "users";
    public static final String USER_UID = "id";
    public static final String USER_EMAIL = "email";
    public static final String USER_LAST_NAME = "lname";
    public static final String USER_FIRST_NAME = "fname";
    public static final String USER_PASSWORD_HASH = "pwd";
    public static final String USER_PHONE = "phone";
    public static final String USER_IS_ADMIN = "admin";

    /* Catalog/Book List */

    // General Information
    public static final String BOOKS_TABLE = "catalog";
    public static final String BOOK_BARCODE = "id";
    public static final String BOOK_ISBN = "isbn";
    public static final String BOOK_TITLE = "title";
    public static final String BOOK_AUTHOR_LAST = "lname";
    public static final String BOOK_AUTHOR_FIRST = "fname";
    // Circulation Information
    public static final String BOOK_CHECKED_OUT = "checked_out";
    public static final String BOOK_BORROW_UID = "borrower_uid";
    public static final String BOOK_DUE_DATE = "due_date";
    // Loaner Books (Clerical Information)
    public static final String BOOK_LOANED = "loaned";
    public static final String BOOK_LOANER_UID = "loaner_uid";
    public static final String BOOK_LOAN_END = "loaner_end";
    // School Information
    public static final String BOOK_SUBJECT = "subject";
    public static final String BOOK_COURSE_NUMBER = "courseno";
    public static final String BOOK_ASSIGNING_PROFESSORS = "professors";

}
