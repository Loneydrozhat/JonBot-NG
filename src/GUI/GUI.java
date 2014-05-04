package GUI;

import Core.Core;
import Core.SaveLoader;
import Core.ZezeniaHandler;
import JNA.JNACore;
import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultCaret;

public class GUI extends javax.swing.JFrame implements HotkeyListener {

    private static Core core;
    private static JNACore jnaCore;
    private static ZezeniaHandler reader;
    private static Timer timer;
    private static TimerTask task;

    private static boolean botMove = true;
    public static boolean isPaused = true;
    public static boolean caveBotIsPaused = true;
    public static boolean huntingStarted = false;
    public static int startDelay = 3000;
    public static long healStartTime = 0;
    public static long botStartTime = 0;

    private BufferedImage ico = null;

    /*
     GUI constructor. Here I initialize JIntellitype Libraries for hotkey support
     and the bots other Core classes.
     */
    public GUI() {

        //Load JIntellitype for hotkey support
        int bitType = Integer.valueOf(System.getProperty("sun.arch.data.model"));
        if (bitType == 32) {
            JIntellitype.setLibraryLocation("C:\\Windows\\System32\\JIntellitype.dll");
            System.loadLibrary("JIntellitype");

        }
        if (bitType == 64) {
            JIntellitype.setLibraryLocation("C:\\Windows\\System32\\JIntellitype64.dll");
            System.loadLibrary("JIntellitype64");
        }
        if (bitType != 32 && bitType != 64) {
            JIntellitype.setLibraryLocation("C:\\Windows\\System32\\JIntellitype.dll");
            System.loadLibrary("JIntellitype");
        }

        //Set the icon for the GUI. :D
        try {
            ico = ImageIO.read(getClass().getResourceAsStream("/Assets/jicon.png"));
            setIconImage(ico);
        } catch (IOException ex) {
            System.err.println("icon file is absent?");
        }
        jnaCore = JNACore.getInstance();
        jnaCore.getFirstProcesses();
        //Iniitialize core object which in turn initializes all other JonBotNG classes
        core = new Core();
        //Initialize zezeniaReaderWriter
        reader = ZezeniaHandler.getInstance();

        //Finish constructing the GUI
        initComponents();

        //load settings
        SaveLoader.loadSettings();

        //Add the hotkeys to listen for and finally the listener to listen for them.
        JIntellitype.getInstance().registerHotKey(2, 0, KeyEvent.VK_PAUSE);//pause key
        JIntellitype.getInstance().registerHotKey(1, 0, 45);//insert key
        JIntellitype.getInstance().registerHotKey(3, JIntellitype.MOD_ALT, (int) 'A');//Alt A
        JIntellitype.getInstance().addHotKeyListener((HotkeyListener) this);

        //This task makes the heart beat every 1ms
        timer = new Timer();
        task = new TimerTask() {

            /*
             Cycle through the core loop.
             */
            @Override
            public void run() {
                core.cycle();
            }
        };

        timer.scheduleAtFixedRate(task, 0, 1);//task repeats every 1ms
    }

