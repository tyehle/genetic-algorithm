package tobin.gui;

import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

/**
 * Displays the progress of the genetic algorithm as it executes.
 * @author Tobin
 * @version 1
 */
public class ProgressFrame extends JFrame
{
    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 500, HEIGHT = 110;

    private JTextArea textArea;
    /**
     * Makes a new progress frame with all of the components, and at the correct
     * size and location.  The new frame is not set visible.
     * @param title The title of the new frame
     * @param text The text to initially put in the text area which displays
     * the progress of the algorithm
     */
    public ProgressFrame(String title, String text)
    {
        super(title);
//        addWindowListener(new CloseEventHandler());

	JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
	Border b = BorderFactory.createEtchedBorder();
	p.setBorder(b);
        textArea = new JTextArea(text);
	p.add(textArea);
//        label.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(p,
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	add(scrollPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	EntryFrame.centerFrame(this, WIDTH, HEIGHT);
//        pack();
    }
    /**
     * Sets the text of the text area to update the frame based on the progress
     * of the algorithm.
     * @param text The new text
     */
    public void setText(String text)
    {
        textArea.setText(text);
//        pack();
        doLayout();
        repaint();
    }
}