package tobin.equation;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tobin.genetic.VariableMap;

/**
 * Used to store part of an equation before the entire thing is parsed.  Part
 * of the equation is a string, and part of it is represented in a Node tree.
 * Only used for temporarily storing data.
 * @author Tobin
 * @version 1
 */
public class PartialEquation
{
    private LinkedList<EquationPiece> equation = new LinkedList<EquationPiece>();

    /**
     * Makes a new <tt>PartialEquation</tt> with the specified pieces
     * initially stored in it.  Pieces may be added, changed, or removed from
     * the equation later.
     * @param parts The pieces initially in the equation
     */
    public PartialEquation(EquationPiece ... parts)
    {
        equation.addAll(Arrays.asList(parts));
        clearEmpties();
    }
    /**
     * Deletes all of the empty equation pieces stored in this partial equation.
     */
    public final void clearEmpties()
    {
        for (int i = 0; i < equation.size(); i++)
        {
            EquationPiece p = equation.get(i);
            if(!p.isComplete() && p.getEquation().equals(""))
            {
                equation.remove(i--);
            }
        }
    }
    /**
     * Gets the <tt>EquationPiece</tt> at the specified location.  Note that
     * this is not a copy of the piece stored in this equation.
     * @param index The index of the piece to retrieve
     * @return The piece at the specified index
     */
    public EquationPiece get(int index)
    {
        return equation.get(index);
    }
    /**
     * Removes the equation piece at the given index in the list of pieces.
     * @param index The index of the piece to remove
     * @return The piece that was removed
     */
    public EquationPiece remove(int index)
    {
        return equation.remove(index);
    }
    /**
     * Removes the last piece in the list.
     * @return The piece that was removed
     */
    public EquationPiece removeLast()
    {
        return equation.removeLast();
    }
    /**
     * Gets the character at the specified location.  If the location refers
     * to an equation piece that is complete then an
     * <tt>IllegalArgumentException</tt> is thrown.
     * @param loc The location of the character to retrieve
     * @return The character at the given location
     */
    public char getChar(PartialEquationLocation loc)
    {
        EquationPiece piece = equation.get(loc.getNodeNum());
        if(piece.isComplete())
        {
            throw new IllegalArgumentException("Piece is complete: "+piece);
        }
        return piece.getEquation().charAt(loc.getStringLoc());
    }
    /**
     * Gets a substring of the equation.  Equivalent to calling
     * <tt>getEquation().substring(start, end);</tt>
     * @param start The beginning of the substring
     * @param end The end of the substring
     * @return The part of the equation, as a String
     */
    public String getString(int start, int end)
    {
        return getEquation().substring(start, end);
    }
    /**
     * Replaces the given substring (both ends inclusive) of the equation with
     * the given <tt>EquationPiece</tt>.
     * @param start
     * @param end
     * @param piece
     */
    public void insert(int start, int end, EquationPiece piece)
    {
        PartialEquationLocation open = getLocation(start);
        PartialEquationLocation close = getLocation(end);

        EquationPiece openPiece;
        String openString = "";
        char openChars[] = get(open.getNodeNum()).getEquation().toCharArray();
        for (int i = 0; i < open.getStringLoc(); i++)
        {
            openString += openChars[i];
        }
        openPiece = new EquationPiece(openString);

        EquationPiece closePiece;
        String closeString = "";
        char closeChars[] = get(close.getNodeNum()).getEquation().toCharArray();
        for (int i = close.getStringLoc() + 1; i < closeChars.length; i++)
        {
            closeString += closeChars[i];
        }
        closePiece = new EquationPiece(closeString);

        for (int i = open.getNodeNum(); i <= close.getNodeNum(); i++)
        {
            remove(open.getNodeNum());
        }

        if(!closeString.equals(""))
        {
            add(open.getNodeNum(), closePiece);
        }
        add(open.getNodeNum(), piece);
        if(!openString.equals(""))
        {
            add(open.getNodeNum(), openPiece);
        }
    }
    /**
     * Gets the <tt>PartialEquationLocation</tt> corresponding to a string
     * location from the equation string.
     * @param stringLocation The string index to convert.
     * @return The <tt>PartialEquationLocation</tt>
     */
    public PartialEquationLocation getLocation(int stringLocation)
    {
        int counter = 0;
        for (int i = 0; i < equation.size(); i++)
        {
            if(!get(i).isComplete())
            {
                char[] string = get(i).getEquation().toCharArray();
                for (int j = 0; j < string.length; j++)
                {
                    if(counter == stringLocation)
                    {
                        return new PartialEquationLocation(i, j);
                    }
                    counter ++;
                }
            }
        }

        throw new IllegalArgumentException("String location out of bounds.  Location: "+stringLocation+", Length: "+getEquation().length());
    }
    /**
     * Replaces the piece at the specified position in this equation with the
     * specified piece.
     *
     * @param index index of the element to replace
     * @param piece the piece to be stored at the specified position
     * @return the piece previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public EquationPiece set(int index, EquationPiece piece)
    {
        return equation.set(index, piece);
    }
    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any
     * subsequent elements to the right (adds one to their indices).
     * @param index The index to add it at
     * @param o The object to add
     */
    public void add(int index, EquationPiece o)
    {
        equation.add(index, o);
    }
    /**
     * Adds the object to the end of the list.
     * @param o The object to add
     */
    public void add(EquationPiece o)
    {
        equation.add(o);
    }
    /**
     * Returns a <tt>LinkedList</tt> of the equation pieces in this partial
     * equation.  Note that the list is a copy of the one stored in this
     * partial equation, but the equation pieces themselves are not copied.
     * Any change to the pieces in the list returned will result in a change
     * in the pieces stored in this equation.
     * @return The list of pieces
     */
    public String getEquation()
    {
        String out = "";
        for (int i = 0; i < equation.size(); i++)
        {
            if(!get(i).isComplete())
            {
                out += get(i).getEquation();
            }
        }
        return out;
    }
    /**
     * Returns the number of equation pieces in this <tt>PartialEquation</tt>.
     * @return The number of pieces as an int
     */
    public int size()
    {
        return equation.size();
    }
    /**
     * Returns if the given equation piece is complete or not.  This is
     * equivalent to <tt>get(index).isComplete()</tt>.
     * @param index The index of the piece to check
     * @return If the piece is completed or not
     */
    public boolean isComplete(int index)
    {
        return equation.get(index).isComplete();
    }
    /**
     * Gets a string representation of this equation.  This returns the unparsed
     * pieces of the equation as strings, without any of the parsed pieces.
     * @return The string
     */
    public String toString()
    {
        return equation.toString();
    }
    /**
     * Returns a <tt>PartialEquation</tt> representing everything in between
     * the start and end indexes (inclusive, exclusive, respectively).  Note
     * that equation pieces in the new partial equation are the same the ones
     * in this partial equation, so changes effect both.
     * @param begin The string index to start at
     * @param end The string index to end at
     * @return The <tt>PartialEquation</tt> representing all of the pieces in
     * between that start and end indexes
     */
    public PartialEquation getSubEquation(int begin, int end)
    {
        PartialEquation out = new PartialEquation();

        PartialEquationLocation start = getLocation(begin);
        PartialEquationLocation stop = getLocation(end);

        String first = get(start.getNodeNum()).getEquation().substring(start.getStringLoc() + 1);
        out.add(new EquationPiece(first));

        for (int i = start.getNodeNum() + 1; i < stop.getNodeNum(); i++)
        {
            out.add(get(i));
        }

        String lastEquation = get(stop.getNodeNum()).getEquation();
//        System.out.println("Last equation: "+lastEquation);
        String last = lastEquation.substring(0, stop.getStringLoc());
        out.add(new EquationPiece(last));

        return out;
    }
    /**
     * Gets the location of the closing parenthesis that matches the opening
     * parenthesis passed to it.  If the location passed is not an open paren it
     * throws an IllegalArgumentException.
     * @param open The location of the open parenthesis
     * @return The location of the close parenthesis
     */
    public int getClose(int open)
    {
        if(getEquation().charAt(open) != '(')
        {
            throw new IllegalArgumentException("Expected: '(', Found: '"+getEquation().charAt(open)+"'");
        }

        char chars[] = getEquation().toCharArray();
        int depth = 0;
        for (int i = open; i < chars.length; i++)
        {
//            System.out.println(chars[i]);
            char c = chars[i];
            if(c == '(')
            {
                depth++;
            }
            else if(c == ')')
            {
                depth--;
                if(depth == 0)
                {
                    return i;
                }
            }
        }

        throw new SyntaxException();
    }
    /**
     * Replaces the parentheses indicated and their contents with a Node.  If
     * the location given is not a '(' then an IllegalArgumentException is
     * thrown.
     * @param open The location of the open parenthesis.
     * @param map the VariableMap to add any new variables to
     * @return The node that has been added to the equation.  Note that this
     * node does not need to be added to the equation, because it is already
     * done in this method.
     */
    public Node parseParentheses(int open, VariableMap map)
    {
        if(getEquation().charAt(open) != '(')
        {
            throw new IllegalArgumentException("Expected: '(', Found: '"+getEquation().charAt(open)+"'");
        }

        int close = getClose(open);
//        System.out.println(getSubEquation(open, close));
//        System.out.println(getEquation()+", open: "+open+", close: "+close);
        Node n = Node.parseEquation(getSubEquation(open, close), map);

        insert(open, close, new EquationPiece(n));
        return n;
    }
    /**
     * Replaces all instances of parentheses in the equation with node
     * representations.
     * @param map the VariableMap to add any new variables to
     */
    public void parseAllParentheses(VariableMap map)
    {
        Pattern p = Pattern.compile("\\(");

        while(true)
        {
            Matcher m = p.matcher(getEquation());
            if(m.find())
            {
//                System.out.println(m.start()+", "+getEquation().charAt(m.start()));
                parseParentheses(m.start(), map);
            }
            else
            {
                return;
            }
        }
    }
}