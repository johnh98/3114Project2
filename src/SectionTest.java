import student.TestCase;

/**
 * 
 */

/**
 * Tests the methods of the Section class
 * 
 * @author Sam Robles <robleshs>
 * @version 10/22/19
 *
 */
public class SectionTest extends TestCase {

    private Section tester;


    /**
     * Sets up for the tests
     */
    public void setUp() {
        tester = new Section(0);
    }


    /**
     * Tests the insert method
     */
    public void testInsert() {
        // Inserts a student and shows that sizes update
        Student s1 = tester.insert("t1", "a", "b", "c", 20, "f", 0);
        assertEquals(1, tester.getSize());
        assertEquals(1, tester.getNumStudents());

        // Inserts a new student and shows that the sizes updates
        tester.insert("t2", "first", "mid", "last", 100, "a", 0);
        assertEquals(2, tester.getSize());
        assertEquals(2, tester.getNumStudents());

        // Tries to insert a duplicate (same pid), and shows that instead the
        // existing student is returned
        Student s3 = tester.insert("t1", "b", "b", "b", 20, "f", 0);
        assertEquals(2, tester.getSize());
        assertEquals(2, tester.getNumStudents());
        assertTrue(s1.equals(s3));

        tester.insertNoText("t3", "first", "mid", "last", 16, "grade", 0);
        tester.insertNoText("t3", "first", "mid", "last", 16, "grade", 0);

    }


    /**
     * Tests the searchID method
     */
    public void testSearchID() {
        // Inserts two new students
        Student s1 = tester.insert("t1", "a", "b", "c", 12, "f", 0);
        Student s2 = tester.insert("t2", "e", "f", "g", 13, "f", 0);

        // Searches the two inserted students and shows that they are searched
        // correctly
        Student comp = tester.searchId("t1");
        assertTrue(comp.equals(s1));
        comp = tester.searchId("t2");
        assertTrue(comp.equals(s2));
        // Tries to search a nonexistent student, which returns null
        comp = tester.searchId("t3");
        assertNull(comp);
    }


    /**
     * Tests the two methods that search names
     */
    public void testSearchNames() {
        // Sets us up by inserting students
        tester.insert("t1", "a", "b", "c", 12, "f", 0);
        tester.insert("t2", "e", "f", "g", 13, "f", 0);
        tester.insert("t3", "a", "d", "e", 24, "f", 0);
        tester.insert("t4", "e", "d", "g", 24, "f", 0);
        tester.insert("t5", "a", "d", "a", 24, "f", 0);

        // Tries to do a single name search on a name not present
        Student[] names = tester.search("z");
        // Shows that the resulting array is empty
        assertNull(names[0]);

        // Searches for a name that is in the array multiple times (as both
        // first and last name) then shows that all of the students with the
        // name are present
        names = tester.search("e");
        assertNull(names[3]);
        assertTrue(names[0].getID().equals("t3"));
        assertTrue(names[1].getID().equals("t4"));
        assertTrue(names[2].getID().equals("t2"));

        // Searches for a specific name that isn't in the section
        names = tester.search("e", "a");
        // Shows that resulting array is empty
        assertNull(names[0]);

        // Searches for a name that is in the array, and shows that students
        // with that name are in the resulting array
        names = tester.search("e", "g");
        assertNull(names[2]);
        assertTrue(names[0].getID().equals("t4"));
        assertTrue(names[1].getID().equals("t2"));

    }


    /**
     * Tests the two remove methods
     */
    public void testRemove() {
        // Set up (students inserted)
        tester.insert("t1", "a", "b", "c", 12, "f", 0);
        tester.insert("t2", "e", "f", "g", 13, "f", 0);
        tester.insert("t3", "a", "d", "e", 24, "f", 0);
        tester.insert("t4", "e", "d", "g", 24, "f", 0);
        tester.insert("t5", "a", "d", "a", 24, "f", 0);
        // Check current size of section
        assertEquals(5, tester.getNumStudents());
        // Try to remove nonexistent student and show that size doesn't change
        // (no removal)
        tester.remove("t0");
        assertEquals(5, tester.getNumStudents());
        // Removes the student with pid t1 using remove(pid) then shows that
        // student isn't in section and size updates
        tester.remove("t1");
        assertEquals(4, tester.getNumStudents());
        assertNull(tester.searchId("t1"));

        // Tries to remove student with name that isn't in section
        tester.remove("a", "g");
        assertEquals(4, tester.getNumStudents());
        // Try to remove student with a name that is shared by multiple
        // students, which results in no removal (students are still searchable)
        tester.remove("e", "g");
        assertEquals(4, tester.getNumStudents());
        assertTrue(tester.search("e", "g")[0].getID().equals("t4"));
        assertTrue(tester.search("e", "g")[1].getID().equals("t2"));
        // Removes a student who has a name that only appears once, which allows
        // it to be removed (size updated and student not searchable)
        tester.remove("a", "a");
        assertEquals(3, tester.getNumStudents());
        assertNull(tester.searchId("t5"));

    }


