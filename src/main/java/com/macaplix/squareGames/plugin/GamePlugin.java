package com.macaplix.squareGames.plugin;

import com.macaplix.squareGames.dto.GameParams;
import com.macaplix.squareGames.dto.GameParamsAnswer;
import fr.le_campus_numerique.square_games.engine.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

public interface GamePlugin
{
    //public int defaultPlayerCount=0;
    //public int defaultBoradSize = 0;
    public String getName(Locale locale);
    public GameParamsAnswer checkParams(GameParams params);
    public Game createGame( GameParamsAnswer params);
    public int getDefaultPlayerCount();
    public int getDefaultBoardSize();
   // public int defaultPlayerCount();
  //  public int defaultBoardSize();

}
