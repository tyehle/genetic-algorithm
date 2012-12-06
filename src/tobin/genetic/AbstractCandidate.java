package tobin.genetic;

/**
 * compareTo interface assumes that the worst fitness is 0.
 * @author Tobin
 */
public abstract class AbstractCandidate implements Comparable<AbstractCandidate>
{
    public abstract double getFitness();
    public abstract String getGenomeString();
    public abstract KeyValue[] getGenome();
    public abstract void recalculateFitness();
    /**
     * Compares this candidate to the one passed to it.  It returns 1, 0, or
     * -1 depending on if the given candidate has a higher fitness.  This is
     * used in the sorting algorithm.
     * @param o The candidate to compare this one to
     * @return
     */
    public int compareTo(AbstractCandidate o)
    {
        return o.getFitness() > getFitness() ? -1 : (o.getFitness() == getFitness() ? 0 : 1);
    }
}