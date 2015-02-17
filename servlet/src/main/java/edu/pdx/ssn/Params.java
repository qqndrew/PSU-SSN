package edu.pdx.ssn;

public enum Params {
    APP("app");
    private final String key;

    Params(String name) {
        this.key = name;
    }

    public String getKey() {
        return key;
    }
}
