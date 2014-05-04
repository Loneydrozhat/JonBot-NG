package CaveBot;

import Core.ZezeniaHandler;
import java.awt.event.KeyEvent;

public class Walker {

    private static Walker instance = null;
    private final ZezeniaHandler reader;

    //memory objects for better movement logic
    private boolean triedMovingX = false;
    private boolean triedMovingY = false;
    public static long lastMovementTime;

    //current direction of movement
    private boolean movingWest = false;
    private boolean movingEast = false;
    private boolean movingNorth = false;
    private boolean movingSouth = false;

    //timing objects controlling the speed of movement attempts
    //too low a value results in keypresses being lost and not reaching the client
    //too high results in slow movement of the character.
    private int movementDelay = 100;
    private final int robotDelay = 10;

    //the location to move to
    private int xDestination = 0;
    private int yDestination = 0;

    //the location of the player when movement is attempted.
    //used to see if progress has been made
    private int lastLocationX = 0;
    private int lastLocationY = 0;

    /*
     Private contructor to facilitate singleton construction
     */
    private Walker() {
        reader = ZezeniaHandler.getInstance();
    }

    /*
     Returns the singleton instance of Walker
     */
    public static Walker getInstance() {
        if (instance == null) {
            instance = new Walker();
        }
        return instance;
    }

    /*
     Sets the destination for the player.
     Generally the destination is the next waypoint, but can also be a monster's
     position.
     */
    public void setDestination(int xLoc, int yLoc) {
        xDestination = xLoc;
        yDestination = yLoc;
    }

    /*
     Moves the player closer to the destination location, and when reached,
     updates the caveBot to the next script line.
     */
    public void move() {
        //first, see if previous movement has gotten us to our destination
        //if so, update destination location
        if (at(xDestination, yDestination)) {
            CaveBot.getInstance().nextLine();
        }

        //if it has been long enough since the last movement attempt
        if (System.currentTimeMillis() - lastMovementTime > movementDelay) {
            //update the movement booleans incase progress has been made
            updateAttempts();

            //update the movement time
            lastMovementTime = System.currentTimeMillis();

            //save the robots delay to be set back afterwards
            //and set new delay
            int oldDelay = reader.robot.getAutoDelay();
            reader.robot.setAutoDelay(robotDelay);

            //now move as needed
            moveX();
            moveY();
            
            //after moving, set the old delay back
            reader.robot.setAutoDelay(oldDelay);
        }
    }

    /*
     Updates the movement booleans setting them to false if progress has been made since
     the last movement attempt.
     */
    private void updateAttempts() {
        //if we have moved
        if (triedMovingX || triedMovingY) {
            //and if we have made ANY progress, reset states
            if (lastLocationX != reader.getXCoord() || lastLocationY != reader.getYCoord()) {
                lastLocationX = reader.getXCoord();
                lastLocationY = reader.getYCoord();
                triedMovingX = false;
                triedMovingY = false;
                reader.robot.waitForIdle();
            }
        }
    }

    /*
     Move the player up or down depending on the location
     */
    private void moveY() {
        if (yDestination - reader.getYCoord() > 0) {
            System.out.println("doing action y");
            lastLocationY = reader.getYCoord();
            triedMovingY = true;
            //stop moving in other directions
            if (movingNorth) {
                reader.robot.keyRelease(KeyEvent.VK_UP);
                movingNorth = false;
                //start moving in new direction
                reader.robot.keyPress(KeyEvent.VK_DOWN);
                movingSouth = true;
                return;
            }
            if (movingWest) {
                reader.robot.keyRelease(KeyEvent.VK_LEFT);
                movingWest = false;
                //start moving in new direction
                reader.robot.keyPress(KeyEvent.VK_DOWN);
                movingSouth = true;
                return;
            }
            if (movingEast) {
                reader.robot.keyRelease(KeyEvent.VK_RIGHT);
                movingEast = false;
                //start moving in new direction
                reader.robot.keyPress(KeyEvent.VK_DOWN);
                movingSouth = true;
                return;
            }
            if (!movingSouth) {
                //start moving in new direction
                reader.robot.keyPress(KeyEvent.VK_DOWN);
                movingSouth = true;
                return;
            }
        }
        if (yDestination - reader.getYCoord() < 0) {
            System.out.println("doing action y");
            lastLocationY = reader.getYCoord();
            triedMovingY = true;
            //stop moving in other directions
            if (movingSouth) {
                reader.robot.keyRelease(KeyEvent.VK_DOWN);
                movingSouth = false;
                //start moving in new direction
                reader.robot.keyPress(KeyEvent.VK_UP);
                movingNorth = true;
            }
            if (movingWest) {
                reader.robot.keyRelease(KeyEvent.VK_LEFT);
                movingWest = false;
                //start moving in new direction
                reader.robot.keyPress(KeyEvent.VK_UP);
                movingNorth = true;
            }
            if (movingEast) {
                reader.robot.keyRelease(KeyEvent.VK_RIGHT);
                movingEast = false;
                //start moving in new direction
                reader.robot.keyPress(KeyEvent.VK_UP);
                movingNorth = true;
            }
            if (!movingNorth) {
                //start moving in new direction
                reader.robot.keyPress(KeyEvent.VK_UP);
                movingNorth = true;
                return;
            }
        } else {
            System.out.println("at y");
        }
    }

