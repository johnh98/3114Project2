
/**
 * 
 */

//import java.io.PrintWriter;

/**
 * This class represents a section of a course. It contains a binary search tree
 * that stores students, and contains methods that allows
 * for the modifying of this BST.
 * 
 * @author Sam Robles <robleshs>
 * @author John Hoskinson <johnh98>
 * @version 9/24/2019
 *
 */
public class Section {
    private BinarySearchTree<Student> students;
    private int currID;
    private int size;
    private int nameArrSize;


    /**
     * Creates a new Section with a BST and an integer counting the current id
     * 
     * @param section
     *            is the section number
     */
    public Section(int section) {
        students = new BinarySearchTree<Student>();
        currID = (section * 10000) + 1;
        size = 0;

    }


    /**
     * Gets the current ID that will be assigned to a student
     * 
     * @return the ID that the next student will have
     */
    public int getID() {
        return currID;
    }


    /**
     * Adds a student to a section. If a student is already in the BST/section,
     * then
     * it calls an exception. On successful insertion it generates a new id.
     * 
     * @param first
     *            the students first name
     * 
     * @param last
     *            the students last name
     * 
     * @return the student record that was just created (or the students
     *         existing
     *         record if the student was already in the section)
     */
    public Student insert(String first, String last) {
        /*
         * So this bad boy is a mess basically
         * First we make a placeholder student, iterate our size and id
         * Then we try to insert the newStudent into students
         * If that fails, we reset the size and id, print our error, and set
         * newStu to be the student already in students
         * then we return our student
         */
        Student newStu = new Student(first, last, "0" + Integer.toString(
            currID));
        currID++;
        // Student result = (students.insert(newStu)).getValue();
        size += 1;
        try {
            students.insert(newStu);
        }
        catch (DuplicateItemException e) {
            size -= 1;
            currID--;
            int sectionNum = (currID - (size + 1)) / 10000;
            newStu = (Student)students.find(newStu);
            String err1 = first + " " + last + " is already in section "
                + sectionNum;
            System.out.println(err1);
            String err2 = newStu.getID() + ", " + newStu.getFirstName() + " "
                + newStu.getLastName() + ", score = " + Integer.toString(newStu
                    .getGrade());
            System.out.println(err2);
            return newStu;
        }
        System.out.println(first + " " + last + " inserted");
        return newStu;
    }


    /**
     * Removes a student from the section
     * 
     * @param first
     *            The students first name
     * @param last
     *            The students last name
     */
    public void remove(String first, String last) {
        /*
         * Create new student placeholder and decrement size
         * Try to remove student
         * If that fails, restore size, get section number, and print error
         * message
         */
        Student placeHolder = new Student(first, last, "0");
        size -= 1;
        // Number of the current section
        int sectionNum = (currID - (size + 1)) / 10000;
        String err = "Student " + first + " " + last
            + " get removed from section " + Integer.toString(sectionNum);
        try {
            students.remove(placeHolder);
        }
        catch (ItemNotFoundException e) {
            size += 1;
            err = "Remove failed. Student " + first + " " + last
                + " doesn't exist in section " + Integer.toString(sectionNum);
        }
        System.out.println(err);

    }


    /**
     * Completely removes all students from a section and resets the sections
     * student
     * ids
     */
    public void removeSection() {
        // This method just uses the BST makeEmpty method, and resets the size
        // and currID
        students.makeEmpty();
        currID = ((currID / 10000) * 10000) + 1;
        size = 0;

    }


    /**
     * Searches for all students that have a given name
     * 
     * @param name
     *            the name of the student (can be either first or last name)
     * @return an array of all students who have the name
     */
    public Student[] search(String name) {
        Student[] allFinds = new Student[500];
        if (size != 0) {
            this.findNames(name, students.getRoot(), allFinds);
        }
        nameArrSize = 0;
        return allFinds;
    }


    /**
     * Search for a specific student by name
     * 
     * @param fName
     *            is the target first name
     * @param lName
     *            is the target surname
     * @return the student found or NULL if none
     */
    public Student search(String fName, String lName) {
        Student target = new Student(fName, lName, "000000");
        return students.find(target);
    }


    /**
     * Prints out the contents of the section
     * 
     * @return a string representing the contents of the section
     */
    public String dumpSection() {
        return students.toString();
    }


    /**
     * Goes through the section and assigns every student a grade based on
     * their score. Records the total number of students with each letter grade
     * 
     * @return an array containing the number of students with each letter grade
     */
    public int[] grade() {
        int[] grades = new int[12];
        if (size != 0) {
            this.tallyScore(students.getRoot(), grades);
        }
        return grades;

    }


    /**
     * Returns the size of the BST
     * 
     * @return the BST' size
     */
    public int getSize() {

        return size;

    }


    /**
     * This method is a recursive method that assists in finding out how many
     * students in a section have which grade (in order traversal)
     * 
     * @param node
     *            the node that is being checked out
     * @param grader
     *            the array holding the quantities for each grade
     */
    private void tallyScore(BinaryNode<Student> node, int[] grader) {
        // if left not null call method
        if (node.getLeft() != null) {
            this.tallyScore(node.getLeft(), grader);
        }
        /*
         * If the node isn't null, pull its grade, then use this unholy ladder
         * of if-else statements to figure out what grade it has and update the
         * array (i might make this prettier later)
         */
        if (node != null) {
            int grade = node.getValue().getGrade();

            if (grade < 50) {
                grader[11] += 1;
            }
            else if (grade < 53) {
                grader[10] += 1;
            }
            else if (grade < 55) {
                grader[9] += 1;
            }
            else if (grade < 58) {
                grader[8] += 1;
            }
            else if (grade < 60) {
                grader[7] += 1;
            }
            else if (grade < 65) {
                grader[6] += 1;
            }
            else if (grade < 70) {
                grader[5] += 1;
            }
            else if (grade < 75) {
                grader[4] += 1;
            }
            else if (grade < 80) {
                grader[3] += 1;
            }
            else if (grade < 85) {
                grader[2] += 1;
            }
            else if (grade < 90) {
                grader[1] += 1;
            }
            else if (grade <= 100) {
                grader[0] += 1;
            }
        }
        // if right not null call method
        if (node.getRight() != null) {
            this.tallyScore(node.getRight(), grader);
        }
    }


    /**
     * Finds all instances of a name in a tree and adds them to an array
     * 
     * @param name
     *            the name we are looking for (can be first or last name)
     * @param node
     *            the node we are looking at right now
     * @param sameNames
     *            the array of students who have the name (whether first or last
     */
    private void findNames(
        String name,
        BinaryNode<Student> node,
        Student[] sameNames) {
        // If the left node/subtree exists, we check that out
        if (node.getLeft() != null) {
            this.findNames(name, node.getLeft(), sameNames);
        }
        // Checks if the current node even exists, then checks if either of its
        // student's names match the given name. if they do, add them to the
        // array
        if (node != null && (node.getValue().getFirstName().equalsIgnoreCase(
            name) || node.getValue().getLastName().equalsIgnoreCase(name))) {
            sameNames[nameArrSize] = node.getValue();
            nameArrSize++;
        }
        // Checks the right node/subtree and travels down it if it exists
        if (node.getRight() != null) {
            this.findNames(name, node.getRight(), sameNames);
        }
    }
}
