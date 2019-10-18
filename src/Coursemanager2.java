import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

/**
 * Contains the main method that handles input and output
 * 
 * @version 2019.10.15
 * @author John Hoskinson(johnh98@vt.edu)
 * @author Sam Robles(robleshs@vt.edu)
 *
 */
public class Coursemanager2 {

    /**
     * The set of sections we will be working with for the project
     */
    private static Section section1;
    private static Section section2;
    private static Section section3;
    private static Section section4;
    private static Section section5;
    private static Section section6;
    private static Section section7;
    private static Section section8;
    private static Section section9;
    private static Section section10;
    private static Section section11;
    private static Section section12;
    private static Section section13;
    private static Section section14;
    private static Section section15;
    private static Section section16;
    private static Section section17;
    private static Section section18;
    private static Section section19;
    private static Section section20;
    private static Section section21;
    // Holds all sections
    private static Section[] allSects;
    // The current operational section
    private static int currSect;
    // The active Student from the last search/insert
    private static Student currStud;
    // Checks if there is a current student
    private static boolean isStud;
    // Holds string representations of grade values
    private static String[] gradeNames;


    /**
     * The main method that handles reading in from the file and calling the
     * appropriate methods
     * 
     * 
     * @param args
     *            are the target input file
     * @throws FileNotFoundException
     *             with no command file
     */
    public static void main(String[] args) throws FileNotFoundException {
        declareSections();
        // name of the command file to read from, not assigned immediately for
        // safety
        String fileName;
        // Checks if there is a command file
        if (args.length == 1) {
            fileName = args[0];
        }
        else {
            throw new FileNotFoundException("Please add a command file.");
        }
        // File containing commands to the program
        File cmdFile = new File(fileName);
        // Scanner that parses the commands
        Scanner file = new Scanner(cmdFile);
        while (file.hasNextLine()) {
            // Next command line of the file
            String line = file.nextLine();
            // Removes the whitespace and makes an array of substrings
            String[] lineSpl = line.trim().split("\\s+");
            // The first string, the command to execute
            String cmd = lineSpl[0].toLowerCase();
            switch (cmd) {
                case ("loadstudentdata"): {
                    String fileExt = lineSpl[1].substring(lineSpl[1].length()
                        - 4, lineSpl[1].length());
                    if (fileExt.equals(".csv")) {
                        parseStudentText(lineSpl[1]);
                    }
                    if (fileExt.equals("data")) {
                        parseStudentBin(lineSpl[1]);
                    }
                }
                case ("loadcoursedata"): {
                    String fileExt = lineSpl[1].substring(lineSpl[1].length()
                        - 4, lineSpl[1].length());
                    if (fileExt.equals(".csv")) {
                        parseCourseText(lineSpl[1]);
                    }
                    if (fileExt.equals("data")) {
                        parseCourseBin(lineSpl[1]);
                    }
                }
                case ("section"): {
                    currSect = Integer.parseInt(lineSpl[1]) - 1;
                    System.out.println("Switch to section " + lineSpl[1]);
                }
                /*
                 * JOHN,
                 * I'M NOT SURE WHAT THE IF/ELSE IS FOR SO I WON'T TOUCH IT, BUT
                 * THE INSERT HAS NEW SYNTAX. CHECK SECTION TO SEE HOW IT WORKS.
                 * IF ITS A NORMAL INSERT THEN JUST SET THE GRADE AND SCORE TO F
                 * AND 0 RESPECTIVELY
                 * <3,
                 * SAM UwU
                 */
                case ("insert"): {
                    if (lineSpl.length == 4) {
                        currStud = allSects[currSect].insert(lineSpl[1]
                            .toLowerCase(), lineSpl[2].toLowerCase(), lineSpl[3]
                                .toLowerCase(), lineSpl[4].toLowerCase());
                    }
                    else {
                        currStud = allSects[currSect].insert(lineSpl[1]
                            .toLowerCase(), lineSpl[2].toLowerCase(), "",
                            lineSpl[3].toLowerCase());
                    }
                    isStud = true;
                }
                /*
                 * JOHN,
                 * I NOTICED YOU HADN'T ADDED THIS CASE YET, SO I WENT AHEAD AND
                 * DID SO. FEEL FREE TO CHECK HIM OVER TO MAKE SURE IT'S DOING
                 * WHAT ITS SUPPOSED TO, BUT I THINK IT WORKS AS INTENDED.
                 * <3,
                 * SAM UwU
                 */
                case ("searchid"): {
                    Student result = allSects[currSect].searchId(lineSpl[1]);
                    if (result == null) {
                        System.out.println(
                            "Search Failed. Couldn't find any student with id "
                                + lineSpl[1]);
                        isStud = false;
                    }
                    else {
                        System.out.println("Found " + result.toString());
                        currStud = result;
                        isStud = true;
                    }
                }
                case ("search"): {
                    if (lineSpl.length == 2) {
                        // The set of students returned from search, possibly
                        // empty
                        Student[] results = allSects[currSect].search(lineSpl[1]
                            .toLowerCase());
                        System.out.println("search results for " + lineSpl[1]
                            + ":");
                        if (results[1] == null) {
                            currStud = results[0];
                            isStud = true;
                        }
                        else {
                            isStud = false;
                        }
                        // Iterator through results
                        int i = 0;
                        while (i < results.length && results[i] != null) {
                            System.out.println(results[i].toString());
                            i++;
                        }
                        System.out.println(lineSpl[1] + " was found in "
                            + Integer.toString(i) + " records in section "
                            + Integer.toString(currSect + 1));
                    }
                    /*
                     * HEY JOHN,
                     * THIS RETURNS AN ARRAY NOW SO YOU SHOULD FIX NOTE
                     * THAT AND CHANGE YOUR IMPLEMENTATION ACCORDINGLY
                     * <3,
                     * Sam UwU
                     */
                    else if (lineSpl.length == 3) {
                        // The student returned by search, possibly null
                        Student result = allSects[currSect].search(lineSpl[1]
                            .toLowerCase(), lineSpl[2].toLowerCase());
                        if (result == null) {
                            System.out.println("Search failed. Student "
                                + lineSpl[1] + " " + lineSpl[2]
                                + " doesn't exist " + "in section " + Integer
                                    .toString(currSect + 1));
                            isStud = false;
                        }
                        else {
                            System.out.println("Found " + result.toString());
                            currStud = result;
                            isStud = true;
                        }
                    }
                }
                case ("score"): {
                    // The new score to be assigned
                    int newScore = Integer.parseInt(lineSpl[1]);
                    if (!isStud) {
                        System.out.println(
                            "score command can only be called after an "
                                + "insert command or a successful search "
                                + "command with one exact output.");
                    }
                    else if (newScore >= 0 && newScore <= 100) {
                        allSects[currSect].updateStudentScore(currStud.getID(),
                            newScore);
                        currStud.setScore(newScore);
                        System.out.println("Update " + currStud.getFirstName()
                            + " " + currStud.getLastName() + " record, score = "
                            + lineSpl[1]);
                    }
                    else {
                        System.out.println(
                            "Scores have to be integers in range 0 to 100.");
                    }
                }
                /*
                 * JOHN,
                 * I ADDED CASE FOR WHEN ITS THE PID REMOVE (1 PARAMETER)
                 * I BASED IT ON CODE YOU'VE PREVIOUSLY WRITTEN BUT CHECK IT OUT
                 * IN CASE IT DOESN'T DO WHAT YOU WANT
                 * <3,
                 * SAM UwU
                 */
                case ("remove"): {
                    if (lineSpl.length == 2) {
                        allSects[currSect].remove(lineSpl[1]);
                    }
                    else if (lineSpl.length == 3) {
                        allSects[currSect].remove(lineSpl[1].toLowerCase(),
                            lineSpl[2].toLowerCase());
                    }
                }
                /*
                 * JOHN,
                 * I ADDED THE PRINT THAT IT NEEDS TO MAKE. JUST THOUGHT I'D LET
                 * YOU KNOW
                 * <3,
                 * SAM UwU
                 */
                case ("grade"): {
                    allSects[currSect].grade();
                    System.out.println("grading completed");
                }
                /*
                 * HEY JOHN
                 * SAM AGAIN HERE TO LET YOU KNOW THAT I CORRECTED THE INITIAL
                 * PRINT HERE SO IT FOLLOWS THE SPEC. JUST WANTED TO LET YOU
                 * KNOW :)
                 * <3,
                 * SAM UwU
                 */
                case ("stat"): {
                    // Holds the grade totals of the students
                    int[] result = allSects[currSect].stat();
                    System.out.println("Statistics of section " + currSect
                        + ":");
                    // iterator through result
                    int j = 0;
                    while (j < result.length) {
                        if (result[gradeNames.length - j - 1] > 0) {
                            System.out.println(Integer.toString(
                                result[gradeNames.length - j - 1])
                                + " students with grade "
                                + gradeNames[gradeNames.length - j - 1]);
                        }
                        j++;
                    }
                }
                /*
                 * JOHN,
                 * ME AGAIN HERE TO LET YOU KNOW THAT YOU DIDN'T NEED TO PRINT
                 * OUT DUMPSECTION, IT ALREADY DOES THAT. I FIXED IT THOUGH :D
                 * <3,
                 * SAM UwU
                 */
                case ("dumpsection"): {
                    System.out.println("Section " + Integer.toString(currSect
                        + 1) + " dump:");
                    allSects[currSect].dumpSection();
                    System.out.println("Size = " + Integer.toString(
                        allSects[currSect].getNumStudents()));
                }
                /**
                 * JOHN,
                 * I WASN'T SURE IF THIS IS WHAT REMOVESECTION IS SUPPOSED TO
                 * BE, BUT I JUST WENT AHEAD AND ADDED HANDLING FOR
                 * CLEARSECTION. FEEL FREE TO MODIFY IF THIS ISN'T WHAT IT NEEDS
                 * TO BE/ISN'T UP TO FORMATTING
                 * <3,
                 * SAM UwU
                 */
                case ("clearsection"): {
                    allSects[currSect].clearSection();
                    System.out.println("Section " + currSect + " cleared");
                }
                case ("removesection"): {
                    if (lineSpl.length > 1) {
                        allSects[Integer.parseInt(lineSpl[1]) - 1]
                            .removeSection();
                        System.out.println("Section " + lineSpl[1]
                            + " removed");
                    }
                    else {
                        allSects[currSect].removeSection();
                        System.out.println("Section " + Integer.toString(
                            currSect + 1) + " removed");
                    }
                }
                /*
                 * JOHN,
                 * SO LIST RETURNS AN ARRAY OF STUDENT OBJECTS, AND THE I
                 * FIGURED I COULD PRINT IT IS BY ITERATING THROUGH THE ARRAY.
                 * PLEASE LET ME KNOW IF MY IMPLEMENTATION OF THIS HANDLING IS
                 * NOT UP TO PAR.
                 * <3,
                 * SAM UwU
                 */
                case ("list"): {
                    System.out.println("Students with grade " + lineSpl[1]
                        + " are:");
                    Student[] listed = allSects[currSect].list(lineSpl[1]);
                    int listCount = 0;
                    while (listed[listCount] != null) {
                        System.out.println(listed[listCount].toString());
                        listCount++;
                    }
                    System.out.println("Found " + listCount + " students");
                }
                /*
                 * JOHN,
                 * SINCE WE ADDED THIS IN FRONT OF THE TA AND YOU DON'T HAVE THE
                 * LATEST COURSEMANAGER1, IT DIDN'T HAVE OUR HANDLING FOR
                 * FINDPAIR. I ADDED IT THOUGH, SO WE'RE GOOD ON THIS
                 * <3,
                 * SAM UwU
                 */
                case ("findpair"): {
                    int difference = 0;
                    if (lineSpl.length == 2) {
                        difference = Integer.parseInt(lineSpl[1]);

                    }
                    System.out.println(
                        "Students with score difference less than or equal "
                            + Integer.toString(difference) + ":");
                    System.out.print(allSects[currSect].findPair(difference));
                }
            }
            if (!cmd.equals("insert") && !cmd.equals("search")) {
                isStud = false;
            }
        }
        file.close();
    }


