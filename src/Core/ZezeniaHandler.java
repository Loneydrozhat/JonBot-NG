package Core;

import JNA.JNACore;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;

public class ZezeniaHandler {

    private static ZezeniaHandler instance = null;

    public Robot robot;

    //All the offsets for the various zezenia client informations
    public static final int base = 0x400000;
    private static final int[] xCoord = {0x0019D4E0, 0x8, 0x14, 0xe11c, 0xc, 0x4};
    private static final int[] yCoord = {0x0019D4E0, 0x8, 0x14, 0xe11c, 0xc, 0x8};
    private static final int[] zCoord = {0x0019D4E0, 0x8, 0x14, 0xe11c, 0xc, 0xc};
    private static final int[] pid = {0x0019D4E0, 0x8, 0x14, 0xe11c, 0xc, 0x10};
    private static final int[] lightValue = {0x0019D4E0, 0x8, 0x14, 0xe11c, 0xc, 0x9c};
    private static final int[] hpPercent = {0x0019D4E0, 0x8, 0x14, 0xe11c, 0xc, 0x8c};
    private static final int[] manaValue = {0x0019D4E0, 0x8, 0x9c, 0x25ac, 0x8};
    private static final int[] playerTarget = {0x0019D4E0, 0x8, 0x14, 0xe128};
    private static final int[] playerMouseOver = {0x0019D4E0, 0x8, 0x20, 0x4, 0x14, 0xe130};
    private static final int[] abilityWindowX = {0x0019D4E0, 0x8, 0x9c, 0xc, 0x28};
    private static final int[] abilityWindowY = {0x0019D4E0, 0x8, 0x9c, 0xc, 0x2c};
    private static final int[] inventoryWindowX = {0x0019D4E0, 0x8, 0x98, 0xc, 0x28};
    private static final int[] inventoryWindowY = {0x0019D4E0, 0x8, 0x98, 0xc, 0x2c};
    private static final int[] lastWindowX = {0x0019D07C, 0x4, 0x4, 0x1c, 0x8, 0x28};
    private static final int[] lastWindowY = {0x0019D07C, 0x4, 0x4, 0x1c, 0x8, 0x2c};
    private static final int[] playerDirectionLooking = {0x0019D4E0, 0x8, 0x14, 0xe11c, 0xc, 0x90};
    private static final int[] lastToEnterScreen = {0x0019D4E0, 0x8, 0x14, 0xe118, 0x8, 0x10};
    private static final int[] tileSize = {0x0019D4E0, 0x8, 0x14, 0x8};

    //backpack offsets
    private final int[] firstBackpackSlotGFX = {0x0019D4E0, 0x8, 0x3c, 0x3c, 0x4};
    private final int[] numberInSlotOffset = {0x0, 0x0, 0x0, 0x0, 0x4};
    private final int[] nextBpOffset = {0x0, 0x0, 0x4, 0x0, 0x0};
    private final int[] bpSlotOffset = {0x0, 0x0, 0x0, 0x4, 0x0};

    private static JNACore jnaCore;

    //Backpack Identifiers
    public int mainBP = 1;
    public int healthPotionBP = 0;
    public int manaPotionBP = 0;
    public int arrowBP = 0;
    public int lootBP = 2;
    public int foodBP = 0;


