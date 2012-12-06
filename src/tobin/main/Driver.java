package tobin.main;

import tobin.gui.EntryFrame;

/**
 * The main class of the program.  This class makes an instance of the entry
 * frame, and sets it visible, starting the AWT thread which deals with the rest
 * of the program.
 * @author Tobin
 */
public class Driver
{
//    static
//    {
//        boolean ae = false;
//        assert ae = true;
//        if(!ae)
//        {
//            throw new IllegalStateException("Assertions must be enabled");
//        }
//    }

    public Driver()
    {
	toString();
    }
    /**
     * Makes an instance of the entry frame, and sets it visible, starting the
     * AWT thread which deals with the rest of the program.
     * @param args The command line arguments
     */
    public static void main(String[] args)
    {
        EntryFrame.setBestLAF();
        EntryFrame f = new EntryFrame();
        f.setVisible(true);
    }
}