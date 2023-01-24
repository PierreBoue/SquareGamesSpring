package com.macaplix.squareGames;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Token;

import java.util.Map;
//@JsonUnwrapped GameParams params,
public record GameParamsAnswer( int gameIndex, int playerCount, int boardSize,  String gameUUID, String errorMessage, boolean isOk, String gameName) {
}
