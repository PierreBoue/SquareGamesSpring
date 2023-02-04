// JavaScript Document
var apiController = new ApiController();
if ( apiController != undefined ) console.assert("Hello Java Script Login");
//sendGetRequest( url, requestNm, callbackFctn )
//sendPostRequest( url, requestNm, callbackFctn, body )

function sendUserData()
{
    const name = document.getElementById("username").value;
    const pswd = document.getElementById("password").value;
    apiController.sendPostRequest( '/api/public/login', 'login call', tokenReceive, {"username":name, "password":pswd});
}
function tokenReceive( jsn )
{
    tokenDTO = JSON.parse(jsn);
    document.getElementById("tokenView").value = tokenDTO["token"];
}