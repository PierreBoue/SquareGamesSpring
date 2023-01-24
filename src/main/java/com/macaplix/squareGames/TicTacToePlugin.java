package com.macaplix.squareGames;
import com.macaplix.squareGames.GamePlugin;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.IntRange;
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
public class TicTacToePlugin extends GamePluginBase
{
    @Value("${game.titactoe.default-player-count}")
    protected int defaultPlayerCount;
    @Value("${game.tictactoe.default-board-size}")
    protected int defaultBoardSize;
    public TicTacToePlugin()
    {
        factory = new TicTacToeGameFactory();
    }
}
