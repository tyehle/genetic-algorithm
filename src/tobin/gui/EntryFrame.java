package tobin.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import tobin.equation.EquationPiece;
import tobin.equation.Node;
import tobin.equation.PartialEquation;
import tobin.genetic.Data;
import tobin.genetic.Genetic;
import tobin.genetic.Guess;
import tobin.genetic.VariableMap;

/**
 * This is the first thing that the program runs after going through the driver.
 * This class is the main frame where the user enters data for the program to
 * process.
 * @author Tobin
 * @version 1
 */
public class EntryFrame extends JFrame implements ActionListener
{
    private static final String HELP_TEXT = "\tThere are two parts to this "+
            "program.  One is the equation that the user enters as a model for"+
            " the regression, and the other is the data that the program uses"+
            " to find the regression.\n\tThe equation must be entered in a "+
            "very specific syntax.  Parentheses cannot be used for "+
            "multiplication, and variables next to values will not be "+
            "multiplied.  No part of the equation is case sensative, and all "+
            "white space is ignored.  Allowed constants are PI, and E.  The "+
            "variables that the program will change to fit the equation to the"+
            " data are preceded by a $, and the independent variable is always"+
            " x.  An example of an equation would be \"$a*x^2 + $b*x + $c\".  "+
            "Allowed unary operators are sin, cos, tan, arcsin, arccos, arctan"+
            ", abs, ln, and log.  They are notated by simply typing out the "+
            "function followed by a set of parentheses.  An example of an "+
            "equation with unary operators in it would be \"$a * sin($b*x - $c"+
            ") + $d\", and one with constants would be \"$a * e^($b*x - $c) + "+
            "$d\".\n\tThe data file that the program reads to obtain the "+
            "points to fit the equation to must be either tab or comma "+
            "delimeted, and must be two columns.  The first column is the x "+
            "position of each point, and the second column is they y location "+
            "for that x location.  The data for one point should be stored on "+
            "each line.  An example of an acceptable text file is as follows:"+
            "\n1\t1.0\n2\t3.9\n3\t9.4\n4\t15.8\nIf the file is in any other "+
            "format the program will prompt you to fix it.";
    private static final long serialVersionUID = 1L;
    private static final Color goodColor = new Color(80, 250, 80),
                               badColor = new Color(250, 80, 80);

    private JButton ok, cancel, browse, help;
    private JTextField equationField, dataField;
//    private JFormattedTextField dimensions;
    private JLabel equationLabel, dataLabel;
    private boolean equationGood = false, dataGood = false;

