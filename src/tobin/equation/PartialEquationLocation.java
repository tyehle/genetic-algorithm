package tobin.equation;

/**
 * This represents a location in a <tt>PartialEquation</tt>.  It has a node
 * number and a string location.  The node number notates the equation piece
 * in the partial equation, and the string location notates the location in the
 * string.  This class is just a convenient way of storing locations in a
 * <tt>PartialEquation</tt>.
 * @author Tobin
 * @version 1
 */
public class PartialEquationLocation
{
    private int nodeNum, stringLoc;
    /**
     * Makes a new <tt>PartialEquationLocation</tt> that notates the given node
     * number and string location.
     * @param nodeNum The node number
     * @param stringLoc The string location
     */
    public PartialEquationLocation(int nodeNum, int stringLoc)
    {
        this.nodeNum = nodeNum;
        this.stringLoc = stringLoc;
    }
    /**
     * Gets the node number that this location refers to.
     * @return The node number.
     */
    public int getNodeNum()
    {
        return nodeNum;
    }
    /**
     * Gets the string location that this location refers to.
     * @return The string location.
     */
    public int getStringLoc()
    {
        return stringLoc;
    }
    /**
     * Gets a string representation of this object, in this case the node and
     * string locations.
     * @return The string representation
     */
    public String toString()
    {
        return "Node: "+nodeNum+", Loc: "+stringLoc;
    }
    /**
     * Gets a hash code for this object.  I don't know if this is ever used, but
     * it was suggested that I put this here.  There are possibilities of hash
     * collisions.
     * @return The hash code.
     */
    public int hashCode()
    {
        int hash = 3;
        hash = 59 * hash + this.nodeNum;
        hash = 59 * hash + this.stringLoc;
        return hash;
    }
    /**
     * Returns whether the given object is equal to this one.  This is true if
     * the node number and the string location are the same.
     * @param o The object to test
     * @return If it is equal to this
     */
    public boolean equals(Object o)
    {
        return o != null && o.getClass() == getClass() &&
                ((PartialEquationLocation)o).nodeNum == nodeNum &&
                ((PartialEquationLocation)o).stringLoc == stringLoc;
    }
}
