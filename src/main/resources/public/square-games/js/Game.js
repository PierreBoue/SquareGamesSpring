// JavaScript Document
// int gameIndex, int playerCount, int boardSize,  String gameUUID, String errorMessage, boolean isOk, String gameName
class Game
{
    gameIndex;
    gameType;
    playerCount;
    boardSize;
    gameID;
    sqlID;
    creation;
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
        this.duration = dto["duration"];
        this.board = dto["board"];
        this.playerCount = dto["playerCount"];
        this.boardSize = dto["boardSize"];
        this.gameName = dto["typeLocale"];
        //alert(dto);
    }
    getDomElement()
    {
        /*
                      <tr class="">
                <td>4f54df4z5G4W6D4FG</td>
                <td>Morpion</td>
              </tr>

        */
        const elt = document.createElement("tr");
        elt.addEventListener("click", selectGame );
        elt.setAttribute("title", this.gameID);
        elt.setAttribute("id","g" + this.gameIndex);
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
    gamesDomList = [];
    gameIndex=0;
    constructor()
    {
        
    }
    add( game )
    {
        
        const tgbe = document.getElementById("gameTableBody");
       if ( this.gamesList.length > 0 )
        {
            //alert(tgbe.firstChild.nodeName);
            tgbe.children[0].setAttribute( "class", "" );
            this.gamesList[0].selected = false;
        }
        this.gamesList.unshift( game );
        game.selected = true;
        //this.gameIndex++;
        tgbe.insertBefore(game.getDomElement(), tgbe.firstChild);
        this.gameDomList.unshift( tgbe.children[0]));
    }
    selectGameAtIndex( idx )
    {
        
    }
}