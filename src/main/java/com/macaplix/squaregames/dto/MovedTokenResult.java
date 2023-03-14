package com.macaplix.squaregames.dto;

public record MovedTokenResult( String gameid, int tokenid, int newx, int newy, boolean success, String errorMessage) {
}
