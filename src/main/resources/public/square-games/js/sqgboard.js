// JavaScript Document

function displayBoard( size )
{// style=\"width: 81px; height: 81px;\" p-0
    var htmlout = " <table class=\"table table-sm table-bordered border-3 border-dark m-auto boardtable\">\n";
    for ( let y = 0; y < size; y++)
    {
       htmlout += "     <tr class='boardcell'>\n";
        for ( let x =0; x < size; x++)
        {
            htmlout += "     <td align='center' width='26' height='26' id='bc" + x + "-" + y + "' onclick='selectCell( this );'>\n";
            htmlout += "      &nbsp;\n";
            htmlout += "     </td>\n";
        }
       htmlout += "     </tr>\n";
    }
    htmlout += "   </table>\n";
    const boardesk =  document.getElementById("boarddesk");
    //const  boardcard =  document.getElementById("boardcard");
    const boardiv =document.getElementById("boarddiv");
//    let width = 42 * ( size + 2 );
//    boardiv.style.width = width + "px;";
//    boardiv.style.height = width + "px;";
//    width += 40;
//    boardesk.style.width = width + "px;";
//    boardesk.style.height = width + "px;";
   // console.log("displayBoard " + size);
    //boardcard.style.width = width + "px;";
    //boardcard.style.height = width + "px;";
    boardiv.innerHTML = htmlout;
        
}