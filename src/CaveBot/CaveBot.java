package CaveBot;

import GUI.GUI;

public class CaveBot {

    /*
    CaveBot constructor. Initializes variables.
    */
    public CaveBot(){
        
    }
    
    /*
    Brain of the bot. Finds a target if available, attacks it, and if it cant do
    the previous two, it moves.
    */
    public static void bot() {
        target();
        attack();
        move();
    }

    /*
    Finds a targetable, in range monster.
    */
    private static void target() {
    }

    /*
    Attacks the previously targeted monster, if one was found.
    */
    private static void attack() {
    }

    /*
    If no monster is being attacked, moves the player towards the next
    waypoint.
    */
    private static void move() {
    }
    
}
