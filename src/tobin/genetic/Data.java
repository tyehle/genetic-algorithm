package tobin.genetic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is the class that store all of the data the program read in from the
 * text file provided by the user.  This data is what the program uses to
 * evaluate each solution in the genetic algorithm.
 * @author Tobin
 * @version 1
 */
public class Data
{
    public static final int DIMENSIONS = 2;
    private final ArrayList<double []> list = new ArrayList<double []>();
    private final double average;
    /**
     * Makes a new data object from the given file.
     * @param f
     * @throws IOException When the readLine method fails.
     * @throws NumberFormatException When the numbers the program is trying to
     * parse do not parse right
     */
    public Data(File f) throws IOException, NumberFormatException
    {
        BufferedReader br = new BufferedReader(new FileReader(f));

        String line = br.readLine();
        double coordinates[] = new double[DIMENSIONS];

        while(line != null)
        {
            coordinates = new double[DIMENSIONS];
//            System.out.println("line: "+line);
            String [] parts = line.split("\t|,", DIMENSIONS + 1);
            for (int i= 0; i < DIMENSIONS; i++)
            {
                coordinates[i] = Double.parseDouble(parts[i]);
            }
//            System.out.println(Arrays.toString(coordinates));
            list.add(coordinates);
            line = br.readLine();
        }

        double temp = 0;
        for (int i = 0; i < list.size(); i++)
        {
            temp += list.get(i)[1];
        }
        temp /= list.size();
        average = temp;

//        System.out.println(this);
    }
    /**
     * Gets the two double values representing a point at the given index in
     * the list of data points.
     * @param index The index of the point to retrieve.
     * @return The two values representing the data point
     */
    public double[] getPoint(int index)
    {
        return list.get(index);
    }
    /**
     * Gets the number of data points stored in this class.
     * @return the number of data points
     */
    public double size()
    {
        return list.size();
    }

    @Override
    public String toString()
    {
        String out = Arrays.toString(list.get(0));

        for (int i = 1; i < list.size(); i++)
        {
            out += "\n"+Arrays.toString(list.get(i));
        }

        return out;
    }
}