package tobin.equation.ends;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tobin.equation.EquationPiece;
import tobin.equation.IllegalChildCountException;
import tobin.equation.Node;
import tobin.equation.PartialEquation;

/**
 * This is used to store a constant value in an equation tree.  This node does
 * not have any children.  The evaluate method will always return the value
 * given to the object when it was initialized.
 * @author Tobin
 * @version 1
 */
public class Value extends Node
{
    private double value;

    /**
     * Makes a new node with the given value stored in it, and no children.
     * @param value The value of this node of the tree.
     */
    public Value(double value)
    {
        super();
        this.value = value;
    }
    /**
     * Gets the value stored in this node.
     * @return The value
     */
    public double getValue()
    {
        return value;
    }

    public String toString()
    {
        return value+"";
    }
    /**
     * Gets copy of this value object.  The copy has no relation to the original
     * object, so any changes to the copy are not made to the original.
     * @return The copy of this Value
     */
    public Node clone()
    {
        return new Value(value);
    }

    public void validityCheck()
    {
        if(getChildren().length != 0)
        {
            throw new IllegalChildCountException("Value with children: "+this+", "+Arrays.toString(getChildren()));
        }
    }
    /**
     * Parses the given partial equation for values.  Any numerical values found
     * in the equation are converted to nodes, and removed from the string part
     * of the partial equation.  This method is called recursively until there
     * are no more numerical values in the equation.
     * @param in The partial equation to parse.
     * @return The same partial equation, but without string instances of
     * numerical values.
     */
    public static PartialEquation parse(PartialEquation in)
    {
        Pattern p = Pattern.compile("[\\d.]+", Pattern.CASE_INSENSITIVE);
        Matcher m;

        while(true)
        {
            m = p.matcher(in.getEquation());
            if(m.find())
            {
                double val = Double.parseDouble(in.getString(m.start(), m.end()));
                EquationPiece piece = new EquationPiece(new Value(val));
                in.insert(m.start(), m.end() - 1, piece);
                return Value.parse(in);
            }
            else
            {
                return in;
            }
        }
    }
}
