package com.macaplix.squareGames;

import org.springframework.stereotype.Component;

import java.util.Collection;

public interface GameCatalog {
    Collection<String> getGameIdentifiers();
}
