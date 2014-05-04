package Healer;

import Core.ZezeniaHandler;
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
    //Timing variables
    private boolean canCast = true;
    private boolean canPot = true;

    //track the last time potions and spells were cast
    private static Long lastCastTime;
    private static Long lastPotTime;
    private static Long lastManaTime;
    private final int delay = 1050;
    
    //location objects
    private Point oldLocation;
    private int healthPotionX;
    private int healthPotionY;
    

    //size objects
    private int manaPercent;
    private final double pixelPercent = 1.2;
    private final int extendedBPVerticalSize = 325;

    private static ZezeniaHandler reader;

    /*
     Constructor for the healer. Initializes variables.
     */
    private Healer() {
        needHpHigh = false;
        needHpLow = false;
        needMpHigh = false;
        canCast = true;
        canPot = true;

        lastCastTime = System.currentTimeMillis();
        lastPotTime = System.currentTimeMillis();
        lastManaTime = System.currentTimeMillis();

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
        //refresh canPot and canCast bools
        if (!canCast && System.currentTimeMillis() - lastCastTime > delay) {
            canCast = true;
        }
        if (!canPot && System.currentTimeMillis() - lastPotTime > 1200) {
            canPot = true;
        }

        //if healing is enabled, then heal
        if (GUI.highHealCheck.isSelected() || GUI.lowHealCheck.isSelected()) {
            checkHealth();
            restoreHealth();
        }

        if (GUI.manaRestoreCheck.isSelected() && System.currentTimeMillis() - lastManaTime > 1200) {
            checkMana();
            restoreMana();
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
            if (GUI.spellCheck.isSelected() && canCast) {
                spellHeal();
            }
            if (GUI.healthPotionCheck.isSelected() && canPot) {
                useHealthPotion();
                System.out.println("using a health potion");
            }
            needHpLow = false;
            return;
        }
        if (needHpHigh == true && canCast) {
            if (GUI.spellCheck.isSelected()) {
                spellHeal();
            }
        }
    }

    /*
     Restores mana if checkMana decided it was neccessary.
     */
    private void restoreMana() {
        if (GUI.manaRestoreCheck.isSelected() && ((needMpHigh == true) && canPot)) {
            useManaPotion();
            needMpHigh = false;
        }
    }

    /*
     Sends the keypress for lowHeal spell
     */
    private void spellHeal() {
        if (reader.getHealth() < Integer.valueOf(GUI.lowHealBox.getText())) {
            reader.robot.keyPress(KeyEvent.VK_F11);
            reader.robot.keyRelease(KeyEvent.VK_F11);
            canCast = false;
            lastCastTime = System.currentTimeMillis();
            return;
        }
        if (reader.getHealth() < Integer.valueOf(GUI.highHealBox.getText())) {
            reader.robot.keyPress(KeyEvent.VK_F11);
            reader.robot.keyRelease(KeyEvent.VK_F11);
            canCast = false;
            lastCastTime = System.currentTimeMillis();
            return;
        }
    }

    /*
     Move the mouse to the mana potion location and right click.
     Finally, return to old location
     */
    private void useManaPotion() {
        if (canPot) {
            System.out.println("using mana potion");
            //save old location so we can move back there afterwards
            Point pastLocation = new Point(MouseInfo.getPointerInfo().getLocation());
            int manaPotionX = screenSize.width - 130;
            int manaPotionY = 0;

            if (GUI.manaBPSelector.getSelectedIndex() < GUI.lootBPSelector.getSelectedIndex()) {
                manaPotionY = ((GUI.manaBPSelector.getSelectedIndex() + 1) * 90) - 30;
            }
            if (GUI.manaBPSelector.getSelectedIndex() > GUI.lootBPSelector.getSelectedIndex()) {
                manaPotionY = ((GUI.manaBPSelector.getSelectedIndex() + 1) * 90) - 30
                        + extendedBPVerticalSize;
            }

            //move to the new location
            reader.robot.mouseMove(manaPotionX, manaPotionY);

            //right click and use the potion
            reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
            reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);

            //Move back to old location
            //if cavebot is running though, this isnt neccessary and just wastes time
            if (!GUI.huntingStarted) {
                reader.robot.mouseMove(pastLocation.x, pastLocation.y);
            }
            canPot = false;
            lastManaTime = System.currentTimeMillis();
            lastPotTime = System.currentTimeMillis();
            needMpHigh = false;
        }
    }

    /*
     Move the mouse to the health potion location and right click.
     Finally, return to old location
     */
    private void useHealthPotion() {
        if (canPot) {
            oldLocation = new Point(MouseInfo.getPointerInfo().getLocation());

            healthPotionX = screenSize.width - 130;
            healthPotionY = ((GUI.manaBPSelector.getSelectedIndex() + 1) * 90) - 30;
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
            canPot = false;
            lastPotTime = System.currentTimeMillis();
        }
    }

}
