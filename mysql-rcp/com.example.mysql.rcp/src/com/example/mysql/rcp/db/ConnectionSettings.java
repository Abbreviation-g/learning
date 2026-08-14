package com.example.mysql.rcp.db;

public record ConnectionSettings(String host, String port, String database, String user, String password) {
    private static final String OPTIONS = "useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    public String toJdbcUrl() {
        return "jdbc:mysql://" + host.strip() + ":" + port.strip() + "/" + database.strip() + "?" + OPTIONS;
    }

    public String displayUrl() {
        return toJdbcUrl();
    }
}
