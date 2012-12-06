package tobin.equation.operators;

import tobin.equation.Node;

/**
 * Represents an arc tangent in an equation.  Like all other trig this is always
 * in radians, and is only defined for pi radians, not 2 pi.
 * @author Tobin
 * @version 1
 */
public class Arctan extends UnaryOperator implements Cloneable
{

    public Arctan(Node operand)
    {
        super(operand);
    }

    public double getValue()
    {
        double val = getChild().getValue();
//        System.out.println("arctan("+val+"): "+Math.atan(val));
        return Math.atan(val);
    }

    public Node clone()
    {
        return new Arctan((getChild()).clone());
    }

    public String getFunction()
    {
        return "arctan";
    }

    public Arctan getInstance(Node operand)
    {
        return new Arctan(operand);
    }
}