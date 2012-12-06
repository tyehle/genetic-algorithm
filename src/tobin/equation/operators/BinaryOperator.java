package tobin.equation.operators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tobin.equation.EquationPiece;
import tobin.equation.IllegalChildCountException;
import tobin.equation.Node;
import tobin.equation.PartialEquation;
import tobin.equation.PartialEquationLocation;
import tobin.equation.SyntaxException;
import tobin.genetic.VariableMap;

/**
 * The general class that all binary operators inherit from, and defines the
 * functions which all unary operators must have.  This class also contains the
 * general parse method to parse all binary operators out of a partial
 * equation.<br>
 * A list of the functions and what they do:<br>
 * add = a + b<br>
 * subtract = a - b<br>
 * multiply = a * b<br>
 * divide = a / b<br>
 * mod = a % b<br>
 * power = a^b<br>
 * @author Tobin
 * @version 1
 */
public abstract class BinaryOperator extends Node
{
    public static final String NEGATE_REGEX = "\\-";
    /**
     * Makes a new binary operator with the two given nodes to perform the
     * operation on.
     * @param a The first node to operate on.
     * @param b The second node to operate on.
     */
    public BinaryOperator(Node a, Node b)
    {
        super(a, b);
    }
    /**
     * Gets the child at the specified index.  The index can only be 0 or 1,
     * because there are only ever two children in a binary operator.
     * @param index The index of the child to get.
     * @return The node at that index.
     */
    public Node getChild(int index)
    {
        validityCheck();
        if(index > 1)
        {
            throw new IndexOutOfBoundsException("index: "+index+", size: 2");
        }
        return getChildren()[index];
    }
    /**
     * Gets the primary operand from this binary operator.  This is equivalent
     * to calling getChild(0).
     * @return The first child.
     */
    public Node getPrimaryOperand()
    {
        validityCheck();
        return getChildren()[0];
    }
    /**
     * Gets the secondary operand from this binary operator.  This is equivalent
     * to calling getChild(1).
     * @return The second child.
     */
    public Node getSecondaryOperand()
    {
        validityCheck();
        return getChildren()[1];
    }
    /**
     * Parses the given partial equation for all of the binary operators it may
     * contain.  After this method is complete there will no longer be any
     * instances of binary operators in the string portion of the partial
     * equation.
     * @param in The partial equation to parse for binary operators.
     * @param map The variable map to add any new variables to.
     * @return The same partial equation that was passed without any string
     * references to binary operators.
     */
    public static PartialEquation parse(PartialEquation in, VariableMap map)
    {
        in = BinaryOperator.parse(in, new Power(null, null), map);
//        System.out.println("\n\n"+in+"\n\n");

        in = BinaryOperator.parse(in, new Multiply(null, null), map);
//        System.out.println("\n\n"+in+"\n\n");
        in = BinaryOperator.parse(in, new Divide(null, null), map);
//        System.out.println("\n\n"+in+"\n\n");
        in = BinaryOperator.parse(in, new Mod(null, null), map);
//        System.out.println("\n\n"+in+"\n\n");

        in = BinaryOperator.parse(in, new Add(null, null), map);
//        System.out.println("\n\n"+in+"\n\n");
        in = BinaryOperator.parse(in, new Subtract(null, null), map);
//        System.out.println("\n\n"+in+"\n\n");

        return in;
    }
    /**
     * Assumes all binary operators are one character long, and parses a partial
     * equation for one in particular.  The method finds the location of the
     * unary operator T and replaces the string with a node representation.
     * This method will call itself until no more instances of the operator T
     * are found in the partial equation.
     * @param <T> The binary operator to parse out of the partial equation.
     * @param in The partial equation to parse for the operator T.
     * @param t An instance of t, so nonstatic methods can be called.
     * @param map The variable map to add any new variables to.
     * @return The same partial equation, without any string references to the
     * binary operator T.
     */
    public static <T extends BinaryOperator> PartialEquation parse(PartialEquation in, T t, VariableMap map)
    {
        String function = t.getFunction();
        Pattern p = Pattern.compile(function, Pattern.CASE_INSENSITIVE);

        while(true)
        {
            Matcher m = p.matcher(in.getEquation());

            if(m.find())
            {
                PartialEquationLocation start = in.getLocation(m.start());

                Node b = in.get(start.getNodeNum() + 1).getNode();
                int end = m.end() - 1;//the ending location of the operator (different if there is negation)
                Node a = in.get(start.getNodeNum() - 1).getNode();

                if(a == null || b == null)
                {
                    throw new SyntaxException("Null children when creating binary operator: ("+a+", "+b+")");
                }

                Node n = t.getInstance(a, b);

                in.insert(m.start(), end, new EquationPiece(n));

                in.remove(start.getNodeNum() + 1);
                in.remove(start.getNodeNum() - 1);
            }
            else
            {
                return in;
            }
        }
    }

    public void validityCheck()
    {
        if(getChildren().length != 2)
        {
            throw new IllegalChildCountException("Illegal child count.  Expected: 2, Found: "+getChildren().length);
        }
    }

    public abstract String toString();
    /**
     * Gets the string that defines the function when the user writes it out.
     * All of the binary operators have one character long functions such as +.
     * @return The string to search for in the partial equation
     */
    public abstract String getFunction();
    /**
     * Gets an instance of this node, so new instances can be declared using
     * generics in the parse method.
     * @param a The first operand for the new node.
     * @param b The second operand for the new node.
     * @return The new node.
     */
    public abstract BinaryOperator getInstance(Node a, Node b);
}