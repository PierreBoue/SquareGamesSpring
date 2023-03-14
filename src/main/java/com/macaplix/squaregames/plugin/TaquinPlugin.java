package com.macaplix.squaregames.plugin;

import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TaquinPlugin extends GamePluginBase {
    @Value("${game.15puzzle.default-player-count}")
    protected int defaultPlayerCount;
    @Value("${game.15puzzle.default-board-size}")
    protected int defaultBoardSize;

    public TaquinPlugin()
    {
        factory = new TaquinGameFactory();
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
