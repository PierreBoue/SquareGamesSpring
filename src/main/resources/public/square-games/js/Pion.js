// JavaScript Document
class Pion 
{
   index;
    ownerId;
    symbol;
    position;
    allowedMoves=[];
    constructor( apidat, idx )
    {
        this.index= idx;
        this.ownerId = apidat.ownerId;
        this.symbol = apidat.name;
        this.position = new Position( apidat.position.x, apidat.position.y);
        this.allowedMoves = apidat.allowedMoves;
        //console.log( "Pion :" );
        //console.log( apidat );
    }
    display( carpet )
    {
        if ( carpet == "board")
        {
            
        } else ‘{
            
        }
    }
}

class PionController
{
    index=0;
    listePions=[];
    constructor( apidat )
    {
        if ( (apidat == undefined) || ( apidat == null ) || ( ! Array.isArray(apidat))) return;
        this.listePions = apidat.map( this.api2Pion);
        this.index =0;
    }
    api2Pion( apidat )
    {
        return new Pion( apidat, this.index++ );
    }
    displayPions( carpet )
    {
        let c = 0;
            for ( let pion of this.remainingTokens ) pion.displayAtPosition();
        } else {}
            for ( let pion of this.remainingTokens ) pion.displayAtIndex( carpet, c++ );
        }
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