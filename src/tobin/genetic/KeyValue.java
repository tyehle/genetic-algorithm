package tobin.genetic;

/**
 * A pair of a key and a value.  This used because it is often more convenient
 * to make an array of these than a parallel array.
 * @author Tobin
 * @version 1
 */
public class KeyValue implements Cloneable
{
    private String key;
    private double value;
    /**
     * Makes a new instance of a pair of a key and a value.
     * @param key The key in the pair
     * @param value The value in the pair
     */
    public KeyValue(String key, double value)
    {
        this.key = key;
        this.value = value;
    }
    /**
     * Gets the value.
     * @return The value
     */
    public double getValue()
    {
        return value;
    }
    /**
     * Gets the key.
     * @return The key
     */
    public String getKey()
    {
        return key;
    }
    /**
     * Makes a new copy of this pair.  Any changes to the copy do not affect the
     * original.
     * @return The copy
     */
    public KeyValue clone()
    {
        return new KeyValue(key, value);
    }

    public String toString()
    {
        return key+": "+value;
    }
}