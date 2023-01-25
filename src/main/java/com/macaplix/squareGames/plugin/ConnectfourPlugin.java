package com.macaplix.squareGames.plugin;

import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConnectfourPlugin extends GamePluginBase {
    @Value("${game.connect4.default-player-count}")
    protected int defaultPlayerCount;
    @Value("${game.connect4.default-board-size}")
    protected int defaultBoardSize;
    ConnectfourPlugin()
    {
        factory = new ConnectFourGameFactory();
    }
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

}
