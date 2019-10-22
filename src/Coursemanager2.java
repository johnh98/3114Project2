import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
    // Records if there is student data loaded
    private static boolean isStudData;
    // The student manager that holds enrollment data
    private static StudentManager studManager;


    /**
     * The main method that handles reading in from the file and calling the
     * appropriate methods
     * 
     * 
     * @param args
     *            are the target input file
     * @throws IOException
     *             when reading/writing binary files with errors
     */
    public static void main(String[] args) throws IOException {
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
            String cmd = lineSpl[0].toLowerCase();
            String cmdPre = cmd.substring(0, 4);
            if (!cmdPre.equals("load") && !cmdPre.equals("save")) {
                for (int i = 0; i < lineSpl.length; i++) {
                    lineSpl[i] = lineSpl[i].toLowerCase();
                }
            }
            // Turns the initial command into an enum to switch on
            Command command = Command.setCommand(cmd);
            switch (command) {
                case loadstudentdata: {
                    String fileExt = lineSpl[1].substring(lineSpl[1].length()
                        - 4, lineSpl[1].length());
                    if (fileExt.equals(".csv")) {
                        parseStudentText(lineSpl[1]);
                    }
                    if (fileExt.equals("data")) {
                        parseStudentBin(lineSpl[1]);
                    }
                }
                case loadcoursedata: {
                    String fileExt = lineSpl[1].substring(lineSpl[1].length()
                        - 4, lineSpl[1].length());
                    if (fileExt.equals(".csv") && isStudData) {
                        parseCourseText(lineSpl[1]);
                    }
                    else if (fileExt.equals("data") && isStudData) {
                        parseCourseBin(lineSpl[1]);
                    }
                    else {
                        System.out.println("No valid student data loaded");
                    }
                }
                case section: {
                    currSect = Integer.parseInt(lineSpl[1]) - 1;
                    System.out.println("Switch to section " + lineSpl[1]);
                }
                case insert: {
                    // Prevents the command being run on a merged section
                    if (allSects[currSect].isMerged()) {
                        System.out.println(
                            "Command insert is not valid for merged sections");
                        break;
                    }
                    String fName = "";
                    String mName = "";
                    String lName = "";
                    String perID = "";
                    String grade = "F";
                    int score = 0;
                    if (lineSpl.length % 2 == 0) {
                        fName = lineSpl[2];
                        mName = lineSpl[3];
                        lName = lineSpl[4];
                        perID = lineSpl[1];
                    }
                    else {
                        perID = lineSpl[1];
                        fName = lineSpl[2];
                        lName = lineSpl[3];
                    }
                    if (lineSpl.length > 4) {
                        grade = lineSpl[lineSpl.length - 1];
                        score = Integer.parseInt(lineSpl[lineSpl.length - 2]);
                    }
                    currStud = allSects[currSect].insert(perID, fName, mName,
                        lName, score, grade, currSect + 1);
                    isStud = true;
                }
                case searchid: {
                    // Prevents the command being run on a merged section
                    if (allSects[currSect].isMerged()) {
                        System.out.println(
                            "Command searchid is not valid for merged sections");
                        break;
                    }
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
                case search: {
                    // Prevents the command being run on a merged section
                    if (allSects[currSect].isMerged()) {
                        System.out.println(
                            "Command search is not valid for merged sections");
                        break;
                    }

                    if (lineSpl.length == 2) {
                        // The set of students returned from search, possibly
                        // empty
                        Student[] results = allSects[currSect].search(
                            lineSpl[1]);
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
                    else if (lineSpl.length == 3) {
                        // The students returned by search, possibly null
                        Student[] result = allSects[currSect].search(lineSpl[1]
                            .toLowerCase(), lineSpl[2].toLowerCase());
                        System.out.println("Search results for " + lineSpl[1]
                            + " " + lineSpl[2] + ":");
                        int j = 0;
                        while (j < result.length && result[j] != null) {
                            System.out.println(result[j].toString());
                        }
                        if (j > 0) {
                            isStud = true;
                        }
                        System.out.println(lineSpl[1] + " " + lineSpl[2]
                            + " was found in " + Integer.toString(j)
                            + " records in section " + Integer.toString(currSect
                                + 1));
                    }
                }
                case score: {
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
                case remove: {
                    // Prevents the command being run on a merged section
                    if (allSects[currSect].isMerged()) {
                        System.out.println(
                            "Command remove is not valid for merged sections");
                        break;
                    }
                    if (lineSpl.length == 2) {
                        allSects[currSect].remove(lineSpl[1]);
                    }
                    else if (lineSpl.length == 3) {
                        allSects[currSect].remove(lineSpl[1], lineSpl[2]);
                    }
                }
                case grade: {
                    // Prevents the command being run on a merged section
                    if (allSects[currSect].isMerged()) {
                        System.out.println(
                            "Command grade is not valid for merged sections");
                        break;
                    }
                    allSects[currSect].grade();
                    System.out.println("grading completed");
                }
                case stat: {
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
                case dumpsection: {
                    System.out.println("Section " + Integer.toString(currSect
                        + 1) + " dump:");
                    allSects[currSect].dumpSection();
                    System.out.println("Size = " + Integer.toString(
                        allSects[currSect].getNumStudents()));
                }
                case clearsection: {
                    allSects[currSect].clearSection();
                    System.out.println("Section " + currSect + " cleared");
                }
                case list: {
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
                case findpair: {
                    int difference = 0;
                    if (lineSpl.length == 2) {
                        difference = Integer.parseInt(lineSpl[1]);

                    }
                    System.out.println(
                        "Students with score difference less than or equal "
                            + Integer.toString(difference) + ":");
                    System.out.print(allSects[currSect].findPair(difference));
                }
                case merge: {
                    if (allSects[currSect].getNumStudents() != 0) {
                        System.out.println(
                            "Sections could only be merged to an  empty section. section "
                                + Integer.toString(currSect + 1)
                                + " is not empty");
                    }
                    else {
                        mergeAll();
                        System.out.println("All sections merged at section "
                            + Integer.toString(currSect + 1));
                    }
                }
                case savestudentdata: {

                }
                case savecoursedata: {

                }
                case clearcoursedata: {

                }
                default:
                    break;
            }
            if (!cmd.equals("insert") && !cmd.equals("search")) {
                isStud = false;
            }
        }
        file.close();
    }


    // TODO header
    private static void mergeAll() {
        int totLeng = 0;
        // Gets the total size needed for the merged section
        for (int i = 0; i < allSects.length; i++) {
            totLeng = totLeng + allSects[i].getNumStudents();
        }
        // Sets the sections "merged" tag to true
        allSects[currSect].setMerge(true);
        // Enlarges the section to fit all the data from the others
        allSects[currSect].setUpMerge(totLeng);

        for (int tgtSect = 0; tgtSect < allSects.length; tgtSect++) {
            if (tgtSect != currSect && allSects[tgtSect]
                .getNumStudents() != 0) {
                mergeIn(tgtSect);
            }
        }
    }


    /**
     * A helper method that inserts all students from a given section into the current section
     * Uses list by grade as it is an available way to safely access all students in a section
     * Creates a deep copy so that changes to the main sections do not affect the merged one
     * @param tgtSect is the section to pull data from
     */
    private static void mergeIn(int tgtSect) {
        for (int gd = 0; gd < gradeNames.length; gd++) {
            Student[] studGroup = allSects[tgtSect].list(gradeNames[gd]);
            for (int i = 0; i < studGroup.length; i++) {
                allSects[currSect].insert(studGroup[i].getID(), studGroup[i]
                    .getFirstName(), studGroup[i].getMiddleName(), studGroup[i]
                        .getLastName(), studGroup[i].getScore(), studGroup[i]
                            .getGrade(), studGroup[i].getSection());
            }
        }
    }


    /**
     * Parses the binary file holding course data
     * 
     * @param fileName
     *            is the name of the target binary file
     * @throws IOException 
     */
    private static void parseCourseBin(String fileName) throws IOException {
        //TODO binary parsing
        InputStream binCourseFile = new FileInputStream(fileName);
        
        String[] 
        int byteRead;
        
        while ((byteRead = binCourseFile.read()) != -1) {
            outputStream.write(byteRead);
            int charCode = Integer.parseInt(info, 2);
            ((char)charCode).toString();
        }
        
        binCourseFile.close();
    }


    /**
     * Parses a text file holding information about course sections
     * 
     * @param fileName
     *            is the file to be read from
     * @throws FileNotFoundException
     *             when there is no course data file passed
     */
    private static void parseCourseText(String fileName)
        throws FileNotFoundException {
        // Integer variables that constitute a student, initialized once for
        // memory
        int score;
        int sectionID;
        Student tempStudent;
        File courseFile = new File(fileName);
        // Scanner that parses the commands
        Scanner cFile = new Scanner(courseFile);
        while (cFile.hasNextLine()) {
            String cLine = cFile.nextLine();
            // Removes the whitespace and makes an array of substrings
            String[] split = cLine.trim().split(",\\s+");
            for (int i = 0; i < split.length; i++) {
                split[i] = split[i].toLowerCase();
            }
            sectionID = Integer.parseInt(split[0]);
            score = Integer.parseInt(split[4]);
            int idCheck = studManager.checkIdentity(split[1], split[2],
                split[3]);
            if (idCheck == 1) {
                System.out.println(split[2] + " " + split[3]
                    + " insertion failed. Wrong student information. ID doesn't exist");
            }
            else if (idCheck == 2) {
                System.out.println(split[2] + " " + split[3]
                    + " insertion failed. Wrong student information. ID belongs to another student");
            }
            else {
                tempStudent = allSects[sectionID - 1].searchId(split[1]);
                if (tempStudent != null) {
                    tempStudent.setGrade(split[5]);
                    tempStudent.setScore(score);
                    studManager.updateScore(split[1], score);
                }
                else {
                    allSects[sectionID - 1].insert(split[1], split[2], "",
                        split[3], score, split[5], sectionID);
                }
            }
        }
        cFile.close();
    }


    /**
     * 
     * @param fileName
     */
    private static void parseStudentBin(String fileName) {
        // TODO Auto-generated method stub

    }


    /**
     * Assigns data to the student manager system that can later be used
     * Reads a text file
     * 
     * @param fileName
     *            is the student data file
     * @throws FileNotFoundException
     *             is thrown when a file name is invalid or not present
     */
    private static void parseStudentText(String fileName)
        throws FileNotFoundException {
        File studentFile = new File(fileName);
        // Scanner that parses the commands
        Scanner sFile = new Scanner(studentFile);
        while (sFile.hasNextLine()) {
            String sLine = sFile.nextLine();
            // Removes the whitespace and makes an array of substrings
            String[] split = sLine.trim().split(",\\s+");
            for (int i = 0; i < split.length; i++) {
                split[i] = split[i].toLowerCase();
            }
            studManager.insert(split[0], split[1], split[2], split[3]);
        }
        sFile.close();
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

        studManager = new StudentManager();
    }


    /**
     * Creates an array that holds string representations of grade names
     * in order to ease implementation of the grade() command
     */
    private static void setGradeNames() {
        gradeNames = new String[12];
        gradeNames[0] = "a ";
        gradeNames[1] = "a-";
        gradeNames[2] = "b+";
        gradeNames[3] = "b ";
        gradeNames[4] = "b-";
        gradeNames[5] = "c+";
        gradeNames[6] = "c ";
        gradeNames[7] = "c-";
        gradeNames[8] = "d+";
        gradeNames[9] = "d ";
        gradeNames[10] = "d-";
        gradeNames[11] = "f";
    }
}
