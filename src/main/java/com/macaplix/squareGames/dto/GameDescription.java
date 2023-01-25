package com.macaplix.squareGames.dto;

import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Token;

import java.util.Map;

public record GameDescription(String key, String name, int boardSize, int playerCount, Map<CellPosition, Token> board)
{
}
