package edu.pdx.ssn;

public enum Queries {
    APP("app");
    private final String key;

    Queries(String name) {
        this.key = name;
    }

    public String getKey() {
        return key;
    }
}
