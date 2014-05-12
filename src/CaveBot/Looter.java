package CaveBot;

import Core.ZezeniaHandler;
import JNA.JNACore;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Looter {

    private static Looter instance = null;
    private final JNACore jnaCore;
    private final ZezeniaHandler reader;

    //for positioning
    private final Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
    private final int extendedBPVerticalSize = 240;
    private final int tileSize;
    private final Point center;

    //mouse information
    private Point oldLocation;

    int currentLootBP = 2;

    //timing devices
    private final int corpseCheckingDelay = 25;
    private long lastFullCheckTime = 0;

    /*
     Private constructor for singleton creation
     */
    private Looter() {
        jnaCore = JNACore.getInstance();
        reader = ZezeniaHandler.getInstance();
        center = reader.returnCenterScreen();
        tileSize = reader.getTileSize();
    }

    /*
     Returns the singleton instance of Looter
     */
    public static Looter getInstance() {
        if (instance == null) {
            instance = new Looter();
        }
        return instance;
    }

    /*
     Searches for loot near the player, and if it finds it, it loots it.
     */
    public void findLoot() {
        if (CaveBot.getInstance().isLooting() && !CaveBot.getInstance().isAttacking()) {
            clickAroundPlayer();
        }
        //save old location so we can move back there afterwards
        oldLocation = MouseInfo.getPointerInfo().getLocation();
        if (bpOpened()) {
            while (bpOpened() && (!GUI.GUI.isPaused || !GUI.GUI.caveBotIsPaused)) {
                Walker.getInstance().stopMoving();
                checkForLoot();
            }
            CaveBot.getInstance().setLooting(false);
            reader.robot.mouseMove(oldLocation.x, oldLocation.y);
        }
        //if enough time has passed, check and see if our bp is full.
        if (System.currentTimeMillis() - lastFullCheckTime > 5000) {
            checkForFullBP();
            reader.robot.mouseMove(oldLocation.x, oldLocation.y);
        }
    }

    /*
     Checks if our loot backpack is full, and if so, closes the backpack and 
     opens the next one.
     */
    private void checkForFullBP() {
        lastFullCheckTime = System.currentTimeMillis();
        System.out.println("checking for full bp");
        //if we have filled all loot bps, there is nothing we can do.
        if (currentLootBP > 24) {
            System.out.println("current bp is : " + currentLootBP);
            GUI.GUI.debug.append("All BP's are considered full. Empty BP's and"
                    + " reset bot to continue.\n");
            return;
        }
        //otherwise, check
        if (isFull()) {
            System.out.println("bp was detected as full");
            int delay = 25;
            int oldDelay = reader.robot.getAutoDelay();
            reader.robot.setAutoDelay(delay);
            closeExtraBags();
            closeLootBP();
            reader.robot.delay(100);
            openNextBag();
            reader.robot.setAutoDelay(oldDelay);
        }
    }

    /*
     Returns true if there is an object in the last slot of the loot backpack
     regardless of the number of items in that backpacks first slot.
     */
    private boolean isFull() {
        //if there is an item in the last slot of the loot backpack
        if (jnaCore.findDynAddress(reader.getBackPack(Core.Core.returnNumberOfBackpacks() + 1, 24, false), ZezeniaHandler.base) != 0) {
            if (jnaCore.readMemory(jnaCore.zezeniaProcessHandle,
                    jnaCore.findDynAddress(reader.getBackPack(Core.Core.returnNumberOfBackpacks() + 1, 24, false), ZezeniaHandler.base),
                    4).getInt(0) > 0) {
                System.out.println("full bp detected");
                return true;
            }
        }
        return false;
    }

    /*
     Closes every backpack past the last backpack.
     */
    private void closeExtraBags() {
        System.out.println("closing extra bps");
        while (bpOpened()) {
            closeCorpses();
        }
    }

    /*
     Closes the current loot backpack
     */
    private void closeLootBP() {
        reader.robot.mouseMove(dimensions.width - 10, (Core.Core.returnNumberOfBackpacks() * 90) + extendedBPVerticalSize - 20 + 85);
        System.out.println("clicking to close bp at : " + (dimensions.width - 10) + "," + ((Core.Core.returnNumberOfBackpacks() * 90) + extendedBPVerticalSize - 20 + 85));
        reader.robot.mousePress(MouseEvent.BUTTON1_MASK);
        reader.robot.mouseRelease(MouseEvent.BUTTON1_MASK);
    }

    /*
     Opens the next lootbackpack
     */
    private void openNextBag() {
        int bpX = dimensions.width - 130;
        int bpY = ((GUI.GUI.lootBPSelector.getSelectedIndex() + 1) * 90) - 30;

        //now for the long but easy to read code that decides which bp to open next.
        //1st bp column
        if (currentLootBP % 3 == 1) {
            //first row
            if (currentLootBP < 4) {
                System.out.println("opening bp 1");
                reader.robot.mouseMove(bpX, bpY);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            //second row
            if (currentLootBP < 7) {
                System.out.println("opening bp 4");
                reader.robot.mouseMove(bpX, bpY + 40);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }//third row
            if (currentLootBP < 10) {
                System.out.println("opening bp 7");
                reader.robot.mouseMove(bpX, bpY + 80);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            //fouth row
            if (currentLootBP < 13) {
                System.out.println("opening bp 10");
                reader.robot.mouseMove(bpX, bpY + 120);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            //fifth row
            if (currentLootBP < 16) {
                System.out.println("opening bp 13");
                reader.robot.mouseMove(bpX, bpY + 160);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            //sixth row
            if (currentLootBP < 19) {
                System.out.println("opening bp 16");
                reader.robot.mouseMove(bpX, bpY + 200);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            //seventh row
            if (currentLootBP < 22) {
                System.out.println("opening bp 19");
                reader.robot.mouseMove(bpX, bpY + 240);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            //eigth row
            if (currentLootBP < 25) {
                System.out.println("opening bp 22");
                reader.robot.mouseMove(bpX, bpY + 260);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            return;
        }
        //2nd bp column
        if (currentLootBP % 3 == 2) {
            //first row
            if (currentLootBP < 4) {
                System.out.println("opening bp 2");
                reader.robot.mouseMove(bpX + 40, bpY);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            //second row
            if (currentLootBP < 7) {
                System.out.println("opening bp 5");
                reader.robot.mouseMove(bpX + 40, bpY + 40);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }//third row
            if (currentLootBP < 10) {
                System.out.println("opening bp 8");
                reader.robot.mouseMove(bpX + 40, bpY + 80);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            //fouth row
            if (currentLootBP < 13) {
                System.out.println("opening bp 11");
                reader.robot.mouseMove(bpX + 40, bpY + 120);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            //fifth row
            if (currentLootBP < 16) {
                System.out.println("opening bp 14");
                reader.robot.mouseMove(bpX + 40, bpY + 160);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            //sixth row
            if (currentLootBP < 19) {
                System.out.println("opening bp 17");
                reader.robot.mouseMove(bpX + 40, bpY + 200);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            //seventh row
            if (currentLootBP < 22) {
                System.out.println("opening bp 20");
                reader.robot.mouseMove(bpX + 40, bpY + 240);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            //eigth row
            if (currentLootBP < 25) {
                System.out.println("23");
                reader.robot.mouseMove(bpX + 40, bpY + 260);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            return;
        }
        //third bp column
        if (currentLootBP % 3 == 0) {
            //first row
            if (currentLootBP < 4) {
                System.out.println("opening bp 3");
                reader.robot.mouseMove(bpX + 80, bpY);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            //second row
            if (currentLootBP < 7) {
                System.out.println("opening bp 6");
                reader.robot.mouseMove(bpX + 80, bpY + 40);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }//third row
            if (currentLootBP < 10) {
                System.out.println("opening bp 9");
                reader.robot.mouseMove(bpX + 80, bpY + 80);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            //fouth row
            if (currentLootBP < 13) {
                System.out.println("opening bp 12");
                reader.robot.mouseMove(bpX + 80, bpY + 120);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            //fifth row
            if (currentLootBP < 16) {
                System.out.println("opening bp 15");
                reader.robot.mouseMove(bpX + 80, bpY + 160);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            //sixth row
            if (currentLootBP < 19) {
                System.out.println("opening bp 18");
                reader.robot.mouseMove(bpX + 80, bpY + 200);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            //seventh row
            if (currentLootBP < 22) {
                System.out.println("opening bp 21");
                reader.robot.mouseMove(bpX + 80, bpY + 240);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            //eigth row
            if (currentLootBP < 25) {
                System.out.println("opening bp 24");
                reader.robot.mouseMove(bpX + 80, bpY + 260);
                reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
                reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                currentLootBP++;
                return;
            }
            System.out.println("opening bp ???");
            return;
        }
    }

    /*
     Returns true if a new bp has been opened.
     */
    private boolean bpOpened() {
        int bps = Core.Core.returnNumberOfBackpacks();
        return bpOpened(bps + 2) || bpOpened(bps + 3) || bpOpened(bps + 4)
                || bpOpened(bps + 5) || bpOpened(bps + 6) || bpOpened(bps + 7)
                || bpOpened(bps + 8) || bpOpened(bps + 9) || bpOpened(bps + 10);
    }

    /*
     If there are no more monsters on the screen, click all the squares arround
     the player one time each.
     */
    private void clickAroundPlayer() {
        int oldDelay = reader.robot.getAutoDelay();
        reader.robot.setAutoDelay(corpseCheckingDelay);

        //west of player
        reader.robot.mouseMove(center.x - tileSize, center.y);
        if (reader.getMouseTargetID() == 0) {
            reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
            reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
        }
        //northwest of player
        reader.robot.mouseMove(center.x - tileSize, center.y - tileSize);
        if (reader.getMouseTargetID() == 0) {
            reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
            reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
        }
        //above of player
        reader.robot.mouseMove(center.x, center.y - tileSize);
        if (reader.getMouseTargetID() == 0) {
            reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
            reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
        }

        //northeast of player
        reader.robot.mouseMove(center.x + tileSize, center.y - tileSize);
        if (reader.getMouseTargetID() == 0) {
            reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
            reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
        }
        //east of player
        reader.robot.mouseMove(center.x + tileSize, center.y);
        if (reader.getMouseTargetID() == 0) {
            reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
            reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
        }
        //southeast of player
        reader.robot.mouseMove(center.x + tileSize, center.y + tileSize);
        if (reader.getMouseTargetID() == 0) {
            reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
            reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
        }
        //south of player
        reader.robot.mouseMove(center.x, center.y + tileSize);
        if (reader.getMouseTargetID() == 0) {
            reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
            reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
        }
        //southwest of player
        reader.robot.mouseMove(center.x - tileSize, center.y + tileSize);
        if (reader.getMouseTargetID() == 0) {
            reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
            reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
        }
        //underneath us
        reader.robot.mouseMove(center.x, center.y);
        if (reader.getMouseTargetID() == 0) {
            reader.robot.mousePress(MouseEvent.BUTTON3_MASK);
            reader.robot.mouseRelease(MouseEvent.BUTTON3_MASK);
        }
        reader.robot.setAutoDelay(oldDelay);
    }

    /*
     Drags the loot into the loot bp.
     */
    private void checkForLoot() {
        System.out.println("Checking for loot.");
        int numBPS = Core.Core.returnNumberOfBackpacks();

        //if a bp was opened
        if (bpOpened(numBPS + 2)) {
            System.out.println("Looting first corpse.");
            //if there is still loot in the bps first slot
            while (hasLoot(numBPS + 2) && bpOpened(numBPS + 2)) {
                lootCorpses();
                stackItems();
            }
            closeCorpses();
        }

        //if hunting isnt started, there will never be more than one corpse
        //opened at a time, so we can exit here
        if (!GUI.GUI.huntingStarted) {
            return;
        }

        //loot second open corpse
        if (bpOpened(numBPS + 3) && !bpOpened(numBPS + 2)) {
            System.out.println("Looting second corpse.");
            //if there is still loot in the bps first slot
            while (hasLoot(numBPS + 3) && bpOpened(numBPS + 3)) {
                lootCorpses();
                stackItems();
            }
            closeCorpses();
        }

        //loot 3rd open corpse
        if (bpOpened(numBPS + 4) && !bpOpened(numBPS + 3) && !bpOpened(numBPS + 2)) {
            System.out.println("Looting third corpse.");
            //if there is still loot in the bps first slot
            while (hasLoot(numBPS + 4) && bpOpened(numBPS + 4)) {
                lootCorpses();
                stackItems();
            }
            closeCorpses();
        }

        //loot 4th open corpse
        if (bpOpened(numBPS + 5) && !bpOpened(numBPS + 4) && !bpOpened(numBPS + 3) && !bpOpened(numBPS + 2)) {
            System.out.println("Looting fourth corpse.");
            //if there is still loot in the bps first slot
            while (hasLoot(numBPS + 5) && bpOpened(numBPS + 5)) {
                lootCorpses();
                stackItems();
            }
            closeCorpses();
        }

        //loot 5th open corpse
        if (bpOpened(numBPS + 6) && !bpOpened(numBPS + 5) && !bpOpened(numBPS + 4) && !bpOpened(numBPS + 3) && !bpOpened(numBPS + 2)) {
            System.out.println("Looting fifth corpse.");
            //if there is still loot in the bps first slot
            while (hasLoot(numBPS + 6) && bpOpened(numBPS + 6)) {
                lootCorpses();
                stackItems();
            }
            closeCorpses();
        }

        //loot 6th open corpse
        if (bpOpened(numBPS + 7) && !bpOpened(numBPS + 6) && !bpOpened(numBPS + 5) && !bpOpened(numBPS + 4) && !bpOpened(numBPS + 3) && !bpOpened(numBPS + 2)) {
            System.out.println("Looting sixth corpse.");
            //if there is still loot in the bps first slot
            while (hasLoot(numBPS + 7) && bpOpened(numBPS + 7)) {
                lootCorpses();
                stackItems();
            }
            closeCorpses();
        }

        //loot 7th open corpse
        if (bpOpened(numBPS + 8) && !bpOpened(numBPS + 7) && !bpOpened(numBPS + 6) && !bpOpened(numBPS + 5) && !bpOpened(numBPS + 4) && !bpOpened(numBPS + 3) && !bpOpened(numBPS + 2)) {
            System.out.println("Looting seventh corpse.");
            //if there is still loot in the bps first slot
            while (hasLoot(numBPS + 8) && bpOpened(numBPS + 8)) {
                lootCorpses();
                stackItems();
            }
            closeCorpses();
        }

        //loot 8th open corpse
        if (bpOpened(numBPS + 9) && !bpOpened(numBPS + 8) && !bpOpened(numBPS + 7) && !bpOpened(numBPS + 6) && !bpOpened(numBPS + 5) && !bpOpened(numBPS + 4) && !bpOpened(numBPS + 3) && !bpOpened(numBPS + 2)) {
            System.out.println("Looting eigth corpse.");
            //if there is still loot in the bps first slot
            while (hasLoot(numBPS + 9) && bpOpened(numBPS + 9)) {
                lootCorpses();
                stackItems();
            }
            closeCorpses();
        }

        //loot 9th open corpse
        if (bpOpened(numBPS + 10) && !bpOpened(numBPS + 9) && !bpOpened(numBPS + 8) && !bpOpened(numBPS + 7) && !bpOpened(numBPS + 6) && !bpOpened(numBPS + 5) && !bpOpened(numBPS + 4) && !bpOpened(numBPS + 3) && !bpOpened(numBPS + 2)) {
            System.out.println("Looting eigth corpse.");
            //if there is still loot in the bps first slot
            while (hasLoot(numBPS + 10) && bpOpened(numBPS + 10)) {
                lootCorpses();
                stackItems();
            }
            closeCorpses();
        }

        //Move back to old location
        reader.robot.mouseMove(oldLocation.x, oldLocation.y);
    }

    /*
     Drags the loot from the monsers corpse to the loot backpack
     */
    private void lootCorpses() {
        System.out.println("Looting a corpse.");
        int numBPS = Core.Core.returnNumberOfBackpacks();
        //Set the loot bp location
        int lootBPX = dimensions.width - 130;
        int lootBPY = ((numBPS + 1) * 90) + extendedBPVerticalSize + 10;
        //set the X and Y for the monsterCorpseLocation
        int monsterCorpseBagX = dimensions.width - 130;
        int monsterCorpseBagY = ((numBPS + 2) * 90) + extendedBPVerticalSize + 10;

        reader.robot.mouseMove(monsterCorpseBagX, monsterCorpseBagY);

        //press control and then left click and hold
        reader.robot.keyPress(KeyEvent.VK_CONTROL);
        reader.robot.mousePress(MouseEvent.BUTTON1_MASK);

        //and release the button
        reader.robot.mouseMove(lootBPX, lootBPY);
        reader.robot.mouseRelease(MouseEvent.BUTTON1_MASK);
        reader.robot.keyRelease(KeyEvent.VK_CONTROL);
        reader.robot.delay(20);
        reader.robot.keyPress(KeyEvent.VK_ENTER);
        reader.robot.keyRelease(KeyEvent.VK_ENTER);
    }

    /*
     Returns true if the specified bp was opened
     */
    private boolean bpOpened(int bp) {
        return jnaCore.findDynAddress(reader.getBackPack(bp, 1, true), ZezeniaHandler.base) > 0;
    }

    /*
     Returns true if there is loot in the specified bp
     */
    private boolean hasLoot(int bp) {
        return jnaCore.readMemory(JNACore.getInstance().zezeniaProcessHandle, jnaCore.findDynAddress(reader.getBackPack(bp, 1, false), ZezeniaHandler.base), 4).getInt(0) > 0;
    }

    /*
     Returns true if there is loot in the specified slot
     */
    private boolean lootInSlot(int bp, int slot) {
        return jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(reader.getBackPack(bp, slot, true), ZezeniaHandler.base), 4).getInt(0) != 0;
    }

    /*
     Returns true if the specified slots in the specified bp, contain the same
     items.
     */
    private boolean slotsHaveSameItems(int bp, int slot1, int slot2) {
        return jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(reader.getBackPack(bp, slot1, true), ZezeniaHandler.base), 4).getInt(0)
                == jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(reader.getBackPack(bp, slot2, true), ZezeniaHandler.base), 4).getInt(0);
    }

    /*
     Returns true if the specified slot contains a stackable item that is not fully
     stacked yet
     */
    private boolean canBeStacked(int bp, int slot) {
        //return false if there is nothing in that slot
        if (jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(reader.getBackPack(bp, slot, false), ZezeniaHandler.base), 4).getInt(0) == 0) {
            return false;
        }
        //otherwise return whether or not the slot is already full
        return jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(reader.getBackPack(bp, slot, false), ZezeniaHandler.base), 4).getInt(0) < 100;
    }

    /*
     Stacks similar items in the loot bp
     */
    private void stackItems() {
        System.out.println("Stacking items");

        int oldDelay = reader.robot.getAutoDelay();
        int stackDelay = 20;

        int numBPS = Core.Core.returnNumberOfBackpacks();
        int lootBPX = dimensions.width - 130;
        int lootBPY = (numBPS * 90) + extendedBPVerticalSize + 105;

        //if there is nothing to stack, return
        if (!lootInSlot(Core.Core.returnNumberOfBackpacks() + 1, 1)) {
            return;
        }

        //set robot new timing
        reader.robot.setAutoDelay(stackDelay);

        //and if the first and third slots contain the same type of item
        if (slotsHaveSameItems(numBPS + 1, 1, 3)) {
            System.out.println("first and third slots can be stacked");
            //if the third slot is a stack of less than 100
            if (canBeStacked(numBPS + 1, 3)) {
                reader.robot.mouseMove(lootBPX, lootBPY);
                reader.robot.keyPress(KeyEvent.VK_CONTROL);
                reader.robot.mousePress(MouseEvent.BUTTON1_MASK);
                reader.robot.mouseMove(lootBPX + 80, lootBPY);
                reader.robot.mouseRelease(MouseEvent.BUTTON1_MASK);
                reader.robot.keyRelease(KeyEvent.VK_CONTROL);
                reader.robot.delay(20);
                reader.robot.keyPress(KeyEvent.VK_ENTER);
                reader.robot.keyRelease(KeyEvent.VK_ENTER);
                reader.robot.setAutoDelay(oldDelay);
            }
        }
        //if the first and second slots are the same item
        if (slotsHaveSameItems(numBPS + 1, 1, 2)) {
            System.out.println("first and second slots can be stacked");
            //if the second slot has less than 100 items
            if (canBeStacked(numBPS + 1, 2)) {
                reader.robot.mouseMove(lootBPX, lootBPY);
                reader.robot.keyPress(KeyEvent.VK_CONTROL);
                reader.robot.mousePress(MouseEvent.BUTTON1_MASK);
                reader.robot.mouseMove(lootBPX + 40, lootBPY);
                reader.robot.mouseRelease(MouseEvent.BUTTON1_MASK);
                reader.robot.keyRelease(KeyEvent.VK_CONTROL);
                reader.robot.delay(20);
                reader.robot.keyPress(KeyEvent.VK_ENTER);
                reader.robot.keyRelease(KeyEvent.VK_ENTER);
                reader.robot.setAutoDelay(oldDelay);
            }
        }

        //finally, check the 2nd and third after stacking the previous two
        if (slotsHaveSameItems(numBPS + 1, 2, 3)) {
            System.out.println("second and third slots can be stacked");
            //if the third slot has less than 100 items
            if (canBeStacked(numBPS + 1, 3)) {
                reader.robot.mouseMove(lootBPX + 40, lootBPY);
                reader.robot.keyPress(KeyEvent.VK_CONTROL);
                reader.robot.mousePress(MouseEvent.BUTTON1_MASK);
                reader.robot.mouseMove(lootBPX + 80, lootBPY);
                reader.robot.mouseRelease(MouseEvent.BUTTON1_MASK);
                reader.robot.keyRelease(KeyEvent.VK_CONTROL);
                reader.robot.delay(20);
                reader.robot.keyPress(KeyEvent.VK_ENTER);
                reader.robot.keyRelease(KeyEvent.VK_ENTER);
                reader.robot.setAutoDelay(oldDelay);
            }
        }
    }

    /*
     Close the open corpse
     */
    private void closeCorpses() {
        System.out.println("Closing a corpse.");
        reader.robot.mouseMove(dimensions.width - 10, ((Core.Core.returnNumberOfBackpacks() + 2) * 90) + 210);
        reader.robot.mousePress(MouseEvent.BUTTON1_MASK);
        reader.robot.mouseRelease(MouseEvent.BUTTON1_MASK);
        reader.robot.delay(100);
    }

    public boolean contains(final Color[] array, final Color value) {
        for (final Color i : array) {
            if (i.getRGB() == value.getRGB()) {
                return true;
            }
        }
        return false;
    }
}
