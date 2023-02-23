// JavaScript Document
//var apiController = new ApiController();

function sendUserData()
{
    const name = document.getElementById("floatingInput").value;
    const pswd = document.getElementById("floatingPassword").value;
    if ( ! isIndexPage ) document.getElementById("tokenView").value = "";// else console.log( name +" - " +pswd );
    if ( isIndexPage ) displayMessage( "info", "Attente réponse du serveur..."); else printMessage("Attente réponse serveur.");
    apiController.sendPostRequest( '/api/public/login', 'login call', tokenReceive, {"username":name, "password":pswd});
}
function tokenReceive( jsn )
{
    const tokenDTO = JSON.parse(jsn);
   if ( ! isIndexPage )  document.getElementById("tokenView").value = tokenDTO["token"];
    displayMessage( "success", tokenDTO["username"] + " connecté le " + ( new Date()).toLocaleString("fr-FR",{ hour:'2-digit', minute:'2-digit', weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' }) );
   //apiController.setToken(tokenDTO["token"]);
    window.sessionStorage.setItem("user", jsn);
    console.log("tokenreceive");
    apiController.setUser(tokenDTO);
    toggleAuthForm( false );
    if ( isIndexPage   && ( apiController.getToken() != null ))
    {
        askAPIforGames();
    }
}
function logout()
{
    console.log("logout");
    apiController.setUser( null );
    sessionStorage.removeItem("user");
    toggleAuthForm( true );
}
function toggleAuthForm( ison )
{
    const lgin = document.getElementById("login-form")
     lgin.style.visibility   = (ison)? "visible":"collapse";
    lgin.classList.toggle( "collapse");
    
    const lgd = document.getElementById("logged");
    lgd.style.visibility = (ison)?"collapse":"visible";
    lgd.classList.toggle( "collapse");
    lgd.parentNode.insertBefore( (ison)?lgd:lgin, (ison)?lgin:lgd );
    
}
function copyToken( token )
{
    if ( token == null ) token = document.getElementById("tokenView").value;
    navigator.clipboard.writeText("Bearer " + token).then(() => {
              //alert("Token writen to pateboard:\n" + token);
                printMessage("le Token a été copié");
            })
            .catch(err => {
              alert('Error in copying token:\n', err);
            });
    
}
function gotoProtectedURL( url )
{
    //apiController.gotoProtected(url);
    window.location.href = url;
}
function printMessage( mesg )
{
    if ( ! isIndexPage  )
    {
         document.getElementById("mesgbox").innerHTML = mesg;
    } else {
       // printULlog("warn", mesg, document.getElementById("gametypelist"), false); // console.log( mesg);
        displayMessage( "info", mesg );
        console.log( mesg );
    }
}