    /*
     Here we set the hotkeys and their respective actions to be performed if
     triggered.
     */
    @Override
    public void onHotKey(int aIdentifier) {
        if (aIdentifier == 1) {//pause button is pressed
            togglePause();
        }
        if (aIdentifier == 2) {//insert button is pressed
            togglePauseCaveBot();
        }
        if (aIdentifier == 3) {//alt+a is pressed
            //add a waypoint to the script at the current location.
            caveScriptArea.append("MOVE," + reader.getXCoord() + "," + reader.getYCoord()
                    + "," + reader.getZCoord() + "\n");
            debug.append("Waypoint added to script.\n");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        highHealBox = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lowHealBox = new javax.swing.JTextField();
        highHealCheck = new javax.swing.JCheckBox();
        lowHealCheck = new javax.swing.JCheckBox();
        healingTab = new javax.swing.JScrollPane();
        debug = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        manaRestoreBox = new javax.swing.JTextField();
        manaRestoreCheck = new javax.swing.JCheckBox();
        healthPotionCheck = new javax.swing.JCheckBox();
        spellCheck = new javax.swing.JCheckBox();
        startButton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        miscTab = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        spellTrainingBox = new javax.swing.JTextField();
        spellTrainingCheck = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        lightCheck = new javax.swing.JCheckBox();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 32767));
        playerAlertCheck = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        foodCheck = new javax.swing.JCheckBox();
        jLabel33 = new javax.swing.JLabel();
        ammoCheck = new javax.swing.JCheckBox();
        lootCheck = new javax.swing.JCheckBox();
        jLabel30 = new javax.swing.JLabel();
        caveBotTab = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        caveScriptArea = new javax.swing.JTextArea();
        leftButton = new javax.swing.JButton();
        upButton = new javax.swing.JButton();
        downButton = new javax.swing.JButton();
        rightButton = new javax.swing.JButton();
        centerButton = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel7 = new javax.swing.JLabel();
        caveStartButton = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        deleteLinebutton = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        sayBox = new javax.swing.JTextField();
        sayButton = new javax.swing.JButton();
        exactButton = new javax.swing.JButton();
        bpTab = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        mainBPSelector = new javax.swing.JComboBox();
        manaBPSelector = new javax.swing.JComboBox();
        healthBPSelector = new javax.swing.JComboBox();
        arrowBPSelector = new javax.swing.JComboBox();
        foodBPSelector = new javax.swing.JComboBox();
        lootBPSelector = new javax.swing.JComboBox();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        hotKeyTab = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        loadScriptMenuButton = new javax.swing.JMenuItem();
        saveScriptMenuButton = new javax.swing.JMenuItem();
        saveMenuButton = new javax.swing.JMenuItem();

