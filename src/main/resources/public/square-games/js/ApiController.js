// JavaScript Document
class ApiController
{
    _ajaxRequest;
    _callbackFunction;
    _requestName;
    _token;
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
        this._token = null;
    }
    sendGetRequest( url, requestNm, callbackFctn )
    {
        this._callbackFunction = callbackFctn;
        this._requestName = requestNm;
       if ( this._token != null ) this._ajaxRequest.setRequestHeader( "Authorization", this._token);
        console.log( this.rootURL + "%%%" + url);
        this._ajaxRequest.open( "GET", this.rootURL + url, true);
        this._ajaxRequest.onreadystatechange = this._privateCallback;
        this._ajaxRequest.send( null );
        
    }

    sendPostRequest( url, requestNm, callbackFctn, body )
    {
        this._callbackFunction = callbackFctn;
        this._requestName = requestNm;
        this._ajaxRequest.open("POST", this.rootURL + url, true);
        if ( this._token != null ) this._ajaxRequest.setRequestHeader( "Authorization", this._token);
        this._ajaxRequest.onreadystatechange = this._privateCallback;
        this._ajaxRequest.setRequestHeader('Accept', 'application/json');
      
        //ajaxRequest.setRequestHeader('contentType', 'application/json; charset=UTF-8'); 

        this._ajaxRequest.setRequestHeader('Content-Type', 'application/json');//
        this._ajaxRequest.withCredentials = true; // added to test localajaxRequest.onreadystatechange = processGameTypeRequest;
        this._ajaxRequest.send(JSON.stringify(body));
       
    }
    setToken( tkn )
    {
        this._token = tkn;
    }
    gotoProtected( url )
    {
      //console.log(this._token);
       this._ajaxRequest.open( this.rootURL +  url, {'Authorization':'Bearer ' + this._token},
           function (res)
           {
               console.log( "goto callback "+res);
           }
       );
       this._ajaxRequest.send( null);
        /*
        const cont = fetch( url, {
            method: 'GET',
            headers: new Headers({
                'Authorization': this._token
            })

        });
        document.write(cont);
        */

    }
    getToken()
    {
        return this._token;
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




