package Healer;

import Core.ZezeniaHandler;
import Core.Core;
import GUI.GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Healer {

    private static Healer instance;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    //Health variables
    private boolean needHpHigh;
    private boolean needHpLow;
    //Mana variables
    private boolean needMpHigh;

    //location objects
    private Point oldLocation;
    private int healthPotionX;
    private int healthPotionY;

    //size objects
    private int manaPercent;
    private final double pixelPercent = 1.2;
    private final int extendedBPVerticalSize = 285;

    private long lastHealCheck = 0;

    private static ZezeniaHandler reader;

    /*
     Constructor for the healer. Initializes variables.
     */
    private Healer() {
        needHpHigh = false;
        needHpLow = false;
        needMpHigh = false;

        reader = ZezeniaHandler.getInstance();
    }

    /*
     Returns this singleton instance of Healer
     */
    public static Healer getInstance() {
        if (instance == null) {
            instance = new Healer();
        }
        return instance;
    }

    /*
     Brain of the healer.
     */
    public void heal() {

        if (System.currentTimeMillis() - lastHealCheck > 200) {
            lastHealCheck = System.currentTimeMillis();

            //if healing is enabled, then heal
            if (GUI.highHealCheck.isSelected() || GUI.lowHealCheck.isSelected()) {
                checkHealth();
                restoreHealth();
            }

            if (GUI.manaRestoreCheck.isSelected() && System.currentTimeMillis() - Core.lastManaTime > 1200) {
                checkMana();
                restoreMana();

            }
        }
    }

    /*
     Checks the players current health and compares it to their set healing
     percentages. 
     */
    private void checkHealth() {
        if (reader.getHealth() < Integer.valueOf(GUI.lowHealBox.getText())) {
            needHpLow = true;
            return;
        }
        if (reader.getHealth() < Integer.valueOf(GUI.highHealBox.getText())) {
            needHpHigh = true;
            return;
        }
    }

    /*
     Checks the players currentMana, comparing it to their set restore %.
     */
    private void checkMana() {
        manaPercent = Integer.valueOf(GUI.manaRestoreBox.getText());

        Color currentColor = reader.robot.getPixelColor((int) (reader.getAbilityWindowX()
                + 14 + (manaPercent * pixelPercent)), reader.getAbilityWindowY() + 115);

        if (currentColor.getRGB() != -16777046) {
            needMpHigh = true;
        }
    }

    /*
     Restores health if checkHealth decided it was neccessary.
     */
    private void restoreHealth() {
        if (needHpLow == true) {
            if (GUI.spellCheck.isSelected() && Core.canCast) {
                spellHeal();
            }
            if (GUI.healthPotionCheck.isSelected() && Core.canPot) {
                useHealthPotion();
                System.out.println("using a health potion");
            }
            needHpLow = false;
            needHpHigh = false;
            return;
        }
        if (needHpHigh == true && Core.canCast) {
            if (GUI.spellCheck.isSelected()) {
                spellHeal();
            }
            needHpHigh = false;
        }
    }

    /*
     Restores mana if checkMana decided it was neccessary.
     */
    private void restoreMana() {
        if (GUI.manaRestoreCheck.isSelected() && ((needMpHigh == true) && Core.canPot)) {
            useManaPotion();
            needMpHigh = false;
        }
    }

    /*
     Sends the keypress for lowHeal spell
     */
    private void spellHeal() {
        if (reader.getHealth() < Integer.valueOf(GUI.lowHealBox.getText())) {
            reader.robot.delay((int) randomDelay());
            reader.robot.keyPress(KeyEvent.VK_F11);
            reader.robot.keyRelease(KeyEvent.VK_F11);
            Core.canCast = false;
            Core.lastCastTime = System.currentTimeMillis();
            return;
        }
        if (reader.getHealth() < Integer.valueOf(GUI.highHealBox.getText())) {
            reader.robot.delay((int) randomDelay());
            reader.robot.keyPress(KeyEvent.VK_F12);
            reader.robot.keyRelease(KeyEvent.VK_F12);
            Core.canCast = false;
            Core.lastCastTime = System.currentTimeMillis();
            return;
        }
    }

    /*
     Move the mouse to the mana potion location and right click.
     Finally, return to old location
     */
    private void useManaPotion() {
        if (Core.canPot) {
            System.out.println("using mana potion");
            //save old location so we can move back there afterwards
            Point pastLocation = new Point(MouseInfo.getPointerInfo().getLocation());
            int manaPotionX = screenSize.width - 130;
            int manaPotionY = 0;

            if (GUI.manaBPSelector.getSelectedIndex() < GUI.lootBPSelector.getSelectedIndex()) {
                manaPotionY = ((GUI.manaBPSelector.getSelectedIndex() + 1) * 90) - 20;
            }
            if (GUI.manaBPSelector.getSelectedIndex() > GUI.lootBPSelector.getSelectedIndex()) {
                manaPotionY = ((GUI.manaBPSelector.getSelectedIndex() + 1) * 90) - 20
                        + extendedBPVerticalSize;
            }

            //move to the new location
            reader.robot.mouseMove(manaPotionX, manaPotionY);

            //right click and use the potion
            reader.robot.delay((int) randomDelay());
            reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
            reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);

            //Move back to old location
            //if cavebot is running though, this isnt neccessary and just wastes time
            if (!GUI.huntingStarted) {
                reader.robot.mouseMove(pastLocation.x, pastLocation.y);
            }
            Core.canPot = false;
            Core.lastPotTime = System.currentTimeMillis();
            Core.lastManaTime = System.currentTimeMillis();
            needMpHigh = false;
        }
    }

    /*
     Move the mouse to the health potion location and right click.
     Finally, return to old location
     */
    private void useHealthPotion() {
        if (Core.canPot) {
            oldLocation = new Point(MouseInfo.getPointerInfo().getLocation());

            healthPotionX = screenSize.width - 130;
            healthPotionY = 0;

            if (GUI.healthBPSelector.getSelectedIndex() < GUI.lootBPSelector.getSelectedIndex()) {
                healthPotionY = ((GUI.healthBPSelector.getSelectedIndex() + 1) * 90) - 20;
            }
            if (GUI.healthBPSelector.getSelectedIndex() > GUI.lootBPSelector.getSelectedIndex()) {
                healthPotionY = ((GUI.healthBPSelector.getSelectedIndex() + 1) * 90) - 20
                        + extendedBPVerticalSize;
            }

            //move to the new location
            reader.robot.mouseMove(healthPotionX, healthPotionY);

            //right click and use the potion
            reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
            reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);

            //move back to old location
            //if cavebot is running though, this isnt neccessary and just wastes time
            if (!GUI.huntingStarted) {
                reader.robot.mouseMove(oldLocation.x, oldLocation.y);
            }
            Core.canPot = false;
            Core.lastPotTime = System.currentTimeMillis();
        }
    }

    /*
     Returns a random number between 0, and 200.
     */
    private long randomDelay() {
        long delay = (long) (Math.random() * 280);
        if (delay < 130) {
            delay = 130;
        }
        return delay;
    }

}
