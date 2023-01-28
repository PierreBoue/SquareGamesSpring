// JavaScript Document

function displayBoard( size )
{
    var htmlout = " <table border='2' align='center' cellpadding='5' class='table border border-2'>\n";
    for ( let y = 0; y < size; y++)
    {
       htmlout += "     <tr>\n";
        for ( let x =0; x < size; x++)
        {
            htmlout += "     <td align='center' width='20' height='20' id='bc" + x + "-" + y + "' onclick='selectCell( this );'>\n";
            htmlout += "      &nbsp;\n";
            htmlout += "     </td>\n";
        }
       htmlout += "     </tr>\n";
    }
    htmlout += "   </table>\n";
    document.getElementById("boarddiv").innerHTML = htmlout;
        
}