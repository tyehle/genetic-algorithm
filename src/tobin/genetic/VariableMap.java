package tobin.genetic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * The map that stores all of the information about the variables used in the
 * algorithm.  All of the information about the variables is stored in a hash
 * map of strings and doubles.  The strings are the variable names, and the
 * doubles are their current values.
 * @author Tobin
 * @version 1
 */
public class VariableMap
{
    private HashMap<String, Double> map;

    /**
     * Makes a new variable map with an initial capacity of 16, and the pi and
     * e associations.
     */
    public VariableMap()
    {
        map = new HashMap<String, Double>();

        map.put("pi", Math.PI);
        map.put("e", Math.E);

//	System.out.println(this);
    }
    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced, unless the key is pi or e.
     * @param k key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     */
    public void setVariable(String k, double value)
    {
        if(k.equalsIgnoreCase("pi") || k.equalsIgnoreCase("e"))
        {
            map.get(k.toLowerCase());
        }
        map.put(k.toLowerCase(), value);
//        System.out.println("key: "+k+", value: "+value+", old value: ");
    }
    /**
     * Returns a list of all of the variables stored in the map.
     * @return the list of variables as strings.
     */
    public String[] getAllVariables()
    {
        Set<String> set = map.keySet();
        return set.toArray(new String[0]);
    }
    /**
     * Gets all of the variables that should be modified during the algorithm
     * to find the solution.  This is the list of all of the variables in the
     * map except pi, e, and x.
     * @return An array of strings containing all of the keys to the variables
     * that should be modified.
     */
    public String[] getVariables()//assumes that the map does not change
    {
        Set<String> set = map.keySet();
	Iterator<String> iterator = set.iterator();

	String out[] = new String[set.size() - 3];
	String reject = "[Xx]|[Ee]|[Pp][Ii]";

	for (int i = 0; i < out.length; i++)
	{
	    String j = iterator.next();
	    if(!j.matches(reject))
	    {
		out[i] = j;
	    }
	    else
	    {
		i--;
	    }
	}

        return out;
    }
    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that {@code (key==null ? k==null :
     * key.equals(k))}, then this method returns {@code v}; otherwise
     * it returns {@code null}.  (There can be at most one such mapping.)
     *
     * <p>A return value of {@code null} does not <i>necessarily</i>
     * indicate that the map contains no mapping for the key; it's also
     * possible that the map explicitly maps the key to {@code null}.
     * The {@link #containsKey containsKey} operation may be used to
     * distinguish these two cases.
     *
     * @param k The key to return the value of.
     * @return The value at that location.
     */
    public double getVariable(String k)
    {
//	System.out.println(map);
        return map.get(k.toLowerCase());
    }
    /**
     * The string representation of the map.  This is the same as
     * map.toString();
     * @return
     */
    public String toString()
    {
        return map.toString();
    }
    /**
     * Adds the specified key to the map if it does not already exist on the
     * map.  The initial value for the new key is 0.0.
     * @param key The key to add to the map.
     */
    public void addVariable(String key)
    {
        if(!map.containsKey(key))
        {
            map.put(key.toLowerCase(), 0.0);
        }
//	System.out.println(this);
    }
}