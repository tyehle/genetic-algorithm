package tobin.equation.operators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tobin.equation.EquationPiece;
import tobin.equation.IllegalChildCountException;
import tobin.equation.Node;
import tobin.equation.PartialEquation;
import tobin.equation.PartialEquationLocation;
import tobin.genetic.VariableMap;

/**
 * This is the class that all unary operators extend, and defines the functions
 * which all unary operators must have.  This class also contains the general
 * parse method to parse all unary operators out of a partial equation.<br>
 * A few things to note about unary operators:<br>
 * 1. log is log base 10.<br>
 * 2. Will not ever do !, because it only deals with integers.<br>
 * 3. All operations are done on doubles.<br>
 * 4. All trig is in radians.<br>
 *<br>
 * Subclasses:
 * sin
 * cos
 * tan
 * arcsin
 * arccos
 * arctan
 * ln
 * log
 * abs
 * @author Tobin
 * @version 1
 */
public abstract class UnaryOperator extends Node implements Cloneable
{
    /**
     * This is called by all of the subclasses to create a new instance of
     * themselves.
     * @param child The node to perform the unary operation on.
     */
    public UnaryOperator(Node child)
    {
        super(child);
    }
    /**
     * Gets the child of this node.  Unary operators will only have one child
     * ever, so this method returns the only child of this node.
     * @return The child node.
     */
    public Node getChild()
    {
        validityCheck();
        return getChildren()[0];
    }
    /**
     * Parses a partial equation for all unary operators.  When this method is
     * complete all unary operators will have been parsed out of the partial
     * equation that was passed, including any instances of the negation
     * operator which does not use the general parsing method.
     * @param in The equation to parse for unary operators.
     * @param map The variable map to add any new variables to.
     * @return The same partial equation with all instances of any unary
     * operator removed from the string portion, and added to the node portion.
     */
    public static PartialEquation parse(PartialEquation in, VariableMap map)
    {
        in = Negative.parseNegatives(in, map);

        in = UnaryOperator.parse(in, new AbsoluteValue(null), map);
        in = UnaryOperator.parse(in, new Arcsin(null), map);
//        System.out.println(in);
        in = UnaryOperator.parse(in, new Arccos(null), map);
//        System.out.println(in);
        in = UnaryOperator.parse(in, new Arctan(null), map);
//        System.out.println(in);
        in = UnaryOperator.parse(in, new Sin(null), map);
//        System.out.println(in);
        in = UnaryOperator.parse(in, new Cos(null), map);
//        System.out.println(in);
        in = UnaryOperator.parse(in, new Tan(null), map);
//        System.out.println(in);
        in = UnaryOperator.parse(in, new Log(null), map);
//        System.out.println(in);
        in = UnaryOperator.parse(in, new Ln(null), map);
//        System.out.println(in);

        return in;
    }
    /**
     * Extracts the given function from the given string.  There will be no
     * other occurrences of that function in the partial equation returned.
     * The instance of the function is not changed during the method.  The
     * string representation of the function, and a new instance of itself are
     * extracted from it during the method.
     * @param <T> The type of function removed from the string.
     * @param in The partial equation to parse
     * @param t An instance of the type of function to extract.  This instance
     * is not changed during the method.
     * @param map The VariableMap any variables will be added to
     * @return A partial equation with all instances of the given function
     * removed
     * @throws IllegalArgumentException if there is not an ( after any
     * occurrence of the function string.
     */
    public static <T extends UnaryOperator> PartialEquation parse(PartialEquation in, T t, VariableMap map)
    {
        String function = t.getFunction();
        Pattern p = Pattern.compile(function, Pattern.CASE_INSENSITIVE);
        Matcher m;

        while(true)
        {
            m = p.matcher(in.getEquation());

            if(m.find())//assumes that all parens have been parsed
            {
//                System.out.println("Function found: ("+m.start()+", "+m.end()+")");
                PartialEquationLocation startLocation = in.getLocation(m.start());
//                System.out.println("Location: "+startLocation);
                Node n = t.getInstance(in.remove(startLocation.getNodeNum() + 1).getNode());//remove the operand from the equation
//                System.out.println("Operand removed: "+in);
                EquationPiece operand = new EquationPiece(n);
                in.insert(m.start(), m.end() - 1, operand);//remove the function from the equation
            }
            else
            {
                return in;
            }
        }
    }

    public void validityCheck()
    {
        if(getChildren().length != 1)
        {
            throw new IllegalChildCountException("Illegal child count.  Expected: 1, Found: "+getChildren().length);
        }
    }

    public String toString()
    {
        return getFunction()+"("+getChild()+")";
    }
    /**
     * Gets the string that defines the function when the user writes it out.
     * All of the unary operators have functions in the form xxxx().  For
     * example an absolute value would return abs for the function, because the
     * user would notate it as abs(x).
     * @return The string to search for in the partial equation
     */
    public abstract String getFunction();
    /**
     * Gets an instance of this node, so new instances can be declared using
     * generics in the parse method.
     * @param operand The operand for the new node.
     * @return The new node.
     */
    public abstract UnaryOperator getInstance(Node operand);
}