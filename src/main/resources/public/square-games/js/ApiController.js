// JavaScript Document
class ApiController
{
    _ajaxRequest;
    _callbackFunction;
    _requestName;
    _token;
    //_tokenDate;
    _tokenExpiration;
    _userimage;
    rootURL;
    constructor()
    {
        try 
        {
           this._ajaxRequest = new XMLHttpRequest();
        } catch (e) {
            alert( e.toString());
        }
        if ( this._ajaxRequest == undefined) console.assert("ajax not instanciated !!!!"); //else console.log( "opened ------> " + this._ajaxRequest.LOADING)
        this.rootURL = document.location.origin;
       // window.sessionStorage.clear();
        const ssusr = window.sessionStorage.getItem("user");
        if ((ssusr !== undefined) && (ssusr != null))
        {
            this.setUser( JSON.parse(ssusr));
        } else this._token = null;
        
        //
    }
    sendGetRequest( url, requestNm, callbackFctn, authenticate )
    {
        this._callbackFunction = callbackFctn;
        this._requestName = requestNm;
        const tkn = this.getToken();
        console.log( this.rootURL + url);
        this._ajaxRequest.open( "GET", this.rootURL + url, true);
        if ( authenticate && ( tkn != null ) && ( this._token.length )) this._ajaxRequest.setRequestHeader( "Authorization", "Bearer " +  tkn);
        this._ajaxRequest.onreadystatechange = this._privateCallback;
        this._ajaxRequest.send( null );
        
    }
    sendPostRequest( url, requestNm, callbackFctn, body )
    {
        this._callbackFunction = callbackFctn;
        this._requestName = requestNm;
        this._ajaxRequest.open("POST", this.rootURL + url, true);
        const tkn = this.getToken();
        if ( tkn!= null ) this._ajaxRequest.setRequestHeader( "Authorization", "Bearer " + tkn);
        this._ajaxRequest.onreadystatechange = this._privateCallback;
        this._ajaxRequest.setRequestHeader('Accept', 'application/json');
      
        //ajaxRequest.setRequestHeader('contentType', 'application/json; charset=UTF-8'); 

        this._ajaxRequest.setRequestHeader('Content-Type', 'application/json');//
        this._ajaxRequest.withCredentials = true; // added to test localajaxRequest.onreadystatechange = processGameTypeRequest;
        this._ajaxRequest.send(JSON.stringify(body));
       
    }
    setUser( userdto )
    {//String username, String token, Date expiration, String imgpath, Collection<? extends GrantedAuthority> roles
       console.log( userdto );
        this._token = userdto["token"];
        this._tokenExpiration = new Date( userdto["expiration"]);
        this._userimage = userdto["imgpath"];
        //const imgsrc = ( this._userimage == null)?"https://www.selfstir.com/wp-content/uploads/2015/11/default-user.png": this._userimage;
       // if (  imgsrc != null ) document.getElementById("avatar").src = imgsrc;
     }
    setToken( tkninfo )// inutilisée
    {
        this._token = tkninfo["token"];
        this._tokenDate = tkninfo["date"];
    }
    getToken()
    {
        if (( new Date() - this._tokenExpiration) > 0 )
        {
            this._token = null;
            console.log("expired token");
        }
        return this._token;
    }
    getUserImage()
    {
        return this._userimage;
    }
    _privateCallback()
    {
       // this = apiController;
        //console.log( apiController._ajaxRequest );
        if (apiController._ajaxRequest.readyState == 4) 
        {
             if (apiController._ajaxRequest.status == 200)
             {
                const jsn = apiController._ajaxRequest.responseText;
                apiController._logJson( jsn );
                apiController._callbackFunction( jsn );
             } else if (apiController._ajaxRequest.status == 401){
                 alert("Identifiant ou mot de passe incorrect, merci de réessayer");
                 
             } else {
                 alert("Le serveur a renvoyé une erreur ", apiController._ajaxRequest.status);
             }
            
        }

    }

    _logJson( jsn )
    {
        const debugbox =document.getElementById("debugbox");
        debugbox.value = debugbox.value +
            "\n______________ " + this._requestName + " ______________\n" +
            jsn +
            "\n___________________________________________\n";
    }
}