    private File dataFile = new File(".");
    /**
     * Constructs a new Entry Frame, with all fields and buttons, but does not
     * set it visible.
     */
    public EntryFrame()
    {
        super("Regressions");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setResizable(false);
        setLayout(new GridLayout(3, 1));
        JPanel p;

        equationField = new JTextField(25);
        equationField.setText("$a*x^2+$b*x+$c");
//	equationField.setText("$a*$b^($c*x-$h)+10000");
        equationField.setSelectionStart(0);
        equationField.setSelectionEnd(equationField.getText().length());
        equationField.setInputVerifier(new EquationVerifier());
        equationLabel = new JLabel("f(x)=");
        p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p.add(equationLabel);
        p.add(equationField);
        add(p);

        dataLabel = new JLabel("Data location: ");
        dataField = new JTextField(25);
        dataField.setInputVerifier(new DataVerifier());
        browse = new JButton("Browse");
        browse.addActionListener(this);
        p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p.add(dataLabel);
        p.add(dataField);
        p.add(browse);
        add(p);

        ok = new JButton("Ok");
        ok.addActionListener(this);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        help = new JButton("Help");
        help.addActionListener(this);

        p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p.add(help);
        p.add(ok);
        p.add(cancel);
        
        add(p);
        pack();
        centerFrame(this, getWidth(), getHeight());
    }
    /**
     * Deals with all of the actions that can take place when the user presses
     * a button.
     * @param e The action event.
     */
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        if(source == ok)
        {
	    /**
	     * Tests if the user has entered all data correctly, and if so moves
	     * on to the next part of the program.  If they have not entered all
	     * of the data right then an error message is displayed and the user
	     * is returned to this frame.
	     */
            System.out.println("ok");
            if(dataGood && equationGood)
            {
		/**
		 * The user has entered all data, so the program will try to
		 * move to getting a guess and starting the genetic algorithm.
		 */
                String text = Genetic.removeWhiteSpace(equationField.getText());
                PartialEquation p = new PartialEquation(new EquationPiece(text));
                VariableMap v = new VariableMap();
                Node equation = Node.parseEquation(p, v);
//                if(v.getVariables().length < 1)
//                {
//                    showError("Equation must have variables, such as '$a'");
//                    return;
//                }

                try
                {
		    /**
		     * Parses in the data file, and deals with error in reading
		     * the file.  If there is and error an error message is
		     * displayed and the user is returned to this frame.
		     */
                    Data d = new Data(dataFile);
		    System.out.println(v);

                    String keys[] = v.getVariables();
		    System.out.println(v);
                    Guess g = GuessFrame.getGuess(keys);
		    System.out.println(v);
                    if(g == null)
                    {
                        return;
                    }

                    Genetic re = new Genetic(d, equation, v, g, "Status", "null");
                    Thread t = new Thread(re, "Genetic Evaluator");
                    t.start();
                    dispose();
                    System.out.println("EntryFrame disposed");
                }
                catch (FileNotFoundException fofe)
                {
                    showError("File not found");
                    dataField.requestFocus();
                    return;
                }
                catch (IOException ioe)
                {
                    showError("Error reading file.\nFile may be open in another"+
                            " program.\nPlease close suhc programs and try again.");
                    dataField.requestFocus();
                    return;
                }
                catch(NumberFormatException nfe)
                {
                    showError("Error reading file.\nFile must contain tab or "+
                            "comma delimeted decimals in columns.");
                    dataField.requestFocus();
                    return;
                }
            }
            else
            {
		/**
		 * The user has not entered all the data correctly, so an error
		 * message is displayed telling them what they did wrong.
		 */
                String message = "";

                if(!dataGood)
                {
                    message += "Illegal data file";
                    dataField.requestFocus();

                    if(!equationGood)
                    {
                        message += "\nIllegal equation syntax";
                        equationField.requestFocus();
                    }
                }
                else if(!equationGood)
                {
                    message += "Illegal equation syntax";
                    equationField.requestFocus();
                }

                showError(message);
            }
        }
        else if(source == cancel)
        {
	    /**
	     * Exits the program because the user pressed cancel.
	     */
            System.exit(0);
        }
        else if(source == browse)
        {
	    /**
	     * Makes a new JFileChooser for the user to browse for a data file.
	     */
            JFileChooser chooser = new JFileChooser();
            chooser.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f)
                {
                    return f.getAbsolutePath().matches(".*.[Tt][Xx][Tt]") || f.isDirectory();
                }

