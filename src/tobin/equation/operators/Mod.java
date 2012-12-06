package tobin.equation.operators;

import tobin.equation.Node;

/**
 * The addition operator.  Performs the function a % b.
 * @author Tobin
 * @version 1
 */
public class Mod extends BinaryOperator
{
    public Mod(Node a, Node b)
    {
        super(a, b);
    }

    public String getFunction()
    {
        return "%";
    }

    public BinaryOperator getInstance(Node a, Node b)
    {
        return new Mod(a, b);
    }

    public double getValue()
    {
        double val1 = getPrimaryOperand().getValue(), val2 = getSecondaryOperand().getValue();
//        System.out.println(val1+"%"+val2+": "+(val1 % val2));
        return val1 % val2;
    }

    public Node clone()
    {
        return new Mod(getPrimaryOperand().clone(), getSecondaryOperand().clone());
    }

    public String toString()
    {
        validityCheck();
        return getPrimaryOperand()+"%"+getSecondaryOperand();
    }
}