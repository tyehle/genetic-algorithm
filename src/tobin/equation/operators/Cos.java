package tobin.equation.operators;

import tobin.equation.Node;

/**
 * Represents a cos in an equation.  Like all other trig this is always in
 * radians.
 * @author Tobin
 * @version 1
 */
public class Cos extends UnaryOperator
{

    public Cos(Node operand)
    {
        super(operand);
    }

    public double getValue()
    {
        double val = getChild().getValue();
//        System.out.println("cos("+val+"): "+Math.cos(val));
        return Math.cos(val);
    }

    public Node clone()
    {
        return new Cos(getChild().clone());
    }

    public String getFunction()
    {
        return "cos";
    }

    public Cos getInstance(Node operand)
    {
        return new Cos(operand);
    }
}
