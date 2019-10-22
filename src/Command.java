
/**
 * An enumerated type to define the commands for the switch in Coursemanager2
 * 
 * @author John Hoskinson<johnh98>
 *         Version 10.22.19
 */
public enum Command {
    loadstudentdata,
    loadcoursedata,
    section,
    insert,
    searchid,
    search,
    score,
    remove,
    grade,
    stat,
    dumpsection,
    clearsection,
    list,
    findpair,
    merge,
    savestudentdata,
    savecoursedata,
    clearcoursedata,
    none;

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
