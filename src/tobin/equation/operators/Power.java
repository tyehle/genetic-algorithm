package tobin.equation.operators;

import tobin.equation.Node;

/**
 * The addition operator.  Performs the function a ^ b.
 * @author Tobin
 * @version 1
 */
public class Power extends BinaryOperator
{
    public Power(Node a, Node b)
    {
        super(a, b);
    }

    public String getFunction()
    {
        return "\\^";
    }

    public BinaryOperator getInstance(Node a, Node b)
    {
        return new Power(a, b);
    }
    /**
     * a^b
     * @return a^b
     */
    public double getValue()
    {
        double val1 = getPrimaryOperand().getValue(), val2 = getSecondaryOperand().getValue();
//        System.out.println(val1+"^"+val2+": "+Math.pow(val1, val2));
        return Math.pow(val1, val2);
    }

    public Node clone()
    {
        return new Power(getPrimaryOperand().clone(), getSecondaryOperand().clone());
    }

    public String toString()
    {
        validityCheck();
        return getPrimaryOperand()+"^"+getSecondaryOperand();
    }
}