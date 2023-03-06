// JavaScript Document
 
var apiController = null;//new ApiController();
var gameTypeController = null;
var gamesController = null;

function processGameTypeRequest( jsn )
{
    //sendPostRequest( url, requestNm, callbackFctn, body )
    populateTypeList(jsn);
    if ( apiController.getToken() == null )
    {
        return;
    }
    askAPIforGames();
    //const url = "/games";
   // apiController.sendGetRequest( url, "games list request", populateGameList );
}
function printULlog( type, mesg, parent, append )
{
    let listli = document.createElement("li");
    switch ( type )
    {
        case "log":
            listli.setAttribute("class","");
            break;
        case "warn":
           listli.setAttribute("class","alert alert-warning");
            break;
        case "error":
           listli.setAttribute("class","");
            break;
        default:
           listli.setAttribute("class","");
            break;
    }
    listli.appendChild( document.createTextNode( mesg ));
    if ( append )
    {
        parent.appendChild(listli);
    } else {
        parent.replaceChildren( listli );
    
    }
    //let ul = parent
}
function askAPIforGames()
{
    if ( apiController.getToken() == null)
    {
        displayMessage("warning", "Vous devez être identifié pour pouvoir jouer");
        toggleAuthForm( true );
        return;
    }
    const url = "/games";
    apiController.sendGetRequest( url, "games list request", populateGameList, true );
    
}
//var debugon = false;
function toggleDebugBox( event )
{
   
    //document.getElementById("currentGameNameDiv").disabled
    if ( ! event.altKey ) return;
    //console.log(event);
    const dbgbox = document.getElementById("debugbox");
    dbgbox.classList.toggle("visually-hidden");
    
    //debugon = ! debugon;
}
function selectGameType( idx )
{
    gameTypeController.selectTypeAtIndex( idx );
}
function selectGame( event )
{
   try
    {
        const idx = parseInt( event.currentTarget.id.slice(1));
        //console.log("selected index: " +idx );
        gamesController.selectGameAtIndex( idx );
    } catch( e ) {
        console.error(e);
    }
}
function selectCell( celltd )
{
    const position = Position.positionFromid( celltd.id );
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
    gamesController.updateUI();
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
    //console.log( jsonList );
    const games = JSON.parse(jsonList);
    for ( let g of games)
    {
        //console.log( "- " + g["creation"]);
        const game = new Game( g );
        gamesController.add( game);
    }
    gamesController.updateUI();
    if (  games.length > 0 ) gamesController.selectGameAtIndex(0);
    //console.log("UI updated");
}
function initContent()
{
    apiController = new ApiController();
    const url ="/api/public/games/types";
    apiController.sendGetRequest( url, "game types query", processGameTypeRequest, false);
    if ( apiController.getToken() != null )
    {
        toggleAuthForm( false );
        displayMessage("success",  apiController.userName + ", vous êtes identifié(e)");
    }
    document.getElementById("avatar").src = apiController.getUserImage();
    document.getElementById("debugbut").addEventListener("click", toggleDebugBox );
}
function displayMessage( type, msg )
{
    //alert alert-success
   //alert-primary, alert-secondary, alert-success alert-danger, alert-warning, alert-info, alert-light, alert alert-dark
    let clss = "alert alert-" + type + " fade show w-auto" ;
    const alertbox = document.getElementById("alertbox");
    if ( alertbox == null )
    {
        console.log("alertbox not found");
        console.log(msg);
        return;
    }
    alertbox.setAttribute("class", clss );
    while (alertbox.firstChild) 
    {
        alertbox.removeChild(alertbox.firstChild);
    }
    alertbox.appendChild( document.createTextNode( msg ));
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
