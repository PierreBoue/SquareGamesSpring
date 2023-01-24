package com.macaplix.squareGames;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.IntRange;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
@Service
public abstract class GamePluginBase implements GamePlugin {
    protected GameFactory factory;
    @Autowired
    private MessageSource messageSource;
    //@Value("${game.titactoe.default-player-count}")
    //protected int defaultPlayerCount;
   // @Value("${game.tictactoe.default-board-size}")
    //protected int defaultBoardSize;
    //private TicTacToeGameFactory factory;// = new TicTacToeGameFactory();

    @Override
    public String getName(Locale locale)
    {
        return messageSource.getMessage("game." + factory.getGameId().replaceAll("\\s", "") + ".name", null, locale);
    }


    @Override
    public GameParamsAnswer checkParams( GameParams params)
    {
        String checkAnswer = "";
        int playerCount =  params.playerCount();
        if ( playerCount == 0 )
        {
            playerCount = defaultPlayerCount;
        } else {
            checkAnswer = checkPlayerCount( playerCount);
        }
        if ( ! checkAnswer.isBlank()) return new GameParamsAnswer( params.gameIndex(), playerCount, params.boardSize(), null, "playerCount should be between " + factory.getPlayerCountRange().min() + " and " + factory.getPlayerCountRange().max(), false, "");
        int boardSize = params.boardSize();
        if ( boardSize == 0)
        {
            boardSize = defaultBoardSize;
        } else {
            checkAnswer = checkBoardSize(boardSize, playerCount);
        }
        if ( ! checkAnswer.isBlank()) return new GameParamsAnswer( params.gameIndex(), playerCount, boardSize, null, "board size should be between " + factory.getBoardSizeRange(params.playerCount()).min() +" and " +factory.getBoardSizeRange(params.playerCount()).max(), false, "");
        return new GameParamsAnswer( params.gameIndex(), playerCount, boardSize, null,"",true, factory.getGameId());
    }

    @Override
    public Game createGame( GameParamsAnswer params)
    {
        return factory.createGame( params.playerCount(),params.boardSize());
    }
    private String checkPlayerCount( int playerCount )
    {
        IntRange range = factory.getPlayerCountRange();
        if ( !range.contains(playerCount))
        {
            return "playerCount should be between " + range.min() + " and " + range.max();
        }
        return "";
    }
    private String checkBoardSize(  int boardSize, int playerCount)
    {
        IntRange range =factory.getBoardSizeRange( playerCount);
        if ( !range.contains(boardSize))
        {
            return "boardSize should be between " + range.min() + " and " + range.max();
        }
        return "";
    }
}
