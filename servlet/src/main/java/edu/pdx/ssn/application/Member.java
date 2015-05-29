package edu.pdx.ssn.application;

import edu.pdx.ssn.Server;
import edu.pdx.ssn.sql.Schema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Collection;

public class Member {

    private static final String MEMBER_LOOKUP_EMAIL = "SELECT * FROM `" + Schema.USERS_TABLE + "` WHERE `" + Schema.USER_EMAIL + "`=?";
    private static final String MEMBER_LOOKUP_USER = "SELECT * FROM `" + Schema.USERS_TABLE + "` WHERE `" + Schema.USER_UID + "`=?";

    public static Member getMember(String email) {
        ResultSet rs = Server.getConnection().executeQuery("member_lookup_email", false, MEMBER_LOOKUP_EMAIL, email.toLowerCase());
        try {
            if (!rs.next()) {
                return null;
            } else {
                return new Member(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Member getMember(long uid) {
        ResultSet rs = Server.getConnection().executeQuery("member_lookup_uid", false, MEMBER_LOOKUP_USER, uid);
        try {
            if (!rs.next()) {
                return null;
            } else {
                return new Member(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private long uid;
    private String email;
    private String phone;
    private String fname;
    private String lname;
    private boolean isAdmin;
    private Collection<Record> recordsBorrowed;
    private Collection<Record> recordsLoaned;

    public Member(ResultSet result) throws SQLException {
        uid = result.getLong(Schema.USER_UID);
        email = result.getString(Schema.USER_EMAIL);
        String temp = result.getString(Schema.USER_PHONE);
        String s = new DecimalFormat("0000000000").format(Long.valueOf(temp.replaceAll("[^0-9]", "")));
        phone = "(" + s.substring(0, 3) + ") "
                + s.substring(3, 6) + "-"
                + s.substring(6, 10);
        fname = result.getString(Schema.USER_FIRST_NAME);
        lname = result.getString(Schema.USER_LAST_NAME);
        recordsBorrowed = Server.getLibrary().getBorrowedRecords(uid);
        recordsLoaned = Server.getLibrary().getLoanedRecords(uid);
        isAdmin = result.getBoolean(Schema.USER_IS_ADMIN);
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getFirstName() {
        return fname;
    }

    public String getLastName() {
        return lname;
    }

    public long getUid() {
        return uid;
    }

    public Collection<Record> getBorrowedRecords() {
        return recordsBorrowed;
    }

    public Collection<Record> getLoanedRecords() {
        return recordsLoaned;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }
}
