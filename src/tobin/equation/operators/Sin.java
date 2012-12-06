package tobin.equation.operators;

import tobin.equation.Node;

/**
 * Represents a sin in an equation.  Like all other trig this is always in
 * radians.
 * @author Tobin
 * @version 1
 */
public class Sin extends UnaryOperator implements Cloneable
{

    public Sin(Node operand)
    {
        super(operand);
    }

    public double getValue()
    {
        double val = getChild().getValue();
//        System.out.println("sin("+val+"): "+Math.sin(val));
        return Math.sin(val);
    }

    public Node clone()
    {
        return new Sin((getChild()).clone());
    }

    public String getFunction()
    {
        return "sin";
    }

    public Sin getInstance(Node operand)
    {
        return new Sin(operand);
    }
}