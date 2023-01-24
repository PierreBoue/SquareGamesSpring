package com.macaplix.squareGames;

import fr.le_campus_numerique.square_games.engine.CellPosition;

public record TokenInfo(String gameid, CellPosition position, String name, boolean canMove, boolean onBoard) {
}
