package Misc;

import Core.ZezeniaHandler;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Misc {

    private static Misc instance = null;
    private static ZezeniaHandler reader;

    private long lastRestockingTime;
    private long lastAteTime;
    private long lastTrainTime = 0;
    private final int restockingDelay = 60000;//60 seconds
    private final int eatFoodDelay = 30000;//30 seconds
    private long lastBeepTime = 0;
    private final int beepDelay = 500;//half of a second between beeps

    private final int rightHandXOffset = 128;
    private final int rightHandYOffset = 130;
    private final int extendedBPVerticalSize = 285;
    private final double pixelPercent = 1.2;
    private int trainingPercent = 0;

    private final Color manaColor = new Color(0, 0, 170);

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    int arrowBP = 0;
    int foodBP = 0;

    /*
     Returns the singleton instance of Misc
     */
    public static Misc getInstance() {
        if (instance == null) {
            instance = new Misc();
        }
        return instance;
    }

    /*
     Misc features constructor.
     */
    private Misc() {
        reader = ZezeniaHandler.getInstance();
        lastRestockingTime = System.currentTimeMillis();
        lastAteTime = System.currentTimeMillis();
    }

    /*
     Core of Misc class.
     */
    public void doMisc() {
        if (GUI.GUI.ammoCheck.isSelected()) {
            restockArrows();
        }
        if (GUI.GUI.foodCheck.isSelected()) {
            eatFood();
        }
        if (GUI.GUI.spellTrainingCheck.isSelected()) {
            spellTrain();
        }
        if (GUI.GUI.lightCheck.isSelected()) {
            lightHack();
        }
        if (GUI.GUI.playerAlertCheck.isSelected()) {
            playerAlert();
        }
    }

    /*
     Moves arrows from the arrow backpack into the arrow slot.
     */
    private void restockArrows() {
        if (System.currentTimeMillis() - lastRestockingTime > restockingDelay && GUI.GUI.arrowBPSelector.getSelectedIndex() != 6) {
            lastRestockingTime = System.currentTimeMillis();
            //Save the old robot delay and set the new delay for this method

            //save old location so we can move back there afterwards
            Point oldLocation = new Point(MouseInfo.getPointerInfo().getLocation());

            //Set the arrow bp location
            int arrowBPX = screenSize.width - 130;
            int arrowBPY = 0;
            if (GUI.GUI.arrowBPSelector.getSelectedIndex() < GUI.GUI.lootBPSelector.getSelectedIndex()) {
                arrowBPY = ((GUI.GUI.arrowBPSelector.getSelectedIndex() + 1) * 90) - 30;
            }
            if (GUI.GUI.arrowBPSelector.getSelectedIndex() > GUI.GUI.lootBPSelector.getSelectedIndex()) {
                arrowBPY = ((GUI.GUI.arrowBPSelector.getSelectedIndex() + 1) * 90) - 30
                        + extendedBPVerticalSize;
            }
            if (arrowBPY == 0) {
                GUI.GUI.debug.append("Arrow BP is the same bp as something else."
                        + "Restart the bot, fix, and save settings, before continuing.");
                return;
            }
            //set the X and Y for the arrowLocation
            int handX = reader.getInventoryX() + rightHandXOffset;
            int handY = reader.getInventoryY() + rightHandYOffset;
            //move to the new location
            reader.robot.mouseMove(arrowBPX, arrowBPY);

            //press control and then left click and hold
            reader.robot.keyPress(KeyEvent.VK_CONTROL);
            reader.robot.mousePress(MouseEvent.BUTTON1_MASK);

            //and release the button
            reader.robot.mouseMove(handX, handY);
            reader.robot.mouseRelease(MouseEvent.BUTTON1_MASK);
            reader.robot.keyRelease(KeyEvent.VK_CONTROL);
            reader.robot.delay(20);
            reader.robot.keyPress(KeyEvent.VK_ENTER);
            reader.robot.keyRelease(KeyEvent.VK_ENTER);

            //Return us back to our old delay settings
            //if cavebot is running though, this isnt neccessary and just wastes time
            if (!GUI.GUI.huntingStarted) {
                reader.robot.mouseMove(oldLocation.x, oldLocation.y);
            }
        }

    }

    /*
     Eats food out of the food backpack if it is selected.
     */
    private void eatFood() {

        if (System.currentTimeMillis() - lastAteTime > eatFoodDelay && GUI.GUI.foodBPSelector.getSelectedIndex() != 6) {
            lastAteTime = System.currentTimeMillis();
            //save old location so we can move back there afterwards
            Point oldLocation = new Point(MouseInfo.getPointerInfo().getLocation());

            //set the location of the food bp
            int foodX = screenSize.width - 130;
            int foodY = 0;
            //set y location, depdending on if it is before or after the expanded loot bp
            if (GUI.GUI.foodBPSelector.getSelectedIndex() < GUI.GUI.lootBPSelector.getSelectedIndex()) {
                foodY = ((GUI.GUI.foodBPSelector.getSelectedIndex() + 1) * 90) - 30;
            }
            if (GUI.GUI.foodBPSelector.getSelectedIndex() > GUI.GUI.lootBPSelector.getSelectedIndex()) {
                foodY = ((GUI.GUI.arrowBPSelector.getSelectedIndex() + 1) * 90) - 30
                        + extendedBPVerticalSize;
            }
            //check to see if foodbp wasnt set
            if (foodY == 0) {
                GUI.GUI.debug.append("Food BP is the same bp as something else."
                        + "Restart the bot, fix, and save settings, before continuing.");
                return;
            }
            //move to the new location
            reader.robot.mouseMove(foodX, foodY);

            //right click and use the potion
            reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
            reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);

            //Move back to old location
            //if cavebot is running though, dont move. it just wastes time.
            if (!GUI.GUI.huntingStarted || GUI.GUI.caveBotIsPaused) {
                reader.robot.mouseMove(oldLocation.x, oldLocation.y);
            }
        }
    }


    /*
     Casts a spell when the specified amount of time has passed.
     */
    private void spellTrain() {
        if (System.currentTimeMillis() - lastTrainTime > 5000) {
            lastTrainTime = System.currentTimeMillis();
            trainingPercent = Integer.valueOf(GUI.GUI.spellTrainingBox.getText()) - 2;

            Color currentColor = reader.robot.getPixelColor((int) (reader.getAbilityWindowX()
                    + 14 + (trainingPercent * pixelPercent)), reader.getAbilityWindowY() + 115);

            if (currentColor.getRGB() == -16777046) {//number is shorthand for mana color
                reader.robot.keyPress(KeyEvent.VK_F10);
                reader.robot.keyRelease(KeyEvent.VK_F10);
            }
        }
    }

    /*
     If lightCheck is selected, do lighthack
     */
    private void lightHack() {
        //if the lighthack is enabled.
        reader.setLight();
    }

    /*
     Plays an alert if there is another player on the screen.
     */
    private void playerAlert() {
        //if we have player alert enabled
        if (reader.getPID() != reader.lastToEnterScreenID()) {
            if (reader.lastToEnterScreenID() - reader.getPID() > -3000) {
                //play a beep if it has been long enough since the last one.
                if (System.currentTimeMillis() - lastBeepTime > beepDelay) {
                    lastBeepTime = System.currentTimeMillis();
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
            }
        }
    }
}
