package com.macaplix.squaregames.dao;

import com.macaplix.squaregames.dto.GameSaveDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import javax.xml.datatype.Duration;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.Duration;
import java.util.Date;
import java.util.List;



@Component
public class GameDAOSQL implements GameDAOInterface {
    final String GAME_TABLE_NAME = "games";
    final boolean ISACTIVE = true;
    @Autowired
    SQLconnector connector;
    public GameDAOSQL()
    {
        if ( ! ISACTIVE ) return;
       //createGameTable();
    }
    @PostConstruct
    private boolean createGameTable()
    {
        //int sqlid, String gameKey, int gameType, int currentPlayerID, String gameStatus, int boardSize, Date creationDate, Duration duration
        //SQLconnector connector = SQLconnector.getInstance();" + connector.getDatabaseName() + "
        final String req = "CREATE TABLE IF NOT EXISTS " +GAME_TABLE_NAME +" ( " +
                "id int(10) PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                "gameuuid VARCHAR( 36 ) UNIQUE," +
                "gametype TINYINT," +
                "currentPlayerID INT(10)," +
                "gameStatus ENUM('SETUP', 'ONGOING', 'TERMINATED') DEFAULT 'SETUP'," +
                "boardSize TINYINT," +
                "creation DATETIME DEFAULT NOW()," +
                "duration INT(6) DEFAULT 0 );";
/*
       final String req = "CREATE TABLE IF NOT EXISTS `GAMES` (  id int PRIMARY KEY NOT NULL , gameuuid VARCHAR( 36 ) UNIQUE, gametype INT, currentPlayerID INT, gameStatus ENUM('SETUP', 'ONGOING', 'TERMINATED') DEFAULT 'SETUP', boardSize INT, creation DATETIME DEFAULT date(), duration INT DEFAULT 0 );";
        h2 -> final String req = "CREATE TABLE IF NOT EXISTS GAMES (  id int PRIMARY KEY NOT NULL AUTO_INCREMENT, gameuuid VARCHAR( 36 ) UNIQUE, gametype INT, currentPlayerID INT, gameStatus ENUM('SETUP', 'ONGOING', 'TERMINATED') DEFAULT 'SETUP', boardSize INT, creation DATETIME DEFAULT NOW(), duration INT DEFAULT 0 );";
 */
        connector.updateStatement(req);

        return true;
    }
    public GameSaveDTO saveGame( GameSaveDTO gameData)
    {
        if ( ! ISACTIVE ) return errorDTO("SQL is not active");
       // System.out.println("game save");
       String req = "INSERT INTO " + GAME_TABLE_NAME + " ( gameuuid, gametype, currentPlayerID, boardSize ) VALUES ( ?, ?, ? , ? );";
        PreparedStatement smt = connector.prepareStatment(req );
        try {
            smt.setString(1, gameData.gameKey());
            smt.setInt(2, gameData.gameType());
            smt.setInt(3, gameData.currentPlayerID());
            smt.setInt(4, gameData.boardSize());
        } catch (SQLException e) {
            return errorDTO( "prepared statment failure: " + e.toString());
            //throw new RuntimeException(e);
        }
        int sqlid=0;
        try {
            if ( smt.executeUpdate() == 0)
            {

                ResultSet rs = smt.getGeneratedKeys();
                if ( rs.next()) sqlid = rs.getInt(1);
                //sqlid =  MySQLconnector.getInstance().mysqlConnection.g
            }
        } catch (SQLException e) {
            System.err.println( "prepared statment failure: " + e.toString() );
            return errorDTO( "prepared statment failure: " + e.toString());

        }
        //System.err.println("sql id: " + sqlid);
        return null;// new GameSaveDTO();
    }
    public ArrayList<GameSaveDTO> readGames( )
    {
        if ( ! ISACTIVE ) return (ArrayList<GameSaveDTO>) List.of( errorDTO("SQL is not active"));
        String req ="SELECT id, gameuuid, gameType, currentPlayerID, gameStatus, boardSize, creation, duration FROM " + GAME_TABLE_NAME + " ORDER BY  creation DESC;";
        ResultSet rezset = connector.selectStatement(req);
        ArrayList<GameSaveDTO> games = new ArrayList<GameSaveDTO>();
        if ( rezset == null ) return games;
        while(true) {
            try {
                if (!rezset.next()) break;
            } catch (SQLException e) {
                games.add(errorDTO("result iteration failed: " + e.toString() ));
                return games;
             }
            try {
                games.add(new GameSaveDTO( rezset.getInt("id"),
                        rezset.getString("gameuuid"), rezset.getInt("gameType"),
                        rezset.getInt("currentPlayerID"), rezset.getString("gameStatus"),
                        rezset.getInt("boardSize"), new Date(rezset.getTimestamp("creation").getTime()),
                        Duration.ofSeconds( rezset.getLong("duration")),true,"ok"));
                //System.err.println(new Date(rezset.getTimestamp("creation").getTime()));

            } catch (SQLException e) {
                games.add(errorDTO( "result set reading failed: " + e.toString()));
                return games;
            }
        }
       // System.err.println(games);
        return games;
    }

    public GameSaveDTO readGame( GameSaveDTO gameData)
    {
        if ( ! ISACTIVE ) return errorDTO("SQL is not active");
        String where = " WHERE ";
        boolean hasNumID = false;
        if ( gameData.sqlid() !=0 )
        {
            where += "sqlid = ?";
            hasNumID = true;
        } else if (( gameData.gameKey() != null) && ( ! gameData.gameKey().isBlank())) {
            where += "gameKey = ?";
            hasNumID = false;
        } else {
            return errorDTO("no valid key provided to get the game");
        }
        where += " ;";
        String req ="SELECT sqlid, gameKey, gameType, currentPlayerID, gameStatus, boardSize, creation, duration FROM " + GAME_TABLE_NAME + where;
        //SQLconnector connector = SQLconnector.getInstance();
        PreparedStatement stmt = connector.prepareStatment(req);
        try {
            if ( hasNumID ) {
                stmt.setInt(1, gameData.sqlid());
            } else {
                stmt.setString(1, gameData.gameKey());
            }
        } catch (SQLException e) {
            return errorDTO("Impossible to inject value in prepared statment");
        }
        GameSaveDTO ret = null;
        try {
            ResultSet rezset = stmt.executeQuery();
            ret = new GameSaveDTO( rezset.getInt("sqlid"),
                    rezset.getString("gameKey"), rezset.getInt("gameType"),
                    rezset.getInt("currentPlayerID"), rezset.getString("gameStatus"),
                    rezset.getInt("boardSize"), rezset.getDate("creationDate"),
                    Duration.ofSeconds( rezset.getLong("duration")),true,"ok");
        } catch (SQLException e) {
            return errorDTO("Parsing result failed");
            //throw new RuntimeException(e);
        }
        // return JSON.stringify(ret);
       return ret;
    }
    private GameSaveDTO errorDTO( String message)
    {
        return new GameSaveDTO(0,"",0, 0, "",0, null, null, false, message);
    }

}
