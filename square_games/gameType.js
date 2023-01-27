function GameType( jsonGameType )
{
// int index, String defaultName, String localName, int defaultPlayerCount, int defaultBoardSize
    this.index= jsonGameType["index"];
    this.name = jsonGameType["defaultName"];
    this.localName = jsonGameType["localName"];
    this.defaultPlayerCount = jsonGameType["defaultPlayerCount"];
    this.minPlayerCount = jsonGameType["minPlayerCount"];
    this.maxPlayerCount = jsonGameType["maxPlayerCount"];
    this.defaultBoardSize = jsonGameType["defaultBoardSize"];
    this.minBoarSize = jsonGameType["minBoardSize"];
    this.maxBoardSize = jsonGameType["maxBoardSize"];
    this.getListItem = function () {
        return "<li class=\"list-group-item\">" + this.localName + " ( " + this.localName +" ) </li>\n";
    };
}