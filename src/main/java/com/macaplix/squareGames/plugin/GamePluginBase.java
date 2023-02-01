package com.macaplix.squareGames.plugin;

import com.macaplix.squareGames.dto.GameDescription;
import com.macaplix.squareGames.dto.GameParams;
import fr.le_campus_numerique.square_games.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
@Service
public abstract class GamePluginBase implements GamePlugin {
    protected GameFactory factory;
    @Autowired
    private MessageSource messageSource;
    @Override
    public String getName(Locale locale)
    {
        Locale curLocale = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);

        String msg = messageSource.getMessage("game." + this.getType() + ".name", null, locale);
        Locale.setDefault(curLocale);
        return msg;
    }
    public String getType()
    {
        return factory.getGameId().replaceAll("\\s", "");
    }


    @Override
    public GameDescription checkParams(GameParams params)
    {
        String checkAnswer = "";
        int playerCount =  params.playerCount();
        if ( playerCount == 0 )
        {
            playerCount = getDefaultPlayerCount();
        } else {
            checkAnswer = checkPlayerCount( playerCount);
        }
        //int gameIndex, String gameKey, int sqlid, String typeLocale, String typeName, int playerCount, int boardSize, Map<CellPosition, Token> board, Date creation, int duration, String errorMessage, boolean isOk
        if ( ! checkAnswer.isBlank()) return new GameDescription( params.gameIndex(), "",0, factory.getGameId(), factory.getGameId(), playerCount, params.boardSize(), new HashMap<CellPosition, Token>(), new Date(), 0, "playerCount should be between " + factory.getPlayerCountRange().min() + " and " + factory.getPlayerCountRange().max(), false);
        int boardSize = params.boardSize();
        if ( boardSize == 0)
        {
            boardSize = getDefaultBoardSize();
        } else {
            checkAnswer = checkBoardSize(boardSize, playerCount);
        }
        if ( ! checkAnswer.isBlank()) return new GameDescription( params.gameIndex(), "",0, "","", playerCount, boardSize, new HashMap<CellPosition, Token>(), new Date(),0,"board size should be between " + factory.getBoardSizeRange(params.playerCount()).min() +" and " +factory.getBoardSizeRange(params.playerCount()).max(), false);
        //int gameIndex, String gameKey, int sqlid, String typeLocale, String typeName, int playerCount, int boardSize, Map<CellPosition, Token> board, Date creation, int duration, String errorMessage, boolean isOk
        return new GameDescription( params.gameIndex(), "", 0, factory.getGameId(), factory.getGameId(), playerCount, boardSize, new HashMap<CellPosition,Token>(), new Date(), 0,"",true);
    }

    @Override
    public Game createGame( GameDescription params)
    {
        return factory.createGame( params.playerCount(),params.boardSize());
    }
    @Override
    public int getDefaultPlayerCount()
    {
        return 0;
    }
    public int getMinPlayerCount()
    {
        IntRange r = factory.getPlayerCountRange();
        return r.min();
    }
    public int getMaxPlayerCount()
    {
        return factory.getPlayerCountRange().max();
    }


    public int getMinBoardSize()
    {
        return factory.getBoardSizeRange( getDefaultPlayerCount()).min();
    }
    public int getMaxBoardSize()
    {
        return factory.getBoardSizeRange( getDefaultPlayerCount()).max();
    }

    @Override
    public int getDefaultBoardSize()
    {
        return 0;
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
