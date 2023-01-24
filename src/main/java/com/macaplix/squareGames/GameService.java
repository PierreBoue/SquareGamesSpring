package com.macaplix.squareGames;

import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import org.springframework.stereotype.Service;

import java.util.HashMap;


public interface GameService
{
    public GameParamsAnswer createGame( GameParams params);
    //public Game getGame( String gameid );
    public GameDescription getGameDescription( String gameid );
    public TokenInfo getTokenInfo( String gameid, CellPosition position);
    public MovedTokenResult moveToken(String gameid, CellPosition oldpos, CellPosition newpos);
    public HashMap<String, Game> getAllGames();
    //public GameDescription[] getAllGameDescription();
}
