package CaveBot;

public class ScriptInterpreter {

    private static ScriptInterpreter instance = null;
    private Walker walker;
    private Talker talker;
    private User user;

    /*
     Private constructor for singleton creation
     */
    private ScriptInterpreter() {
        walker = Walker.getInstance();
        talker = Talker.getInstance();
        user = User.getInstance();
    }

    /*
     Returns the one instance of ScriptInterpreter
     */
    public static ScriptInterpreter getInstance() {
        if (instance == null) {
            instance = new ScriptInterpreter();
        }
        return instance;
    }

    /*
     Interprets the line and passes the commands on to the Walker class
     */
    public void interpret(String string) {
        String[] line = string.split(",");

        //Movement waypoint
        if (line[0].equals("MOVE")) {
            int xLoc = Integer.valueOf(line[1]);
            int yLoc = Integer.valueOf(line[2]);

//            System.out.println("setting location for movement to : " + xLoc + "," + yLoc);
            walker.setDestination(xLoc, yLoc);
            CaveBot.getInstance().setWalking(true);
        }

        //Use wayPoint
        if (line[0].equals("USE")) {
            int xLoc = Integer.valueOf(line[1]);
            int yLoc = Integer.valueOf(line[2]);

//            System.out.println("setting location for use to : " + xLoc + "," + yLoc);
            CaveBot.getInstance().setUsing(true);
            user.useAt(xLoc, yLoc);
        }

        //Say command
        if (line[0].equals("SAY")) {
//            System.out.println("setting saying : " + line[1]);

            talker.setSay(line[1]);
            CaveBot.getInstance().setTalking(false);
        }
    }
}
