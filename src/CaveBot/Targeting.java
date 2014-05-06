package CaveBot;

import Core.ZezeniaHandler;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class Targeting {

    private static Targeting instance = null;
    private final ZezeniaHandler reader;

    //the width of the tiles to use to determine where to click
    private final int tileWidth;
    //the lowest range to for targeting
    private final int playerLowerLimit;
    private final Point center;
    //how long to wait before clicking a target
    private final int targetingDelay = 10;
    private final int targetCheckFrequency = 100;
    private long lastCheckTime = 0;


    /*
     Returns the singleton Targeting instance
     */
    public static Targeting getInstance() {
        if (instance == null) {
            instance = new Targeting();
        }
        return instance;
    }

    /*
     Private constructor to aid singleton creation
     */
    private Targeting() {
        reader = ZezeniaHandler.getInstance();
        playerLowerLimit = reader.getPID() - 2000;
        center = reader.returnCenterScreen();
        tileWidth = reader.getTileSize();
    }

    /*
     Move the mouse arround until we find a target.
     */
    public void findTarget() {
        //if enough time has ellapsed since the last check
        if (System.currentTimeMillis() - lastCheckTime > targetCheckFrequency) {
            lastCheckTime = System.currentTimeMillis();
            //save the old robot delay
            int oldDelay = reader.robot.getAutoDelay();
            reader.robot.setAutoDelay(targetingDelay);

            //if what we have moused over is less than the playerLowerLimit
            //and not zero
            if (reader.getTargetID() != 0) {
                Walker.getInstance().stopMoving();
                return;
            }

            //west of player
            reader.robot.mouseMove(center.x - tileWidth, center.y);
            if (reader.getMouseTargetID() != 0 && reader.getMouseTargetID() < playerLowerLimit) {
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                CaveBot.getInstance().setAttacking(true);
                CaveBot.getInstance().setLooting(true);
                reader.robot.setAutoDelay(oldDelay);
                return;
            }

            //northwest of player
            reader.robot.mouseMove(center.x - tileWidth, center.y - tileWidth);
            if (reader.getMouseTargetID() != 0 && reader.getMouseTargetID() < playerLowerLimit) {
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                CaveBot.getInstance().setAttacking(true);
                CaveBot.getInstance().setLooting(true);
                reader.robot.setAutoDelay(oldDelay);
                return;
            }

            //above of player
            reader.robot.mouseMove(center.x, center.y - tileWidth);
            if (reader.getMouseTargetID() != 0 && reader.getMouseTargetID() < playerLowerLimit) {
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                CaveBot.getInstance().setAttacking(true);
                CaveBot.getInstance().setLooting(true);
                reader.robot.setAutoDelay(oldDelay);
                return;
            }

            //northeast of player
            reader.robot.mouseMove(center.x + tileWidth, center.y - tileWidth);
            if (reader.getMouseTargetID() != 0 && reader.getMouseTargetID() < playerLowerLimit) {
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                CaveBot.getInstance().setAttacking(true);
                CaveBot.getInstance().setLooting(true);
                reader.robot.setAutoDelay(oldDelay);
                return;
            }

            //east of player
            reader.robot.mouseMove(center.x + tileWidth, center.y);
            if (reader.getMouseTargetID() != 0 && reader.getMouseTargetID() < playerLowerLimit) {
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                CaveBot.getInstance().setAttacking(true);
                CaveBot.getInstance().setLooting(true);
                reader.robot.setAutoDelay(oldDelay);
                return;
            }

            //southeast of player
            reader.robot.mouseMove(center.x + tileWidth, center.y + tileWidth);
            if (reader.getMouseTargetID() != 0 && reader.getMouseTargetID() < playerLowerLimit) {
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                CaveBot.getInstance().setAttacking(true);
                CaveBot.getInstance().setLooting(true);
                reader.robot.setAutoDelay(oldDelay);
                return;
            }

            //south of player
            reader.robot.mouseMove(center.x, center.y + tileWidth);
            if (reader.getMouseTargetID() != 0 && reader.getMouseTargetID() < playerLowerLimit) {
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                CaveBot.getInstance().setAttacking(true);
                CaveBot.getInstance().setLooting(true);
                reader.robot.setAutoDelay(oldDelay);
                return;
            }

            //southwest of player
            reader.robot.mouseMove(center.x - tileWidth, center.y + tileWidth);
            if (reader.getMouseTargetID() != 0 && reader.getMouseTargetID() < playerLowerLimit) {
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                CaveBot.getInstance().setAttacking(true);
                CaveBot.getInstance().setLooting(true);
                reader.robot.setAutoDelay(oldDelay);
                return;
            } else {
                CaveBot.getInstance().setAttacking(false);
                reader.robot.setAutoDelay(oldDelay);
                return;
            }
        }
    }
}
