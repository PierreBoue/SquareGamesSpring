package com.macaplix.squareGames.service;

import com.macaplix.squareGames.dto.*;
import com.macaplix.squareGames.plugin.GamePlugin;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;

import java.util.HashMap;


public interface GameService
{
    /**
     * creates a new game
     * @param params includes game type ( by index ), playerCount, boardSize
     * @return game creation status with UUID on success and error message on failure
     */
    public GameParamsAnswer createGame(GameParams params);

    /**
     * get the game info including current board size
     * @param gameid
     * @return
     */
    public GameDescription getGameDescription(String gameid );

    /**
     * get the token list of a game including both on board and remaining
     * @param gameid UUID of the game
     * @return a list of token infos
     */
    public TokenInfo[] getTokenList( String gameid );

    /**
     * get a token found by its position on the board info
     * @param gameid game UUID
     * @param position token position on the board
     * @return token info
     */
    public TokenInfo getTokenInfo(String gameid, CellPosition position);

    /**
     * moves a token either on the board or from remaining to board
     * @param gameid game UUID
     * @param tokenIndex index of the token as returned by the previous method
     * @param newpos new position on the board
     * @return MovedTokenResult which encapsulate success and error message in case of failure
     */
    public MovedTokenResult moveToken(String gameid, int tokenIndex, CellPosition newpos);

    /**
     * accessor to games instance list
     * @return a HashMap indexed by UUID
     */
    public HashMap<String, Game> getAllGames();

    /**
     * gets the plugin for a game type
     * @param gameName
     * @return a GamePlugin instance
     */
    public GamePlugin pluginForGame(String gameName );
    //public GameDescription[] getAllGameDescription();
}
