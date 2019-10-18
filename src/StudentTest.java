import student.TestCase;

/**
 * Tests all of the methods of the Student class
 * 
 * @author Sam Robles <robleshs>
 * @version 9/25/2019
 *
 */
public class StudentTest extends TestCase {

    /**
     * Student we are using to test our methods
     */
    private Student tester;


    /**
     * Sets up the tests
     */
    public void setUp() {
        tester = new Student("ID", "first", "mid", "last", 0, "F");
    }


    /**
     * Tests getGrade, setGrade, and the other getters
     */
    public void testGettersSetters() {
        assertEquals(tester.getScore(), 0);
        tester.setScore(100);
        assertEquals(tester.getScore(), 100);

        assertTrue(tester.getID().equals("ID"));
        assertTrue(tester.getFirstName().equals("first"));
        assertTrue(tester.getLastName().equals("last"));

        assertTrue(tester.getGrade().equals("F"));
        tester.setGrade("A");
        assertTrue(tester.getGrade().equals("A"));

    }


    /**
     * Tests toString for student
     */
    public void testToString() {
        String testString = tester.toString();
        assertTrue(testString.equals("ID, first last, score = 0"));

        Student test2 = new Student("0000", "John", null, null, 0, "B");
        test2.setScore(50);
        String testString2 = test2.toString();
        assertTrue(testString2.equals("0000, John null, score = 50"));
    }


    /**
     * Tests the student compareTo method
     */
    public void testCompareTo() {
        Student test1 = new Student("0002", "Bart", "b", "Simpson", 3, "A");
        Student test2 = new Student("0002", "Bart", "b", "Simpson", 2, "B");
        Student test3 = new Student("0001", "OJ", "juice", "Simpson", 1, "C");
        Student test4 = new Student("0003", "Nikola", "and", "Bart", 0, "D");

        assertEquals(0, test1.compareTo(test2));
        assertTrue(0 < test1.compareTo(test3));
        assertTrue(0 > test1.compareTo(test4));
    }

}
