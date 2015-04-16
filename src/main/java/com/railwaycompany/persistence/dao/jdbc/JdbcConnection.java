package com.railwaycompany.persistence.dao.jdbc;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Repository
public class JdbcConnection {

    private static final Logger LOG = Logger.getLogger(JdbcConnection.class.getName());

    private String url = "jdbc:mysql://localhost:3306/railway_company";
    private String password = "toor";
    private String username = "root";

    private Connection connection;

    public JdbcConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            if (!connection.isClosed()) {
                LOG.info("JDBC connection has been created");
            }
        } catch (ClassNotFoundException | SQLException e) {
            LOG.warn(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}