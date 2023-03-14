package com.macaplix.squaregames.service;

import com.macaplix.squaregames.dto.GameTypeInfo;

import java.util.Collection;

public interface GameCatalog {
    Collection<String> getGameIdentifiers();
    GameTypeInfo[] getGameTypes( GameService gameService);
}
