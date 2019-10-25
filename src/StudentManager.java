/**
 * 
 */

/**
 * Represents a student manager that holds student data for all students. Used
 * for referece when performing operations on course manager
 * 
 * @author Sam Robles <robleshs>
 * @version 10/18/19
 *
 */
public class StudentManager {
    /**
     * The tree that holds the students
     */
    private BinarySearchTree<String, Student> students;

    /**
     * Holds the number of total students being managed
     */
    private int numStudents;


    /**
     * Sets up a student manager by creating the binary tree
     */
    public StudentManager() {

        students = new BinarySearchTree<String, Student>();
        numStudents = 0;
    }


    /**
     * Returns the number of students in the student manager
     * 
     * @return the number of students in the student manager
     */
    public int getNumStud() {
        return numStudents;
    }


    /**
     * This method inserts into the student manager class
     * 
     * @param pid
     *            the student's pid
     * @param first
     *            the students first name
     * @param mid
     *            the students middle name
     * @param last
     *            the students last name
     */
    public void insert(String pid, String first, String mid, String last) {

        Student newStu = new Student(pid, first, mid, last, 0, "f ");
        students.insert(pid, newStu);
        numStudents++;
    }


    /**
     * Creates a shallow copy of the array of students for the purposes of
     * merging and saving data. Will be deep copied at the source of the call
     * 
     * @return an array of all Students in the manager
     */
    public Student[] copyStudents() {
        Student[] copy = new Student[numStudents];
        students.makeCopy(copy);

        return copy;
    }


    /**
     * Updates a student in the managers score
     * 
     * @param pid
     *            the pid of the student we are trying to update
     * @param score
     *            the score we are updating to
     */
    public void updateScore(String pid, int score) {

        Student updated = students.find(pid);
        if (updated != null) {
            updated.setScore(score);

            if (score < 50) {
                updated.setGrade("f ");
            }
            else if (score < 53) {
                updated.setGrade("d-");
            }
            else if (score < 55) {
                updated.setGrade("d ");
            }
            else if (score < 58) {
                updated.setGrade("d+");
            }
            else if (score < 60) {
                updated.setGrade("c-");
            }
            else if (score < 65) {
                updated.setGrade("c ");
            }
            else if (score < 70) {
                updated.setGrade("c+");
            }
            else if (score < 75) {
                updated.setGrade("b-");
            }
            else if (score < 80) {
                updated.setGrade("b ");
            }
            else if (score < 85) {
                updated.setGrade("b+");
            }
            else if (score < 90) {
                updated.setGrade("a-");
            }
            else if (score <= 100) {
                updated.setGrade("a ");
            }
        }

    }


    /**
     * Updates a student's section in the manager
     * 
     * @param pid
     *            the student's pid
     * @param section
     *            the new section
     */
    public void updateSection(String pid, int section) {
        Student updated = students.find(pid);
        if (updated != null) {
            updated.setSection(section);
        }
    }


    /**
     * This method checks to ensure that a student is allowed to be modified by
     * checking if the student is in the student manager, and whether a
     * hypothetical student in the manager has the same first and last name as
     * the given student
     * 
     * @param pid
     *            the given student's pid
     * @param first
     *            the given students first name
     * @param last
     *            the given students last name
     * @return 1 if the student isn't in the student manager (pid isn't there),
     *         2 if the pid is there but names dont match, 0 if the student is
     *         correct
     */
    public int checkIdentity(String pid, String first, String last) {
        Student matcher = students.find(pid);
        // If the pid doesn't exist return 1
        if (matcher == null) {
            return 1;
        }
        // If it does but the names don't match, print 2
        else if (!(matcher.getFirstName().equals(first) && matcher.getLastName()
            .equals(last))) {
            return 2;
        }
        // If it checks out, print 0
        else {
            return 0;
        }
    }


    /**
     * This method allows for students to be found in the student manager
     * 
     * @param pid
     *            the pid of the student in the student manager
     * @return the student found in the student manager (null if such a student
     *         doesn't exist)
     */
    public Student searchStu(String pid) {
        Student found = students.find(pid);
        return found;
    }


    /**
     * Tombstones all students from a section in the student manager
     * 
     * @param sec
     *            the section we want gone
     */
    public void clearSec(int sec) {
        clearSectionData(students.getRoot(), sec);
    }


    /**
     * Recurses through the student manager and tombstones all students in a
     * section
     * 
     * @param node
     *            the node we are currently looking at
     * @param sec
     *            the section we are removing
     */
    private void clearSectionData(KeyNode<String, Student> node, int sec) {
        // If the left node/subtree exists, we check that out
        if (node.getLeft() != null) {
            this.clearSectionData(node.getLeft(), sec);
        }
        // prints the student held at the index held in the node
        if (node.getValue().getSection() == sec) {
            node.getValue().setSection(-1);
        }
        // Checks the right node/subtree and travels down it if it exists
        if (node.getRight() != null) {
            this.clearSectionData(node.getRight(), sec);
        }
    }
}
