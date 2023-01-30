package com.macaplix.squareGames.dao;

import com.macaplix.squareGames.dto.GameSaveDTO;
import org.springframework.stereotype.Component;

//import javax.xml.datatype.Duration;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.Duration;
import java.util.List;

@Component
public class GameDAO {
    final String GAME_TABLE_NAME = "games";
    final boolean ISACTIVE = true;
    public GameDAO()
    {
        if ( ! ISACTIVE ) return;
       createGameTable();
    }
    private boolean createGameTable()
    {
        //int sqlid, String gameKey, int gameType, int currentPlayerID, String gameStatus, int boardSize, Date creationDate, Duration duration
        MySQLconnector connector = MySQLconnector.getInstance();
        final String req = "CREATE TABLE IF NOT EXISTS " + connector.getDatabaseName() + "." +GAME_TABLE_NAME +" ( " +
                "id int(10) PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                "gameuuid VARCHAR( 36 ) UNIQUE," +
                "gametype TINYINT," +
                "currentPlayerID INT(10)," +
                "gameStatus ENUM('SETUP', 'ONGOING', 'TERMINATED') DEFAULT 'SETUP'," +
                "boardSize TINYINT," +
                "creation DATETIME DEFAULT NOW()," +
                "duration INT(6) DEFAULT 0 );";
        connector.insertStatment(req);
        return true;
    }
    public GameSaveDTO saveGame( GameSaveDTO gameData)
    {
        if ( ! ISACTIVE ) return errorDTO("SQL is not active");
       String req = "INSERT INTO " + GAME_TABLE_NAME + " ( gameuuid, gametype, currentPlayerID, boardSize ) VALUES ( ?, ?, ? , ? );";
        PreparedStatement smt = MySQLconnector.getInstance().prepareStatment(req );
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
            return errorDTO( "prepared statment failure: " + e.toString());

        }
        return null;// new GameSaveDTO();
    }
    public ArrayList<GameSaveDTO> readGames( )
    {
        if ( ! ISACTIVE ) return (ArrayList<GameSaveDTO>) List.of( errorDTO("SQL is not active"));
        String req ="SELECT id, gameuuid, gameType, currentPlayerID, gameStatus, boardSize, creation, duration FROM " + GAME_TABLE_NAME + ";";
        ResultSet rezset = MySQLconnector.getInstance().selectStatment(req);
        ArrayList<GameSaveDTO> games = new ArrayList<GameSaveDTO>();
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
                        rezset.getInt("boardSize"), rezset.getDate("creation"),
                        Duration.ofSeconds( rezset.getLong("duration")),true,"ok"));

            } catch (SQLException e) {
                games.add(errorDTO( "result set reading failed: " + e.toString()));
                return games;
            }
        }

        return games;
    }

    public GameSaveDTO readGame( GameSaveDTO gameData)
    {
        if ( ! ISACTIVE ) return errorDTO("SQL is not active");
        String where = " WHERE ";
        boolean hasNumID = false;
        if ( gameData.sqlid() !=0 )
        {
            where += "sqlid = ? ;";
            hasNumID = true;
        } else if (( gameData.gameKey() != null) && ( ! gameData.gameKey().isBlank())) {
            where += "gameKey = ? ;";
            hasNumID = false;
        } else {
            return errorDTO("no valid key provided to get the game");
        }
        String req ="SELECT sqlid, gameKey, gameType, currentPlayerID, gameStatus, boardSize, creationDate, duration FROM " + GAME_TABLE_NAME + where;
        MySQLconnector connector = MySQLconnector.getInstance();
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
        return ret;
    }
    private GameSaveDTO errorDTO( String message)
    {
        return new GameSaveDTO(0,"",0, 0, "",0, null, null, false, message);
    }

}
