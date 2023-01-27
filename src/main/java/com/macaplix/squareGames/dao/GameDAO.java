package com.macaplix.squareGames.dao;

import com.macaplix.squareGames.dto.GameSaveDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameDAO {
    final String GAME_TABLE_NAME = "games";
    boolean createGameTable()
    {
        //int sqlid, String gameKey, int gameType, int currentPlayerID, String gameStatus, int boardSize, Date creationDate, Duration duration
        MySQLconnector connector = MySQLconnector.getInstance();
        final String req = "CREATE TABLE IF NOT EXISTS " + connector.getDatabaseName() + ".games ( " +
                "id int(10) PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                "gameuuid VARCHAR( 36 )," +
                "gametype TINYINT," +
                "currentPlayerID INT(10)," +
                "gameStatus ENUM('SETUP', 'ONGOING', 'TERMINATED') DEFAULT 'SETUP'," +
                "boardSize TINYINT," +
                "creation DATETIME DEFAULT NOW()," +
                "duration INT(6) DEFAULT 0 );";
        connector.insertStatment(req);
        return true;
    }
    GameSaveDTO saveGame( GameSaveDTO gameData)
    {
       String req = "INSERT INTO " + GAME_TABLE_NAME + " ( gameuuid, gametype, currentPlayerID, boardSize ) VALUES ( ?, ?, ? , ? );";
        PreparedStatement smt = MySQLconnector.getInstance().prepareStatment(req );
        try {
            smt.setString(1, gameData.gameKey());
            smt.setInt(2, gameData.gameType());
            smt.setInt(3, gameData.currentPlayerID());
            smt.setInt(4, gameData.boardSize());
        } catch (SQLException e) {
            return new GameSaveDTO(0, gameData.gameKey(), gameData.gameType(), gameData.currentPlayerID(),gameData.gameStatus(), gameData.boardSize(),
                    gameData.creationDate(), gameData.duration(), false, "prepared statment failure: " + e.toString());
            //throw new RuntimeException(e);
        }
        int sqlid=0;
        try {
            if ( smt.executeUpdate() == 0)
            {
                //sqlid =  MySQLconnector.getInstance().mysqlConnection.g
            }
        } catch (SQLException e) {
            return new GameSaveDTO(0, gameData.gameKey(), gameData.gameType(), gameData.currentPlayerID(),gameData.gameStatus(), gameData.boardSize(),
                    gameData.creationDate(), gameData.duration(), false, "prepared statment failure: " + e.toString());

        }
        return null;// new GameSaveDTO();
    }
    GameSaveDTO readGame( GameSaveDTO gameData)
    {

        return null;
    }

}
