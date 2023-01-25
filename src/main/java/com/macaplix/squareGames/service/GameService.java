package com.macaplix.squareGames.service;

import com.macaplix.squareGames.dto.*;
import com.macaplix.squareGames.plugin.GamePlugin;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;

import java.util.HashMap;


public interface GameService
{
    public GameParamsAnswer createGame(GameParams params);
    //public Game getGame( String gameid );
    public GameDescription getGameDescription(String gameid );
    public TokenInfo[] getTokenList( String gameid );
    public TokenInfo getTokenInfo(String gameid, CellPosition position);
    public MovedTokenResult moveToken(String gameid, int tokenIndex, CellPosition newpos);
    public HashMap<String, Game> getAllGames();
    public GamePlugin pluginForGame(String gameName );
    //public GameDescription[] getAllGameDescription();
}
