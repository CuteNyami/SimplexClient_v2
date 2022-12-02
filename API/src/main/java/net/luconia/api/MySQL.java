package net.luconia.api;

import java.sql.*;

public class MySQL {

    private final String host, user, password, database;

    private final int port;

    private Connection connection;

    public MySQL(String host, String user, String password, String database, int port) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.database = database;
        this.port = port;
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", user, password);
            System.out.println("[MySQL] The connection to MySQL was established!");
        } catch (SQLException e) {
            System.out.println("[MySQL] Connection to MySQL failed! Error: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("[MySQL] The connection to MySQL was terminated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("[MySQL] Error terminating connection to MySQL! Error: " + e.getMessage());
        }
    }

    public void update(String qry) {
        try {
            if (connection.isClosed()) {
                connect();
            }
            Statement st = connection.createStatement();
            st.executeUpdate(qry);
            st.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public ResultSet query(String qry) {
        ResultSet rs = null;

        try {
            if (connection.isClosed()) {
                connect();
            }
            Statement st = connection.createStatement();
            st.executeQuery(qry);
            rs = st.getResultSet();
            return rs;
        } catch (SQLException e) {
            System.err.println(e);
        }
        return rs;
    }

    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public int getPort() {
        return port;
    }
}
