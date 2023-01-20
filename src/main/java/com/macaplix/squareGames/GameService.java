package com.macaplix.squareGames;

import fr.le_campus_numerique.square_games.engine.Game;
import org.springframework.stereotype.Service;


public interface GameService
{
    public GameParamsAnswer createGame( GameParams params);
}
