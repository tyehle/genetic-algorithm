
package tobin.equation.operators;

import tobin.equation.Node;

/**
 * Represents an absolute value.  In an equation this is represented similarly
 * to the rest of the unary operators, where the function is expressed in three
 * characters with the operand in parentheses following them.
 * @author Tobin
 * @version 1
 */
public class AbsoluteValue extends UnaryOperator implements Cloneable
{
    public AbsoluteValue(Node operand)
    {
        super(operand);
    }

    public double getValue()
    {
        double val = getChild().getValue();
//        System.out.println("abs("+val+"): "+Math.abs(val));
        return Math.abs(val);
    }

    public Node clone()
    {
        return new AbsoluteValue((getChild()).clone());
    }

    public String getFunction()
    {
        return "abs";
    }

    public AbsoluteValue getInstance(Node operand)
    {
        return new AbsoluteValue(operand);
    }
}