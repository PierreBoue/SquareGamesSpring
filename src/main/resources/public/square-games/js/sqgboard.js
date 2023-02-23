// JavaScript Document

function displayBoard( size )
{
    var htmlout = " <table class=\"table table-sm table-bordered border-3 border-dark m-auto p-0 mt-5 boardtable\" style=\"width: 81px; height: 81px;\">\n";
    for ( let y = 0; y < size; y++)
    {
       htmlout += "     <tr class='boardcell'>\n";
        for ( let x =0; x < size; x++)
        {
            htmlout += "     <td align='center' width='24' height='24' id='bc" + x + "-" + y + "' onclick='selectCell( this );'>\n";
            htmlout += "      &nbsp;\n";
            htmlout += "     </td>\n";
        }
       htmlout += "     </tr>\n";
    }
    htmlout += "   </table>\n";
    const boardesk =  document.getElementById("boarddesk");
    //const  boardcard =  document.getElementById("boardcard");
    const boardiv =document.getElementById("boarddiv");
    let width = 42 * ( size + 2 );
    boardiv.style.width = width + "px;";
    boardiv.style.height = width + "px;";
    width += 40;
    boardesk.style.width = width + "px;";
    boardesk.style.height = width + "px;";
    console.log("displayBoard " + size);
    //boardcard.style.width = width + "px;";
    //boardcard.style.height = width + "px;";
    //boardiv.innerHTML = htmlout;
        
}