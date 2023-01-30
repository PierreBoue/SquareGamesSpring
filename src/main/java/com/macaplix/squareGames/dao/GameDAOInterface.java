package com.macaplix.squareGames.dao;

import com.macaplix.squareGames.dto.GameSaveDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public interface GameDAOInterface {
    //private boolean createGameTable();
    public GameSaveDTO saveGame(GameSaveDTO gameData);
    public ArrayList<GameSaveDTO> readGames( );

    public GameSaveDTO readGame( GameSaveDTO gameData);
}
