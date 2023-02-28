// JavaScript Document
class Pion 
{
    ownerId;
    symbol;
    position;
    allowedMoves=[];
    constructor( apidat )
    {
        this.ownerId = apidat.ownerId;
        this.symbol = apidat.name;
        this.position = new Position( apidat.position.x, apidat.position.y);
        this.allowedMoves = apidat.allowedMoves;
        //console.log( "Pion :" );
        //console.log( apidat );
    }
}

class PionController
{
    listePions=[];
    constructor( apidat )
    {
        if ( (apidat == undefined) || ( apidat == null ) || ( ! Array.isArray(apidat))) return;
        this.listePions = apidat.map( this.api2Pion);
    }
    api2Pion( apidat )
    {
        return new Pion( apidat );
    }
}
class Position
{
    x;
    y;
    constructor( x, y)
    {
        this.x = x;
        this.y = y;
    }
}