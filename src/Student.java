
/**
 * This class represents a student object
 * It contains information such as the students name, id,
 * and grade, all of which can be modified using methods in the class
 * 
 * @author John Hoskinson <johnh98>
 * @version 9/23/2019
 *
 */
public class Student implements ClassMember {

    private String firstName;
    private String middleName;
    private String lastName;
    private String studID;
    private int totGrade;
    private String grade;
    private int section;


    /**
     * Creates a new Student with a given name and ID but no grades
     * 
     * @param fName
     *            is the student's first name
     * @param lName
     *            is the student's last name
     * @param newID
     *            is the ID number, with 2 digits indicating section
     *            and the following 4 representing the order this was created in
     */
    public Student(
        String pid,
        String fName,
        String midName,
        String lName,
        int score,
        String letterGrade) {
        firstName = fName;
        middleName = midName;
        lastName = lName;
        studID = pid;
        totGrade = score;
        grade = letterGrade;
        section = 0;

    }


    /**
     * Returns the section the student is in or -1 if they aren't enrolled
     * anywhere
     * 
     * @return the section they're in
     */
    public int getSection() {
        return section;
    }


    /**
     * Sets the student's section
     * 
     * @param sec
     */
    public void setSection(int sec) {
        section = sec;
    }


    /**
     * Creates a string of the student's ID, name, and grade
     * 
     * @return the student's representation in string form
     */
    public String toString() {
        return (studID + ", " + firstName + " " + lastName + ", score = "
            + String.valueOf(totGrade));
    }


    /**
     * Sets the students grade to a given value
     * 
     * @param grade
     *            is the new grade for the student
     */
    public void setScore(int grade) {
        totGrade = grade;
    }


    // ----------------------------------------------
    /**
     * Public getter for the student's score
     * 
     * @return the student's grade
     */
    public int getScore() {
        return totGrade;
    }


    /**
     * Sets the students grade to a given value
     * 
     * @param grade
     *            is the new grade for the student
     */
    public void setGrade(String letterGrade) {
        grade = letterGrade;
    }


    // ----------------------------------------------
    /**
     * Public getter for the student's score
     * 
     * @return the student's grade
     */
    public String getGrade() {
        return grade;
    }


    /**
     * Public getter for Student ID
     * 
     * @return the student's ID number
     */
    public String getID() {
        return studID;
    }


    /**
     * Public getter for the student's first name
     * 
     * @return the student's last name
     */
    public String getFirstName() {
        return firstName;
    }


    /**
     * Public getter for the student's last name
     * 
     * @return the student's last name
     */
    public String getLastName() {
        return lastName;
    }
    // ---------------------------------------------


    /**
     * A comparator for Students based on name
     * 
     * @param compStudent
     *            is the student to compare with
     * @return 0 if the names are identical,
     *         1 when compStudent < this,
     *         -1 when this < compStudent
     */
    // @Override
    public int compareTo(ClassMember compStudent) {
        /*
         * int compNames = (this.lastName).compareTo(compStudent.getLastName());
         * if (compNames == 0) {
         * return (this.firstName).compareTo(compStudent.getFirstName());
         * }
         */
        int compID = (this.getID()).compareTo(compStudent.getID());
        return compID;
    }
}
