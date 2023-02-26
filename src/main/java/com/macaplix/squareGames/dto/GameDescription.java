package com.macaplix.squareGames.dto;

import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Token;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
//int gameIndex, int playerCount, int boardSize,  String gameUUID, String errorMessage, boolean isOk, String gameName
public record GameDescription(int gameIndex, String gameKey, int sqlid, String typeLocale, String typeName, int playerCount, int boardSize, Map<CellPosition, Token> board, Collection<Token> remainingTokens, Collection<Token> lostToken, long creation, int duration, String errorMessage, boolean isOk )
{
}
