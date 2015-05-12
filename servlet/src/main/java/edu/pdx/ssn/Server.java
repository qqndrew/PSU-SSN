package edu.pdx.ssn;

import edu.pdx.ssn.application.Library;
import edu.pdx.ssn.application.SQLLibrary;
import edu.pdx.ssn.pages.PageManager;
import edu.pdx.ssn.pages.ServerPage;
import edu.pdx.ssn.sql.MySQLConnection;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public final class Server extends HttpServlet implements Sessions {

    protected static final String CONFIGURATION_FILE_NAME = "configuration.yml";

    protected static Server instance;
    protected static ServerConfiguration config;
    protected static Library library;
    protected static MySQLConnection conn;

    public static Server getInstance() {
        return instance;
    }

    public static ServerConfiguration getConfig() {
        return config;
    }

    public static Library getLibrary() {
        return library;
    }

    public static MySQLConnection getConnection() {
        return conn;
    }

    @Override
    public void init() throws ServletException {
        instance = this;
        File configFile = new File(CONFIGURATION_FILE_NAME);
        if (!configFile.exists()) {
            throw new ServletException("Could not load configuration! Please ensure configuration.yml is present!.");
        }
        Yaml configYml = new Yaml();
        try {
            config = new ServerConfiguration((Map<String, Object>)configYml.load(new FileInputStream(configFile)));
            String server = config.getValue("mysql.server");
            int port = Integer.valueOf(config.getValue("mysql.port"));
            String user = config.getValue("mysql.username");
            String pass = config.getValue("mysql.password");
            String database = config.getValue("mysql.database");
            conn = new MySQLConnection(server, port, user, pass, database);
            library = new SQLLibrary(conn);
        } catch (FileNotFoundException e) {
            throw new ServletException("Could not load configuration! This should never be reached.");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Construct session
        HttpSession session = req.getSession();
        if (session.isNew()) {
            session.setAttribute(IS_LOGGED_IN, false);
            session.setAttribute(ADMIN, false);
        }
        // Determine page
        ServerPage page;
        String pageKey;
        Map<String, String[]> params = req.getParameterMap();
        if (params.containsKey(Params.APP.getKey())) {
            pageKey = params.get(Params.APP.getKey())[0];
            page = PageManager.getPage(pageKey);
        } else {
            page = PageManager.getPage(null);
            pageKey = PageManager.DEFAULT_KEY;
        }
        // Process the request and set appropriate attributes
        page.processRequest(req);
        // Set meta attributes
        page.setMetaAttributes(req);
        // Redirect to page
        req.getRequestDispatcher("/WEB-INF/index.jsp?app=" + pageKey).forward(req, resp);
    }

}


