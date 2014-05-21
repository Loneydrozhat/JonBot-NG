package Core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;

public class SaveLoader {

    public static JFileChooser fileChooser = new JFileChooser();
    private static BufferedReader reader;
    private static BufferedWriter writer;
    private static final File fileDir = new File(System.getProperty("user.home") + "\\Documents\\");
    private static final File file = new File(fileDir, "JonBotNGSettings.txt");

    /*
     Saves the users settings to JonBotNGSettings.txt in the users 
     'My Documents' folder.
     */
    public static void saveSettings() {
        try {
            file.createNewFile();
        } catch (IOException ex) {
            file.mkdirs();
        }
        try {
            writer = new BufferedWriter(new FileWriter(file));

            //save the healing settings
            writer.write(GUI.GUI.highHealBox.getText() + "\n");
            writer.write(GUI.GUI.lowHealBox.getText() + "\n");
            writer.write(GUI.GUI.manaRestoreBox.getText() + "\n");
            
            //save the misc settings
            writer.write(GUI.GUI.spellTrainingBox.getText() + "\n");
            
            //save the backpack settings
            writer.write(GUI.GUI.mainBPSelector.getSelectedIndex()+"\n");
            writer.write(GUI.GUI.manaBPSelector.getSelectedIndex()+"\n");
            writer.write(GUI.GUI.healthBPSelector.getSelectedIndex()+"\n");
            writer.write(GUI.GUI.arrowBPSelector.getSelectedIndex()+"\n");
            writer.write(GUI.GUI.foodBPSelector.getSelectedIndex()+"\n");
            writer.write(GUI.GUI.lootBPSelector.getSelectedIndex()+"\n");
            writer.write(GUI.GUI.sideSelector.getSelectedIndex()+"\n");

            writer.flush();
            writer.close();
        } catch (IOException ex) {
            GUI.GUI.debug.append("Error opening the settings for for writing.\n");
        }

    }

    /*
     Loads the users settings and sets all the bot's fields to the previously
     saved values.
     */
    public static void loadSettings() {
        try {
            if (file.exists()) {
                reader = new BufferedReader(new FileReader(file.getAbsolutePath()));

                //load the healing settings
                GUI.GUI.highHealBox.setText(reader.readLine());
                GUI.GUI.lowHealBox.setText(reader.readLine());
                GUI.GUI.manaRestoreBox.setText(reader.readLine());
                
                //load the misc settings
                GUI.GUI.spellTrainingBox.setText(reader.readLine());
                
                //load the backpack settings
                GUI.GUI.mainBPSelector.setSelectedIndex(Integer.valueOf(reader.readLine()));
                GUI.GUI.manaBPSelector.setSelectedIndex(Integer.valueOf(reader.readLine()));
                GUI.GUI.healthBPSelector.setSelectedIndex(Integer.valueOf(reader.readLine()));
                GUI.GUI.arrowBPSelector.setSelectedIndex(Integer.valueOf(reader.readLine()));
                GUI.GUI.foodBPSelector.setSelectedIndex(Integer.valueOf(reader.readLine()));
                GUI.GUI.lootBPSelector.setSelectedIndex(Integer.valueOf(reader.readLine()));
                GUI.GUI.sideSelector.setSelectedIndex(Integer.valueOf(reader.readLine()));

                reader.close();
            }
        } catch (FileNotFoundException ex) {
            GUI.GUI.debug.append("Error opening settings file for reading.\n");
        } catch (IOException ex) {
            GUI.GUI.debug.append("Error closing settings file after reading.\n");
        }
    }
}
