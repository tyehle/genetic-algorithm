package tobin.equation.operators;

import tobin.equation.Node;

/**
 * Represents a tangent in an equation.  Like all other trig this is always in
 * radians.
 * @author Tobin
 * @version 1
 */
public class Tan extends UnaryOperator
{
    /**
     * Makes a new tangent object with the given operand.
     * @param operand The node to perform the operation on.
     */
    public Tan(Node operand)
    {
        super(operand);
    }

    public double getValue()
    {
        double val = getChild().getValue();
//        System.out.println("tan("+val+"): "+Math.tan(val));
        return Math.tan(val);
    }

    public Node clone()
    {
        return new Tan(getChild().clone());
    }

    public String getFunction()
    {
        return "tan";
    }

    public Tan getInstance(Node operand)
    {
        return new Tan(operand);
    }
}
