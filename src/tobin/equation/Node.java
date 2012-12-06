package tobin.equation;

import tobin.equation.ends.Value;
import tobin.equation.ends.Variable;
import tobin.equation.operators.BinaryOperator;
import tobin.equation.operators.UnaryOperator;
import tobin.genetic.VariableMap;

/**
 ******Data Structures******
 * Defines all of the general requirements for a node in the equation tree.
 * Note that Nodes only have references to their children, not their parents.
 * @author Tobin
 * @version 1
 */
public abstract class Node implements Cloneable
{
    private Node children[];
    /**
     * Makes a new node with the given children.
     * @param children the children that the node has
     */
    public Node(Node ... children)
    {
//        System.out.println("Making a new node: "+Arrays.toString(children));
        this.children = new Node[children.length];
        System.arraycopy(children, 0, this.children, 0, children.length);
    }
    /**
     * Gets a list of the children of this node.
     * @return
     */
    public Node[] getChildren()
    {
        return children;
    }
    /**
     * Parses a partial equation.  Returns a node representing the given
     * partial equation.  This method is the basis for the equation parser.
     * If there is some sort of syntax error then this method will throw
     * an exception.
     * @param in The partial equation to parse
     * @param map The map to put any new variables into
     * @return The node representing the partial equation
     */
    public static Node parseEquation(PartialEquation in, VariableMap map)
    {
        System.out.println("Input: "+in);
        in = Variable.parse(in, map);
        System.out.println("Parsed variables: "+in);
        in = Value.parse(in);
        System.out.println("Parsed values: "+in);
        in.parseAllParentheses(map);
        System.out.println("Parsed parentheses: "+in);
        in = UnaryOperator.parse(in, map);
        System.out.println("Parsed unary operators: "+in);
        in = BinaryOperator.parse(in, map);
        System.out.println("Parsed binary operators: "+in);
        in.clearEmpties();
        System.out.println("Done: "+in);

        if(in.size() > 1 || !in.get(0).isComplete())//check to make sure everything has been parsed
        {
            System.out.println(in);
            throw new SyntaxException();
        }
        else
        {
            return in.get(0).getNode();
        }
    }
    /**
     * Gets the numerical value of this node.  This will call getValue() of
     * all of the children of this node, and then perform the operation that
     * defines the node.
     * @return The value of this node.
     */
    public abstract double getValue();
    /**
     * Gets a copy of this node.
     * @return The copy.
     */
    @Override
    public abstract Node clone();
    /**
     * Used to check the validity of the node, by testing the number of
     * children it has associated with it.  This should be called before any
     * method call which relies on the number of children in the node being
     * the expected number for that node type.  If the correct number of
     * children is not found an IllegalChildCountException should be thrown.
     */
    public abstract void validityCheck();
    /**
     * Gets a string representation of this object.  This is used to get the
     * friendly equation for any node.  The to string method for any node will
     * return a string representing the equation as if that node were the top
     * of the equation.
     * @return The string representation of this node
     */
    @Override
    public abstract String toString();
}