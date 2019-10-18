
/**
 * This class just holds a students first and last name, and exists to act as a
 * key in the tree
 * 
 * @author Samuel Robles <robleshs>
 * @version 10/15/19
 *
 */
public class NameGroup implements Comparable<NameGroup> {

    private String firstName;
    private String lastName;


    /**
     * The constructor for the NameGroup class
     * 
     * @param first
     *            the first name
     * @param last
     *            the last name
     */
    public NameGroup(String first, String last) {
        firstName = first;
        lastName = last;
    }


    /**
     * returns the first name in the NameGroup
     * 
     * @return the first name
     */
    public String getFirst() {
        return firstName;
    }


    /**
     * Gets the last name and returns it
     * 
     * @return the last name
     */
    public String getLast() {
        return lastName;
    }


    /**
     * Allows for the comparison of two name groups
     * 
     * @param name
     *            the name group being compared to
     * @return x = 0 if they are equal,
     *         0 < x when name < this,
     *         x < 0 when this < name
     */
    public int compareTo(NameGroup name) {
        int compNames = (this.lastName).compareTo(name.getLast());
        if (compNames == 0) {
            return (this.firstName).compareTo(name.getFirst());
        }
        return compNames;
    }

}
