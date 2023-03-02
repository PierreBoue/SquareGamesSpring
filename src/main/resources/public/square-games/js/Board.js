// JavaScript Document

class Board
{
    size;
    constructor( siz )
    {
        this.size = siz;
    }
    clear()
    {
        const boardiv =document.getElementById("board");
        boardiv.textContent = "";
        const tabledom = document.createElement("table");
        tabledom.setAttribute("class", "table table-sm table-bordered border-3 border-dark m-auto boardtable");
        for ( let y = 0; y < this.size; y++)
        {
            const trdom = document.createElement("tr");
            trdom.setAttribute("class", "boardcell");
            for ( let x =0; x < this.size; x++)
            {
                const tddom = document.createElement("td");
                tddom.setAttribute("width","29");
                tddom.setAttribute("height", "29");
                tddom.setAttribute("class", "fs-6 fw-bold");
                tddom.setAttribute("id", "bc" + x + "-" + y );
                tddom.setAttribute("align","center");
                tddom.addEventListener("click", selectCell );
                tddom.innerHTML = "&nbsp;";
                //tddom.appendChild( document.createTextNode("&nbsp;"));
                trdom.appendChild( tddom );
            }
            tabledom.appendChild(trdom);
        }
        boardiv.appendChild( tabledom );
        //const boardesk =  document.getElementById("boarddesk");
        
    }
    refresh()
    {
        var htmlout = " <table class=\"table table-sm table-bordered border-3 border-dark m-auto boardtable\">\n";
        for ( let y = 0; y < this.size; y++)
        {
           htmlout += "     <tr class='boardcell'>\n";
            for ( let x =0; x < this.size; x++)
            {
                htmlout += "     <td align='center' width='29' height='29' class='fs-6 fw-bold' id='bc" + x + "-" + y + "' onclick='selectCell( this );'>\n";
                htmlout += "      &nbsp;\n";//"+ (((( x + y )%2) == 0)?"X":"O") +"
                htmlout += "     </td>\n";
            }
           htmlout += "     </tr>\n";
        }
        htmlout += "   </table>\n";
        //const boardesk =  document.getElementById("boarddesk");
        
        const boardiv =document.getElementById("board");
        boardiv.innerHTML = htmlout;
    }
}
