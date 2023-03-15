package com.macaplix.squaregames.plugin;

import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TicTacToePlugin extends GamePluginBase {
    @Value("${game.titactoe.default-player-count}")
    protected int defaultPlayerCount;
    @Value("${game.tictactoe.default-board-size}")
    protected int defaultBoardSize;

    public TicTacToePlugin() {
        factory = new TicTacToeGameFactory();
    }

    @Override
    public int getDefaultPlayerCount() {
        return defaultPlayerCount;
    }

    @Override
    public int getDefaultBoardSize() {
        return defaultBoardSize;
    }

}
