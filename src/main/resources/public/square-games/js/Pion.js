// JavaScript Document
class Pion 
{
    symbol;
    position;
    allowedMoves=[];
    constructor( apidat )
    {
        console.log( apidat );
    }
}

class PionController
{
    listePions=[];
    constructor( apidat )
    {
        //this.listePions = apidat.map( this.apidat2Pion);
    }
    apidat2Pion( apidat )
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