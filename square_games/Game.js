// JavaScript Document
// int gameIndex, int playerCount, int boardSize,  String gameUUID, String errorMessage, boolean isOk, String gameName
class Game
{
    gameIndex;
    gameType;
    playerCount;
    boardSize;
    gameID;
    gameName;
    board;
    selected = false;
    constructor( dto )
    {
        //int gameIndex, String gameKey, int sqlid, String typeLocale, String typeName, int playerCount, int boardSize, Map<CellPosition, Token> board, Date creation, int duration, String errorMessage, boolean isOk
        this.gameIndex = dto["gameIndex"];
        this.playerCount = dto["playerCount"];
        this.boardSize = dto["boardSize"];
        this.gameID = dto["key"];
        this.gameName = dto["name"];
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
        //alert(this.gameName);
        for ( let text of [this.gameName, this.gameID])
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
    }
}