package edu.pdx.ssn;

import org.yaml.snakeyaml.Yaml;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public final class Server extends HttpServlet {

    protected static final String CONFIGURATION_FILE_NAME = "configuration.yml";

    protected static Server instance;
    protected static ServerConfiguration config;

    @Override
    public void init() throws ServletException {
        File configFile = new File(CONFIGURATION_FILE_NAME);
        // Check for presence of configuration file
        if (!configFile.exists()) {
            throw new ServletException("No configuration file provided!");
        }
        Yaml configYml = new Yaml();
        try {
            config = new ServerConfiguration((Map<String, Object>)configYml.load(new FileInputStream(configFile)));

        } catch (FileNotFoundException e) {
            throw new ServletException("Could not load configuration! This should never be reached.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
