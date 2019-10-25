
// -------------------------------------------------------------------------
/**
 * Exception for a search or remove called on a target that does not exist
 * 
 * @author John Hoskinson <johnh98>
 * @version 9/22/2019
 */
public class ItemNotFoundException extends RuntimeException {

    /**
     * The version ID of the error
     */
    private static final long serialVersionUID = 1L;


    /**
     * The exception in basic form
     */
    public ItemNotFoundException() {
        super();
    }

}
