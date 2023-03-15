package com.macaplix.squaregames.dao;

import com.macaplix.squaregames.dto.GameSaveDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class GameDaoSQL implements GameDaoInterface {
    static final String GAME_TABLE_NAME = "games";

    static final String PREPARED_STATEMENT_FAILURE = "Prepared Statement Failure";
    static final String SQL_IS_NOT_ACTIVE = "SQL is not active";
    static final boolean IS_ACTIVE = true;

    @Autowired
    SQLConnector connector;

    @PostConstruct
    private boolean createGameTable() {
        //int sqlid, String gameKey, int gameType, int currentPlayerID, String gameStatus, int boardSize, Date creationDate, Duration duration
        //SQLconnector connector = SQLconnector.getInstance();" + connector.getDatabaseName() + "
        final String req = "CREATE TABLE IF NOT EXISTS " + GAME_TABLE_NAME + " ( " +
                "id int(10) PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                "gameuuid VARCHAR( 36 ) UNIQUE," +
                "gametype TINYINT," +
                "currentPlayerID INT(10)," +
                "gameStatus ENUM('SETUP', 'ONGOING', 'TERMINATED') DEFAULT 'SETUP'," +
                "boardSize TINYINT," +
                "creation DATETIME DEFAULT NOW()," +
                "duration INT(6) DEFAULT 0 );";

        connector.updateStatement(req);

        return true;
    }

    public void saveGame(GameSaveDTO gameData) {
        String req = "INSERT INTO " + GAME_TABLE_NAME + " ( gameuuid, gametype, currentPlayerID, boardSize ) VALUES ( ?, ?, ? , ? );";
        PreparedStatement smt = connector.prepareStatement(req);
        try {
            smt.setString(1, gameData.gameKey());
            smt.setInt(2, gameData.gameType());
            smt.setInt(3, gameData.currentPlayerID());
            smt.setInt(4, gameData.boardSize());
        } catch (SQLException e) {
            errorDTO(PREPARED_STATEMENT_FAILURE + " : " + e);
        }
    }

    public ArrayList<GameSaveDTO> readGames() {
        if (!IS_ACTIVE) return (ArrayList<GameSaveDTO>) List.of(errorDTO(SQL_IS_NOT_ACTIVE));
        String req = "SELECT id, gameuuid, gameType, currentPlayerID, gameStatus, boardSize, creation, duration FROM " + GAME_TABLE_NAME + " ORDER BY  creation DESC;";
        ResultSet rezset = connector.selectStatement(req);
        ArrayList<GameSaveDTO> games = new ArrayList<>();
        if (rezset == null) return games;
        while (true) {
            try {
                if (!rezset.next()) break;
            } catch (SQLException e) {
                games.add(errorDTO("result iteration failed: " + e));
                return games;
            }
            try {
                games.add(new GameSaveDTO(rezset.getInt("id"),
                        rezset.getString("gameuuid"), rezset.getInt("gameType"),
                        rezset.getInt("currentPlayerID"), rezset.getString("gameStatus"),
                        rezset.getInt("boardSize"), new Date(rezset.getTimestamp("creation").getTime()),
                        Duration.ofSeconds(rezset.getLong("duration")), true, "ok"));
            } catch (SQLException e) {
                games.add(errorDTO("result set reading failed: " + e));
                return games;
            }
        }
        return games;
    }

    public GameSaveDTO readGame(GameSaveDTO gameData) {
        if (!IS_ACTIVE) return errorDTO(SQL_IS_NOT_ACTIVE);
        String where = " WHERE ";
        boolean hasNumID = false;
        if (gameData.sqlid() != 0) {
            where += "sqlid = ?";
            hasNumID = true;
        } else if ((gameData.gameKey() != null) && (!gameData.gameKey().isBlank())) {
            where += "gameKey = ?";
        } else {
            return errorDTO("no valid key provided to get the game");
        }
        where += " ;";
        String req = "SELECT sqlid, gameKey, gameType, currentPlayerID, gameStatus, boardSize, creation, duration FROM " + GAME_TABLE_NAME + where;
        PreparedStatement stmt = connector.prepareStatement(req);
        try {
            if (hasNumID) {
                stmt.setInt(1, gameData.sqlid());
            } else {
                stmt.setString(1, gameData.gameKey());
            }
        } catch (SQLException e) {
            return errorDTO("Impossible to inject value in prepared statment");
        }
        GameSaveDTO ret;
        try {
            ResultSet rezset = stmt.executeQuery();
            ret = new GameSaveDTO(rezset.getInt("sqlid"),
                    rezset.getString("gameKey"), rezset.getInt("gameType"),
                    rezset.getInt("currentPlayerID"), rezset.getString("gameStatus"),
                    rezset.getInt("boardSize"), rezset.getDate("creationDate"),
                    Duration.ofSeconds(rezset.getLong("duration")), true, "ok");
        } catch (SQLException e) {
            return errorDTO("Parsing result failed");
        }
        return ret;
    }

    private GameSaveDTO errorDTO(String message) {
        return new GameSaveDTO(0, "", 0, 0, "", 0, null, null, false, message);
    }

}