    /**
     * Tests the multiple merge related methods
     */
    public void testMergeMethods() {
        // For the most part, just ensures that nothing crashes/no errors.
        // Nothing is returned here
        assertFalse(tester.isMerged());
        tester.setUpMerge(200);
        tester.setMerge(true);
        assertTrue(tester.isMerged());
        tester.setUpMerge(200);
    }


    /**
     * Tests the clearSection method
     */
    public void testClearSection() {
        // Sets up by inserting students
        tester.insert("t1", "a", "b", "c", 12, "f", 0);
        tester.insert("t2", "e", "f", "g", 13, "f", 0);
        tester.insert("t3", "a", "d", "e", 24, "f", 0);
        tester.insert("t4", "e", "d", "g", 24, "f", 0);
        tester.insert("t5", "a", "d", "a", 24, "f", 0);
        // Checks size
        assertEquals(tester.getNumStudents(), 5);

        // Runs clear section, then shows that the section has been cleared
        // (size and numstudents are zero and students are no longer searchable)
        tester.clearSection();
        assertEquals(tester.getNumStudents(), 0);
        assertEquals(tester.getSize(), 0);
        assertNull(tester.searchId("t3"));
    }


    /**
     * Tests dumpsection. As this method just prints to console, it's mostly
     * useful for checking that the output to console matches expected output
     */
    public void testDumpSection() {
        // Dump on empty
        tester.dumpSection();

        // Insert students
        tester.insert("t1", "a", "b", "c", 12, "f", 0);
        tester.insert("t2", "e", "f", "g", 13, "f", 0);
        tester.insert("t3", "a", "d", "e", 14, "f", 0);
        tester.insert("t4", "e", "d", "g", 15, "f", 0);
        tester.insert("t5", "a", "d", "a", 16, "f", 0);
        // Full dumpsection
        tester.dumpSection();
        assertEquals(5, tester.getSize());

    }


    /**
     * Tests the grade and stat methods
     */
    public void testStatGrade() {
        // Insert a student at all grade level
        tester.insert("t1", "a", "b", "c", 49, "f", 0);
        tester.insert("t2", "e", "f", "g", 50, "f", 0);
        tester.insert("t3", "a", "d", "e", 53, "f", 0);
        tester.insert("t4", "e", "d", "g", 55, "f", 0);
        tester.insert("t5", "a", "d", "a", 58, "f", 0);
        tester.insert("t6", "a", "d", "a", 60, "f", 0);
        tester.insert("t7", "a", "d", "a", 65, "f", 0);
        tester.insert("t8", "a", "d", "a", 70, "f", 0);
        tester.insert("t9", "a", "d", "a", 75, "f", 0);
        tester.insert("t10", "a", "d", "a", 80, "f", 0);
        tester.insert("t11", "a", "d", "a", 85, "f", 0);
        tester.insert("t12", "a", "d", "a", 90, "f", 0);
        tester.insert("t13", "a", "d", "a", 100, "f", 0);
        tester.insert("t14", "a", "d", "a", 100, "f", -1);

        // Grade the students
        tester.grade();

        // Run stat on the section, then show that all students have been
        // correctly tallied in the array
        int[] stats = tester.stat();
        assertEquals(2, stats[0]);
        assertEquals(1, stats[1]);
        assertEquals(1, stats[2]);
        assertEquals(1, stats[3]);
        assertEquals(1, stats[4]);
        assertEquals(1, stats[5]);
        assertEquals(1, stats[6]);
        assertEquals(1, stats[7]);
        assertEquals(1, stats[8]);
        assertEquals(1, stats[9]);
        assertEquals(1, stats[10]);
        assertEquals(1, stats[11]);

        // Shows that the previous grade command updated student grades (except
        // for 14, who is tombstoned and therefore is not updated)
        assertTrue(tester.searchId("t14").getGrade().equals("f"));
        assertTrue(tester.searchId("t12").getGrade().equals("A"));
        assertTrue(tester.searchId("t2").getGrade().equals("D-"));
    }


