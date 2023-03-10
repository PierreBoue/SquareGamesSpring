// JavaScript Document
// int gameIndex, int playerCount, int boardSize,  String gameUUID, String errorMessage, boolean isOk, String gameName
class Game
{
    //listIndex;
    gameIndex;
    gameType;
    playerCount;
    boardSize;
    gameID;
    sqlID;
    creation;
    start2play;
    gameName;
    gameType;
    duration;
    board;
    boardUI;
    remainingTokens;
    lostTokens;
    selected = false;
    constructor( dto )
    {
        //int gameIndex, String gameKey, int sqlid, String typeLocale, String typeName, int playerCount, int boardSize, Map<CellPosition, Token> board, Date creation, int duration, String errorMessage, boolean isOk
        this.gameIndex = dto["gameIndex"];
        this.gameID = dto["gameKey"];
        this.sqlID = dto["sqlid"];
        this.gameType = dto["typeName"];
        this.creation = new Date( dto["creation"] * 1000);
        this.duration = dto["duration"];
        this.board = new PionController( dto["board"] );
        this.remainingTokens = new PionController( dto["remainingTokens"]);
        this.lostTokens = new PionController( dto["lostToken"] );
        this.playerCount = dto["playerCount"];
        this.boardSize = dto["boardSize"];
        this.gameName = dto["typeLocale"];
        this.boardUI = new Board(this.boardSize);
    }
    play()
    {
        this.start2play = new Date();
        //this.boardUI.clear();
        this.boardUI.refresh();
        //displayBoard(this.boardSize);
        document.getElementById( "currentGameNameDiv" ).innerHTML = this.gameName;
        const url = "/games/" +this.gameID + "/tokens";
        apiController.sendGetRequest( url, "ask for tokens", this.receivedPionsData, true );
        this.board.displayPions("board") ;
        this.remainingTokens.displayPions("remain") ;
        this.lostTokens.displayPions("lost");
        
    }
    receivedPionsData( psjson )
    {
        console.log( psjson );
    }
    showMove( movedTrez )
    {
        //MovedTokenResult( String gameid, int tokenid, int newx, int newy, boolean success, String errorMessage)
        if (( movedTrez == undefined) || ( ! movedTrez.success ))
        {
            displayMessage("danger",  ( movedTrez == undefined)?"moveTrez undefined":movedTrez.errorMessage );
            return;
        }
        const piondep = this.remainingTokens.get( movedTrez.tokenid );
        //pas bon remainingToken n'est pas une liste mais un controller
        piondep.position = new Position( movedTrez.newx, movedTrez.newy );
        piondep.display( "board");
    }
    playNext()
    {
        
    }
    displayPions()
    {
        let c = 0;
        for ( let pion of this.remainingTokens )
        {
            pion.display("remain");
        }
    }
    stop()
    {
        this.duration += new Date() - this.start2play;
    }
    getDomElement( idx )
    {
        const elt = document.createElement("tr");
        elt.addEventListener("click", selectGame );
        elt.setAttribute("title", this.gameID);
        elt.setAttribute("id","g" + idx);
        elt.setAttribute("role", "button");
        const creat = this.creation.toLocaleString( undefined, { hour:'2-digit', minute:'2-digit', day: '2-digit', month: '2-digit', year: 'numeric' } );
        for ( let text of [creat, this.gameName])
        {
            if ( this.selected ) elt.setAttribute("class","bg-warning");
            const td = document.createElement("td");
            //td.setAttribute("class", "col-md-auto");
            const txt = document.createTextNode(text);
            td.appendChild( txt );
            elt.appendChild( td );
            
        }
        return elt;
    }
}
class GamesController
{
    gamesList = [];
    //gamesDomList = [];
    gameIndex=0;
    constructor()
    {
        
    }
    add( game )
    {
        
        this.gamesList.unshift( game );
        //reindex();
       // this.gameDomList.unshift( tgbe.children[0]));
    }
    currentGame()
    {
        return this.gamesList[this.gameIndex];
    }
    updateUI()
    {
        const tgbe = document.getElementById("gameTableBody");
        if ( this.currentGame() !== undefined) this.currentGame().selected = false;
        this.gameIndex =0;
       // reindex();
        tgbe.textContent ="";
        for ( let i =0; i < this.gamesList.length; i++)
        {
            let gme = this.gamesList[i];
            if ( i == 0 )
            {
                gme.selected=true;
                //tgbe.children[0].setAttribute( "class", "bg-warning" );
                //this.gamesList[0].selected = true;
            } else {
                //tgbe.children[0].setAttribute( "class", "" );
                this.gamesList[0].selected = false;
            }
            tgbe.appendChild(gme.getDomElement( i ));
        }
    }
    selectGameAtIndex( idx )
    {
        const tgbe = document.getElementById("gameTableBody");
        document.getElementById("g"+ (this.gameIndex )).setAttribute( "class", "" );
        //tgbe.children[this.gameIndex].setAttribute( "class", "" );
        this.gameIndex = idx;
        //console.log("id = g" + (this.gameIndex  ));
        document.getElementById("g"+ (this.gameIndex  )).setAttribute( "class", "bg-warning" );
        const game = this.currentGame();
        game.play();
        //tgbe.children[this.gameIndex].setAttribute( "class", "bg-warning" );
    }
    getGameByID( gid )
    {
        const game = this.gamesList.find( function ( g ) {
           return ( g.gameID == gid );
        });
        return game;
    }
    /*
    reindex()
    {
        for ( let i=0; i < gamesList.length; i++)
        {
            gamesList[i].listIndex = i;
        }
    }
    */
}