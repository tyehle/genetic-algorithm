package tobin.equation.operators;

import tobin.equation.Node;

/**
 * Represent the log base 10 in an equation.
 * @author Tobin
 * @version 1
 */
public class Log extends UnaryOperator implements Cloneable
{

    public Log(Node operand)
    {
        super(operand);
    }

    public double getValue()
    {
        double val = getChild().getValue();
//        System.out.println("log("+val+"): "+Math.log10(val));
        return Math.log10(val);
    }

    public Node clone()
    {
        return new Log((getChild()).clone());
    }

    public String getFunction()
    {
        return "log";
    }

    public Log getInstance(Node operand)
    {
        return new Log(operand);
    }
}