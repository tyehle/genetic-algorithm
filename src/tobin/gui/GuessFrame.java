package tobin.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import tobin.genetic.Guess;
import tobin.genetic.KeyValue;

/**
 * The frame where the user enters a guess at the regression.
 * @author Tobin
 * @version 1
 */
public class GuessFrame extends JDialog
{
    private static final long serialVersionUID = 1L;

    private JButton ok;
    public Guess guess;
    private KeyValue values[];
    private String keys[];
    private JTextField textFields[];
    private JTextField stDevFields[];
    /**
     * Makes a new frame with all the components and sizes itself, but does not
     * set itself visible.
     * @param keys The variables which the user needs to enter a guess for.
     */
    public GuessFrame(String keys[])
    {
        setTitle("Guess");
        setLayout(new GridLayout(keys.length + 1, 1));
        setModal(true);
        addWindowListener(new CloseEventHandler());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        this.keys = keys;
        JLabel labels[] = new JLabel[keys.length];
        textFields = new JTextField[keys.length];
        stDevFields = new JTextField[keys.length];
        values = new KeyValue[keys.length];

        for (int i = 0; i < keys.length; i++)
        {
            JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            labels[i] = new JLabel(keys[i]+": ");

            textFields[i] = new JTextField();
            textFields[i].setColumns(10);
            textFields[i].setDocument(new DecimalDocument());
            textFields[i].setText("1.0");

            stDevFields[i] = new JTextField();
            stDevFields[i].setColumns(4);
            stDevFields[i].setDocument(new DecimalDocument());
            stDevFields[i].setText("1.0");

            p.add(labels[i]);
            p.add(textFields[i]);
            p.add(new JLabel("Deviation"));
            p.add(stDevFields[i]);
            add(p);
            values[i] = new KeyValue(keys[i], 0.0);
        }

        EventHandler eh = new EventHandler();
        ok = new JButton("Ok");
        ok.addActionListener(eh);
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p.add(ok);
        add(p);

        pack();
        EntryFrame.centerFrame(this, getWidth(), getHeight());
    }
    /**
     * An inner class to handle events
     */
    private class EventHandler implements ActionListener
    {
       /**
	* Deals with setting the public guess and closing the dialog box,
	* because the user pressed ok.
	* @param e The action event.
	*/
        public void actionPerformed(ActionEvent e)
        {
            Object source = e.getSource();
            if(source == ok)
            {
                System.out.println("ok");
                double deviations[] = new double[values.length];
                for (int i = 0; i < values.length; i++)
                {
                    try
                    {
                        values[i] = new KeyValue(keys[i], Double.parseDouble(textFields[i].getText()));
                    }
                    catch (Exception ex)
                    {
                        return;
                    }
                    deviations[i] = Double.parseDouble(stDevFields[i].getText());
                }

                System.out.println(Arrays.toString(values)+", "+Arrays.toString(deviations));

                guess = new Guess(values, deviations);
                setVisible(false);
                dispose();
            }
        }
    }
    /**
     * Defines a document that only allows the user to enter decimals.
     */
    private class DecimalDocument extends DefaultStyledDocument
    {
        private static final long serialVersionUID = 1L;

	/**
	 * Replaces the text at the given location with the new text if and only
	 * if the new text also parses as a double.
	 * @param offs the length from the beginning of the text to start
	 * replacing
	 * @param length the length to replace
	 * @param str the string to replace it with
	 * @param a the set of attributes
	 * @throws BadLocationException if the document fails to retrieve the
	 * text that is already in it.
	 */
        @Override
        public void replace(int offs, int length, String str, AttributeSet a) throws BadLocationException
        {
            String contents = getText(0, getLength());
            String newContents = contents.substring(0,offs)+str+contents.substring(offs+length, contents.length());

            System.out.println("contents: "+contents+", new contents: "+newContents);

            try
            {
                if(newContents.matches("\\.|-|-\\.|.+[eE]|.+[eE]-"))
                {
                    newContents += "1";
                }
                else if(newContents.matches(".*[dDfF]"))
                {
                    return;
                }
                Double.parseDouble(newContents);
                
//                System.out.println("parsed to: "+Double.parseDouble(newContents));
//                System.out.println("Adding");
                super.replace(offs, length, str, a);
            }
            catch (Exception e)
            {
//                System.out.println("not adding");
            }
        }
    }
    /**
     * Used to set the guess to null if the user closes the dialog in some other
     * way besides pressing ok.
     */
    private class CloseEventHandler implements WindowListener
    {
        public void windowOpened(WindowEvent e){}
        public void windowClosing(WindowEvent e)
        {
            System.out.println("Window Closing");
            guess = null;
        }
        public void windowClosed(WindowEvent e){}
        public void windowIconified(WindowEvent e){}
        public void windowDeiconified(WindowEvent e){}
        public void windowActivated(WindowEvent e){}
        public void windowDeactivated(WindowEvent e){}
    }
    /**
     * This method is used to make a new frame and get a guess from the user.
     * This will not return until the user has entered a guess and closed the
     * window.
     * @param keys The variable names to get the guesses for.
     * @return A guess object containing the values and deviations for each
     * variable
     */
    public static Guess getGuess(String keys[])
    {
        KeyValue out[] = new KeyValue[keys.length];
        GuessFrame guessFrame = new GuessFrame(keys);
        guessFrame.setVisible(true);

        return guessFrame.guess;
    }
}