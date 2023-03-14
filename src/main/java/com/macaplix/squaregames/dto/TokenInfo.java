package com.macaplix.squaregames.dto;

import fr.le_campus_numerique.square_games.engine.CellPosition;

public record TokenInfo(String gameid, CellPosition position, int tokenIndex, String name, boolean canMove, boolean onBoard) {
}
