package edu.pdx.ssn.pages.types.admin;

import edu.pdx.ssn.Server;
import edu.pdx.ssn.application.Record;
import edu.pdx.ssn.pages.ServerPage;
import edu.pdx.ssn.sql.Schema;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AdminLoanReport implements ServerPage {

    private static final String LOAN_END_QUERY = "SELECT * FROM `" + Schema.RECORDS_TABLE + "` WHERE `" + Schema.RECORD_LOAN_END + "` IS NOT NULL AND `" + Schema.RECORD_LOAN_END + "` <=?";
    public static final String PAGE_KEY = "loan_report";

    @Override
    public boolean processRequest(HttpServletRequest req, HttpServletResponse resp) {
        List<Record> overdue = new LinkedList<>();
        ResultSet rs = Server.getConnection().executeQuery("loan_ending", false, LOAN_END_QUERY, System.currentTimeMillis() + 604800000);
        try {
            while (rs.next()) {
                overdue.add(new Record(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("records", overdue);
        return false;
    }

    @Override
    public void setMetaAttributes(HttpServletRequest req) {
        req.setAttribute("title", "View Overdue");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {

    }
}
