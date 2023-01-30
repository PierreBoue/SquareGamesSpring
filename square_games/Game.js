// JavaScript Document
// int gameIndex, int playerCount, int boardSize,  String gameUUID, String errorMessage, boolean isOk, String gameName
class Game
{
    gameIndex;
    gameType;
    playerCount;
    boardSize;
    gameID;
    gameName;
    board;
    constructor( dto )
    {
        this.gameIndex = dto["gameIndex"];
        this.playerCount = dto["playerCount"];
        this.boardSize = dto["boardSize"];
        this.gameID = dto["gameUUID"];
        this.gameName = dto["gameName"];
    }
}
class GamesController
{
    gamesList = [];
    gameIndex=0;
    constructor()
    {
        
    }
    add( game )
    {
        this.gamesList.push( game );
        this.gameIndex++;
    }
}