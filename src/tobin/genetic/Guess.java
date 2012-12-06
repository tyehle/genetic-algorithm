package tobin.genetic;

/**
 * Stores all of the information about the guess that the user entered in the
 * guess frame.  This information includes a list of keys, values, and
 * deviations for each value.
 * @author Tobin
 * @version 1
 */
public class Guess
{
    private double deviations[];
    private KeyValue genome[];
    /**
     * Makes a new guess with the given set of keys, values, and deviations.
     * @param genome The list of keys and values
     * @param deviations The list of deviations
     */
    public Guess(KeyValue genome[], double deviations[])
    {
        this.genome = genome;
        this.deviations = deviations;
    }
    /**
     * Gets the list of deviations.
     * @return The list of deviations
     */
    public double[] getDeviations()
    {
        return deviations;
    }
    /**
     * Gets the list of keys and values in this guess.
     * @return The list of keys and values
     */
    public KeyValue[] getGenome()
    {
        return genome;
    }
}