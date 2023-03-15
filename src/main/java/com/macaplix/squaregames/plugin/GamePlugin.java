package com.macaplix.squaregames.plugin;

import com.macaplix.squaregames.dto.GameDescription;
import com.macaplix.squaregames.dto.GameParams;
import fr.le_campus_numerique.square_games.engine.Game;

import java.util.Locale;

public interface GamePlugin {
    //public int defaultPlayerCount=0;
    //public int defaultBoardSize = 0;
    public String getName(Locale locale, GamePluginBase pluginBase);

    public String getType();

    public GameDescription checkParams(GameParams params);

    public Game createGame(GameDescription params);

    public int getDefaultPlayerCount();

    public int getMinPlayerCount();

    public int getMaxPlayerCount();

    public int getDefaultBoardSize();

    public int getMinBoardSize();

    public int getMaxBoardSize();
    // public int defaultPlayerCount();
    //  public int defaultBoardSize();

}
