package Interface;

import javax.swing.*;
import java.awt.*;

import static Utils.SwingUtils.createJFrame;
import static Utils.SwingUtils.translateLineToHtml;

public class ControlWindow {
    public static void openControlWindow() {
        JFrame mainFrame = createJFrame(500, 400, "Control Window", true, true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(2, 1));

        JButton dupeIpButton = new JButton(translateLineToHtml("Check for dupe IPs, Errors,\nUnused two letter variables in\nCraftBook Variables Config"));
        JButton addFileButton = new JButton(translateLineToHtml("Add file path for data-files\nUsage Tutorial"));

        Font font = new Font("Courier New", Font.BOLD, 20);
        dupeIpButton.setFont(font);
        addFileButton.setFont(font);

        Color foreground = new Color(98, 201, 212);
        dupeIpButton.setForeground(foreground);
        addFileButton.setForeground(foreground);

        Color background = Color.DARK_GRAY;
        dupeIpButton.setBackground(background);
        addFileButton.setBackground(background);

        mainFrame.add(addFileButton);
        mainFrame.add(dupeIpButton);

        mainFrame.revalidate();

        dupeIpButton.addActionListener(listener -> VariablesInterface.openVariablesWindow());

        addFileButton.addActionListener(listener -> FileAdderInterface.openFileAdderWindow());
    }
}
