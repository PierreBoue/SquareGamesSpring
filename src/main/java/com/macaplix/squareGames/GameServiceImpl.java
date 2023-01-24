package com.macaplix.squareGames;

import fr.le_campus_numerique.square_games.engine.*;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameService
{
    //private Game activeGame;
    @Autowired
    public TicTacToePlugin ticTacToePluginPlugin;
    private HashMap<String, Game> activeGames;

    public GameServiceImpl()
    {
        activeGames = new HashMap<String, Game>();

    }

    @Override
    public GameParamsAnswer createGame( GameParams params)
    {
        GameParamsAnswer aparam;
       GamePlugin plugin=null;
        switch (params.gameIndex())
        {
            case 0:
                plugin = ticTacToePluginPlugin;
                break;
            case 1:
                //plugin =
                break;
            case 2:
                //factory = new ConnectFourGameFactory();
                break;
            default:
                return new GameParamsAnswer(params, null,"gameIndex out of range", false,"", null);
        }
        if ( plugin == null)
            return null;
        Game activeGame = plugin.getGame();
        String uuid = UUID.randomUUID().toString();
        activeGames.put(uuid, activeGame);
        // System.out.println( activeGames );
        return new GameParamsAnswer(params, uuid, "ok", true, plugin.getName(Locale.getDefault()), activeGame.getBoard());
    }
/*
    @Override
    public GameParamsAnswer createGame( GameParams params)
    {
        //Game game;
        GameParamsAnswer aparam;
        //= new GameParamsAnswer( params,"", true, "");
        GameFactory factory;
        switch (params.gameIndex())
        {
            case 0:
                factory = new TicTacToeGameFactory( );
                break;
            case 1:
                factory = new TaquinGameFactory();
                break;
            case 2:
                factory = new ConnectFourGameFactory();
                break;
            default:
                return new GameParamsAnswer(params, null,"gameIndex out of range", false,"");
        }
        String checkAnswer = checkPlayerCount(factory, params.playerCount());
        if ( ! checkAnswer.isBlank()) return new GameParamsAnswer( params, null, "playerCount should be between " + factory.getPlayerCountRange().min() + " and " + factory.getPlayerCountRange().max(), false, "");
        checkAnswer = checkBoardSize(factory, params.boardSize(), params.playerCount());
        if ( ! checkAnswer.isBlank()) return new GameParamsAnswer( params, null, "board size should be between " + factory.getBoardSizeRange(params.playerCount()).min() +" and " +factory.getBoardSizeRange(params.playerCount()).max(), false, "");
        Game activeGame = factory.createGame(params.playerCount(), params.boardSize());
        String uuid = UUID.randomUUID().toString();
        activeGames.put(uuid, activeGame);
       // System.out.println( activeGames );
        return new GameParamsAnswer(params, uuid, "ok", true, factory.getGameId());
    }
*/

    private Game getGame( String gameid)
    {
        return activeGames.get(gameid);
    }
    @Override
    public GameDescription getGameDescription( String gameid)
    {
        Game game = getGame(gameid);
        return new GameDescription(gameid, game.getFactoryId(), game.getBoardSize(), game.getPlayerIds().size(), game.getBoard());
    }
    @Override
    public TokenInfo getTokenInfo( String gameid, CellPosition position)
    {
        Game game = activeGames.get( gameid);
        if ( game == null ) return new TokenInfo(gameid, position,"no game", false, false);
        Token t = game.getBoard().get(position);
        boolean onBoard = false;
        if ( t== null)
        {
            onBoard = false;
            t = game.getRemainingTokens().iterator().next();
        } else onBoard = true;
        String name = ( t== null)?"null":t.getName();
        boolean canMove = ( t== null)?false:t.canMove();
        return new TokenInfo( gameid, position, name, canMove, onBoard );
    }
    @Override
    public MovedTokenResult moveToken(String gameid, CellPosition oldpos, CellPosition newpos)
    {
        Game game = activeGames.get(gameid);
        //game.getBoard().computeIfAbsent()
        return null;//new MovedTokenResult( gameid, 0, );
    }


    @Override
    public HashMap<String, Game> getAllGames()
    {
        return activeGames;
    }

    private String checkPlayerCount(GameFactory factory, int playerCount )
    {
        IntRange range = factory.getPlayerCountRange();
        if ( !range.contains(playerCount)) return "playerCount should be between " + range.min() + " and " + range.max();
        return "";
    }
    private String checkBoardSize( GameFactory factory, int boardSize, int playerCount)
    {
        IntRange range =factory.getBoardSizeRange( playerCount);
        if ( !range.contains(boardSize)) return "boardSize should be between " + range.min() + " and " + range.max();
        return "";
    }

}
