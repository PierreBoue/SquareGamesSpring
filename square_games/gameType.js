class GameType
{
// int index, String defaultName, String localName, int defaultPlayerCount, int defaultBoardSize
    constructor(  gameType )
    {
        this.index= gameType["index"];
        this.name = gameType["defaultName"];
        this.localName = gameType["localName"];
        this.defaultPlayerCount = gameType["defaultPlayerCount"];
        this.minPlayerCount = gameType["minPlayerCount"];
        this.maxPlayerCount = gameType["maxPlayerCount"];
        this.defaultBoardSize = gameType["defaultBoardSize"];
        this.minBoarSize = gameType["minBoardSize"];
        this.maxBoardSize = gameType["maxBoardSize"];
    }
    getListItemHmtml() {
        return "<li class=\"list-group-item\">" + this.localName + " ( " + this.localName +" ) </li>\n";
    };
    addItemToList() {
        const parent = document.getElementById("gametypelist");
        const elt =document.createElement("li");
        elt.className = "nav-item";
        const href = document.createElement("a");
        href.className = ( this.index == 0 )?"nav-link active":"nav-link";
        href.setAttribute("href", "javascript:selectGameType( " + this.index + " );");
        //create new text nodethis.index + " - " + this.localName + " ( " + this.name +" )"
        const eltValue = document.createTextNode(this.localName);
        href.appendChild( eltValue );
        //add text node to li element
        elt.appendChild(href);

        //add new list element built in previous steps to unordered list
        //elt.className = "list-group-item text-start li-hover";
        parent.appendChild(elt);

        return elt;
    };
}
class GameTypeController
{
    constructor(  )
    {
        this.domElements = [];
        this.index=0;
        this.gameTypes = [];
    }
    add( gtype )
    {
        let item = gtype.addItemToList();
        this.gameTypes.push( gtype );
        this.domElements.push( item );
    }
    selectTypeAtIndex( idx )
    {
        this.domElements[this.index].children[0].className ="nav-link";
        this.index = idx;
        this.domElements[this.index].children[0].className ="nav-link active";
        
    }
}
