package tobin.equation.operators;

import tobin.equation.Node;

/**
 * Represents a natural log in an equation.
 * @author Tobin
 * @version 1
 */
public class Ln extends UnaryOperator implements Cloneable
{

    public Ln(Node operand)
    {
        super(operand);
    }

    public double getValue()
    {
        double val = getChild().getValue();
//        System.out.println("ln("+val+"): "+Math.log(val));
        return Math.log(val);
    }

    public Node clone()
    {
        return new Ln((getChild()).clone());
    }

    public String getFunction()
    {
        return "ln";
    }

    public Ln getInstance(Node operand)
    {
        return new Ln(operand);
    }
}