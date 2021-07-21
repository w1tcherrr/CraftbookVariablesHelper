package Interface;


import javax.swing.*;
import java.awt.*;
import java.io.File;

import static Utils.FileInputReader.*;
import static Utils.SwingUtils.createJFrame;
import static Utils.SwingUtils.createJTextPane;
import static Utils.Utils.findFile;

public class FileAdderInterface {

    static String directory;

    public static String getDirectory() {
        return directory;
    }

    public static void openFileAdderWindow() {
        JFrame addFileFrame = createJFrame(640, 640, "Add Files", false, true);
        addFileFrame.setLayout(new BorderLayout(2, 1));

        String instruction = """
                To add your "variables.yml" config simply put it in the directory you specify in the text-field below.
                You must enter the directory path of the files (on your local computer) in the field below for the program to be able to read the files.
                This file path must NOT be the path to a file itself but only to the folder containing them.
                One such input could be: "C:/Users/MyUserName/Documents/Data/".
                
                The program will create a folder inside of the specified folder to store its data.
                      
                Once you press the "Specify File Path" Button the Data will be read into the program.
                This might take a few seconds. If the inputline reads "SUCCESSFULLY UPDATED PATH" the file you input was found correctly.""";

        Font font = new Font("Courier New", Font.BOLD, 21);
        JTextPane textPaneTitle = createJTextPane(instruction, Color.WHITE, Color.DARK_GRAY, font, false, true);

        JTextField inputField = new JTextField();
        inputField.setFont(font);

        JButton specifyFilePathButton = new JButton("Specify File Path");
        specifyFilePathButton.setFont(font);

        addFileFrame.add(textPaneTitle, BorderLayout.PAGE_START);
        addFileFrame.add(inputField, BorderLayout.CENTER);
        addFileFrame.add(specifyFilePathButton, BorderLayout.PAGE_END);

        specifyFilePathButton.addActionListener(listener -> {
            String inputPath = inputField.getText().trim().replace("\\", "/");
            if (inputPath.charAt(inputPath.length() - 1) == '/') {
                inputPath = inputPath.substring(0, inputPath.length() - 1);
            }

            File file = new File(inputPath);
            if (file.exists() && file.isDirectory()) {
                directory = inputPath + "/variabletooldata";
                File directory = new File(getDirectory());
                directory.mkdirs();

                if (findFile("variables.yml", file) != null) {
                    File variablesYml = findFile("variables.yml", file);
                    if (variablesYml != null) {
                        inputField.setText("SUCCESSFULLY FOUND VARIABLES.YML FILE!");
                        readYmlData(variablesYml.getAbsolutePath());
                    }
                    return;
                }
                inputField.setText("THE VARIABLES.YML WASN'T FOUND IN THE SPECIFIED DIRECTORY!");
                return;
            }

            inputField.setText("YOU MUST INPUT A DIRECTORY, NOT A FILE!");
        });
    }
}
