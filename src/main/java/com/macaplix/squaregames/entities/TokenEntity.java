package com.macaplix.squaregames.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //String gameid, CellPosition position, int tokenIndex, String name, boolean canMove, boolean onBoard
    private String gameId;
    private Integer positionX;
    private Integer positionY;
    private String name;
    private Boolean canMove;
    private Boolean onBoard;

    public Integer getId() {
        return id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCanMove() {
        return canMove;
    }

    public void setCanMove(Boolean canMove) {
        this.canMove = canMove;
    }

    public Boolean getOnBoard() {
        return onBoard;
    }

    public void setOnBoard(Boolean onBoard) {
        this.onBoard = onBoard;
    }
}
