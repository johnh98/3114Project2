import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//On my honor:
//
//- I have not used source code obtained from another student,
//or any other unauthorized source, either modified or
//unmodified.
//
//- All source code and documentation used in my program is
//either my original work, or was derived by me from the
//source code published in the textbook for this course.
//
//- I have not discussed coding details about this project with
//anyone other than my partner (in the case of a joint
//submission), instructor, ACM/UPE tutors or the TAs assigned
//to this course. I understand that I may discuss the concepts
//of this program with other students, and that another student
//may help me debug my program so long as neither of us writes
//anything during the discussion or modifies any computer file
//during the discussion. I have violated neither the spirit nor
//letter of this restriction.

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
     * @throws FileNotFoundException with no command file
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
            if (cmd.equals("section")) {
                currSect = Integer.parseInt(lineSpl[1]) - 1;
                System.out.println("Switch to section " + lineSpl[1]);
            }
            else if (cmd.equals("insert")) {
                currStud = allSects[currSect].insert(lineSpl[1].toLowerCase(),
                    lineSpl[2].toLowerCase());
                isStud = true;
            }
            else if (cmd.equals("search")) {
                if (lineSpl.length == 2) {
                    // The set of students returned from search, possibly empty
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
                    System.out.println(lineSpl[1] + " was found in " + Integer
                        .toString(i) + " records in section " + Integer
                            .toString(currSect + 1));
                }
                else if (lineSpl.length == 3) {
                    // The student returned by search, possibly null
                    Student result = allSects[currSect].search(lineSpl[1]
                        .toLowerCase(), lineSpl[2].toLowerCase());
                    if (result == null) {
                        System.out.println("Search failed. Student "
                            + lineSpl[1] + " " + lineSpl[2] + " doesn't exist "
                            + "in section " + Integer.toString(currSect + 1));
                        isStud = false;
                    }
                    else {
                        System.out.println("Found " + result.toString());
                        currStud = result;
                        isStud = true;
                    }
                }
            }
            else if (cmd.equals("score")) {
                // The new score to be assigned
                int newScore = Integer.parseInt(lineSpl[1]);
                if (!isStud) {
                    System.out.println(
                        "score command can only be called after an "
                            + "insert command or a successful search "
                            + "command with one exact output.");
                }
                else if (newScore >= 0 && newScore <= 100) {
                    currStud.setScore(newScore);
                    System.out.println("Update " + currStud.getFirstName() + " "
                        + currStud.getLastName() + " record, score = "
                        + lineSpl[1]);
                }
                else {
                    System.out.println(
                        "Scores have to be integers in range 0 to 100.");
                }
            }
            else if (cmd.equals("remove")) {
                allSects[currSect].remove(lineSpl[1].toLowerCase(), lineSpl[2]
                    .toLowerCase());
            }
            else if (cmd.equals("grade")) {
                // Holds the grade totals of the students
                int[] result = allSects[currSect].grade();
                System.out.println("grading completed:");
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
            else if (cmd.equals("dumpsection")) {
                System.out.println("Section " + Integer.toString(currSect + 1)
                    + " dump:");
                System.out.println(allSects[currSect].dumpSection());
                System.out.println("Size = " + Integer.toString(allSects[currSect]
                    .getSize()));
            }
            else if (cmd.equals("removesection")) {
                if (lineSpl.length > 1) {
                    allSects[Integer.parseInt(lineSpl[1]) - 1].removeSection();
                    System.out.println("Section " + lineSpl[1] + " removed");
                }
                else {
                    allSects[currSect].removeSection();
                    System.out.println("Section " + Integer.toString(currSect + 1)
                        + " removed");
                }
            }
            else if (cmd.equals("findpair")) {
                System.out.println("Findpair does nothing right now");
            }
            if (!cmd.equals("insert") && !cmd.equals("search")) {
                isStud = false;
            }
        }
        file.close();
    }


    /**
     * Declares all the relevant fields
     */
    private static void declareSections() {
        allSects = new Section[3];
        section1 = new Section(1);
        section2 = new Section(2);
        section3 = new Section(3);
        allSects[0] = section1;
        allSects[1] = section2;
        allSects[2] = section3;
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
