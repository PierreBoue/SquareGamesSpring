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
    selected = false;
    constructor( dto )
    {
        //int gameIndex, String gameKey, int sqlid, String typeLocale, String typeName, int playerCount, int boardSize, Map<CellPosition, Token> board, Date creation, int duration, String errorMessage, boolean isOk
        this.gameIndex = dto["gameIndex"];
        this.gameID = dto["gameKey"];
        this.sqlID = dto["sqlid"];
        this.gameType = dto["typeName"];
        this.creation = new Date( dto["creation"] );
        //console.log(dto["creation"] + " - " + this.creation);
        this.duration = dto["duration"];
        this.board = dto["board"];
        this.playerCount = dto["playerCount"];
        this.boardSize = dto["boardSize"];
        this.gameName = dto["typeLocale"];
        //alert(dto);
    }
    play()
    {
        this.start2play = new Date();
        displayBoard(this.boardSize);
    }
    stop()
    {
        this.duration = new Date() - this.start2play;
    }
    getDomElement( idx )
    {
        const elt = document.createElement("tr");
        elt.addEventListener("click", selectGame );
        elt.setAttribute("title", this.gameID);
        elt.setAttribute("id","g" + idx);
        elt.setAttribute("role", "button");
        //alert(this.gameName);
        const creat = this.creation.toLocaleString( undefined, { hour:'2-digit', minute:'2-digit', day: '2-digit', month: '2-digit', year: 'numeric' } );
        for ( let text of [creat, this.gameName])
        {
            //alert(text);
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