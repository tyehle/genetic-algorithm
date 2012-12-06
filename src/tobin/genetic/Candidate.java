package tobin.genetic;

import java.util.Arrays;
import tobin.equation.Node;

/**
 * A member of the population.
 * @author Tobin
 * @version 1
 */
public class Candidate extends AbstractCandidate
{
    private KeyValue genome[];
    private double fitness;
    private final Data data;
    private final Node equation;
    private final VariableMap map;
    /**
     * Makes a new candidate, and calculates its fitness.
     * @param genome The set of values for the variables for this candidate
     * @param data The data used to evaluate this candidate
     * @param equation The equation to fit to the data
     * @param map the variable map where the values of the variables are
     * stored so they can be accessed by the equation
     */
    public Candidate(KeyValue genome[], Data data, Node equation, VariableMap map)
    {
        this.genome = genome;
        this.data = data;
        this.equation = equation;
        this.map = map;
        fitness = calculateFitness();
//        if(fitness > 0)
//        {
//            System.out.println(this);
//        }
    }
    /**
     * Calculates the fitness of this solution.  The fitness should not need
     * to ever change, so it is stored when the solution is created.  The
     * fitness is the total deviation from each data point of the function at
     * that point.  Since every solution uses the same set of data there is no
     * need to use a normalized scale like R^2, because no solution is ever
     * compared to anything other than another solution.
     * @return The fitness of the solution
     */
    private double calculateFitness()//r = 1- err/tot
    {
        for (int i = 0; i < genome.length; i++)
        {
            map.setVariable(genome[i].getKey(), genome[i].getValue());
//            System.out.println("Variable map "+genome[i].getKey()+" set to: "+genome[i].getValue());
        }

        double error = 0;
        for (int i = 0; i < data.size(); i++)
        {
            map.setVariable("x", data.getPoint(i)[0]);
            double difference = data.getPoint(i)[1] - equation.getValue();
//            System.out.println("point "+Arrays.toString(data.getPoint(i))+" - f(x) ("+getFriendlyString()+"): "+difference);
            error = error + (difference * difference);
//            System.out.println("error: "+error);
        }

//        System.out.println((1 - (error / total)));

        return error;
    }
    /**
     * Gets the fitness that was calculated and stored when this candidate was
     * initialized.
     * @return The fitness
     */
    public double getFitness()
    {
        return fitness;
    }
    /**
     * Gets the string representation of the genome of this candidate.  This
     * method uses the Arrays.toString method to represent the genome.
     * @return the string representation.
     */
    public String getGenomeString()
    {
        return Arrays.toString(genome);
    }
    /**
     * Gets the genome of this candidate, which is a list of keys, and their
     * corresponding values which differentiate this candidate from the others
     * in the population.
     * @return The list of keys and values
     */
    public KeyValue[] getGenome()
    {
        return genome;
    }
    /**
     * Recalculates the fitness of this candidate, in case there has been a
     * change or an error, and the fitness becomes wrong in some way.
     */
    public void recalculateFitness()
    {
        fitness = calculateFitness();
    }
    /**
     * Gets a string which represents the equation with the variables filled
     * in in their numerical form instead of their variable form. This makes
     * it easier for the user can see what the equation is, and to copy and
     * paste the equation out of this progress frame.
     * @return 
     */
    public String getFriendlyString()
    {
        String out = equation.toString();

        for (int i = 0; i < genome.length; i++)
        {
            out = out.replaceAll("\\"+genome[i].getKey(), genome[i].getValue()+"");
        }
        
        return out;
    }

    @Override
    public String toString()
    {
        return getFitness()+/*"\t"+Arrays.toString(genome)+*/", "+getFriendlyString();
    }
}