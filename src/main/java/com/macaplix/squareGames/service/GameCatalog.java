package com.macaplix.squareGames.service;

import com.macaplix.squareGames.dto.GameTypeInfo;
import org.springframework.stereotype.Component;

import java.util.Collection;

public interface GameCatalog {
    Collection<String> getGameIdentifiers();
    GameTypeInfo[] getGameTypes( GameService gameService);
}
