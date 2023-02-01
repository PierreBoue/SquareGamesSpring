package com.macaplix.squareGames.plugin;

import com.macaplix.squareGames.dto.GameDescription;
import com.macaplix.squareGames.dto.GameParams;
import fr.le_campus_numerique.square_games.engine.Game;

import java.util.Locale;

public interface GamePlugin
{
    //public int defaultPlayerCount=0;
    //public int defaultBoradSize = 0;
    public String getName(Locale locale, GamePluginBase pluginBase);
    public String getType();
    public GameDescription checkParams(GameParams params);
    public Game createGame( GameDescription params);
    public int getDefaultPlayerCount();
    public int getMinPlayerCount();
    public int getMaxPlayerCount();

    public int getDefaultBoardSize();
    public int getMinBoardSize();
    public int getMaxBoardSize();
   // public int defaultPlayerCount();
  //  public int defaultBoardSize();

}