    /**
     * Parses the binary file holding course data
     * 
     * @param string
     */
    private static void parseCourseBin(String string) {
        // TODO Auto-generated method stub

    }


    private static void parseCourseText(String string) {
        // TODO Auto-generated method stub

    }


    private static void parseStudentBin(String string) {
        // TODO Auto-generated method stub

    }


    private static void parseStudentText(String string) {
        // TODO Auto-generated method stub

    }


    /**
     * Declares all the relevant fields
     */
    private static void declareSections() {
        allSects = new Section[20];
        section1 = new Section(1);
        section2 = new Section(2);
        section3 = new Section(3);
        section3 = new Section(4);
        section3 = new Section(5);
        section3 = new Section(6);
        section3 = new Section(7);
        section3 = new Section(8);
        section3 = new Section(9);
        section3 = new Section(10);
        section3 = new Section(11);
        section3 = new Section(12);
        section3 = new Section(13);
        section3 = new Section(14);
        section3 = new Section(15);
        section3 = new Section(16);
        section3 = new Section(17);
        section3 = new Section(18);
        section3 = new Section(19);
        section3 = new Section(20);
        allSects[0] = section1;
        allSects[1] = section2;
        allSects[2] = section3;
        allSects[3] = section4;
        allSects[4] = section5;
        allSects[5] = section6;
        allSects[6] = section7;
        allSects[7] = section8;
        allSects[8] = section9;
        allSects[9] = section10;
        allSects[10] = section11;
        allSects[11] = section12;
        allSects[12] = section13;
        allSects[13] = section14;
        allSects[14] = section15;
        allSects[15] = section16;
        allSects[16] = section17;
        allSects[17] = section18;
        allSects[18] = section19;
        allSects[19] = section20;

        currSect = 0;
        isStud = false;
        setGradeNames();
    }


    /**
     * Creates an array that holds string representations of grade names
     * in order to ease implementation of the grade() command
     */
    private static void setGradeNames() {
        gradeNames = new String[12];
        gradeNames[0] = "a";
        gradeNames[1] = "a-";
        gradeNames[2] = "b+";
        gradeNames[3] = "b";
        gradeNames[4] = "b-";
        gradeNames[5] = "c+";
        gradeNames[6] = "c";
        gradeNames[7] = "c-";
        gradeNames[8] = "d+";
        gradeNames[9] = "c";
        gradeNames[10] = "c-";
        gradeNames[11] = "f";
    }
}
