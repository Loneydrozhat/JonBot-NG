package CaveBot;

import GUI.GUI;

public class CaveBot {

    //script objects
    String[] script;
    int currentScriptLine = 0;
    int finalScriptLine = 0;

    private boolean looting = false;
    private boolean attacking = false;
    private boolean talking = false;
    private boolean walking = true;
    private boolean using = false;

    

    private final ScriptInterpreter interpreter;
    private final Walker walker;
    private final Targeting targeting;
    private final Talker talker;
    private final User user;

    private static CaveBot instance = null;

    /*
     CaveBot constructor. Initializes variables.
     */
    private CaveBot() {
        talker = Talker.getInstance();
        user = User.getInstance();
        walker = Walker.getInstance();
        interpreter = ScriptInterpreter.getInstance();
        targeting = Targeting.getInstance();

        
    }

    /*
     Returns the singleton Cavebot instance
     */
    public static CaveBot getInstance() {
        if (instance == null) {
            instance = new CaveBot();
        }
        return instance;
    }

    /*
     Brain of the bot. Finds a target if available, attacks it, and if it cant do
     the previous two, it moves.
     */
    public void bot() {
        
        if (!GUI.caveBotIsPaused) {//If the cavebot isnt paused, do stuff
            interpretLine();
            doAction();
        }
    }

    /*
     Reads the current line of the script and acts accordingly
     */
    private void interpretLine() {
        interpreter.interpret(script[currentScriptLine]);
    }

    /*
     Act upon the interpreted line.
     */
    private void doAction() {
        targeting.findTarget();
        if (GUI.spellAttackCheck.isSelected()) {
            targeting.attackTarget();
        }

        if (!looting && !attacking) {
            //talker.talk();
            //user.use();
            walker.move();
        }
    }

    /*
     Resets the caveBot and sets the script to whatever is loaded into
     the GUI's caveScriptingArea
     */
    public void setScript() {
        resetBot();
        walker.resetActions();
        script = GUI.caveScriptArea.getText().split("\n");
        finalScriptLine = script.length - 1;
    }

    /*
     Resets everything to it's base state.
     */
    private void resetBot() {
        if (!GUI.caveBotIsPaused) {
            GUI.caveBotIsPaused = true;
        }
        currentScriptLine = 0;
        finalScriptLine = 0;
        script = null;
    }

    /*
     Move us to the next script line.
     */
    public void nextLine() {
        currentScriptLine = currentScriptLine + 1;
        if (currentScriptLine > finalScriptLine) {
            currentScriptLine = 0;
        }
        interpretLine();
    }

    /*
     Sets walking to isWalking
     */
    public void setWalking(boolean isWalking) {
        walking = isWalking;
    }

    /*
     Returns walking
     */
    public boolean isWalking() {
        return walking;
    }

    /*
     Sets attacking to isAttacking
     */
    public void setAttacking(boolean isAttacking) {
        attacking = isAttacking;
    }

    /*
     Returns attacking
     */
    public boolean isAttacking() {
        return attacking;
    }

    /*
     Sets looting to isLooting
     */
    public void setLooting(boolean isLooting) {
        looting = isLooting;
    }

    /*
     Returns looting
     */
    public boolean isLooting() {
        return looting;
    }

    /*
     Sets talking to isTalking
     */
    public void setTalking(boolean isTalking) {
        talking = isTalking;
    }

    /*
     Returns talking
     */
    public boolean isTalking() {
        return talking;
    }

    /*
     Sets using to isUsing
     */
    public void setUsing(boolean isUsing) {
        using = isUsing;
    }

    /*
     Returns using
     */
    public boolean isUsing() {
        return using;
    }
}
