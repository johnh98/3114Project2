
/**
 * An enumerated type to define the commands for the switch in Coursemanager2
 * 
 * @author John Hoskinson<johnh98>
 * @version 10.22.19
 */
public enum Command {

    /**
     * command for loadstudentdata
     */
    loadstudentdata,
    /**
     * command for loadcoursedata
     */
    loadcoursedata,
    /**
     * command for section
     */
    section,
    /**
     * command for insert
     */
    insert,
    /**
     * command for searchid
     */
    searchid,
    /**
     * command for search
     */
    search,
    /**
     * command for score
     */
    score,
    /**
     * command for remove
     */
    remove,
    /**
     * command for grade
     */
    grade,
    /**
     * command for stat
     */
    stat,
    /**
     * command for dumpsection
     */
    dumpsection,
    /**
     * command for clearsection
     */
    clearsection,
    /**
     * command for list
     */
    list,
    /**
     * command for findpair
     */
    findpair,
    /**
     * command for merge
     */
    merge,
    /**
     * command for savestudentdata
     */
    savestudentdata,
    /**
     * command for savecoursedata
     */
    savecoursedata,
    /**
     * command for clearcoursedata
     */
    clearcoursedata,
    /**
     * command for no command
     */
    none;

    /**
     * Takes a string and returns a command enum
     * 
     * @param cmdName
     *            the commands name as a string
     * @return the command enum that matches
     */
    public static Command setCommand(String cmdName) {
        Command cmd;
        if (cmdName.equals("loadstudentdata")) {
            cmd = loadstudentdata;
        }
        else if (cmdName.equals("loadcoursedata")) {
            cmd = loadcoursedata;
        }
        else if (cmdName.equals("section")) {
            cmd = section;
        }
        else if (cmdName.equals("insert")) {
            cmd = insert;
        }
        else if (cmdName.equals("searchid")) {
            cmd = searchid;
        }
        else if (cmdName.equals("search")) {
            cmd = search;
        }
        else if (cmdName.equals("score")) {
            cmd = score;
        }
        else if (cmdName.equals("remove")) {
            cmd = remove;
        }
        else if (cmdName.equals("grade")) {
            cmd = grade;
        }
        else if (cmdName.equals("stat")) {
            cmd = stat;
        }
        else if (cmdName.equals("dumpsection")) {
            cmd = dumpsection;
        }
        else if (cmdName.equals("clearsection")) {
            cmd = clearsection;
        }
        else if (cmdName.equals("list")) {
            cmd = list;
        }
        else if (cmdName.equals("findpair")) {
            cmd = findpair;
        }
        else if (cmdName.equals("merge")) {
            cmd = merge;
        }
        else if (cmdName.equals("savestudentdata")) {
            cmd = savestudentdata;
        }
        else if (cmdName.equals("savecoursedata")) {
            cmd = savecoursedata;
        }
        else if (cmdName.equals("clearcoursedata")) {
            cmd = clearcoursedata;
        }
        else {
            cmd = none;
        }

        return cmd;
    }
}
