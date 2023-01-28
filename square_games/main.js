// JavaScript Document
var ajaxRequest = null; 
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
 ajaxRequest.onreadystatechange = processRequest;
 function processRequest()
 {
  if (ajaxRequest.readyState == 4) 
  {
     if (ajaxRequest.status == 200)
     {
        const jsn = ajaxRequest.responseText;
        document.getElementById("messagebox").value = jsn;
        populateTypeList(jsn);
     }
  }
}
function populateTypeList( jsonList)
{
    const types = JSON.parse( jsonList);
    for ( let i =0; i < types.length;   i++)
    {
        const item = new GameType( types[i]);
        item.addItemToList();
    }
}
 function initContent()
  {
    var url ="http://127.0.0.1:8081/games/types";

     // alert("init");
      //if ( ajaxRequest == null ) alert("pas d'ajax !!!");
    ajaxRequest.open("GET", url, true);
    ajaxRequest.withCredentials = true; // added to test local
    ajaxRequest.send( null );
  }
  function createGame()
  {
      console.log("create");
  }
