// JavaScript Document
var apiController = new ApiController();
if ( apiController != undefined ) console.assert("Hello Java Script Login");
//sendGetRequest( url, requestNm, callbackFctn )
//sendPostRequest( url, requestNm, callbackFctn, body )

function sendUserData()
{
    const name = document.getElementById("floatingInput").value;
    const pswd = document.getElementById("floatingPassword").value;
    apiController.sendPostRequest( '/api/public/login', 'login call', tokenReceive, {"username":name, "password":pswd});
}
function tokenReceive( jsn )
{
    const tokenDTO = JSON.parse(jsn);
    document.getElementById("tokenView").value = tokenDTO["token"];
    /*
    navigator.clipboard.writeText("Bearer" + tokenDTO["token"]).then(() => {
              alert("Token writen to pateboard:\n" + tokenDTO["token"]);
            })
            .catch(err => {
              alert('Error in copying text: ', err);
            });
            */
    //copyToken( tokenDTO["token"]);
}
function copyToken( token )
{
    if ( token == null ) token = document.getElementById("tokenView").value;
    navigator.clipboard.writeText("Bearer " + token).then(() => {
              alert("Token writen to pateboard:\n" + token);
            })
            .catch(err => {
              alert('Error in copying text: ', err);
            });
    
}