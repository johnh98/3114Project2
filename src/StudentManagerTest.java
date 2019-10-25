import student.TestCase;

/**
 * 
 */

/**
 * This class tests the methods of the StudentManager class
 * 
 * @author Samuel Robles-Hinckley <robleshs>
 * @version 10/21/19
 *
 */
public class StudentManagerTest extends TestCase {

    private StudentManager tester;


    /**
     * Sets up for the tests methods
     */
    public void setUp() {
        tester = new StudentManager();
    }


    /**
     * This method tests the insert method of StudentManager
     */
    public void testInsert() {
        // Insert into tester
        tester.insert("t1", "a", "b", "c");
        // Show that it rejects duplicates
        Exception exception = null;
        try {
            tester.insert("t1", "a", "b", "c");
        }
        catch (DuplicateItemException e) {
            exception = e;
        }
        assertNotNull(exception);
        // Show that it accepts new student
        exception = null;
        try {
            tester.insert("t2", "d", "e", "f");
        }
        catch (DuplicateItemException e) {
            exception = e;
        }
        assertNull(exception);

        // Shows that it still contains first entry as it still rejects
        // duplicated
        exception = null;
        try {
            tester.insert("t1", "a", "b", "c");
        }
        catch (DuplicateItemException e) {
            exception = e;
        }
        assertNotNull(exception);
        // Show rejection of new element duplicate
        exception = null;
        try {
            tester.insert("t2", "d", "e", "f");
        }
        catch (DuplicateItemException e) {
            exception = e;
        }
        assertNotNull(exception);
    }


    /**
     * Tests studentManager's search method
     */
    public void testSearchStu() {
        Student found = tester.searchStu("t1");
        assertNull(found);

        tester.insert("t1", "a", "b", "c");
        found = tester.searchStu("t1");
        assertTrue(found.getFirstName().equals("a"));
        assertTrue(found.getLastName().equals("c"));

        found = tester.searchStu("t2");
        assertNull(found);
    }


    /**
     * This method tests the update score and section methods
     */
    public void testUpdates() {
        tester.updateScore("t1", 20);
        tester.updateSection("t1", 1);

        tester.insert("t1", "a", "b", "b");
        Student found = tester.searchStu("t1");
        assertEquals(found.getScore(), 0);
        assertTrue(found.getGrade().equals("F"));
        assertEquals(found.getSection(), 0);

        tester.updateScore("t1", 75);
        tester.updateSection("t1", 3);
        assertEquals(found.getSection(), 3);
        assertTrue(found.getGrade().equals("B"));
        assertEquals(found.getScore(), 75);
    }


    /**
     * This method tests the checkIdentity method of StudentManager
     */
    public void testCheckIdentity() {
        tester.insert("t1", "a", "b", "c");
        // Checks when pid doesn't exist
        int check = -1;
        check = tester.checkIdentity("t2", "a", "c");
        assertEquals(1, check);

        // Check when first name is wrong
        check = tester.checkIdentity("t1", "b", "c");
        assertEquals(2, check);
        // Checks when last name is wrong
        check = -1;
        check = tester.checkIdentity("t1", "a", "a");
        assertEquals(2, check);
        // Checks when both are wrong
        check = -1;
        check = tester.checkIdentity("t1", "c", "a");
        assertEquals(2, check);
        // Checks when identity is correct
        check = tester.checkIdentity("t1", "a", "c");
        assertEquals(0, check);

    }

}
