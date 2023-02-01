package com.macaplix.squareGames.service;

import com.macaplix.squareGames.dto.GameTypeInfo;
import com.macaplix.squareGames.plugin.GamePlugin;
import com.macaplix.squareGames.plugin.TicTacToePlugin;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Service
public class GameCatalogImpl implements GameCatalog {
    private TicTacToeGameFactory ticTacToeGameFactory;
    private TaquinGameFactory taquinGameFactory;
    private ConnectFourGameFactory connectFourGameFactory;
    @Autowired
    private TicTacToePlugin ticTacToePlugin;
    public GameCatalogImpl()
    {
        ticTacToeGameFactory = new TicTacToeGameFactory();
        taquinGameFactory = new TaquinGameFactory();
        connectFourGameFactory = new ConnectFourGameFactory();
    }
    public Collection<String> getGameIdentifiers()
    {
        //return List.of(ticTacToeGameFactory.getId());
        return List.of(ticTacToeGameFactory.getGameId(), taquinGameFactory.getGameId(), connectFourGameFactory.getGameId());
    }

    @Override
    public GameTypeInfo[] getGameTypes(GameService gameService)
    {
        GameFactory[] factories = {ticTacToeGameFactory,taquinGameFactory, connectFourGameFactory};
        GameTypeInfo[] types = new GameTypeInfo[factories.length];
        int index=0;
        for (GameFactory factory:factories)
        {
            GamePlugin plugin = gameService.pluginForGame(factory.getGameId());
            types[index] = new GameTypeInfo(index, factory.getGameId(), plugin.getName(gameService.getEndUserLocale(), ticTacToePlugin ), plugin.getDefaultPlayerCount(), plugin.getMinPlayerCount(), plugin.getMaxPlayerCount(), plugin.getDefaultBoardSize(), plugin.getMinBoardSize(), plugin.getMaxBoardSize());
            index++;
        }
        return types;
    }
}
