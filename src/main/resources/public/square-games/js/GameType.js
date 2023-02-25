class GameType
{
    index;
    name;
    localName;
    defaultPlayerCount;
    minPlayerCount;
    maxPlayerCount;
    defaultBoardSize;
    minBoarSize;
    maxBoardSize;
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
    }
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
    }
    updateUI()
    {
        document.getElementById("gameTypeTitle").innerHTML = this.localName;
        const playerCountInput = document.getElementById("playerCount");
        playerCountInput.value = this.defaultPlayerCount;
        playerCountInput.min = this.minPlayerCount;
        playerCountInput.max = this.maxPlayerCount;
        const boardSizeInput = document.getElementById("boardSize");
        boardSizeInput.value = this.defaultBoardSize;
        boardSizeInput.min = this.minBoarSize;
        boardSizeInput.max = this.maxBoardSize;
        displayBoard(this.defaultBoardSize);
    }
}
class GameTypeController
{
    domElements = [];
    index=0;
    gameTypes = [];
    constructor(  )
    {
        const gtls = document.getElementById("gametypelist");
        while ( gtls.lastElementChild ) gtls.removeChild( gtls.lastElementChild );
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
        this.gameTypes[this.index].updateUI();
        
    }
    getGameNameAtIndex( idx )
    {
        gameTypes[idx].localName;
    }
}
