package com.macaplix.squareGames;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.IntRange;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService
{
    private Game activeGame;
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
                return new GameParamsAnswer(params,"gameIndex out of range", false,"");
        }
        String checkAnswer = checkPlayerCount(factory, params.playerCount());
        if ( ! checkAnswer.isBlank()) return new GameParamsAnswer( params, "playerCount should be between " + factory.getPlayerCountRange().min() + " and " + factory.getPlayerCountRange().max(), false, "");
        checkAnswer = checkBoardSize(factory, params.boardSize(), params.playerCount());
        if ( ! checkAnswer.isBlank()) return new GameParamsAnswer( params, "board size should be between " + factory.getBoardSizeRange(params.playerCount()).min() +" and " +factory.getBoardSizeRange(params.playerCount()).max(), false, "");
        activeGame = factory.createGame(params.playerCount(), params.boardSize());
        return new GameParamsAnswer(params, "ok", true, factory.getGameId());
    }
    public Game getGame()
    {
        return activeGame;
    }
    private String checkPlayerCount( GameFactory factory, int playerCount )
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
