package tobin.equation.operators;

import tobin.equation.Node;

/**
 * The addition operator.  Performs the function a - b.
 * @author Tobin
 * @version 1
 */
public class Subtract extends BinaryOperator
{
    public Subtract(Node a, Node b)
    {
        super(a, b);
    }

    public String getFunction()
    {
        return "\\-";
    }

    public BinaryOperator getInstance(Node a, Node b)
    {
        return new Subtract(a, b);
    }

    public double getValue()
    {
        double val1 = getPrimaryOperand().getValue(), val2 = getSecondaryOperand().getValue();
//        System.out.println(val1+"-"+val2+": "+(val1 - val2));
        return val2 - val2;
    }

    public Node clone()
    {
        return new Subtract(getPrimaryOperand().clone(), getSecondaryOperand().clone());
    }

    public String toString()
    {
        validityCheck();
        return getPrimaryOperand()+"-"+getSecondaryOperand();
    }
}