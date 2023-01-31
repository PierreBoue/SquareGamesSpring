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
        this.gameIndex = dto["gameIndex"];
        this.playerCount = dto["playerCount"];
        this.boardSize = dto["boardSize"];
        this.gameID = dto["gameUUID"];
        this.gameName = dto["gameName"];
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
        for ( let text of [this.gameName, this.gameID])
        {
            if ( this.selected ) elt.setAttribute("class","bg-warning");
            const td = document.createElement("td");
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
            tgbe.firstChild.setAttribute( "class", "" );
            this.gamesList[0].selected = false;
        }
        this.gamesList.unshift( game );
        game.selected = true;
        //this.gameIndex++;
        tgbe.insertBefore(game.getDomElement(), null);
    }
}