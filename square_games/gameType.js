function GameType( gameType )
{
// int index, String defaultName, String localName, int defaultPlayerCount, int defaultBoardSize
    this.index= gameType["index"];
    this.name = gameType["defaultName"];
    this.localName = gameType["localName"];
    this.defaultPlayerCount = gameType["defaultPlayerCount"];
    this.minPlayerCount = gameType["minPlayerCount"];
    this.maxPlayerCount = gameType["maxPlayerCount"];
    this.defaultBoardSize = gameType["defaultBoardSize"];
    this.minBoarSize = gameType["minBoardSize"];
    this.maxBoardSize = gameType["maxBoardSize"];
    this.getListItemHmtml = function () {
        return "<li class=\"list-group-item\">" + this.localName + " ( " + this.localName +" ) </li>\n";
    };
    this.addItemToList = function () {
        const parent = document.getElementById("gametypelist");
        const elt =document.createElement("li");
        const href = document.createElement("a");
        href.setAttribute("href", "javascript:alert('ouh!!!');");
        //create new text node
        const eltValue = document.createTextNode(this.index + " - " + this.localName + " ( " + this.name +" )");
        href.appendChild( eltValue );
        //add text node to li element
        elt.appendChild(href);

        //add new list element built in previous steps to unordered list
        //elt.className = "list-group-item text-start li-hover";
        parent.appendChild(elt);

        return elt;
    };
}
