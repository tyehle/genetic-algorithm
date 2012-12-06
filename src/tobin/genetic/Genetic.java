package tobin.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import tobin.equation.Node;
import tobin.gui.ProgressFrame;

/**
 * This class contains all of the data needed to run the genetic algorithm,
 * and, when run, executes the algorithm to try and match the variables in the
 * given equation to the data.  During each iteration of the algorithm new
 * members of the population are added, and then the population is mutated.
 * The population is sorted, and the least fit members of the population are
 * deleted so the size of the population comes back down to the maximum
 * population size.  A new loop begins, and the most fit members of the
 * population tend to be chosen to reproduce to make the new members of the
 * population.  There is some randomness in the selection of parents to
 * maintain diversity in the population.
 * @author Tobin
 * @version 1
 */
public class Genetic implements Runnable
{
    private static final long serialVersionUID = 1L;
    public static final int POPULATION_SIZE = 1000;
    public static final int ITERATIONS_TO_EXIT = 30;
    public static final double SELECTIVITY = .05,/* VARIABILITY = .001, */MUTATION_RATE = 1;

    public double variability = 1, lastBest;
    public int exitCounter = 0;
    
    private ArrayList<Candidate> population = new ArrayList<Candidate>(POPULATION_SIZE * 3 / 2);
    private Data data;
    private Node equation;
    private VariableMap variableMap;
    private ProgressFrame progress;
    /**
     * Makes a new Genetic object, with all data and population initialized.
     * For the algorithm to start the thread needs to be stared.
     * @param d The data to match the equation to
     * @param equation The equation to match to the data
     * @param v The variable map containing the variables in the equation to
     * modulate to try to make the equation fit the data
     * @param guess The guess provided by the user to generate the initial
     * population from
     * @param title The title of the progress frame.
     * @param text The initial text of the progress frame.
     */
    public Genetic(Data d, Node equation, VariableMap v, Guess guess, String title, String text)
    {
        data = d;
        this.equation = equation;
        variableMap = v;

        progress = new ProgressFrame(title, text);
        progress.setVisible(true);

        String[] vars = v.getVariables();
        if(vars.length == 0)
        {
            System.out.println("No variables");
            System.out.println(new Candidate(new KeyValue[0], d, equation, v));
            System.exit(0);
        }

        createPopulation(guess, d, equation, v);
    }
    /**
     * The loop that executes the algorithm.  Loops the cycle of generating
     * new members of the population, mutating and sorting them until there
     * is no change in the fitness of the best member for ITERATIONS_TO_EXIT
     * iterations, and then exits.  During each iteration, if there was no
     * change in fitness from the last iteration the variability is divided by
     * 2, and if there is then it is multiplied by 5/4 to scale the mutation
     * rate to improve the efficiency of the algorithm.
     */
    public void run()
    {
        Collections.sort(population);

        lastBest = population.get(0).getFitness();
        
//        System.out.println(population.get(0).getFitness()+", "+population.get(population.size() - 1).getFitness());

        for(int i = 0;; i++)
        {
            makeNewGeneration();
            Collections.sort(population);
            
            double best = population.get(0).getFitness();
            if(best == lastBest)
            {
                variability *= 1 / 2.0;
		exitCounter++;
		if(exitCounter > ITERATIONS_TO_EXIT)
		{
		    progress.setText("Fitness: " + population.get(0).getFitness() + "\tDone\n" + population.get(0).getFriendlyString());
		    break;
		}
            }
            else
            {
                variability *= 5 / 4.0;
		exitCounter = 0;
		if(progress != null)
		{
		    progress.setText("Fitness: " + population.get(0).getFitness() + "\n" + population.get(0).getFriendlyString());
		}
            }
            lastBest = population.get(0).getFitness();

            double deviation = getStDev();
//            System.out.println(getPopString());
	    System.out.println(i+"\tMax:\t"+population.get(0).toString()+"\tVar:\t"+variability+"\tDev:\t"+deviation);
            trimPopulation();
        }

        System.out.println("ending thread");
    }
    /**
     * Generates a list of the entire population.  Each member of the
     * population is represented by their toString method.
     * @return The list of the population
     */
    public String getPopString()
    {
        boolean virgin = true;
        String out = "["+population.get(0);
        for(Candidate c : population)
        {
            if(virgin)
            {
                virgin = false;
                continue;
            }

            out += ", " + c;
        }
        return out + "]";
    }
    /**
     * Generates the children for the next generation.  Will keep all of the
     * old population as well.  This may create duplicates, but I don't care.
     * The final result of this method is the population list having 1.5 more
     * entries, which are the children for the next generation.  The list is
     * no longer sorted.
     */
    private void makeNewGeneration()
    {
//        System.out.println(">>Making a new generation");
        Random r = new Random();
        ArrayList<Candidate> children = new ArrayList<Candidate>(POPULATION_SIZE / 2);

        for (int i = 0; i < POPULATION_SIZE / 2; i++)
        {
            int a = (int)(Math.abs(r.nextGaussian() * POPULATION_SIZE * SELECTIVITY) % POPULATION_SIZE), b;
            do
            {
                b = (int)(Math.abs(r.nextGaussian() * POPULATION_SIZE  * SELECTIVITY) % POPULATION_SIZE);
            }while(b == a);

//            System.out.print(a+"\t"+b);

            KeyValue genome[] = population.get(a).getGenome().clone();
//            System.out.println("A: "+Arrays.toString(genome));
            KeyValue genomeB[] = population.get(b).getGenome().clone();
//            System.out.println("B: "+Arrays.toString(genomeB));
            System.arraycopy(genomeB, genome.length / 2, genome, genome.length / 2, genome.length / 2);
//            System.out.println("Out: "+Arrays.toString(genome));

            Candidate c = new Candidate(genome, data, equation, variableMap);
            children.add(c);

//            System.out.println("\t"+c);
        }

        doMutation(children);

        while(children.size() > 0)
        {
            population.add(children.remove(0));
        }
    }
    /**
     * Mutates the population.  Mutations are done in random places, so if a
     * member of the population has been mutated there is no guarantee it
     * will not be mutated again.  The number of mutations this method does
     * is the population size times the mutation rate.  The number being
     * mutated each time is added to a random z distributed number times the
     * variability times the original value.
     * @param list The list of candidates to mutate
     */
    private void doMutation(ArrayList<Candidate> list)
    {
        Random r = new Random();

        for (int i = 0; i < list.size() * MUTATION_RATE; i++)
        {
            KeyValue genome[] = list.get((int)(r.nextDouble() * list.size())).getGenome();
            int index = (int)(r.nextDouble() * genome.length);
            double value = genome[index].getValue();
            String key = genome[index].getKey();
//            System.out.print("value: "+value);
            double mutation = (r.nextGaussian() * variability * value);
//            System.out.println("Mutation: "+mutation);
            value += mutation;
            genome[index] = new KeyValue(key, value);
//            System.out.println(", new value: "+value);
        }
    }
    /**
     * Removes the last members of the population, until the size is right.
     * This is used to trim the population after the new generation has been
     * created, and the entire population sorted.
     */
    public void trimPopulation()
    {
        while(population.size() > POPULATION_SIZE)
        {
            population.remove(population.size() - 1);
        }
    }
    /**
     * Removes all whitespace from the given string.  The given string is not
     * changed.
     * @param s The string to remove the whitespace from.
     * @return The new string, with no whitespace
     */
    public static String removeWhiteSpace(String s)
    {
        char in[] = s.toCharArray();
        String out = "";

        for (int i = 0; i < in.length; i++)
        {
            if(!Character.isWhitespace(in[i]))
            {
                out += in[i];
            }
        }

        return out;
    }
    /**
     * Makes the initial population from the guess.  Uses the
     * getRandomizedGuess method to fill the list with random guesses.
     * @param guess The guess the user made at the solution used to generate
     * the initial population
     * @param d The data needed by the new candidates to calculate their
     * fitness
     * @param equation The equation to find the solution for
     * @param v The variable map containing the variables to modulate to find
     * the best fit for the equation
     */
    private void createPopulation(Guess guess, Data d, Node equation, VariableMap v)
    {
        for (int i = 0; i < POPULATION_SIZE; i++)
        {
            population.add(getRandomizedGuess(guess, d, equation, v));
        }
    }
    /**
     * Gets a guess that is close to the one given.
     * @param guess The guess that gives the range that the random guess
     * should be in.  It gives both the values, and the standard deviations
     * of the random guess.
     * @param d The data that is used when evaluating the new Candidate.
     * @param equation The equation that is used when evaluating the new
     * Candidate.
     * @param v The variable map that is used when evaluating the new
     * Candidate.
     * @return The randomized Candidate.
     */
    public Candidate getRandomizedGuess(Guess guess, Data d, Node equation, VariableMap v)
    {
        Random r = new Random();
        KeyValue in[] = guess.getGenome();
        KeyValue out[] = new KeyValue[in.length];

        for(int i = 0; i < out.length; i++)
        {
            double value = r.nextGaussian() * guess.getDeviations()[i] + in[i].getValue();
            out[i] = new KeyValue(in[i].getKey(), value);
        }

        return new Candidate(out, d, equation, v);
    }
    /**
     * Gets the standard deviation of the fitness of the population.
     * @return The standard deviation
     */
    private double getStDev()
    {
        double mean = 0;
        for(Candidate c : population)
        {
            mean += c.getFitness();
        }
        mean /= population.size();

        double out = 0;
        for(Candidate c : population)
        {
            out += (c.getFitness() - mean)*(c.getFitness() - mean);
        }
        out /= population.size();
        return Math.sqrt(out);
    }
}