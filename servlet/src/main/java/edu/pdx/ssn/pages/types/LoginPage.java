package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.Server;
import edu.pdx.ssn.Sessions;
import edu.pdx.ssn.pages.ServerPage;
import edu.pdx.ssn.sql.Schema;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPage implements ServerPage {

    public static final String PAGE_KEY = "login";

    private static final String query = "SELECT * FROM `" + Schema.USERS_TABLE + "` WHERE `" + Schema.USER_EMAIL + "`=?" +
            " AND `" + Schema.USER_PASSWORD_HASH + "`=?";


    @Override
    public void processRequest(HttpServletRequest req) {
        String user = req.getParameter("user").toLowerCase();
        String password = req.getParameter("password");
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            ResultSet result = Server.getConnection().executeQuery("user-login", false, query, user, hash);
            if (result.next()) {
                HttpSession s = req.getSession();
                s.setAttribute(Sessions.IS_LOGGED_IN, true);
                s.setAttribute(Sessions.USER_ID, result.getLong(Schema.USER_UID));
                s.setAttribute(Sessions.FIRST_NAME, result.getString(Schema.USER_FIRST_NAME));
                s.setAttribute(Sessions.LAST_NAME, result.getString(Schema.USER_LAST_NAME));
                s.setAttribute(Sessions.EMAIL, result.getString(Schema.USER_EMAIL));
                s.setAttribute(Sessions.PHONE, result.getString(Schema.USER_PHONE));
                s.setAttribute(Sessions.ADMIN, result.getBoolean(Schema.USER_IS_ADMIN));
                req.setAttribute("success", true);
            } else {
                req.setAttribute("success", false);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("title", "Login");
        req.setAttribute("app", PAGE_KEY);
    }
}
