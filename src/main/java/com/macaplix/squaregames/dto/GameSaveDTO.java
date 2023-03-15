package com.macaplix.squaregames.dto;

import java.time.Duration;
import java.util.Date;

public record GameSaveDTO(int sqlid, String gameKey, int gameType, int currentPlayerID, String gameStatus,
                          int boardSize, Date creationDate, Duration duration, boolean success, String errorMessage) {
}
