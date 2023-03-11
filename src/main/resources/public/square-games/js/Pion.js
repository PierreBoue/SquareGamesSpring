// JavaScript Document
class Pion 
{
   index;
    ownerId;
    symbol;
    position = null;
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
            if (( cell == undefined ) || ( cell == null )) console.log("*** no cell ***");
            cell.textContent = "";
            cell.appendChild( this.innerPion());
            //cell.setAttribute("role","button");
        } else {
            const cardom = document.getElementById(carpet);
            const piondom = document.createElement("div");
            piondom.setAttribute("class", "col col-1 ps-1 pe-1 border " + ((this.index == 0)?"border-danger":"border-dark") + " border-3 m-2 start-0 top-50 fs-6 fw-bold boardtable");
            //piondom.setAttribute("role","button");
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
    get ( pionid )
    {
        return this.listePions[pionid];
    }
    shift( carpet )
    {
        this.listePions.shift();
        //this.reindex();
        const carptdom = document.getElementById(carpet);
        carptdom.removeChild( carptdom.firstChild );
        carptdom.firstChild.classList.add("border-danger");
    }
    reindex()
    {
        let i = 0;
        for ( let pion of this.listePions )
        {
            pion.index = i++;
        }
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
    selectPion( idx )
    {
        this.index = idx;
        
    }
/*
    findPionByID( pid, rmv )
    {
        const tkn = this.listePions.find( function ( p ) {
            return ( pid == p.
        });
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
    static positionFromid( cid )
    {
        const compo = cid.split("-");
        if (( ! Array.isArray( compo)) || ( compo.length < 2 ))
        {
            console.log("compo:");
            console.log( compo );
            return null;
        }
        const x = parseInt(compo[0].slice(2));
        const y = parseInt( compo[1]);
        return new Position( x, y );
    }
}