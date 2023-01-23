package com.macaplix.squareGames;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Token;

import java.util.Map;

public record GameParamsAnswer(@JsonUnwrapped GameParams params, String gameUUID, String errorMessage, boolean isOk, String gameName, Map<CellPosition, Token> board) {
}
