package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.Server;
import edu.pdx.ssn.Sessions;
import edu.pdx.ssn.pages.ServerPage;
import edu.pdx.ssn.sql.Schema;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
        req.setAttribute("message", "");
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("title", "Login");
        req.setAttribute("app", PAGE_KEY);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String user = req.getParameter("user").toLowerCase();
        String password = req.getParameter("password");
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] digested = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hashBuilder = new StringBuilder();
            for(int i=0;i<digested.length;i++){
                hashBuilder.append(Integer.toHexString(0xff & digested[i]));
            }
            ResultSet result = Server.getConnection().executeQuery("user-login", false, query, user, hashBuilder.toString());
            if (result.next()) {
                HttpSession s = req.getSession();
                s.setAttribute(Sessions.IS_LOGGED_IN, true);
                s.setAttribute(Sessions.USER_ID, result.getLong(Schema.USER_UID));
                s.setAttribute(Sessions.FIRST_NAME, result.getString(Schema.USER_FIRST_NAME));
                s.setAttribute(Sessions.LAST_NAME, result.getString(Schema.USER_LAST_NAME));
                s.setAttribute(Sessions.EMAIL, result.getString(Schema.USER_EMAIL));
                s.setAttribute(Sessions.PHONE, result.getString(Schema.USER_PHONE));
                s.setAttribute(Sessions.ADMIN, result.getBoolean(Schema.USER_IS_ADMIN));
                req.setAttribute("app", "idx");
                s.setAttribute("message", "");
                req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
            } else {
                req.setAttribute("message", "Information Incorrect, please try again");
                req.setAttribute("app", "login");
                req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
