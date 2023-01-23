package com.macaplix.squareGames;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public record GameParamsAnswer(@JsonUnwrapped GameParams params, String gameUUID, String errorMessage, boolean isOk, String gameName ) {
}
