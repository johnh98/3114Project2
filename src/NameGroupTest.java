import student.TestCase;

/**
 * Tests the methods of the NameGroup class
 * 
 * @author Samuel Robles <robleshs>
 * @version 10/15/19
 *
 */
public class NameGroupTest extends TestCase {

    /**
     * The NameGroup we're running tests on
     */
    private NameGroup tester;


    /**
     * Sets up for the tests
     */
    public void setUp() {
        tester = new NameGroup("B", "B");
    }


    /**
     * Tests the getters
     */
    public void testGetters() {
        assertTrue(tester.getFirst().equals("B"));
        assertTrue(tester.getLast().equals("B"));
    }


    /**
     * Tests the compareTo method
     */
    public void testCompare() {

        // Tests comparisons of same object
        assertEquals(0, tester.compareTo(tester));

        // Sets up next few cases
        NameGroup test2 = new NameGroup("B", "B");
        NameGroup test3 = new NameGroup("B", "C");
        NameGroup test4 = new NameGroup("B", "A");
        NameGroup test5 = new NameGroup("A", "A");
        NameGroup test6 = new NameGroup("C", "C");

        // Tests for various comparison cases
        assertEquals(0, tester.compareTo(test2));
        assertTrue(0 > tester.compareTo(test3));
        assertTrue(0 < tester.compareTo(test4));
        assertTrue(0 < tester.compareTo(test5));
        assertTrue(0 > tester.compareTo(test6));

    }
}
