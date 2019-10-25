import student.TestCase;

/**
 * Tests the methods of BinarySearchTree
 * 
 * @author Sam Robles <robleshs>
 * @author John Hoskinson <johnh98>
 * @version 9/22/2019
 * 
 * 
 */
public class BinarySearchTreeTest extends TestCase {

    /**
     * The tree being tested
     */
    private BinarySearchTree<String, Integer> bSTree;


    /**
     * Sets up the tests
     */
    public void setUp() {
        bSTree = new BinarySearchTree<String, Integer>();
    }


    /**
     * Tests isEmpty and insert
     */
    public void testIn() {
        assertTrue(bSTree.isEmpty());
        bSTree.insert("Test", 1);
        assertFalse(bSTree.isEmpty());
        Exception exception = null;
        try {
            bSTree.insert("Test", 1);
        }
        catch (DuplicateItemException e) {
            exception = e;
        }
        assertNotNull(exception);
        exception = null;
        try {
            bSTree.insert("Sest", 1);
        }
        catch (DuplicateItemException e) {
            exception = e;
        }
        assertNull(exception);
        try {
            bSTree.insert("Test", 2);
        }
        catch (DuplicateItemException e) {
            exception = e;
        }
        assertNull(exception);
        
        
    }


    /**
     * Tests remove
     */
    public void testRemove() {
        assertTrue(bSTree.isEmpty());
        bSTree.insert("Test", 1);
        bSTree.insert("Test1", 2);
        assertFalse(bSTree.isEmpty());
        bSTree.remove("Test", 1);
        bSTree.remove("Test1", 2);
        assertTrue(bSTree.isEmpty());
        for (int i = 0; i < 20; i++) {
            bSTree.insert("Test " + i, i);
        }
        bSTree.remove("Test 9", 9);
        bSTree.remove("Test 10", 10);
        bSTree.remove("Test 3", 3);
        Exception exception = null;
        try {
            bSTree.remove("Test", 10);
        }
        catch (ItemNotFoundException e) {
            exception = e;
        }
        assertNotNull(exception);
        exception = null;
        try {
            bSTree.remove("Test 10", 10);
        }
        catch (ItemNotFoundException e) {
            exception = e;
        }
        assertNotNull(exception);
    }


    /**
     * Tests findMin and findMax
     */
    public void testMinMax() {
        assertNull(bSTree.findMin());
        assertNull(bSTree.findMax());
        for (int i = 0; i < 5; i++) {
            bSTree.insert("" + i, i);
        }
        assertTrue(bSTree.findMax().equals(4));
        assertTrue(bSTree.findMin().equals(0));
        bSTree.remove("0", 0);
        assertTrue(bSTree.findMin().equals(1));
        bSTree.insert("0", 0);
        assertTrue(bSTree.findMin().equals(0));
    }


    /**
     * Tests makeEmpty
     */
    public void testMakeEmpty() {
        for (int i = 0; i < 5; i++) {
            bSTree.insert("" + i, i);
        }
        assertFalse(bSTree.isEmpty());
        bSTree.makeEmpty();
        assertTrue(bSTree.isEmpty());
    }


    /**
     * Tests find()
     */
    public void testFind() {
        for (int i = 1; i < 5; i++) {
            bSTree.insert("" + i, i);
        }
        bSTree.insert("0", 0);
        bSTree.insert("7", 2);
        assertTrue(bSTree.find("3", 3).equals(3));
        assertTrue(bSTree.find("0", 0).equals(0));
        assertNull(bSTree.find("6", 6));
        assertTrue(bSTree.find("7", 2).equals(2));
    }


    /**
     * Tests toString()
     */
    public void testToString() {
        String none = "";
        String full = "0\n1\n2\n";
        String strE = bSTree.toString();
        for (int i = 0; i < 3; i++) {
            bSTree.insert("Test " + i, i);
        }
        String strF = bSTree.toString();
        assertTrue(strE.equals(none));
        assertTrue(strF.equals(full));
    }
    
    /**
     * Tests the makeCopy method
     */
    public void testMakeCopy() {
        bSTree.insert("3", 3);
        bSTree.insert("2", 2);
        bSTree.insert("4", 4);
        Integer[] copies = new Integer[10];
        bSTree.makeCopy(copies);
        assertNull(copies[3]);
        int copied = copies[1];
        assertEquals(3, copied);
        
    }

}