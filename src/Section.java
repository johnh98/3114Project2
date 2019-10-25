
/**
 * 
 */

// import java.io.PrintWriter;

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
    private Student[] studArray;
    private BinarySearchTree<String, Integer> pidTree;
    private BinarySearchTree<NameGroup, Integer> nameTree;
    private BinarySearchTree<Integer, Integer> scoreTree;
    // private int currID;
    // private int index;
    private int size;
    private int numStud;
    private int nameArrSize;
    private boolean merged;
    private int secNum;


    /**
     * Creates a new Section with a BST and an integer counting the current id
     * 
     * @param section
     *            is the section number
     */
    public Section(int section) {
        secNum = section;
        studArray = new Student[200];
        pidTree = new BinarySearchTree<String, Integer>();
        nameTree = new BinarySearchTree<NameGroup, Integer>();
        scoreTree = new BinarySearchTree<Integer, Integer>();
        // currID = (section * 10000) + 1;
        size = 0;
        numStud = 0;
        merged = false;

    }


    /**
     * This method resizes the student array in cases where a merge would result
     * in out of bounds
     * 
     * @param size
     *            the combined size of all component sections
     */
    public void setUpMerge(int mergeSize) {
        if (merged) {
            studArray = new Student[mergeSize];
        }
    }


    /**
     * Switches the sections merged tag so it can be treated like a merged
     * section
     * 
     * @param mergeState
     *            Whether the section is merged or not
     */
    public void setMerge(boolean mergeState) {
        merged = mergeState;
    }


    /**
     * Checks whether the section is merged or not
     * 
     * @return whether the section is merged or not
     */
    public boolean isMerged() {
        return merged;
    }


    /**
     * Gets the number of enrolled students in a course
     * 
     * @return the number of students enrolled in the course
     */
    public int getNumStudents() {
        return numStud;
    }


    /**
     * Adds a student to a section. If a student is already in the BST/section,
     * then
     * it calls an exception.
     * 
     * @param pid
     *            the pid of the student we are inserting
     * @param first
     *            the students first name
     * @param mid
     *            The middle name of the student
     * 
     * @param last
     *            the students last name
     * @param score
     *            The student's score
     * @param grade
     *            The student's grade in the class
     * 
     * @param sec
     *            the student's section
     * 
     * @return the student record that was just created (or the students
     *         existing
     *         record if the student was already in the section)
     */
    public Student insert(
        String pid,
        String first,
        String mid,
        String last,
        int score,
        String grade,
        int sec) {

        /*
         * Search the PID tree to see if the student is already in the section
         */
        Integer currInd = pidTree.find(pid);
        /*
         * If the student isn't in the section then add it in
         */
        if (currInd == null) {
            // Create the student and update its section
            Student newStu = new Student(pid, first, mid, last, score, grade);
            newStu.setSection(sec);
            // insert into the array and trees
            studArray[size] = newStu;
            pidTree.insert(pid, size);
            NameGroup currName = new NameGroup(first, last);
            nameTree.insert(currName, size);
            scoreTree.insert(score, size);
            size++;
            numStud++;
            // Print success and return the student
            System.out.println(first + " " + last + " inserted");
            return newStu;

        }
        /*
         * Otherwise just return the actual student
         */
        else {
            System.out.println(first + " " + last + " is already in section "
                + sec);
            return studArray[currInd];
        }

    }


    /**
     * Inserts a student into the section but doesn't print any messages
     * 
     * @param pid
     *            the students pid
     * @param first
     *            the students first name
     * @param mid
     *            the students middle name
     * @param last
     *            the students last name
     * @param score
     *            the students current score
     * @param grade
     *            the students grade
     * @param sec
     *            the section the student is in
     * @return the student we just inserted or the student that was already in
     *         the section
     */
    public Student insertNoText(
        String pid,
        String first,
        String mid,
        String last,
        int score,
        String grade,
        int sec) {

        /*
         * Search the PID tree to see if the student is already in the section
         */
        Integer currInd = pidTree.find(pid);
        /*
         * If the student isn't in the section then add it in
         */
        if (currInd == null) {
            // Create the student and update its section
            Student newStu = new Student(pid, first, mid, last, score, grade);
            newStu.setSection(sec);
            // insert into the array and trees
            studArray[size] = newStu;
            pidTree.insert(pid, size);
            NameGroup currName = new NameGroup(first, last);
            nameTree.insert(currName, size);
            scoreTree.insert(score, size);
            size++;
            numStud++;
            // Print success and return the student
            return newStu;

        }
        /*
         * Otherwise just return the actual student
         */
        else {
            return studArray[currInd];
        }

    }


    /**
     * Removes a student from a section based on their pid
     * 
     * @param pid
     *            the pid of the student we are removing
     */
    public void remove(String pid) {
        Integer currId = pidTree.find(pid);
        if (currId == null) {
            System.out.println(
                "Remove failed: couldn't find any student with id " + pid);
        }
        else {
            studArray[currId].setSection(-1);
            NameGroup removed = new NameGroup(studArray[currId].getFirstName(),
                studArray[currId].getLastName());
            pidTree.remove(pid, currId);
            nameTree.remove(removed, currId);
            scoreTree.remove(studArray[currId].getScore(), currId);
            System.out.println("Student " + removed.getFirst() + " " + removed
                .getLast() + " get removed from section " + secNum);
            numStud--;
        }
    }


    /**
     * Removes a student from the section (removes no students if there are no
     * or multiple students with the given name)
     * 
     * @param first
     *            The students first name
     * @param last
     *            The students last name
     */
    public void remove(String first, String last) {

        Student[] finds = search(first, last);
        if (finds[0] == null || finds[1] != null) {
            System.out.println("Remove failed. Student " + first + " " + last
                + " doesn't exist in section " + secNum);
        }
        else {
            remove(finds[0].getID());
        }

    }


    /**
     * Completely removes all students from a section and resets the sections
     * student ids
     */
    public void clearSection() {
        // This method just uses the BST makeEmpty method, resets the array, and
        // resets the size and numStud
        pidTree.makeEmpty();
        nameTree.makeEmpty();
        scoreTree.makeEmpty();
        merged = false;
        size = 0;
        numStud = 0;
        studArray = new Student[200];
    }


    /**
     * Searches for a student based on a their pid
     * 
     * @param pid
     *            the pid of the student we are looking for
     * @return the student or NULL if they are not in the section
     */
    public Student searchId(String pid) {
        Integer currInd = pidTree.find(pid);
        if (currInd == null) {
            return null;
        }
        else {
            return studArray[currInd];
        }
    }


    /**
     * Searches for all students that have a given name
     * 
     * @param name
     *            the name of the student (can be either first or last name)
     * @return an array of all students who have the name
     */
    public Student[] search(String name) {
        Student[] allFinds = new Student[size];
        if (numStud != 0) {
            this.findNames(name, nameTree.getRoot(), allFinds);
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
    public Student[] search(String fName, String lName) {
        Student[] allFinds = new Student[size];
        if (numStud != 0) {
            this.findExactNames(fName, lName, nameTree.getRoot(), allFinds);
        }
        nameArrSize = 0;
        return allFinds;
    }


    /**
     * Prints out the contents of the section
     */
    public void dumpSection() {
        System.out.println("BST by ID:");
        if (numStud != 0) {
            printPIDTree(pidTree.getRoot());
        }
        System.out.println("BST by name:");
        if (numStud != 0) {
            printNameTree(nameTree.getRoot());
        }
        System.out.println("BST by score:");
        if (numStud != 0) {
            printScoreTree(scoreTree.getRoot());
        }
    }


    /**
     * Creates and returns a deep copy of all (non-tombstoned) students in this
     * section
     * Necessary for saving course data
     * 
     * @return the array of new Students based on this Sections internal array
     */
    public Student[] dumpCopy() {
        int tot = numStud;
        int pos = 0;
        Student[] copy = new Student[tot];
        for (int i = 0; i < tot; i++) {
            if (studArray[i].getSection() != -1) {
                copy[pos] = new Student(studArray[i].getID(), studArray[i]
                    .getFirstName(), studArray[i].getMiddleName(), studArray[i]
                        .getLastName(), studArray[i].getScore(), studArray[i]
                            .getGrade());
                pos++;
            }
            else {
                tot++;
            }
        }
        return copy;
    }


    /**
     * Goes through the section and assigns every student a grade based on
     * their score. Records the total number of students with each letter grade
     * 
     * @return an array containing the number of students with each letter grade
     */
    public int[] stat() {
        int[] grades = new int[12];
        if (numStud != 0) {
            this.tallyScore(grades);
        }
        return grades;

    }


    /**
     * Goes through the section and grades all enrolled students (assigns a
     * grade letter based on score)
     */
    public void grade() {
        for (int i = 0; i < size; i++) {
            if (studArray[i].getSection() != -1) {

                int grade = studArray[i].getScore();

                if (grade < 50) {
                    studArray[i].setGrade("F ");
                }
                else if (grade < 53) {
                    studArray[i].setGrade("D-");
                }
                else if (grade < 55) {
                    studArray[i].setGrade("D ");
                }
                else if (grade < 58) {
                    studArray[i].setGrade("D+");
                }
                else if (grade < 60) {
                    studArray[i].setGrade("C-");
                }
                else if (grade < 65) {
                    studArray[i].setGrade("C ");
                }
                else if (grade < 70) {
                    studArray[i].setGrade("C+");
                }
                else if (grade < 75) {
                    studArray[i].setGrade("B-");
                }
                else if (grade < 80) {
                    studArray[i].setGrade("B ");
                }
                else if (grade < 85) {
                    studArray[i].setGrade("B+");
                }
                else if (grade < 90) {
                    studArray[i].setGrade("A-");
                }
                else if (grade <= 100) {
                    studArray[i].setGrade("A ");
                }
            }
        }
    }


    /**
     * Updates the student score tree when a students score is changed
     * 
     * @param pid
     *            the pid of the student being changed
     * @param score
     *            the new score that we are inserting into the tree
     */
    public void updateStudentScore(String pid, Integer score) {
        Integer currId = pidTree.find(pid);
        if (currId != null) {
            Integer oldScore = studArray[currId].getScore();
            studArray[currId].setScore(score);
            scoreTree.remove(oldScore, currId);
            scoreTree.insert(score, currId);

        }
    }


    /**
     * Returns the size of the section
     * 
     * @return the section's size
     */
    public int getSize() {

        return size;

    }


    /**
     * Goes through the list of students and creates an array of students who
     * have the same grade letter, which it returns so that it can be printed
     * 
     * @param grade
     *            the grade we are looking for
     * @return an array of students who have that grade
     */
    public Student[] list(String grade) {
        int listSize = 0;
        Student[] graded = new Student[size];
        if (grade.length() == 1) {
            for (int i = 0; i < size; i++) {
                if (studArray[i].getSection() != -1 && studArray[i].getGrade()
                    .equalsIgnoreCase(grade)) {
                    graded[listSize] = studArray[i];
                    listSize++;
                }
            }
        }
        else if (grade.substring(1).equalsIgnoreCase("*")) {
            for (int i = 0; i < size; i++) {
                if (studArray[i].getSection() != -1 && studArray[i].getGrade()
                    .substring(0, 1).equalsIgnoreCase(grade.substring(0, 1))) {
                    graded[listSize] = studArray[i];
                    listSize++;
                }
            }
        }
        else {
            for (int i = 0; i < size; i++) {
                if (studArray[i].getSection() != -1 && studArray[i].getGrade()
                    .equalsIgnoreCase(grade)) {
                    graded[listSize] = studArray[i];
                    listSize++;
                }
            }
        }
        return graded;
    }


    /**
     * This method checks for pairs within the section that are within a certain
     * score of each other
     * 
     * @param diff
     *            the difference we want between pair scores (maximum
     *            difference)
     * @return a string of all the pairs
     */
    public String findPair(int diff) {

        int numPair = 0;
        String pairs = "";
        for (int i = 0; i < size; i++) {
            if (studArray[i].getSection() != -1) {
                for (int j = i + 1; j < size; j++) {
                    if (studArray[j].getSection() != -1) {
                        int scoreDiff = studArray[i].getScore() - studArray[j]
                            .getScore();
                        if ((0 - diff) <= scoreDiff && scoreDiff <= diff) {
                            pairs = pairs + studArray[i].getFirstName() + " "
                                + studArray[i].getLastName() + ", "
                                + studArray[j].getFirstName() + " "
                                + studArray[j].getLastName() + '\n';
                            numPair++;
                        }
                    }
                }
            }
        }
        pairs = pairs + "found " + numPair + " pairs\n";
        return pairs;
    }


    /**
     * This method is a recursive method that assists in finding out how many
     * students in a section have which grade. This is achieved by going through
     * the array of students, ignoring students no longer in the section, and
     * taking note of their score
     * 
     * @param grader
     *            the array holding the quantities for each grade
     */
    private void tallyScore(int[] grader) {
        for (int i = 0; i < size; i++) {
            if (studArray[i].getSection() != -1) {

                int grade = studArray[i].getScore();

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
        }
    }


    /**
     * Finds all instances of a name in a section and adds them to an array
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
        KeyNode<NameGroup, Integer> node,
        Student[] sameNames) {
        // If the left node/subtree exists, we check that out
        if (node.getLeft() != null) {
            this.findNames(name, node.getLeft(), sameNames);
        }
        // Checks if the current node even exists, then checks if either of its
        // student's names match the given name. if they do, add them to the
        // array
        if (node != null && (node.getKey().getFirst().equalsIgnoreCase(name)
            || node.getKey().getLast().equalsIgnoreCase(name))) {
            sameNames[nameArrSize] = studArray[node.getValue()];
            nameArrSize++;
        }
        // Checks the right node/subtree and travels down it if it exists
        if (node.getRight() != null) {
            this.findNames(name, node.getRight(), sameNames);
        }
    }


    /**
     * Finds all instances of a first and last name in a section and adds them
     * to an array
     * 
     * @param first
     *            the first name we are looking for
     * @param last
     *            the last name we are looking for
     * @param node
     *            the node we are looking at right now
     * @param sameNames
     *            the array of students who have the exact
     */
    private void findExactNames(
        String first,
        String last,
        KeyNode<NameGroup, Integer> node,
        Student[] sameNames) {
        // If the left node/subtree exists, we check that out
        if (node.getLeft() != null) {
            this.findExactNames(first, last, node.getLeft(), sameNames);
        }
        // Checks if the current node even exists, then checks if either its
        // student's names match the given name. if they do, add them to the
        // array
        if (node != null && (node.getKey().getFirst().equalsIgnoreCase(first)
            && node.getKey().getLast().equalsIgnoreCase(last))) {
            sameNames[nameArrSize] = studArray[node.getValue()];
            nameArrSize++;
        }
        // Checks the right node/subtree and travels down it if it exists
        if (node.getRight() != null) {
            this.findExactNames(first, last, node.getRight(), sameNames);
        }
    }


    /**
     * Recurses through the PID tree and prints the students held within
     * 
     * @param node
     *            the node we are currently looking at
     */
    private void printPIDTree(KeyNode<String, Integer> node) {
        // If the left node/subtree exists, we check that out
        if (node.getLeft() != null) {
            this.printPIDTree(node.getLeft());
        }
        // prints the student held at the index held in the node
        System.out.println(studArray[node.getValue()].toString());
        // Checks the right node/subtree and travels down it if it exists
        if (node.getRight() != null) {
            this.printPIDTree(node.getRight());
        }
    }


    /**
     * Recurses through the PID tree and prints the students held within
     * 
     * @param node
     *            the node we are currently looking at
     */
    private void printNameTree(KeyNode<NameGroup, Integer> node) {
        // If the left node/subtree exists, we check that out
        if (node.getLeft() != null) {
            this.printNameTree(node.getLeft());
        }
        // prints the student held at the index held in the node
        System.out.println(studArray[node.getValue()].toString());
        // Checks the right node/subtree and travels down it if it exists
        if (node.getRight() != null) {
            this.printNameTree(node.getRight());
        }
    }


    /**
     * Recurses through the PID tree and prints the students held within
     * 
     * @param node
     *            the node we are currently looking at
     */
    private void printScoreTree(KeyNode<Integer, Integer> node) {
        // If the left node/subtree exists, we check that out
        if (node.getLeft() != null) {
            this.printScoreTree(node.getLeft());
        }
        // prints the student held at the index held in the node
        System.out.println(studArray[node.getValue()].toString());
        // Checks the right node/subtree and travels down it if it exists
        if (node.getRight() != null) {
            this.printScoreTree(node.getRight());
        }
    }
}
