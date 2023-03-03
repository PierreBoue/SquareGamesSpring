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
        if ( apidat.position != null ) this.position = new Position( apidat.position.x, apidat.position.y);
        this.allowedMoves = apidat.allowedMoves;
        //console.log( "Pion :" );
        //console.log( apidat );
    }
    display( carpet )
    {
        if ( carpet == "board")
        {
            if ( this.position == undefined )
            {
                console.log("position undefined " + this.symbol);
                return;
            }
            const cell = this.position.boardDiv();
            cell.textContent = "";
            cell.addChild( this.innerPion());
        } else {
            const cardom = document.getElementById(carpet);
            const piondom = document.createElement("div");
            piondom.setAttribute("class", "col col-1 ps-1 pe-1 border border-dark border-3 m-2 start-0 top-50 fs-6 fw-bold boardtable");
            piondom.setAttribute("role","button");
            piondom.appendChild( this.innerPion());
            cardom.appendChild( piondom );
        }
    }
    innerPion()
    {
        const inp = document.createElement("span");
        inp.setAttribute("class", "pion" + this.symbol.toUpperCase());
        
        const txtnde = document.createTextNode( this.symbol );
        inp.appendChild(txtnde);
        return inp;
    }
}

class PionController
{
    index=0;
    listePions=[];
    constructor( apidat )
    {
        if ( (apidat == undefined) || ( apidat == null ) || ( ! Array.isArray(apidat))) return;
        //this.listePions = apidat.map( this.api2Pion);
        for( let piondto of apidat )
        {
            const p = new Pion( piondto, this.index++ );
            if ( p == undefined )
            {
                console.log("pion not created "+ piondto );
                continue;
            }
            this.listePions.push( p );
            
        }
        this.index =0;
    }
/*
    api2Pion( apidat )
    {
       if ( this == undefined )
       {
           console.log("this api2pion is undefined");
           return null;
       } else 
        return new Pion( apidat, this.index++ );
    }
    */
    displayPions( carpet )
    {
        if ( carpet != "board" ) document.getElementById(carpet).textContent = "";
        for ( let pion of this.listePions ) pion.display( carpet );   
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
    boardDiv()
    {
        return  document.getElementById("bc"+ this.x + "-" + this.y);
    }
}