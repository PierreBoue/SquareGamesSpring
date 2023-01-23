package com.macaplix.squareGames;

import fr.le_campus_numerique.square_games.engine.Game;

import java.util.Locale;

public interface GamePlugin
{
    //public int defaultPlayerCount=0;
    //public int defaultBoradSize = 0;
    public String getName(Locale locale);
    public Game getGame();
   // public int defaultPlayerCount();
  //  public int defaultBoardSize();

}
