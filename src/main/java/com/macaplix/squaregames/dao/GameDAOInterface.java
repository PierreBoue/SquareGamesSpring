package com.macaplix.squaregames.dao;

import com.macaplix.squaregames.dto.GameSaveDTO;

import java.util.ArrayList;

public interface GameDAOInterface {
    //private boolean createGameTable();
    public GameSaveDTO saveGame(GameSaveDTO gameData);
    public ArrayList<GameSaveDTO> readGames( );

    public GameSaveDTO readGame( GameSaveDTO gameData);
}
