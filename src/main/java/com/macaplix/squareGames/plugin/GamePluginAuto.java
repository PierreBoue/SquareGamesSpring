package com.macaplix.squareGames.plugin;

import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GamePluginAuto extends GamePluginBase{
    //@Value("${game.connect4.default-player-count}")
    private final Map<String, GameFactory> factories = Stream.of(
                    new TicTacToeGameFactory(),
                    new TaquinGameFactory(),
                    new ConnectFourGameFactory())
            .collect(Collectors.toUnmodifiableMap(GameFactory::getGameId, Function.identity()));

    protected int defaultPlayerCount;
   // @Value("${game.connect4.default-board-size}")
    protected int defaultBoardSize;
    private String typeName;
    //@Autowired
   // protected MessageSource messageSource;

    @Override
    public int getDefaultPlayerCount()
    {
        return defaultPlayerCount;
    }
    @Override
    public int getDefaultBoardSize()
    {
        return defaultBoardSize;
    }
    public GamePluginAuto( String gameName )
    {
        factory = factories.get(gameName);
        this.typeName = gameName;
        defaultPlayerCount = factory.getPlayerCountRange().max();
        defaultBoardSize = factory.getBoardSizeRange(defaultPlayerCount).max();
        //this.messageSource = new MessageSource() ;
    }
    public int getTypeIndex()
    {
        int idx =0;
        for (String key:factories.keySet())
        {
            if ( key.equals(this.typeName)) break;
            idx++;
        }
        if ( idx >= factories.size()) return -1;
        return idx;
    }
}
