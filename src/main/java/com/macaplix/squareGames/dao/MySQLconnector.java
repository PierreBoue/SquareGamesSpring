package com.macaplix.squareGames.dao;

import java.sql.*;

public class MySQLconnector {
    private final String DATABASE_NAME ="square_games";
    Connection mysqlConnection;
    private static MySQLconnector INSTANCE = null;
    private MySQLconnector()
    {
        try {
            mysqlConnection= DriverManager.getConnection(
                    "jdbc:mysql://localhost:6603/" + DATABASE_NAME,"root","helloworld");
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            throw new RuntimeException(e);
        }
/*

*/
    }
    public static MySQLconnector getInstance()
    {
        if ( INSTANCE == null)
        {
            INSTANCE = new MySQLconnector();
        }
        return INSTANCE;
    }
    public ResultSet selectStatment( String query )
    {
        Statement stmt= null;
        try {
            stmt = mysqlConnection.createStatement();
        } catch (SQLException e) {
            System.err.println("statment creation failed: " + e.toString());
            return null;
        }
        ResultSet ret=null;
        try
        {
            ret =stmt.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("executing query " + query + " failed: " +e.toString());
            return null;
            //throw new RuntimeException(e);
        }
        return ret;
    }
    public int insertStatment( String query)
    {
        Statement stmt= null;
        try {
            stmt = mysqlConnection.createStatement();
        } catch (SQLException e) {
            System.err.println("statment creation failed: " + e.toString());
            return 0;
            //throw new RuntimeException(e);
        }
        int ret=0;
        try {
            ret = stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("executing query " + query + " failed: " +e.toString());
            return 0;
            //throw new RuntimeException(e);
        }
        return ret;
    }
    boolean closeConnection()
    {
        try {
            mysqlConnection.close();
        } catch (SQLException e) {
            System.err.println("Closing connection failed: " + e.toString());
            return false;
            //throw new RuntimeException(e);
        }
        return true;
    }
    PreparedStatement prepareStatment( String query )
    {
        try
        {
            return mysqlConnection.prepareStatement(query);
        } catch (SQLException e) {
            System.err.println("prepared statment failed: " + e.toString());
            return null;
            //throw new RuntimeException(e);
        }
    }
    public String getDatabaseName()
    {
        return DATABASE_NAME;
    }
}
