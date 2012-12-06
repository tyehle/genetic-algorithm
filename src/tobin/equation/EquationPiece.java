package tobin.equation;

/**
 * This is a placeholder for a piece of a real equation.  Used in
 * PartialEquation so it can store the entire thing in a linked list.
 * @author Tobin
 * @version 1
 */
public class EquationPiece
{
    private String equation;
    private Node node;
    /**
     * Makes a new EquationPiece that contains a string.
     * @param equation The string that the piece contains.
     */
    public EquationPiece(String equation)
    {
        this.equation = equation;
    }
    /**
     * Makes a new EquationPiece that contains the given node.
     * @param n The node that the piece contains.
     */
    public EquationPiece(Node n)
    {
        node = n;
    }
    /**
     * Gets the string equation this is the placeholder for.  If this is a
     * complete node then this returns null.
     * @return The unparsed equation.
     */
    public String getEquation()
    {
        return equation;
    }
    /**
     * Gets the node if that is what this piece is storing.  If not then this
     * returns null
     * @return The node
     */
    public Node getNode()
    {
        return node;
    }
    /**
     * Returns if this represents a completed portion of a partial equation.
     * @return if this is a complete piece.
     */
    public boolean isComplete()
    {
        return equation == null;
    }
    /**
     * Gets a string representing this equation piece
     * @return the string.
     */
    public String toString()
    {
        if(equation != null)
        {
            return "\""+getEquation()+"\"";
        }
        else if(node != null)
        {
            return node.toString();
        }
        else
        {
            return "Null equation piece";
        }
    }
}