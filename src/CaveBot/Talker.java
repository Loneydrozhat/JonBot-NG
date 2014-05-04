package CaveBot;

public class Talker {

    private static Talker instance = null;

    private String sayThis = "";

    /*
     Private constructor for singleton.
     */
    private Talker() {
    }

    /*
     Returns the singleton Talker
     */
    public static Talker getInstance() {
        if (instance == null) {
            instance = new Talker();
        }
        return instance;
    }

    /*
     Sets saythis to provided string.
     */
    public void setSay(String string) {
        sayThis = string;
    }

    /*
     Types out sayThis to the client and sets sayThis to "" upon completion.
     */
    public void talk() {
        if (!sayThis.equals("")) {
            CaveBot.getInstance().setTalking(true);
            typeThis(sayThis);
        }
    }

    /*
     Types the specified string and upon completion, resets it and sets talking
     to false.
     */
    private void typeThis(String typeThis) {
        sayThis = "";
        CaveBot.getInstance().setTalking(false);
    }

}
