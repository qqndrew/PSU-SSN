package edu.pdx.ssn;

public enum Params {
    APP("app"), UID("uid"), ISBN("isbn"), TITLE("title"), AUTHOR_LAST("last"), AUTHOR_FIRST("first"), SUBJECT("subj"), COURSE("courseno");
    private final String key;

    Params(String name) {
        this.key = name;
    }

    public String getKey() {
        return key;
    }
}
