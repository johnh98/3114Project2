
// -------------------------------------------------------------------------
/**
 * An exception for an add called on an item already in a tree
 * 
 * @author John Hoskinson <johnh98>
 * @version 9/22/2019
 */
public class DuplicateItemException extends RuntimeException {
    /**
     * The version ID of the error
     */
    private static final long serialVersionUID = 1L;


    /**
     * The basic exception with no message
     */
    public DuplicateItemException() {
        super();
    }

}