    /*
     Private constructor for singleton
     */
    private ZezeniaHandler() {
        //get instances of objects that we will need
        jnaCore = JNACore.getInstance();
        //initialize all values that we will need
        //initilize the robot
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            GUI.GUI.debug.append("Robot wouldn't start for some reason\n");
        }

    }

    /*
     Returns one and only one instance of ZezeniaHandler
     */
    public static ZezeniaHandler getInstance() {
        if (instance == null) {
            instance = new ZezeniaHandler();
        }
        return instance;
    }

    /*
     This method returns an array of offsets that can be used to reach the specified backpack slot
     */
    public int[] getBackPack(int bpNumber, int slot, boolean gfx) {
        int[] result = firstBackpackSlotGFX.clone();//set it to its starting value

        boolean yesGfx = gfx;

        int i = bpNumber;
        while (i > 1) {//apply the offset till we reach the specified backpack location
            result[2] = result[2] + nextBpOffset[2];
            i--;
        }

        i = slot;

        while (i > 1) {//apply the slotOffset until we reach the specified backpack slot :]
            result[3] = result[3] + bpSlotOffset[3];
            i--;
        }

        //and finally, if we want the number of items in that slot, and not its gfx id, return such
        if (yesGfx == false) {
            result[4] = result[4] + numberInSlotOffset[4];
        }
        //does nothing, but for some reason, it solves a problem
        if (yesGfx == false) {
        }

        return result;
    }

    /*
     Returns the current size of tiles.
     */
    public int getTileSize() {
        return jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(tileSize, base), 4).getInt(0);
    }

    /*
     Returns the center of the screen, directly over the player's character.
     */
    public Point returnCenterScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        return new Point((int) (width / 2), (int) (height / 2) - 40);
    }

    /*
     Returns players current xCoordinate
     */
    public int getXCoord() {
        return jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(xCoord, base), 4).getInt(0);
    }

    /*
     Returns players current yCoordinate
     */
    public int getYCoord() {
        return jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(yCoord, base), 4).getInt(0);
    }

    /*
     Returns players current zCoordinate
     */
    public int getZCoord() {
        return jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(zCoord, base), 4).getInt(0);
    }

    /*
     Returns current health percent
     */
    public int getHealth() {
        return jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(hpPercent, base), 4).getInt(0);
    }

    /*
     Returns the players current mana
     */
    public Integer getMana() {
        return jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(manaValue, base), 4).getInt(0);
    }

    /*
     Returns players identifier
     */
    public int getPID() {
        return jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(pid, base), 4).getInt(0);
    }

    /*
     Returns the ID of the last creature to enter the screen
     */
    public int lastToEnterScreenID() {
        return jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(lastToEnterScreen, base), 4).getInt(0);
    }

    /*
     Set the players light value to something high enough to illuminate the whole screen
     */
    public void setLight() {
        //value to write into the clients memory
        byte[] setTo = {0b100000};
        jnaCore.writeMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(lightValue, base), setTo);
    }

    /*
     Returns the last opened windows x coordinate
     */
    public int getLastWindowX() {
        return jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(lastWindowX, base), 4).getInt(0);
    }

    /*
     Returns the last opened windows y coordinate
     */
    public int getLastWindowY() {
        return jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(lastWindowY, base), 4).getInt(0);
    }

    /*
     Returns the inventory windows x coordinate
     */
    public int getInventoryX() {
        return jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(inventoryWindowX, base), 4).getInt(0);
    }

    /*
     Returns the inventory windows y coordinate
     */
    public int getInventoryY() {
        return jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(inventoryWindowY, base), 4).getInt(0);
    }

    public int getAbilityWindowX() {
        return jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(abilityWindowX, base), 4).getInt(0);
    }

    public int getAbilityWindowY() {
        return jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(abilityWindowY, base), 4).getInt(0);
    }

    /*
     Returns current targetID
     */
    public int getTargetID() {
        return jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(playerTarget, base), 4).getInt(0);
    }

    /*
     Returns the current target the mouse is hovering over.
     */
    public int getMouseTargetID() {
        return jnaCore.readMemory(jnaCore.zezeniaProcessHandle, jnaCore.findDynAddress(playerMouseOver, base), 4).getInt(0);
    }

    /*
     This function gives a close approximation of the in memory value of the given
     int would be. This would be used to compare in memory current mana value
     to a value that the user wants to heal at.
     */
    public double Spline_evaluation(int x) {
        double x_in = x;
        double t[] = {5.0000000000000000E+00, 5.0000000000000000E+00, 5.0000000000000000E+00,
            5.0000000000000000E+00, 5.0000000000000000E+01, 1.5000000000000000E+02,
            1.6500000000000000E+02, 1.7000000000000000E+02, 2.0500000000000000E+02,
            2.0500000000000000E+02, 2.0500000000000000E+02, 2.0500000000000000E+02};

        double coeff[] = {1.2289603920032461E+09, 1.2556700502643628E+09, 1.2702220618806999E+09,
            1.2773180063197584E+09, 1.2818508489977622E+09, 1.2841138415471385E+09, 1.2844578534381235E+09,
            1.2852910259565022E+09, 1.2306228883422570E+09, 1.3043808883294861E+09, 1.2714029817879632E+09,
            1.2860653411909285E+09};

        int n = 12;
        int k = 3;

        double h[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

        double hh[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

        int i, j, li, lj, ll;
        double f, temp;

        int k1 = k + 1;
        int l = k1;
        int l1 = l + 1;

        while ((x_in < t[l - 1]) && (l1 != (k1 + 1))) {
            l1 = l;
            l = l - 1;
        }

        while ((x_in >= t[l1 - 1]) && (l != (n - k1))) {
            l = l1;
            l1 += 1;
        }
        h[0] = 1.0;
        for (j = 1; j < k + 1;
                j++) {
            for (i = 0; i < j;
                    i++) {
                hh[i] = h[i];
            }
            h[0] = 0.0;
            for (i = 0; i < j;
                    i++) {
                li = l + i;
                lj = li - j;
                if (t[li] != t[lj]) {
                    f = hh[i] / (t[li] - t[lj]);
                    h[i] = h[i] + f * (t[li] - x_in);
                    h[i + 1] = f * (x_in - t[lj]);
                } else {
                    h[i + 1] = 0.0;

                }
            }
        }
        temp = 0.0;
        ll = l - k1;
        for (j = 0; j < k1;
                j++) {
            ll = ll + 1;
            temp = temp + coeff[ll - 1] * h[j];
        }

        return (long) (temp + 0.5);
    }

}
