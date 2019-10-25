import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
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
            if (cmd.length() >= 4) {
                String cmdPre = cmd.substring(0, 4);
                if (!cmdPre.equals("load") && !cmdPre.equals("save")) {
                    for (int i = 0; i < lineSpl.length; i++) {
                        lineSpl[i] = lineSpl[i].toLowerCase();
                    }
                }
            }

            // Turns the initial command into an enum to switch on
            Command command = Command.setCommand(cmd);
            switch (command) {
                case loadstudentdata: {
                    String fileExt = lineSpl[1].substring(lineSpl[1].length()
                        - 4);
                    if (fileExt.equals(".csv")) {
                        System.out.println(lineSpl[1] + " successfully loaded");
                        parseStudentText(lineSpl[1]);

                    }
                    if (fileExt.equals("data")) {
                        System.out.println(lineSpl[1] + " successfully loaded");
                        parseStudentBin(lineSpl[1]);

                    }
                    isStudData = true;
                    break;
                }
                case loadcoursedata: {
                    String fileExt = lineSpl[1].substring(lineSpl[1].length()
                        - 4);
                    if (fileExt.equals(".csv") && isStudData) {
                        System.out.println(lineSpl[1].substring(0, lineSpl[1]
                            .length() - 4)
                            + " Course has been successfully loaded.");
                        parseCourseText(lineSpl[1]);

                    }
                    else if (fileExt.equals("data") && isStudData) {
                        System.out.println(lineSpl[1].substring(0, lineSpl[1]
                            .length() - 5)
                            + " Course has been successfully loaded.");
                        parseCourseBin(lineSpl[1]);

                    }
                    else {
                        System.out.println(
                            "Course Load Failed. You have to load Student "
                                + "Information file first.");
                    }
                    break;
                }
                case section: {
                    currSect = Integer.parseInt(lineSpl[1]) - 1;
                    System.out.println("Switch to section " + lineSpl[1]);
                    break;
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
                    if (lineSpl.length % 2 != 0) {
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
                    int ident = studManager.checkIdentity(perID, fName, lName);
                    if (ident == 0) {
                        Student tgt = studManager.searchStu(perID);
                        if (tgt.getSection() == 0) {
                            currStud = allSects[currSect].insert(perID, fName,
                                mName, lName, score, grade, currSect + 1);
                            studManager.updateSection(perID, currSect + 1);
                            isStud = true;
                        }
                        else if (tgt.getSection() == currSect + 1) {
                            System.out.println(fName + " " + lName
                                + " is already " + "in section " + (currSect
                                    + 1));
                        }
                        else {
                            System.out.println(fName + " " + lName
                                + " is already "
                                + "registered in a different section");
                        }
                    }
                    else if (ident == 1) {
                        System.out.println(fName + " " + lName
                            + " insertion failed. Wrong student information. "
                            + "ID doesn't exist");
                    }
                    else if (ident == 2) {
                        System.out.println(fName + " " + lName
                            + " insertion failed. Wrong student information. "
                            + "ID belongs to another student");
                    }
                    break;
                }
                case searchid: {
                    // Prevents the command being run on a merged section
                    if (allSects[currSect].isMerged()) {
                        System.out.println("Command searchid is not "
                            + "valid for merged sections");
                        break;
                    }
                    Student result = allSects[currSect].searchId(lineSpl[1]);
                    if (result == null) {
                        System.out.println("Search Failed. Couldn't find any "
                            + "student with id " + lineSpl[1]);
                        isStud = false;
                    }
                    else {
                        System.out.println("Found " + result.toString());
                        currStud = result;
                        isStud = true;
                    }
                    break;
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
                            j++;
                        }
                        if (j > 0) {
                            isStud = true;
                        }
                        System.out.println(lineSpl[1] + " " + lineSpl[2]
                            + " was found in " + Integer.toString(j)
                            + " records in section " + Integer.toString(currSect
                                + 1));
                    }
                    break;
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
                        studManager.updateScore(currStud.getID(), newScore);
                        System.out.println("Update " + currStud.getFirstName()
                            + " " + currStud.getLastName() + " record, score = "
                            + lineSpl[1]);
                    }
                    else {
                        System.out.println(
                            "Scores have to be integers in range 0 to 100.");
                    }
                    break;
                }
                case remove: {
                    // Prevents the command being run on a merged section
                    if (allSects[currSect].isMerged()) {
                        System.out.println(
                            "Command remove is not valid for merged sections");
                        break;
                    }
                    String remPID = "";
                    if (lineSpl.length == 2) {
                        remPID = allSects[currSect].remove(lineSpl[1]);
                    }
                    else if (lineSpl.length == 3) {
                        remPID = allSects[currSect].remove(lineSpl[1],
                            lineSpl[2]);
                    }
                    if (remPID != null) {
                        studManager.updateSection(remPID, -1);
                    }
                    break;
                }
                case grade: {
                    allSects[currSect].grade();
                    System.out.println("grading completed");
                    break;
                }
                case stat: {
                    // Holds the grade totals of the students
                    int[] result = allSects[currSect].stat();
                    System.out.println("Statistics of section " + Integer
                        .toString(currSect + 1) + ":");
                    // iterator through result
                    int j = 0;
                    while (j < result.length) {
                        if (result[j] > 0) {
                            System.out.println(Integer.toString(result[j])
                                + " students with grade " + gradeNames[j]);
                        }
                        j++;
                    }
                    break;
                }
                case dumpsection: {
                    System.out.println("Section " + Integer.toString(currSect
                        + 1) + " dump:");
                    allSects[currSect].dumpSection();
                    System.out.println("Size = " + Integer.toString(
                        allSects[currSect].getNumStudents()));
                    break;
                }
                case clearsection: {

                    allSects[currSect].clearSection();
                    if (studManager.getNumStud() != 0) {
                        studManager.clearSec(currSect + 1);
                    }
                    System.out.println("Section " + currSect + " cleared");
                    break;
                }
                case list: {
                    System.out.println("Students with grade " + lineSpl[1]
                        + " are:");
                    Student[] listed = allSects[currSect].list(lineSpl[1]);
                    int listCount = 0;
                    if (listed.length == 0) {
                        System.out.println("Found 0 students");
                        break;
                    }
                    while (listed[listCount] != null) {
                        System.out.println(listed[listCount].toString());
                        listCount++;
                    }
                    System.out.println("Found " + listCount + " students");
                    break;
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
                    break;
                }
                case merge: {
                    if (allSects[currSect].getNumStudents() != 0) {
                        System.out.println(
                            "Sections could only be merged to an empty section."
                                + " section " + Integer.toString(currSect + 1)
                                + " is not empty");
                    }
                    else {
                        mergeAll();
                        System.out.println("All sections merged at section "
                            + Integer.toString(currSect + 1));
                    }
                    break;
                }
                case savestudentdata: {
                    saveStudBin(lineSpl[1]);
                    System.out.println("Saved all Students data to "
                        + lineSpl[1]);
                    break;
                }
                case savecoursedata: {
                    saveCourseBin(lineSpl[1]);
                    System.out.println("Saved all course data to "
                        + lineSpl[1]);
                    break;
                }
                case clearcoursedata: {
                    saveCourseBin("courseDataBackupJHSH.data");
                    for (int i = 0; i < allSects.length; i++) {
                        allSects[i].clearSection();
                    }
                    studManager.resetStuMan();
                    System.out.println("All course data cleared.");
                    break;
                }
                default:
                    break;
            }
            if (!cmd.equals("insert") && !cmd.equals("search") && !cmd.equals(
                "searchid")) {
                isStud = false;
            }
        }
        file.close();
    }


    /**
     * Saves the current course data from all sections to a specified .data
     * file.
     * 
     * @param fileName
     *            is the target file to save to
     * @throws IOException
     *             when the RAF functions encounter unexpected behavior
     */
    private static void saveCourseBin(String fileName) throws IOException {
        // Opens the file of course data
        RandomAccessFile saveCourseFile = new RandomAccessFile(fileName, "rw");

        saveCourseFile.writeBytes("CS3114atVT");

        int sectIter = 0;
        while (allSects[sectIter].getNumStudents() != 0) {
            sectIter++;
        }
        saveCourseFile.writeInt(sectIter);

        for (int i = 0; i < sectIter; i++) {
            Section thisSection = allSects[i];
            int studentNum = thisSection.getNumStudents();
            saveCourseFile.writeInt(studentNum);
            for (int j = 0; j < studentNum; j++) {
                Student[] allStudents = thisSection.dumpCopy();
                Student curr = allStudents[j];
                // if (curr != null) {
                long newPID = Long.parseLong(curr.getID());
                saveCourseFile.writeLong(newPID);

                saveCourseFile.writeBytes((curr.getFirstName() + "$"));
                saveCourseFile.writeBytes((curr.getLastName() + "$"));

                saveCourseFile.writeInt(curr.getScore());
                if (curr.getGrade().length() < 2) {
                    curr.setGrade(curr.getGrade() + " ");
                }
                saveCourseFile.writeBytes(curr.getGrade());
                // }
            }
            saveCourseFile.writeBytes("GOHOKIES");
        }
        saveCourseFile.close();
    }


    /**
     * Saves the student data from the student manager to a target binary file.
     * 
     * @param fileName
     *            is the name of the file to be saved to
     * @throws IOException
     *             in case of the RAF encountering unexpected behavior.
     */
    private static void saveStudBin(String fileName) throws IOException {
        // Opens the file of student data
        RandomAccessFile saveStudFile = new RandomAccessFile(fileName, "rw");
        Student[] allStudents = studManager.copyStudents();

        saveStudFile.writeBytes("VTSTUDENTS");
        saveStudFile.writeInt(allStudents.length);

        for (int i = 0; i < allStudents.length; i++) {
            Student curr = allStudents[i];

            long newPID = Long.parseLong(curr.getID());
            saveStudFile.writeLong(newPID);

            saveStudFile.writeBytes(curr.getFirstName() + "$");
            saveStudFile.writeBytes(curr.getMiddleName() + "$");
            saveStudFile.writeBytes(curr.getLastName() + "$");

            saveStudFile.writeBytes("GOHOKIES");
        }
        saveStudFile.close();
    }


    /**
     * Resizes the internal array in this section to accommodate all the
     * sections to be merged into, sets this section to be a merged section, and
     * then calls a method to retrieve all students from the manager.
     * Once all the managed students are available, transfers the ones in a
     * section
     * into the new one by deep copying their data into a new student object
     * in the current merged section.
     */
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

        Student[] allStudents = studManager.copyStudents();
        for (int j = 0; j < allStudents.length; j++) {
            Student curr = allStudents[j];
            if (curr != null && curr.getSection() > 0) {
                allSects[currSect].insertNoText(curr.getID(), curr
                    .getFirstName(), curr.getMiddleName(), curr.getLastName(),
                    curr.getScore(), curr.getGrade(), curr.getSection());
            }
        }
    }


    /**
     * Parses the binary file holding course data
     * 
     * @param fileName
     *            is the name of the target binary file
     * @throws IOException
     *             in the event of unexpected behavior from the RAF
     */
    private static void parseCourseBin(String fileName) throws IOException {
        // Opens the file of course data
        RandomAccessFile binCourseFile = new RandomAccessFile(fileName, "r");
        // If the file is less than the initial string length, it is invalid
        // Stops IOExceptions from reading over the file end
        if (binCourseFile.length() < 10) {
            binCourseFile.close();
            return;
        }
        // System.out.println(binCourseFile.length());
        // Allocates the variables that make up the student so they don't have
        // to be reinitialized every loop
        String firstName;
        String lastName;
        long perID;
        String newPID;
        int scoreNum;
        String grade;
        // Retrieves the first line, "CS3114atVT"
        byte[] dLimit1 = new byte[10];
        binCourseFile.readFully(dLimit1);
        // Gets the number of students and iterates through each
        int numSects = binCourseFile.readInt();
        int line = 1;
        while (line <= numSects) {
            int stud = 0;
            int numStudents = binCourseFile.readInt();
            while (stud < numStudents) {
                // Retrieves the PID as a long and casts it to a string
                perID = binCourseFile.readLong();
                newPID = Long.toString(perID);
                while (newPID.length() < 9) {
                    newPID = "0" + newPID;
                }

                // Creates a byte array to hold exactly 1 character at a time
                // As the max length is specified, we could grab 16 characters
                // at
                // once but that is bad for generalization and has a chance of
                // reading past file end in the case of a short name in the last
                // line.
                byte[] next = new byte[1];
                binCourseFile.readFully(next);
                String nextStr = new String(next, StandardCharsets.UTF_8);
                // Sets the first name to an empty string, can be added to.
                firstName = nextStr;
                // Adds each subsequent character to the name until the
                // delimiter
                while (!(nextStr.equals("$"))) {

                    binCourseFile.readFully(next);
                    nextStr = new String(next, StandardCharsets.UTF_8);
                    firstName = firstName + nextStr;
                }
                firstName = firstName.substring(0, firstName.length() - 1)
                    .toLowerCase();

                // As above, repeated for last name
                next = new byte[1];
                binCourseFile.readFully(next);
                nextStr = new String(next, StandardCharsets.UTF_8);
                lastName = nextStr;
                while (!(nextStr.equals("$"))) {

                    binCourseFile.readFully(next);
                    nextStr = new String(next, StandardCharsets.UTF_8);
                    lastName = lastName + nextStr;
                }
                lastName = lastName.substring(0, lastName.length() - 1)
                    .toLowerCase();

                scoreNum = binCourseFile.readInt();

                byte[] gradeBytes = new byte[2];
                binCourseFile.readFully(gradeBytes);
                String gradeStr = new String(gradeBytes,
                    StandardCharsets.UTF_8);
                grade = gradeStr;

                int ident = studManager.checkIdentity(newPID, firstName,
                    lastName);
                if (ident == 0) {
                    Student tgt = studManager.searchStu(newPID);
                    if (tgt.getSection() == line) {
                        allSects[line - 1].updateStudentScore(newPID, scoreNum);
                        studManager.updateScore(newPID, scoreNum);
                    }
                    else if (tgt.getSection() == 0) {
                        allSects[line - 1].insertNoText(newPID, firstName, "",
                            lastName, scoreNum, grade, line);
                        studManager.updateSection(tgt.getID(), line);
                        studManager.updateScore(newPID, scoreNum);
                    }
                    else {
                        System.out.println("Warning: Student " + firstName + " "
                            + lastName + " is not loaded to section " + Integer
                                .toString(line)
                            + " since he/she is already enrolled "
                            + "in section " + Integer.toString(tgt
                                .getSection()));
                    }
                }
                else if (ident == 1) {
                    System.out.println("Warning: Student " + firstName + " "
                        + lastName + " is not loaded to section " + Integer
                            .toString(line) + " since he/she doesn't exist in "
                        + "the loaded student records.");
                }
                else if (ident == 2) {
                    System.out.println("Warning: Student " + firstName + " "
                        + lastName + " is not loaded to section " + Integer
                            .toString(line) + " since the corresponding pid "
                        + "belongs to another student.");
                }
                stud++;
            }
            // Retrieves GOHOKIES at the end of each section
            byte[] dLimit2 = new byte[8];
            binCourseFile.readFully(dLimit2);
            line++;
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
            String[] split = cLine.trim().split(",");
            for (int i = 0; i < split.length; i++) {
                split[i] = split[i].toLowerCase();
            }
            sectionID = Integer.parseInt(split[0]);
            score = Integer.parseInt(split[4]);
            String newID = split[1];
            while (newID.length() < 9) {
                newID = "0" + newID;
                System.out.println(newID);
            }
            int idCheck = studManager.checkIdentity(newID, split[2], split[3]);
            if (idCheck == 1) {
                System.out.println(split[2] + " " + split[3]
                    + " insertion failed. Wrong student information."
                    + " ID doesn't exist");
            }
            else if (idCheck == 2) {
                System.out.println(split[2] + " " + split[3]
                    + " insertion failed. Wrong student information."
                    + " ID belongs to another student");
            }
            else {

                tempStudent = studManager.searchStu(newID);
                if (tempStudent.getSection() == sectionID) {
                    tempStudent.setGrade(split[5]);
                    tempStudent.setScore(score);
                    studManager.updateScore(newID, score);
                }
                else if (tempStudent.getSection() <= 0) {
                    allSects[sectionID - 1].insertNoText(newID, split[2], "",
                        split[3], score, split[5], sectionID);
                    studManager.updateSection(newID, sectionID);
                    studManager.updateScore(newID, score);
                }
                else {
                    System.out.println("Warning: Student " + split[2] + " "
                        + split[3] + " is not loaded to section " + Integer
                            .toString(sectionID)
                        + " since he/she is already enrolled " + "in section "
                        + Integer.toString(tempStudent.getSection()));
                }
            }
        }
        cFile.close();
    }


    /**
     * Reads student data from a binary file using the initial header string
     * "VTSTUDENTS", a delimiter of '$' and a final string after each student of
     * "GOHOKIES"
     * 
     * @param fileName
     *            is the name of the binary file ending in ".data" (Ensured by
     *            the call in main)
     * @throws IOException
     *             when there is unexpected behavior from the RandomAccessFile.
     */
    private static void parseStudentBin(String fileName) throws IOException {
        // Opens the file of student data
        RandomAccessFile studDataFile = new RandomAccessFile(fileName, "r");
        // If the file is less than the initial string length, it is invalid
        // Stops IOExceptions from reading over the file end
        if (studDataFile.length() < 10) {
            studDataFile.close();
            return;
        }
        // Allocates the variables that make up the student so they don't have
        // to be reinitialized every loop
        String firstName;
        String middleName;
        String lastName;
        long perID;
        String newPID;
        // Retrieves the first line, "VTSTUDENTS"
        byte[] dLimit1 = new byte[10];
        studDataFile.readFully(dLimit1);
        // Gets the number of students and iterates through each
        int numStudents = studDataFile.readInt();
        int line = 0;
        while (line < numStudents) {
            // Retrieves the PID as a long and casts it to a string
            perID = studDataFile.readLong();
            newPID = Long.toString(perID);

            // Creates a byte array to hold exactly 1 character at a time
            // As the max length is specified, we could grab 16 characters at
            // once but that is bad for generalization and has a chance of
            // reading past file end in the case of a short name in the last
            // line.
            byte[] next = new byte[1];
            studDataFile.readFully(next);
            String nextStr = new String(next, StandardCharsets.UTF_8);
            // Sets the first name to its first character, can be added to.
            firstName = "";
            // Adds each subsequent character to the name until the delimiter
            while (!(nextStr.equals("$"))) {
                firstName = firstName + nextStr;
                studDataFile.readFully(next);
                nextStr = new String(next, StandardCharsets.UTF_8);
            }
            firstName = firstName.toLowerCase();

            // As above, repeated for middle name
            next = new byte[1];
            studDataFile.readFully(next);
            nextStr = new String(next, StandardCharsets.UTF_8);
            middleName = "";
            while (!(nextStr.equals("$"))) {
                middleName = middleName + nextStr;
                studDataFile.readFully(next);
                nextStr = new String(next, StandardCharsets.UTF_8);
            }
            middleName = middleName.toLowerCase();

            // As above, repeated for last name
            next = new byte[1];
            studDataFile.readFully(next);
            nextStr = new String(next, StandardCharsets.UTF_8);
            lastName = "";
            while (!(nextStr.equals("$"))) {
                lastName = lastName + nextStr;
                studDataFile.readFully(next);
                nextStr = new String(next, StandardCharsets.UTF_8);
            }
            lastName = lastName.toLowerCase();

            // Retrieves GOHOKIES at the end of each name
            byte[] dLimit2 = new byte[8];
            studDataFile.readFully(dLimit2);

            // Inserts the newly created student into StudentManager
            studManager.insert(newPID, firstName, middleName, lastName);

            line++;
        }
        studDataFile.close();
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
            String[] split = sLine.trim().split(",");
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
        section4 = new Section(4);
        section5 = new Section(5);
        section6 = new Section(6);
        section7 = new Section(7);
        section8 = new Section(8);
        section9 = new Section(9);
        section10 = new Section(10);
        section11 = new Section(11);
        section12 = new Section(12);
        section13 = new Section(13);
        section14 = new Section(14);
        section15 = new Section(15);
        section16 = new Section(16);
        section17 = new Section(17);
        section18 = new Section(18);
        section19 = new Section(19);
        section20 = new Section(20);
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
        gradeNames[11] = "f ";
    }
}
