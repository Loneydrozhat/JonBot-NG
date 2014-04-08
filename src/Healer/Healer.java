package Healer;

import GUI.GUI;

public class Healer {
    
    //Health variables
    private boolean needHpHigh;
    private boolean needHpLow;
    //Mana variables
    private boolean needMpHigh;
    private boolean needMpLow;
    //Timing variables
    private boolean canCast;
    private boolean canPot;

    /*
    Constructor for the healer. Initializes variables.
    */
    public Healer(){
        needHpHigh = false;
        needHpLow = false;
        
        needMpHigh = false;
        needMpLow = false;
        
        canCast = true;
        canPot = true;
    }
    
    /*
    Brain of the healer.
    */
    public static void heal() {
        
        checkHealth();
        checkMana();
        restoreHealth();
        restoreMana();
        
    }
    
    /*
    Checks the players current health and compares it to their set healing
    percentages. 
    */
    private static void checkHealth() {
    }

    /*
    Checks the players currentMana, comparing it to their set restore %.
    */
    private static void checkMana() {
    }

    /*
    Restores health if checkHealth decided it was neccessary.
    */
    private static void restoreHealth() {
    }

    /*
    Restores mana if checkMana decided it was neccessary.
    */
    private static void restoreMana() {
    }
    
}
