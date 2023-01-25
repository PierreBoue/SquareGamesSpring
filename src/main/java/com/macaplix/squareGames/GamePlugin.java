package com.macaplix.squareGames;

import fr.le_campus_numerique.square_games.engine.Game;

import java.util.Locale;

public interface GamePlugin
{
    //public int defaultPlayerCount=0;
    //public int defaultBoradSize = 0;
    public String getName(Locale locale);
    public GameParamsAnswer checkParams( GameParams params);
    public Game createGame( GameParamsAnswer params);
    public int getDefaultPlayerCount();
    public int getDefaultBoardSize();
   // public int defaultPlayerCount();
  //  public int defaultBoardSize();

}
