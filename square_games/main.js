// JavaScript Document
var ajaxRequest = null; 
var gameTypeController = null;
var gamesController = null;
  //Browser Support Code
function ajaxFunction()
{

    try {
       // Opera 8.0+, Firefox, Safari 
       ajaxRequest = new XMLHttpRequest();
    } catch (e) {

       // Internet Explorer Browsers
       try {
          ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
       } catch (e) {

          try {
             ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
          } catch (e) {

             // Something went wrong
             alert("Your browser broke!");
             return false;
          }
       }
    }
 }
 ajaxFunction();
 //
 function processGameTypeRequest()
 {
  if (ajaxRequest.readyState == 4) 
  {
     if (ajaxRequest.status == 200)
     {
        const jsn = ajaxRequest.responseText;
        displayJson(jsn,"game type answer");

        populateTypeList(jsn);
         const url = "http://127.0.0.1:8081/games";
         ajaxRequest.open( "GET", url, true);
        ajaxRequest.onreadystatechange = processGameListRequest;
        ajaxRequest.send( null );
     }
  }
}
function processGameListRequest()
{
    if (ajaxRequest.readyState == 4)
    {
        if (ajaxRequest.status == 200)
        {
            const jsn = ajaxRequest.responseText;
            displayJson(jsn,"get game list");
            populateGameList(jsn);
        }
    }
}

function processCreateGameRequest()
{
    if (ajaxRequest.readyState == 4)
    {
        if (ajaxRequest.status == 200)
        {
            const jsn = ajaxRequest.responseText;
            console.log(jsn);
            displayJson(jsn,"game creation");
            newGameCreated( jsn );
            //populateGameList(jsn);
        }
    }
}
function displayJson( jsn, msg )
{
    const debugbox =document.getElementById("debugbox");
    debugbox.value = debugbox.value +
        "\n______________ " + msg + " ______________\n" +
        jsn +
        "\n___________________________________________\n";
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
  {
    const url ="http://127.0.0.1:8081/games/types";
    ajaxRequest.onreadystatechange = processGameTypeRequest;
     // alert("init");
      //if ( ajaxRequest == null ) alert("pas d'ajax !!!");
    ajaxRequest.open("GET", url, true);
    ajaxRequest.withCredentials = true; // added to test localajaxRequest.onreadystatechange = processGameTypeRequest;
    ajaxRequest.send( null );

/*
    url = "http://127.0.0.1:8081/games";
    ajaxRequest.open("GET", url, true);
    ajaxRequest.onreadystatechange = processGameListRequest;
    ajaxRequest.send( null );
*/

  }
  function createGame()
  {
    const url ="http://127.0.0.1:8081/games";
     //int gameIndex, int playerCount, int boardSize
      const playerCount= parseInt( document.getElementById("playerCount").value );
      const boardSize= parseInt( document.getElementById("boardSize").value );
      const jsonGame = {"gameIndex":gameTypeController.index, "playerCount":playerCount, "boardSize":boardSize};
      //console.log(JSON.stringify( jsonGame));
        ajaxRequest.open("POST", url, true);
     ajaxRequest.onreadystatechange = processCreateGameRequest;
    ajaxRequest.setRequestHeader('Accept', 'application/json');
      
      //ajaxRequest.setRequestHeader('contentType', 'application/json; charset=UTF-8'); 

      ajaxRequest.setRequestHeader('Content-Type', 'application/json');//
        ajaxRequest.withCredentials = true; // added to test localajaxRequest.onreadystatechange = processGameTypeRequest;
        ajaxRequest.send(JSON.stringify(jsonGame));//
/*
      headers: {
          'Accept': 'application/json',
              'Content-Type': 'application/json'
      }
*/
      // console.log("create");
  }
