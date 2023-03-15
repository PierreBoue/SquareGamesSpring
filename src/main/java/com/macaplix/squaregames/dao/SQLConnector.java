package com.macaplix.squaregames.dao;

import org.apache.logging.slf4j.Log4jLogger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class SQLConnector {
    private static final String DATABASE_NAME = "testdb";

    private static final Log4jLogger LOGGER = (Log4jLogger) LoggerFactory.getLogger(SQLConnector.class);

    Connection connection;

    public ResultSet selectStatement(String query) {
        Statement stmt;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            LOGGER.error("statement creation failed: " + e);
            return null;
        }
        ResultSet ret;
        try {
            ret = stmt.executeQuery(query);
        } catch (SQLException e) {
            LOGGER.error("executing query " + query + " failed: " + e);
            return null;
        }
        return ret;
    }

    public int insertStatement(String query) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            LOGGER.error("statement creation failed: " + e);
            return 0;
        }
        int ret = 0;
        try {
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            ret = rs.getInt(1);
        } catch (SQLException e) {
            LOGGER.error("executing query " + query + " failed: " + e);
            return 0;
        }
        return ret;
    }

    public int updateStatement(String query) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            LOGGER.error("statement creation failed: " + e);
            return 0;
        }
        int ret = 0;
        try {
            ret = stmt.executeUpdate(query);
        } catch (SQLException e) {
            LOGGER.error("executing query " + query + " failed: " + e);
            return 0;
        }
        return ret;

    }

    boolean closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Closing connection failed: " + e);
            return false;
        }
        return true;
    }

    PreparedStatement prepareStatement(String query) {
        try {
            return connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            LOGGER.error("Prepared Statement Failed: " + e);
            return null;
        }
    }

    public String getDatabaseName() {
        return DATABASE_NAME;
    }
}
