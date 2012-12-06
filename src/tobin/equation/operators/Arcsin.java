package tobin.equation.operators;

import tobin.equation.Node;

/**
 * Represents an arc sin in an equation.  Like all other trig this is always in
 * radians, and is only defined for pi radians, not 2 pi.
 * @author Tobin
 * @version 1
 */
public class Arcsin extends UnaryOperator implements Cloneable
{

    public Arcsin(Node operand)
    {
        super(operand);
    }

    public double getValue()
    {
        double val = getChild().getValue();
//        System.out.println("arcsin("+val+"): "+Math.asin(val));
        return Math.asin(val);
    }

    public Node clone()
    {
        return new Arcsin((getChild()).clone());
    }

    public String getFunction()
    {
        return "arcsin";
    }

    public Arcsin getInstance(Node operand)
    {
        return new Arcsin(operand);
    }
}