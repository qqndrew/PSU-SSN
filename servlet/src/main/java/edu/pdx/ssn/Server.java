package edu.pdx.ssn;

import edu.pdx.ssn.pages.PageManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public final class Server extends HttpServlet implements Sessions {

    protected static final String CONFIGURATION_FILE_NAME = "configuration.yml";

    protected static Server instance;
    protected static ServerConfiguration config;

    @Override
    public void init() throws ServletException {
        File configFile = new File(CONFIGURATION_FILE_NAME);
        // Check for presence of configuration file
        /*if (!configFile.exists()) {
            throw new ServletException("No configuration file provided!");
        }
        Yaml configYml = new Yaml();
        try {
            config = new ServerConfiguration((Map<String, Object>)configYml.load(new FileInputStream(configFile)));

        } catch (FileNotFoundException e) {
            throw new ServletException("Could not load configuration! This should never be reached.");
        }*/
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Doing get");
        // Construct session
        HttpSession session = req.getSession();
        if (session.isNew()) {
            session.setAttribute(IS_LOGGED_IN, false);
            session.setAttribute(ADMIN, false);
        }
        // Determine page
        String page;
        Map<String, String[]> params = req.getParameterMap();
        if (params.containsKey(Params.APP.getKey())) {
            String pageKey = params.get(Params.APP.getKey())[0];
            page = PageManager.getPage(pageKey);
        } else {
            page = PageManager.getPage(null);
        }
        String title = PageManager.getTitle(page);
        req.setAttribute("title", title);
        req.setAttribute("app", page);
        // Redirect to page
        req.getRequestDispatcher("/WEB-INF/index.jsp?app=" + page).forward(req, resp);
    }

}


