package com.macaplix.squareGames;
import com.macaplix.squareGames.GamePlugin;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Locale;
@Service
public class TicTacToePlugin implements GamePlugin
{
    //@Configuration
    //@PropertySources("classpath:application.properties")

    @Autowired
    private MessageSource messageSource;

    @Value("${game.titactoe.default-player-count}")
    private int defaultPlayerCount;
    @Value("${game.tictactoe.default-board-size}")
    private int defaultBoardSize;
    private TicTacToeGameFactory factory = new TicTacToeGameFactory();

    @Override
    public String getName(Locale locale)
    {
        return messageSource.getMessage("game.tictatoe.name", null, Locale.FRENCH);
    }

    @Override
    public Game getGame()
    {
        return factory.createGame( defaultPlayerCount, defaultBoardSize);
    }
    private int getDefaultPlayerCount()
    {
        return  defaultPlayerCount;
    }
    private int getDefaultBoardSize()
    {
        return defaultBoardSize;
    }
}
