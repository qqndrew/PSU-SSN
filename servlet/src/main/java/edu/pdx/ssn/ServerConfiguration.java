package edu.pdx.ssn;

import java.util.Map;

public final class ServerConfiguration {

    public static final String PATH_SEPERATOR = ".";

    private Map<String, Object> config;

    public ServerConfiguration(Map<String, Object> load) {
        this.config = load;
    }

    public String getValue(String query) {
        return containsKey(query) ? String.valueOf(config.get(query)) : null;
    }

    public boolean containsKey(String query) {
        return config.containsKey(query);
    }
}
