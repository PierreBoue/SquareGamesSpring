package com.macaplix.squaregames.dao;

import com.macaplix.squaregames.dto.GameSaveDTO;

import java.util.ArrayList;

public interface GameDaoInterface {
    void saveGame(GameSaveDTO gameData);

    ArrayList<GameSaveDTO> readGames();

    GameSaveDTO readGame(GameSaveDTO gameData);
}
