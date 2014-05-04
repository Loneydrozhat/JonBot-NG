package CaveBot;

public class User {

    private static User instance;

    /*
     Private constructor for singleton.
     */
    private User() {

    }

    /*
     Returns the singleton User
     */
    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    /*
     Set the walker location to near target position and when next to it,
     right click at the location.
     */
    public void useAt(int xLoc, int yLoc) {

    }

    void use() {
    }
}