        jToolBar1.setRollover(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JonBot - NG");
        setName("window"); // NOI18N
        setResizable(false);

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jLabel1.setText("High Heal %");

        highHealBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        highHealBox.setText("80");
        highHealBox.setToolTipText("Specify the % of health you want to be your High Heal.");

        jLabel2.setText("Low Heal %");

        lowHealBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lowHealBox.setText("25");
        lowHealBox.setToolTipText("Specify the % of health you want to be your Low Heal.");

        highHealCheck.setText("Enabled");

        lowHealCheck.setText("Enabled");

        debug.setColumns(20);
        debug.setLineWrap(true);
        debug.setRows(5);
        debug.setWrapStyleWord(true);
        debug.setFocusable(false);
        DefaultCaret debugCaret = (DefaultCaret)debug.getCaret();
        debugCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        healingTab.setViewportView(debug);

        jLabel3.setText("Mana Restore %");

        manaRestoreBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        manaRestoreBox.setText("50");
        manaRestoreBox.setToolTipText("Specify the exact number you want your Mana Restored at.");

        manaRestoreCheck.setText("Enabled");

        healthPotionCheck.setText("Use Health Potions");

        spellCheck.setText("Use Healing Spells");

        startButton.setText("Start");
        startButton.setToolTipText("Pressing Start begins all functionality of the bot except the cavebot.");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        jLabel11.setText("Start Bot");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(healingTab)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lowHealBox)
                            .addComponent(highHealBox)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(highHealCheck)
                            .addComponent(lowHealCheck))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(manaRestoreBox, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(manaRestoreCheck)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spellCheck)
                            .addComponent(healthPotionCheck))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(startButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(highHealBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(highHealCheck)
                    .addComponent(manaRestoreBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(manaRestoreCheck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lowHealBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lowHealCheck))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(healthPotionCheck)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spellCheck)
                    .addComponent(startButton))
                .addGap(18, 18, 18)
                .addComponent(healingTab, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Heailing", jPanel1);

        jLabel6.setText("Spell Training %");

        spellTrainingBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        spellTrainingBox.setText("95");
        spellTrainingBox.setToolTipText("If you mana is above the given percent, a spell will be cast.");

        spellTrainingCheck.setText("Enabled");

        jLabel10.setText("Light Hack");

        lightCheck.setText("Enabled");

        filler3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        playerAlertCheck.setText("Enabled");

        jLabel9.setText("Player Alert");

        jLabel32.setText("Eat Food");

        foodCheck.setText("Enabled");

        jLabel33.setText("Ammo Restocker");

        ammoCheck.setText("Enabled");

        lootCheck.setText("Enabled");

        jLabel30.setText("Looting");

        javax.swing.GroupLayout miscTabLayout = new javax.swing.GroupLayout(miscTab);
        miscTab.setLayout(miscTabLayout);
        miscTabLayout.setHorizontalGroup(
            miscTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(miscTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(miscTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(miscTabLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(274, 274, 274))
                    .addGroup(miscTabLayout.createSequentialGroup()
                        .addGroup(miscTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(filler3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(miscTabLayout.createSequentialGroup()
                                .addGroup(miscTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(miscTabLayout.createSequentialGroup()
                                        .addComponent(spellTrainingBox, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(spellTrainingCheck))
                                    .addGroup(miscTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(miscTabLayout.createSequentialGroup()
                                            .addComponent(jLabel30)
                                            .addGap(82, 82, 82)
                                            .addComponent(lootCheck))
                                        .addGroup(miscTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(miscTabLayout.createSequentialGroup()
                                                .addComponent(jLabel33)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                                                .addComponent(ammoCheck))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, miscTabLayout.createSequentialGroup()
                                                .addGroup(miscTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel32)
                                                    .addComponent(jLabel9))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(miscTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(foodCheck, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(playerAlertCheck, javax.swing.GroupLayout.Alignment.TRAILING)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, miscTabLayout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lightCheck)))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(16, 16, 16))))
        );
        miscTabLayout.setVerticalGroup(
            miscTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(miscTabLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(miscTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lootCheck)
                    .addComponent(jLabel30))
                .addGap(18, 18, 18)
                .addGroup(miscTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(ammoCheck))
                .addGap(18, 18, 18)
                .addGroup(miscTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lightCheck))
                .addGap(18, 18, 18)
                .addGroup(miscTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(foodCheck))
                .addGap(11, 11, 11)
                .addGroup(miscTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(playerAlertCheck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(miscTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spellTrainingBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spellTrainingCheck))
                .addGap(23, 23, 23))
        );

        jTabbedPane1.addTab("Misc", miscTab);

        caveScriptArea.setColumns(20);
        caveScriptArea.setRows(5);
        caveScriptArea.setFocusable(false);
        jScrollPane3.setViewportView(caveScriptArea);

        leftButton.setText("Left");
        leftButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leftButtonActionPerformed(evt);
            }
        });

        upButton.setText("Up");
        upButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upButtonActionPerformed(evt);
            }
        });

        downButton.setText("Down");
        downButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downButtonActionPerformed(evt);
            }
        });

        rightButton.setText("Right");
        rightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rightButtonActionPerformed(evt);
            }
        });

        centerButton.setText("Center");
        centerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerButtonActionPerformed(evt);
            }
        });

        jToggleButton1.setText("Move");
        jToggleButton1.setToolTipText("Click to toggle between setting Movement Waypoints and Use Waypoints.");
        jToggleButton1.setEnabled(false);
        jToggleButton1.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent ev){
                botMove = !botMove;
                if(botMove==true)
                jToggleButton1.setText("Move");
                else
                jToggleButton1.setText("Use");
            }
        });

        jLabel7.setText("Move/Use Toggle");

        caveStartButton.setText("Start");
        caveStartButton.setEnabled(false);
        caveStartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caveStartButtonActionPerformed(evt);
            }
        });

        jLabel12.setText("Begin Cavebot");

        jLabel29.setText("Delete Last Line");

        deleteLinebutton.setText("Delete");
        deleteLinebutton.setToolTipText("Removes the last line from the script.");
        deleteLinebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteLinebuttonActionPerformed(evt);
            }
        });

        jLabel31.setText("Say This : ");

        sayBox.setText("Text To Say");
        sayBox.setEnabled(false);

        sayButton.setText("Say");
        sayButton.setToolTipText("Adds the text in the say text box below as a line in the script.");
        sayButton.setEnabled(false);
        sayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sayButtonActionPerformed(evt);
            }
        });

        exactButton.setText("Exact");
        exactButton.setToolTipText("Toggles between exact waypoints where precision is needed, or close waypoints where speed is preferred");
        exactButton.setEnabled(false);

        javax.swing.GroupLayout caveBotTabLayout = new javax.swing.GroupLayout(caveBotTab);
        caveBotTab.setLayout(caveBotTabLayout);
        caveBotTabLayout.setHorizontalGroup(
            caveBotTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(caveBotTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(caveBotTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(caveBotTabLayout.createSequentialGroup()
                        .addGroup(caveBotTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(exactButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(leftButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sayButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(caveBotTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(upButton, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(centerButton)
                            .addComponent(downButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(caveBotTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rightButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(deleteLinebutton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addGroup(caveBotTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(caveBotTabLayout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sayBox, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(caveBotTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(caveStartButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))))
                .addContainerGap())
        );
        caveBotTabLayout.setVerticalGroup(
            caveBotTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, caveBotTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(caveBotTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(upButton)
                    .addComponent(jLabel7)
                    .addComponent(jToggleButton1)
                    .addComponent(sayButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(caveBotTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(leftButton)
                    .addComponent(rightButton)
                    .addComponent(centerButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(caveBotTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(downButton)
                    .addComponent(jLabel29)
                    .addComponent(deleteLinebutton)
                    .addComponent(exactButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(caveBotTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(caveStartButton)
                    .addComponent(jLabel31)
                    .addComponent(sayBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Cavebot", caveBotTab);

        jLabel4.setText("Backpack # represents the ordering of the backpacks.");

        jLabel5.setText("Ex - BP 1 is the 1st opened bp. AKA the main bp.");

        jLabel8.setText("Ex - BP 2 is the 2nd opened bp and so on.");

        jLabel20.setText("Main BP");

        jLabel21.setText("Mana Pots");

        jLabel22.setText("Health Pots");

        mainBPSelector.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1"}));

        manaBPSelector.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"1","2","3","4","5","6"}));

        healthBPSelector.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"1","2","3","4","5","6"}));

        arrowBPSelector.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"1","2","3","4","5","6","Not Used" }));

        foodBPSelector.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1","2","3","4","5","6" ,"Not Used"}));

        lootBPSelector.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"1","2","3","4","5","6" }));

        jLabel26.setText("Arrows");

        jLabel27.setText("Food");

        jLabel28.setText("Loot");

        filler1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel23.setText("Loot BP = BP full of Empty BP's");

        jLabel24.setText("Makre sure you dont set the");

        jLabel25.setText("same option twice.");

        javax.swing.GroupLayout bpTabLayout = new javax.swing.GroupLayout(bpTab);
        bpTab.setLayout(bpTabLayout);
        bpTabLayout.setHorizontalGroup(
            bpTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bpTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bpTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filler1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                    .addGroup(bpTabLayout.createSequentialGroup()
                        .addGroup(bpTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(bpTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(foodBPSelector, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(arrowBPSelector, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(healthBPSelector, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(manaBPSelector, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lootBPSelector, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(mainBPSelector, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(31, 31, 31)
                        .addGroup(bpTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25))))
                .addContainerGap())
        );
        bpTabLayout.setVerticalGroup(
            bpTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bpTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bpTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mainBPSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(7, 7, 7)
                .addGroup(bpTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bpTabLayout.createSequentialGroup()
                        .addGroup(bpTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(manaBPSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(bpTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(healthBPSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)))
                    .addGroup(bpTabLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bpTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(arrowBPSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bpTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(foodBPSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bpTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lootBPSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel23))
                .addContainerGap())
        );

        jTabbedPane1.addTab("BP's", bpTab);

        jLabel14.setText("Pause Key");

        jLabel15.setText("Pauses the cavebot but not the healer.");

        jLabel16.setText("Insert Key");

        jLabel17.setText("Pauses the healer and the cavebot,");

        jLabel18.setText("Alt+A");

        jLabel19.setText("Adds your current position as a waypoint.");

        javax.swing.GroupLayout hotKeyTabLayout = new javax.swing.GroupLayout(hotKeyTab);
        hotKeyTab.setLayout(hotKeyTabLayout);
        hotKeyTabLayout.setHorizontalGroup(
            hotKeyTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hotKeyTabLayout.createSequentialGroup()
                .addGroup(hotKeyTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(hotKeyTabLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14))
                    .addGroup(hotKeyTabLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel15))
                    .addGroup(hotKeyTabLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel16))
                    .addGroup(hotKeyTabLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel17))
                    .addGroup(hotKeyTabLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel18))
                    .addGroup(hotKeyTabLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel19)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        hotKeyTabLayout.setVerticalGroup(
            hotKeyTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hotKeyTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addContainerGap(148, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Hotkeys", hotKeyTab);

        jMenu1.setText("File");

        loadScriptMenuButton.setText("Load Script");
        loadScriptMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadScriptMenuButtonActionPerformed(evt);
            }
        });
        jMenu1.add(loadScriptMenuButton);

        saveScriptMenuButton.setText("Save Script");
        saveScriptMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveScriptMenuButtonActionPerformed(evt);
            }
        });
        jMenu1.add(saveScriptMenuButton);

        saveMenuButton.setText("Save Settings");
        saveMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuButtonActionPerformed(evt);
            }
        });
        jMenu1.add(saveMenuButton);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
     When the button is pressed, toggle the paused state of the bot,
     */
    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        startButton.setEnabled(false);
        togglePause();
    }//GEN-LAST:event_startButtonActionPerformed

    /*
     Toggles the caveBot's paused state and sets hunting to true.
     */
    private void caveStartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caveStartButtonActionPerformed
        startBot();
        togglePauseCaveBot();
    }//GEN-LAST:event_caveStartButtonActionPerformed

    /*
     Sets huntingStarted to true, so that the cavebot will start interpreting the
     current script.
     */
    private void startBot() {
        startButton.setEnabled(false);
        //disable the bp customizing
        mainBPSelector.setEnabled(false);
        manaBPSelector.setEnabled(false);
        healthBPSelector.setEnabled(false);
        arrowBPSelector.setEnabled(false);
        foodBPSelector.setEnabled(false);
        lootBPSelector.setEnabled(false);
        //disable the caveStart button.
        caveStartButton.setEnabled(false);
        botStartTime = System.currentTimeMillis();
        if (!huntingStarted) {
            huntingStarted = true;
            CaveBot.CaveBot.getInstance().setScript();
        }
    }

    /*
     Saves the user's settings.
     */
    private void saveMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuButtonActionPerformed
        SaveLoader.saveSettings();
    }//GEN-LAST:event_saveMenuButtonActionPerformed

    /*
     Loads the user selected script and writes the script to the script area.
     */
    private void loadScriptMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadScriptMenuButtonActionPerformed
        int returnVal = SaveLoader.fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = SaveLoader.fileChooser.getSelectedFile();
            try {
                debug.setText("");
                debug.append("Script loaded : " + file.getName());
                //load the script into the scripting area
                caveScriptArea.read(new FileReader(file.getAbsolutePath()), null);
                //set the script in the caveBot so that it is ready to go
                CaveBot.CaveBot.getInstance().setScript();
            } catch (FileNotFoundException ex) {
                debug.append("File chosen could not be found.\n");
            } catch (IOException ex) {
                debug.append("File chosen could not be read from.\n");
            }
        } else {
            debug.append("Script loading was aborted.\n");
        }
    }//GEN-LAST:event_loadScriptMenuButtonActionPerformed

    /*
     Saves the users script to the user specified location on the user's system.
     */
    private void saveScriptMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveScriptMenuButtonActionPerformed
        int returnVal = SaveLoader.fileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = SaveLoader.fileChooser.getSelectedFile();
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(file));
                writer.write(caveScriptArea.getText());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                debug.append("Couldnt save script for some reason.");
            }
        }
    }//GEN-LAST:event_saveScriptMenuButtonActionPerformed

    /*
     Removes the last line from the caveScriptArea.
     Useful incase a waypoint is added by mistake.
     */
    private void deleteLinebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteLinebuttonActionPerformed
        debug.append("Line removed from script.\n");
        if (caveScriptArea.getText().isEmpty()) {
            caveScriptArea.setText("");
            return;
        }
        String[] tempArray = caveScriptArea.getText().split("\n");
        int x = 0;
        caveScriptArea.setText("");
        while (x < tempArray.length - 1) {
            caveScriptArea.append(tempArray[x] + "\n");
            x++;
        }
    }//GEN-LAST:event_deleteLinebuttonActionPerformed

    /*
     Adds the text in the sayBox as a caveBot script line to the current script
     */
    private void sayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sayButtonActionPerformed
        caveScriptArea.append("SAY," + sayBox.getText().replaceAll(",", "") + "\n");
    }//GEN-LAST:event_sayButtonActionPerformed

    /*
     Add a waypoint to the current script at the current location.
     */
    private void centerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_centerButtonActionPerformed
        caveScriptArea.append("MOVE," + reader.getXCoord() + "," + reader.getYCoord()
                + "," + reader.getZCoord() + "\n");
    }//GEN-LAST:event_centerButtonActionPerformed

    /*
     Add a waypoint to the current script to the left of the current location.
     */
    private void leftButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leftButtonActionPerformed
        caveScriptArea.append("MOVE," + (reader.getXCoord() - 1) + "," + reader.getYCoord()
                + "," + reader.getZCoord() + "\n");
    }//GEN-LAST:event_leftButtonActionPerformed

    /*
     Add a waypoint to the current script to the right of the current location.
     */
    private void rightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rightButtonActionPerformed
        caveScriptArea.append("MOVE," + (reader.getXCoord() + 1) + "," + reader.getYCoord()
                + "," + reader.getZCoord() + "\n");
    }//GEN-LAST:event_rightButtonActionPerformed

    /*
     Add a waypoint to the current script to the south of the current location.
     */
    private void downButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downButtonActionPerformed
        caveScriptArea.append("MOVE," + reader.getXCoord() + "," + (reader.getYCoord() + 1)
                + "," + reader.getZCoord() + "\n");
    }//GEN-LAST:event_downButtonActionPerformed

    /*
     Add a waypoint to the current script to the north of the current location.
     */
    private void upButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upButtonActionPerformed
        caveScriptArea.append("MOVE," + reader.getXCoord() + "," + (reader.getYCoord() - 1)
                + "," + reader.getZCoord() + "\n");
    }//GEN-LAST:event_upButtonActionPerformed

    /*
     Set the bot to paused and update the startButton's text.
     */
    private void togglePause() {
        if (startButton.getText().equals("Start")) {
            healStartTime = System.currentTimeMillis();
            caveStartButton.setEnabled(true);
        }
        isPaused = !isPaused;
        if (!isPaused) {
            startButton.setText("Pause");
        } else {
            startButton.setText("Running");
        }
    }

    /*
     Set the cavebot to paused and update the caveBot's start button.
     */
    public static void togglePauseCaveBot() {
        caveBotIsPaused = !caveBotIsPaused;
        System.out.println("CaveBot paused is : " + caveBotIsPaused);
        if (!caveBotIsPaused) {
            caveStartButton.setText("Running");
        } else {
            caveStartButton.setText("Paused");
        }
    }

    /*
     Main method that gets called when the bot is first started.
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.err.println(ex.toString());
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JCheckBox ammoCheck;
    public static javax.swing.JComboBox arrowBPSelector;
    private javax.swing.JPanel bpTab;
    private javax.swing.JPanel caveBotTab;
    public static javax.swing.JTextArea caveScriptArea;
    private static javax.swing.JButton caveStartButton;
    private javax.swing.JButton centerButton;
    public static javax.swing.JTextArea debug;
    private javax.swing.JButton deleteLinebutton;
    private javax.swing.JButton downButton;
    private javax.swing.JButton exactButton;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler3;
    public static javax.swing.JComboBox foodBPSelector;
    public static javax.swing.JCheckBox foodCheck;
    private javax.swing.JScrollPane healingTab;
    public static javax.swing.JComboBox healthBPSelector;
    public static javax.swing.JCheckBox healthPotionCheck;
    public static javax.swing.JTextField highHealBox;
    public static javax.swing.JCheckBox highHealCheck;
    private javax.swing.JPanel hotKeyTab;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton leftButton;
    public static javax.swing.JCheckBox lightCheck;
    private javax.swing.JMenuItem loadScriptMenuButton;
    public static javax.swing.JComboBox lootBPSelector;
    public static javax.swing.JCheckBox lootCheck;
    public static javax.swing.JTextField lowHealBox;
    public static javax.swing.JCheckBox lowHealCheck;
    public static javax.swing.JComboBox mainBPSelector;
    public static javax.swing.JComboBox manaBPSelector;
    public static javax.swing.JTextField manaRestoreBox;
    public static javax.swing.JCheckBox manaRestoreCheck;
    private javax.swing.JPanel miscTab;
    public static javax.swing.JCheckBox playerAlertCheck;
    private javax.swing.JButton rightButton;
    private javax.swing.JMenuItem saveMenuButton;
    private javax.swing.JMenuItem saveScriptMenuButton;
    private javax.swing.JTextField sayBox;
    private javax.swing.JButton sayButton;
    public static javax.swing.JCheckBox spellCheck;
    public static javax.swing.JTextField spellTrainingBox;
    public static javax.swing.JCheckBox spellTrainingCheck;
    private javax.swing.JButton startButton;
    private javax.swing.JButton upButton;
    // End of variables declaration//GEN-END:variables
}
