package tobin.equation;

/**
 * Signals that a Node has an illegal number of children.  This is usually
 * thrown from a checkValidity method in one of the members of the tree.
 * Instantiable Nodes are only allowed to have a specific number of children,
 * because of their relation to other members of the tree, for example a
 * binary operator can not operate on only one value.
 * @author Tobin
 * @version 1
 */
public class IllegalChildCountException extends IllegalStateException
{
    private static final long serialVersionUID = 1L;
    /**
     * Constructs an IllegalChildCountException with no detail message.
     * A detail message is a String that describes this particular exception.
     */
    public IllegalChildCountException()
    {
	super();
    }
    /**
     * Constructs an IllegalChildCountException with the specified detail
     * message.  A detail message is a String that describes this particular
     * exception.
     *
     * @param s the String that contains a detailed message
     */
    public IllegalChildCountException(String s)
    {
	super(s);
    }
    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * <p>Note that the detail message associated with <code>cause</code> is
     * <i>not</i> automatically incorporated in this exception's detail
     * message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link Throwable#getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A <tt>null</tt> value
     *         is permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public IllegalChildCountException(String message, Throwable cause)
    {
        super(message, cause);
    }
    /**
     * Constructs a new exception with the specified cause and a detail
     * message of <tt>(cause==null ? null : cause.toString())</tt> (which
     * typically contains the class and detail message of <tt>cause</tt>).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * java.security.PrivilegedActionException}).
     *
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public IllegalChildCountException(Throwable cause)
    {
        super(cause);
    }
}