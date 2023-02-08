// JavaScript Document
 
var apiController = new ApiController();
var gameTypeController = null;
var gamesController = null;

function processGameTypeRequest( jsn )
{
    //sendPostRequest( url, requestNm, callbackFctn, body )
    populateTypeList(jsn);
    const url = "http://127.0.0.1:8081/games";
    apiController.sendGetRequest( url, "games list request", populateGameList );
}

function selectGameType( idx )
{
    gameTypeController.selectTypeAtIndex( idx );
}
function newGameCreated( jsonAnswer )
{
    let answ = null;
    console.log(jsonAnswer);
    try 
    {
        answ =JSON.parse( jsonAnswer );
    } catch ( erreur ){
        alert( "Server answer is unreadable: " + jsonAnswer + "\n" + erreur);
        return;
    }
    // int gameIndex, int playerCount, int boardSize,  String gameUUID, String errorMessage, boolean isOk, String gameName
    if ( ! answ["isOk"])
    {
        alert("game creation failed:\n" + answ["errorMessage"]);
        return;
    }
    g = new Game( answ );
    
    gamesController.add( g );
    
}
function populateTypeList( jsonList)
{
    const types = JSON.parse( jsonList);
    gameTypeController = new GameTypeController();
    for ( let i =0; i < types.length;   i++)
    {
        const item = new GameType( types[i]);
        if ( i == 0 ) item.updateUI();
        gameTypeController.add( item );
        //item.addItemToList();
    }
}
function populateGameList(jsonList)
{
    gamesController = new GamesController();
    const games = JSON.parse(jsonList);
    for ( let g of games)
    {
        //console.log(g);
        const game = new Game( g );
        gamesController.add( game);
    }
}
 function initContent()
{//sendPostRequest( url, requestNm, callbackFctn, body )
    const url ="/games/types";
    apiController.setToken( "Bearer " + window.localStorage.getItem("sgtoken"));
    apiController.sendGetRequest( url, "game types query", processGameTypeRequest);
}
function createGame()
{ //sendPostRequest( url, requestNm, callbackFctn, body )
    const url ="/games";
     //int gameIndex, int playerCount, int boardSize
    const playerCount= parseInt( document.getElementById("playerCount").value );
    const boardSize= parseInt( document.getElementById("boardSize").value );
    const jsonGame = {"gameIndex":gameTypeController.index, "playerCount":playerCount, "boardSize":boardSize};
    apiController.sendPostRequest( url, "create game request", newGameCreated, jsonGame );
}
