// JavaScript Document
class ApiController
{
    _ajaxRequest;
    _callbackFunction;
    _requestName;
    rootURL;
    constructor()
    {
        try 
        {
           this._ajaxRequest = new XMLHttpRequest();
        } catch (e) {
            alert( e.toString());
        }
        if ( this._ajaxRequest == undefined) console.assert("ajax not instanciated !!!!"); else console.log( "opened ------> " + this._ajaxRequest.LOADING)
        this.rootURL = document.location.origin;
    }
    sendGetRequest( url, requestNm, callbackFctn )
    {
        this._callbackFunction = callbackFctn;
        this._requestName = requestNm;
        this._ajaxRequest.open( "GET", this.rootURL + url, true);
        this._ajaxRequest.onreadystatechange = this._privateCallback;
        this._ajaxRequest.send( null );
        
    }

    sendPostRequest( url, requestNm, callbackFctn, body )
    {
        this._callbackFunction = callbackFctn;
        this._requestName = requestNm;
        this._ajaxRequest.open("POST", this.rootURL + url, true);
        this._ajaxRequest.onreadystatechange = this._privateCallback;
        this._ajaxRequest.setRequestHeader('Accept', 'application/json');
      
        //ajaxRequest.setRequestHeader('contentType', 'application/json; charset=UTF-8'); 

        this._ajaxRequest.setRequestHeader('Content-Type', 'application/json');//
        this._ajaxRequest.withCredentials = true; // added to test localajaxRequest.onreadystatechange = processGameTypeRequest;
        this._ajaxRequest.send(JSON.stringify(body));
       
    }
    _privateCallback()
    {
       // this = apiController;
        console.log( apiController._ajaxRequest );
        if (apiController._ajaxRequest.readyState == 4) 
        {
             if (apiController._ajaxRequest.status == 200)
             {
                const jsn = apiController._ajaxRequest.responseText;
                apiController._logJson( jsn );
                apiController._callbackFunction( jsn );
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