                @Override
                public String getDescription()
                {
                    return "Text Files (*.txt)";
                }
            });
            chooser.setCurrentDirectory(dataFile);
            int returned = chooser.showOpenDialog(this);
            if(returned != JFileChooser.APPROVE_OPTION)
            {
                return;
            }

            dataField.requestFocus();
            dataFile = chooser.getSelectedFile();
            dataField.setText(dataFile.getAbsolutePath());
            ok.requestFocus();//to verify the input

            System.out.println("Data file: "+chooser.getSelectedFile());
        }
        else if (source == help)
        {
	    /**
	     * Displays the help dialog.
	     */
            int rows = 15, columns = 50;
            JTextArea area = new JTextArea(HELP_TEXT, rows, columns);
            area.setEditable(false);
            area.setWrapStyleWord(true);
            area.setLineWrap(true);

            JScrollPane pane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            JOptionPane.showMessageDialog(this, pane, "Help", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /**
     * Defines what the user can enter into the equation field.
     */
    private class EquationVerifier extends InputVerifier
    {
        /**
         * Executes whenever the component looses focus.  Is used to set the
         * color of the field and set the boolean status of the equation.
         * Always returns true.
         * @param input The component loosing focus
         * @return true
         */
        @Override
        public boolean shouldYieldFocus(JComponent input)
        {
            if(input == equationField)
            {
                if(verify(input))
                {
                    equationField.setBackground(goodColor);
                    equationGood = true;
                }
                else
                {
                    equationGood = false;
                    equationField.setBackground(badColor);
                }
            }

            return true;
        }
        /**
         * Tests if the equation the user entered will parse in the parser.
         * Executes the parsing method to test if it parses, and dumps the
         * output.
         * @param input The component to test.
         * @return If the equation entered is valid.
         */
        public boolean verify(JComponent input)
        {
            if(input == equationField)
            {
                try
                {
                    String text = Genetic.removeWhiteSpace(equationField.getText());
                    PartialEquation p = new PartialEquation(new EquationPiece(text));
                    Node n = Node.parseEquation(p, new VariableMap());
//                    System.out.println(n);
                    return true;
                }
                catch(Exception e)
                {
                    return false;
                }
            }
            else//should not happen
            {
                return true;
            }
        }
    }
    /**
     * Defines what the user can enter into the data field.
     */
    private class DataVerifier extends InputVerifier
    {
        /**
         * Executes whenever the component looses focus.  Is used to set the
         * color of the field, update the file location, and set the boolean
         * status of the data.  Always returns true.
         * @param input The component loosing focus
         * @return true
         */
        @Override
        public boolean shouldYieldFocus(JComponent input)
        {
            if(input == dataField)
            {
                if(verify(input))
                {
                    dataField.setBackground(goodColor);
                    dataFile = new File(dataField.getText());
                    dataGood = true;
                }
                else
                {
                    dataGood = false;
                    dataField.setBackground(badColor);
                }
            }

            return true;
        }
        /**
         * Tests if the file exists and if it is a .txt file if the component
         * is the data field.
         * @param input The component to test.
         * @return If the data entered is valid.
         */
        public boolean verify(JComponent input)
        {
            if(input == dataField)
            {
                try
                {
                    File f = new File(dataField.getText());
                    return f.exists() && f.getName().matches(".*.[tT][xX][tT]");
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    return false;
                }
            }
            else//should not happen
            {
                return true;
            }
        }
    }
    /**
     * Shows an error dialog with the given text.  This method will not return
     * until the dialog is closed.
     * @param message The error message to be displayed.
     */
    private void showError(String message)
    {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    /**
     * Centers the frame in the middle of the screen.  It uses the set bounds
     * method so if the width and height are wrong then the frame will come out
     * the wrong size.
     * @param frame The frame that you want to be centered.
     * @param Width The width of the frame
     * @param Height The height of the frame
     */
    public static void centerFrame(Window frame, int Width, int Height)
    {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds((int)((screen.getWidth()-Width) / 2),
                (int)((screen.getHeight()-Height) / 2),Width,Height);
    }
    /**
     * Set the best available look-and-feel into use.
     */
    public static void setBestLAF()
    {
        /*
         * Set the look-and-feel.  On Linux, Motif/Metal is sometimes incorrectly used
         * which is butt-ugly, so if the system l&f is Motif/Metal, we search for a few
         * other alternatives.
         */
        try
        {
            // Set system L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Check whether we have an ugly L&F
            LookAndFeel laf = UIManager.getLookAndFeel();
            if (laf == null || laf.getName().matches(".*[mM][oO][tT][iI][fF].*") || laf.getName().matches(".*[mM][eE][tT][aA][lL].*"))
            {

                // Search for better LAF
                UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();

                String lafNames[] =
                {
                        ".*[gG][tT][kK].*",
                        ".*[wW][iI][nN].*",
                        ".*[mM][aA][cC].*",
                        ".*[aA][qQ][uU][aA].*",
                        ".*[nN][iI][mM][bB].*"
                };

                lf: for (String lafName: lafNames)
                {
                    for (UIManager.LookAndFeelInfo l: info)
                    {
                        if (l.getName().matches(lafName))
                        {
                            UIManager.setLookAndFeel(l.getClassName());
                            break lf;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.err.println("Error setting LAF: " + e);
        }
    }
}