    /**
     * Tests the updateStudentScore method
     */
    public void testUpdateStudentScore() {
        // Inserts students in order to set up for tests
        tester.insert("t1", "a", "b", "c", 12, "f", 0);
        tester.insert("t2", "e", "f", "g", 13, "f", 0);
        tester.insert("t3", "a", "d", "e", 14, "f", 0);
        tester.insert("t4", "e", "d", "g", 15, "f", 0);
        tester.insert("t5", "a", "d", "a", 16, "f", 0);

        // Pulls a student and checks their initial score
        Student found = tester.searchId("t2");
        assertEquals(found.getScore(), 13);

        // Updates the score of a nonexistant student (shows that no error
        // occurs, it just does nothing)
        tester.updateStudentScore("t6", 99);

        // Updates the previously pulled student's score, then shows that its
        // been updated by checking its value. Also checks to ensure that the
        // BST is updated by running dumpSection
        tester.updateStudentScore("t2", 1);
        assertEquals(found.getScore(), 1);
        tester.dumpSection();
    }


    /**
     * Tests the list method
     */
    public void testList() {
        // Sets up by inserting students
        tester.insert("t1", "a", "b", "c", 49, "f", 0);
        tester.insert("t2", "e", "f", "g", 50, "f", 0);
        tester.insert("t3", "a", "d", "e", 78, "f", -1);
        tester.insert("t4", "e", "d", "g", 77, "f", 0);
        tester.insert("t5", "a", "d", "a", 76, "f", 0);
        tester.insert("t6", "a", "d", "a", 80, "f", 0);
        tester.insert("t7", "a", "d", "a", 85, "f", 0);
        tester.insert("t8", "a", "d", "a", 83, "f", 0);
        tester.insert("t9", "a", "d", "a", 75, "f", 0);
        tester.insert("t10", "a", "d", "a", 80, "f", 0);
        tester.insert("t11", "a", "d", "a", 84, "f", 0);
        tester.insert("t12", "a", "d", "a", 83, "f", 0);
        tester.insert("t13", "a", "d", "a", 88, "f", 0);
        tester.insert("t14", "a", "d", "a", 82, "f", -1);

        // grades them to update grades
        tester.grade();

        // Tests for single letter grades
        Student[] list = tester.list("B");
        assertNull(list[3]);
        assertTrue(list[0].getID().equals("t4"));
        assertTrue(list[1].getID().equals("t5"));
        assertTrue(list[2].getID().equals("t9"));

        // Tests for Grade* grades (where all grades with +,-," " are desired)
        list = tester.list("B*");

        assertNull(list[8]);
        assertTrue(list[0].getID().equals("t4"));
        assertTrue(list[1].getID().equals("t5"));
        assertTrue(list[2].getID().equals("t6"));
        assertTrue(list[5].getID().equals("t10"));
        assertTrue(list[6].getID().equals("t11"));
        assertTrue(list[7].getID().equals("t12"));

        // Tests for +/- grades
        list = tester.list("B+");

        assertNull(list[5]);
        assertTrue(list[1].getID().equals("t8"));
        assertTrue(list[2].getID().equals("t10"));
        assertTrue(list[4].getID().equals("t12"));

    }


    /**
     * Tests findPair
     */
    public void testFindPair() {
        // Inserts students to be tested on
        tester.insert("t1", "a", "b", "c", 12, "f", 0);
        tester.insert("t2", "e", "f", "g", 13, "f", 0);
        tester.insert("t3", "a", "d", "e", 14, "f", 0);
        tester.insert("t4", "e", "d", "g", 15, "f", 0);
        tester.insert("t5", "a", "d", "a", 30, "f", 0);
        tester.insert("t6", "a", "b", "c", 40, "f", 0);
        tester.insert("t7", "e", "f", "g", 13, "f", -1);
        tester.insert("t8", "a", "d", "e", 14, "f", 0);

        // Generates the string from findPair (with difference 1)
        String pairs = tester.findPair(1);
        // String to be compared to
        String comp =
            "a c, e g\ne g, a e\ne g, a e\na e, e g\na e, a e\ne g, a e\nfound 6 pairs\n";
        // Compares strings to show that findpair generates correct string
        assertTrue(pairs.equals(comp));
    }
}
