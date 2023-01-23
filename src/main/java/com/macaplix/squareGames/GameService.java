package com.macaplix.squareGames;

import fr.le_campus_numerique.square_games.engine.Game;
import org.springframework.stereotype.Service;

import java.util.HashMap;


public interface GameService
{
    public GameParamsAnswer createGame( GameParams params);
    //public Game getGame( String gameid );
    public GameDescription getGameDescription( String gameid );
    public HashMap<String, Game> getAllGames();
    //public GameDescription[] getAllGameDescription();
}
