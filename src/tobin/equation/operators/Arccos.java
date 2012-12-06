package tobin.equation.operators;

import tobin.equation.Node;

/**
 * Represents an arc cosine in an equation.  Like all other trig this is always
 * in radians, and is only defined for pi radians, not 2 pi.
 * @author Tobin
 * @version 1
 */
public class Arccos extends UnaryOperator implements Cloneable
{

    public Arccos(Node operand)
    {
        super(operand);
    }

    public double getValue()
    {
        double val = getChild().getValue();
//        System.out.println("arccos("+val+"): "+Math.acos(val));
        return Math.acos(val);
    }

    public Node clone()
    {
        return new Arccos((getChild()).clone());
    }

    public String getFunction()
    {
        return "arccos";
    }

    public Arccos getInstance(Node operand)
    {
        return new Arccos(operand);
    }
}