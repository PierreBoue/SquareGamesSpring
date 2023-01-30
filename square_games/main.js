// JavaScript Document
var ajaxRequest = null; 
var gameTypeController = null;
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
    const games = JSON.parse(jsonList);
}
 function initContent()
  {
    let url ="http://127.0.0.1:8081/games/types";
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
      console.log("create");
  }
