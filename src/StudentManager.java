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
    private BinarySearchTree<String, Student> students;


    public StudentManager() {
        
        students = new BinarySearchTree<String, Student>();

    }


    public void insert(String pid, String first, String mid, String last) {

        Student newStu = new Student(pid, first, last, mid, 0, "f");
        students.insert(pid, newStu);

    }
    
    public void updateScore( String pid, int score) {
        
        Student updated = students.find(pid);
        if (!(updated.equals(null))) {
            updated.setScore(score);
        }
        
    }
    
    public void updateSection(String pid, int section) {
        Student updated = students.find(pid);
        if (!(updated.equals(null))) {
            updated.setSection(section);
        }
    }
    
    public int checkIdentity(String pid, String first, String last) {
        Student matcher = students.find(pid);
        //If the pid doesn't exist return 1
        if(matcher.equals(null)) {
            return 1;
        }
        //If it does but the names don't match, print 2
        else if (!(matcher.getFirstName().equals(first) && matcher.getLastName().equals(last))) {
            return 2;
        }
        //If it checks out, print 0
        else {
            return 0;
        }
    }
    
    

}
