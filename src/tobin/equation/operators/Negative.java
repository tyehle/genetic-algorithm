package tobin.equation.operators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tobin.equation.EquationPiece;
import tobin.equation.IllegalChildCountException;
import tobin.equation.Node;
import tobin.equation.PartialEquation;
import tobin.equation.PartialEquationLocation;
import tobin.genetic.VariableMap;

/**
 * The negation operator.  This unary operator is slightly different from the
 * rest of the unary operators, because it is not in the form xxxx().  This
 * means that the parsing function used on the rest of the unary operators
 * cannot be used here.  This class defines its own parsing method, called
 * parseNegatives, which is used to parse the negation operators out of a
 * partial equation.
 * @author Tobin
 * @version 1
 */
public class Negative extends UnaryOperator implements Cloneable
{
    public static String getString()
    {
        return "\\-";
    }

    public String getFunction()
    {
        return "-";
    }

    public Negative(Node operand)
    {
        super(operand);
    }

    public double getValue()
    {
        double val = getChild().getValue();
//        System.out.println("-"+val+": "+(-val));
        return -val;
    }

    public Node clone()
    {
        return new Negative(getChildren()[0].clone());
    }
    
    public Node getChild()
    {
        validityCheck();
        return getChildren()[0];
    }

    @Override
    public String toString()
    {
        return "-"+getChild();
    }

    public void validityCheck()
    {
        if(getChildren().length != 1)
        {
            throw new IllegalChildCountException("Illegal child count.  Expected: 1, Found: "+getChildren().length);
        }
    }

    @Override
    public UnaryOperator getInstance(Node operand)
    {
        return new Negative(operand);
    }
    /**
     * Takes all of the instances of negation out of the partial equation
     * passed.  The instances of subtraction are left in.  This should be
     * called before any other operation parse methods are called, because of
     * issues with order of operations.
     * @param in The partial equation to parse
     * @param map The variable map to put any new variables into
     * @return The same partial equation, but missing any negation operators.
     */
    public static PartialEquation parseNegatives(PartialEquation in, VariableMap map)
    {
        String function = getString();
        Pattern p = Pattern.compile(function, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(in.getEquation());

        while(true)
        {
            if(m.find())
            {
//                System.out.println("Function found: ("+m.start()+", "+m.end()+")");
                PartialEquationLocation startLocation = in.getLocation(m.start());
//                System.out.println("Location: "+startLocation);

                if(startLocation.getStringLoc() == 0 && startLocation.getNodeNum() != 0)//then this is a subtract
                {
//                    System.out.println("continuing");
                    continue;
                }
                Node n = new Negative(in.remove(startLocation.getNodeNum() + 1).getNode());//remove the operand from the equation
//                System.out.println("Operand removed: "+in);
                EquationPiece operand = new EquationPiece(n);
                in.insert(m.start(), m.end() - 1, operand);//remove the function from the equation
//                System.out.println("Done parsing negative: "+in);
                m = p.matcher(in.getEquation());
            }
            else
            {
                return in;
            }
        }
    }
}