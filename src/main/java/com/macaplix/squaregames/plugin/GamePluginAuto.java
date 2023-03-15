package com.macaplix.squaregames.plugin;

import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GamePluginAuto extends GamePluginBase {
    //@Value("${game.connect4.default-player-count}")

    protected int defaultPlayerCount;
    // @Value("${game.connect4.default-board-size}")
    protected int defaultBoardSize;
    private String typeName;
    private final Map<String, GameFactory> factories = Stream.of(
                    new TicTacToeGameFactory(),
                    new TaquinGameFactory(),
                    new ConnectFourGameFactory())
            .collect(Collectors.toUnmodifiableMap(GameFactory::getGameId, Function.identity()));

    @Override
    public int getDefaultPlayerCount() {
        return defaultPlayerCount;
    }

    @Override
    public int getDefaultBoardSize() {
        return defaultBoardSize;
    }

    public GamePluginAuto(String gameName) {
        factory = factories.get(gameName);
        this.typeName = gameName;
        defaultPlayerCount = factory.getPlayerCountRange().max();
        defaultBoardSize = factory.getBoardSizeRange(defaultPlayerCount).max();
    }

    public int getTypeIndex() {
        int idx = 0;
        for (String key : factories.keySet()) {
            if (key.equals(this.typeName)) break;
            idx++;
        }
        if (idx >= factories.size()) return -1;
        return idx;
    }
}
