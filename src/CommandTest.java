import student.TestCase;

/**
 * 
 */

/**
 * Tests the method from command enum
 * 
 * @author Sam Robles <robleshs>
 * @version 10/25/19
 *
 */
public class CommandTest extends TestCase {

    /**
     * Tests the setCommand method
     */
    public void testSetCommand() {
        Command cmd = null;
        cmd = Command.setCommand("clearcoursedata");
        assertEquals(Command.clearcoursedata, cmd);

        cmd = Command.setCommand("clearsection");
        assertEquals(Command.clearsection, cmd);

        cmd = Command.setCommand("dumpsection");
        assertEquals(Command.dumpsection, cmd);

        cmd = Command.setCommand("findpair");
        assertEquals(Command.findpair, cmd);

        cmd = Command.setCommand("grade");
        assertEquals(Command.grade, cmd);

        cmd = Command.setCommand("insert");
        assertEquals(Command.insert, cmd);

        cmd = Command.setCommand("list");
        assertEquals(Command.list, cmd);

        cmd = Command.setCommand("loadcoursedata");
        assertEquals(Command.loadcoursedata, cmd);

        cmd = Command.setCommand("loadstudentdata");
        assertEquals(Command.loadstudentdata, cmd);

        cmd = Command.setCommand("merge");
        assertEquals(Command.merge, cmd);

        cmd = Command.setCommand("remove");
        assertEquals(Command.remove, cmd);

        cmd = Command.setCommand("savecoursedata");
        assertEquals(Command.savecoursedata, cmd);

        cmd = Command.setCommand("savestudentdata");
        assertEquals(Command.savestudentdata, cmd);

        cmd = Command.setCommand("score");
        assertEquals(Command.score, cmd);

        cmd = Command.setCommand("search");
        assertEquals(Command.search, cmd);

        cmd = Command.setCommand("searchid");
        assertEquals(Command.searchid, cmd);

        cmd = Command.setCommand("section");
        assertEquals(Command.section, cmd);

        cmd = Command.setCommand("stat");
        assertEquals(Command.stat, cmd);

        cmd = Command.setCommand(" ");
        assertEquals(Command.none, cmd);

    }
}
