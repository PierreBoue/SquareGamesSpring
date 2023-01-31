package com.macaplix.squareGames.dao;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
@Component
public class SQLconnector {
    private final String DATABASE_NAME = "testdb";//"square_games";
    Connection connection;
    //private static SQLconnector INSTANCE = null;
    private SQLconnector(DataSource dataSource)
    {
        try {
            connection = dataSource.getConnection();
           // mysqlConnection= DriverManager.getConnection(
          //          "jdbc:mysql://localhost:6603/" + DATABASE_NAME,"root","helloworld");
            //mysqlConnection= DriverManager.getConnection(
             //       "jdbc:h2:mem:testdb://localhost:9090/" + DATABASE_NAME,"sa","");
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            throw new RuntimeException(e);
        }
/*

*/
    }
/*
    public static SQLconnector getInstance()
    {
        if ( INSTANCE == null)
        {
            INSTANCE = new SQLconnector();
        }
        return INSTANCE;
    }
*/
    public ResultSet selectStatment( String query )
    {
        Statement stmt= null;
        try {
            stmt = connection.createStatement();
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
            stmt = connection.createStatement();
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
            connection.close();
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
            return connection.prepareStatement(query);
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
