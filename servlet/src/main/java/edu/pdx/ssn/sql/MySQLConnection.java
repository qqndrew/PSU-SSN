package edu.pdx.ssn.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class MySQLConnection {

    private String host;
    private String username;
    private String password;
    private Integer port;
    private String database;
    private String url;
    private Connection con;

    private HashMap<String, PreparedStatement> statements;

    public MySQLConnection(String host, Integer port, String username, String password, String database) {
        if (host == null || username == null || password == null || port == null || database == null) {
            throw new IllegalStateException("Not all necessary information is provided to mysql connection creation");
        }
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.url = generateUrl();
        this.statements = new HashMap<>();
    }

    private String generateUrl() {
        return "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database;
    }

    public boolean connect() {
        try {
            con = DriverManager.getConnection(this.url, this.username, this.password);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet executeQuery(String ident, boolean starNulls, String query, Object... params) {
        try {
            PreparedStatement ps;
            if (ident != null && statements.containsKey(ident)) {
                ps = statements.get(ident);
            } else {
                ps = con.prepareStatement(query);
                if (ident != null) {
                    statements.put(ident, ps);
                }
            }
            for (int i = 0; i < params.length; i++) {
                if (params[i] != null) {
                    ps.setObject(i + 1, params[i]);
                } else {
                    ps.setObject(i + 1, starNulls ? "*" : null);
                }
            }
            if (ps.execute()) {
                return ps.getResultSet();
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean disconnect() {
        try {
            con.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
