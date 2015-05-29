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

public class AdminOverdueNotify implements ServerPage {

    private static final String OVERDUE_QUERY = "SELECT * FROM `" + Schema.RECORDS_TABLE + "` WHERE `" + Schema.RECORD_DUE_DATE + "` IS NOT NULL AND `" + Schema.RECORD_DUE_DATE + "` <=?";
    public static final String PAGE_KEY = "view_overdue";

    @Override
    public boolean processRequest(HttpServletRequest req, HttpServletResponse resp) {
        List<Record> overdue = new LinkedList<>();
        ResultSet rs = Server.getConnection().executeQuery("overdue_records", false, OVERDUE_QUERY, System.currentTimeMillis());
        try {
            while (rs.next()) {
                Record r = new Record(rs);
                if (r.getCheckoutState() == 2) {
                    overdue.add(r);
                }
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
