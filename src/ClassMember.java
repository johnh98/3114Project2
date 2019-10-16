/**
 * This interface is used by student and allows students to
 * be compared
 * 
 * @author John Hoskinson <johnh98>
 * @version 9/22/2019
 */
public interface ClassMember extends Comparable<ClassMember> {
    /**
     * A method that will allow for students to be compared
     * 
     * @param compMember
     *            the student its being compared to
     * @return an integer based on their relationship
     */
    public int compareTo(ClassMember compMember);


    /**
     * Gets the students first name
     * 
     * @return the first name
     */
    public String getFirstName();


    /**
     * Gets the students last name
     * 
     * @return the last name
     */
    public String getLastName();
    
    /**
     * Gets teh students ID
     * 
     * @return the id
     */
    public String getID();
}

