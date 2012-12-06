package tobin.equation.ends;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tobin.equation.EquationPiece;
import tobin.equation.IllegalChildCountException;
import tobin.equation.Node;
import tobin.equation.PartialEquation;
import tobin.genetic.VariableMap;

/**
 * It does not have a fixed value in the equation.  It will return the current
 * value when asked to evaluate itself, however that value can change.  The
 * value it returns is stored in the variable map, which this has a reference
 * to.  This element of the equation tree never has any children.  The key to
 * the hash map is the same as the variable name.
 * @author Tobin
 * @version 1
 */
public class Variable extends Node implements Cloneable
{
    private String key;
    private VariableMap map;
    /**
     * Creates a new variable with the given initial value.
     * @param key The key that this variable maps to in the hash table in
     * @param map The equation this variable is a part of.
     */
    public Variable(String key, VariableMap map)
    {
        super();
        this.key = key;
        this.map = map;
    }
    /**
     * Returns the current value of the variable.
     * @return The current value of the variable.
     */
    public double getValue()
    {
        return map.getVariable(key);
    }

    public String toString()
    {
        return key;
    }
    /**
     * Returns a clone of the variable object.  The key and map are not
     * cloned.
     * @return The copy of this variable.
     */
    public Node clone()
    {
        return new Variable(key, map);
    }
    /**
     * Parses the passed partial equation for any variables it may contain.
     * New variables are added to the variable map.
     * @param in The partial equation to parse
     * @param map The variable map to add any new variables to.
     * @return The passed partial equation, with all of the variables
     * converted to nodes.
     */
    public static PartialEquation parse(PartialEquation in, VariableMap map)
    {
        Pattern p = Pattern.compile("(\\$[a-z]+|x|pi|e)", Pattern.CASE_INSENSITIVE);
        Matcher m;

        while(true)
        {
            m = p.matcher(in.getEquation());
            if(m.find())
            {
                String key = in.getString(m.start(), m.end());
//                System.out.println(key);
                in.insert(m.start(), m.end() - 1, new EquationPiece(new Variable(key, map)));
                map.addVariable(key);

                return Variable.parse(in, map);
            }
            else
            {
                return in;
            }
        }
    }
    
    public void validityCheck()
    {
        if(getChildren().length != 0)
        {
            throw new IllegalChildCountException("Variable with children: "+this+", "+Arrays.toString(getChildren()));
        }
    }
}