    /*
     Move the player left or right depending on the destination location
     */
    private void moveX() {

        if (xDestination - reader.getXCoord() < 0) {
            System.out.println("doing action x");
            lastLocationX = reader.getXCoord();
            triedMovingX = true;
            //stop moving in other directions
            if (movingNorth) {
                reader.robot.keyRelease(KeyEvent.VK_UP);
                movingNorth = false;
                //start moving in new direction
                reader.robot.keyPress(KeyEvent.VK_LEFT);
                movingWest = true;
                return;
            }
            if (movingSouth) {
                reader.robot.keyRelease(KeyEvent.VK_DOWN);
                movingSouth = false;
                //start moving in new direction
                reader.robot.keyPress(KeyEvent.VK_LEFT);
                movingWest = true;
                return;
            }
            if (movingEast) {
                reader.robot.keyRelease(KeyEvent.VK_RIGHT);
                movingEast = false;
                //start moving in new direction
                reader.robot.keyPress(KeyEvent.VK_LEFT);
                movingWest = true;
                return;
            }
            if (!movingWest) {
                //start moving in new direction
                reader.robot.keyPress(KeyEvent.VK_LEFT);
                movingWest = true;
                return;
            }
        }
        if (xDestination - reader.getXCoord() > 0) {
            System.out.println("doing action x");
            lastLocationX = reader.getXCoord();
            triedMovingX = true;
            //stop moving in other directions
            if (movingNorth) {
                reader.robot.keyRelease(KeyEvent.VK_UP);
                movingNorth = false;
                //start moving in new direction
                reader.robot.keyPress(KeyEvent.VK_RIGHT);
                movingEast = true;
            }
            if (movingSouth) {
                reader.robot.keyRelease(KeyEvent.VK_DOWN);
                movingSouth = false;
                //start moving in new direction
                reader.robot.keyPress(KeyEvent.VK_RIGHT);
                movingEast = true;
            }
            if (movingWest) {
                reader.robot.keyRelease(KeyEvent.VK_LEFT);
                movingWest = false;
                //start moving in new direction
                reader.robot.keyPress(KeyEvent.VK_RIGHT);
                movingEast = true;
            }
            if (!movingEast) {
                //start moving in new direction
                reader.robot.keyPress(KeyEvent.VK_RIGHT);
                movingEast = true;
            }
        } else {
            System.out.println("at x");
        }
    }

    /*
     Attempt to maneuver around whatever obstacle is in our way.
     */
    private void moveDiagnol() {
        int xDifference = xDestination - reader.getXCoord();
        int yDifference = yDestination - reader.getYCoord();
        xDifference = Math.abs(xDifference);
        yDifference = Math.abs(yDifference);
        //try to move smartly first
        if (xDifference > yDifference) {
            moveX();
        }
        if (yDifference > xDifference) {
            moveY();
        } else {
            moveY();
        }
        //update to check for progress made
        updateAttempts();
        //if none, try again
        if (triedMovingX && triedMovingY) {
            moveX();
        }
    }

    /*
     Returns true if we are at square of the target location
     */
    private boolean at(int xLoc, int yLoc) {
        int xDifference = xLoc - reader.getXCoord();
        int yDifference = yLoc - reader.getYCoord();

        //testing exact waypoints
        //xDifference == 1 || xDifference == -1 || 
        if (xDifference == 0) {
            //yDifference == 1 || yDifference == -1 || 
            if (yDifference == 0) {
                return true;
            }
        }
        return false;
    }

    /*
     Returns true if exactly at the the target location
     */
    private boolean near(int xLoc, int yLoc) {
        int xDifference = xLoc - reader.getXCoord();
        int yDifference = yLoc - reader.getYCoord();

        //if we are near 1 square of our destination
        if (xDifference == 1 || xDifference == -1 || xDifference == 0) {
            if (yDifference == 1 || yDifference == -1 || yDifference == 0) {
                return true;
            }
        }
        return false;
    }

    /*
     Reset caveBotActions to its default state.
     */
    public void resetActions() {
        triedMovingX = false;
        triedMovingY = false;
        movingNorth = false;
        movingSouth = false;
        movingWest = false;
        movingEast = false;
        lastMovementTime = 0;
        movementDelay = 10;
        xDestination = 0;
        yDestination = 0;
    }

    /*
     Stops all movement.
     */
    public void stopMoving() {
        int oldDelay = reader.robot.getAutoDelay();
        reader.robot.setAutoDelay(robotDelay);
        if (movingNorth) {
            reader.robot.keyRelease(KeyEvent.VK_UP);
            movingNorth = false;
            reader.robot.setAutoDelay(oldDelay);
        }
        if (movingSouth) {
            reader.robot.keyRelease(KeyEvent.VK_DOWN);
            movingSouth = false;
            reader.robot.setAutoDelay(oldDelay);
        }
        if (movingWest) {
            reader.robot.keyRelease(KeyEvent.VK_LEFT);
            movingWest = false;
            reader.robot.setAutoDelay(oldDelay);
        }
        if (movingEast) {
            reader.robot.keyRelease(KeyEvent.VK_RIGHT);
            movingEast = false;
            reader.robot.setAutoDelay(oldDelay);
        }
    }
}
