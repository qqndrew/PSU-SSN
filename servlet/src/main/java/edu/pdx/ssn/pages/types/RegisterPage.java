package edu.pdx.ssn.pages.types;

import edu.pdx.ssn.Server;
import edu.pdx.ssn.pages.ServerPage;
import edu.pdx.ssn.sql.Schema;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterPage implements ServerPage {

    private static String REGISTER_QUERY =  "INSERT INTO `" + Schema.USERS_TABLE + "` (` "
            + Schema.USER_EMAIL + "`, `" + Schema.USER_PASSWORD_HASH + "`, `"
            + Schema.USER_LAST_NAME + "`, `" + Schema.USER_FIRST_NAME + "`, `"
            + Schema.USER_PHONE + "` VALUES (?,?,?,?,?)";
    private static String LOOKUP_QUERY = "SELECT * FROM `" + Schema.USERS_TABLE + "` WHERE " +
            "`" + Schema.USER_EMAIL + "`=?";
    public static String PAGE_KEY = "register";

    @Override
    public void processRequest(HttpServletRequest req) {
        req.setAttribute("errmessage", "");
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("title", "Register New Account");
        req.setAttribute("app", PAGE_KEY);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String user = req.getParameter("user").toLowerCase();
        String password = req.getParameter("password");
        String passwordConfirm = req.getParameter("confpassword");
        String phone = req.getParameter("phone");
        String firstName = req.getParameter("first");
        String lastName = req.getParameter("last");
        if (!password.equals(passwordConfirm)) {
            req.setAttribute("errmessage", "Password Mismatch, please try again!");
            req.setAttribute("app", "register");
            try {
                req.getRequestDispatcher("/WEB-INF/index.jsp?app=register").forward(req, resp);
                return;
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (Server.getConnection().executeQuery("lookupemail", false, LOOKUP_QUERY, user).next()) {
                    req.setAttribute("errmessage", "A registration already exists for this email address!");
                    req.setAttribute("app", "register");
                    req.getRequestDispatcher("/WEB-INF/index.jsp?app=register").forward(req, resp);
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Server.getConnection().executeQuery("register", false, REGISTER_QUERY, user, password, lastName, firstName, phone);
        try {
            req.getRequestDispatcher("/WEB-INF/index.jsp?app=idx").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
