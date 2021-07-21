package Interface;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static Calculation.CraftbookVariableHelper.*;
import static Utils.SwingUtils.*;

public class VariablesInterface {
    public static void openVariablesWindow() {

        JFrame dupeIpWindow = createJFrame(600, 1000, "Find Dupe-Variables and unused two-letter Variables", false, true);
        dupeIpWindow.setLayout(new GridLayout(1, 1));
        BorderLayout layout = new BorderLayout(4, 1);
        JPanel panel = new JPanel(layout);
        Font font = new Font("Courier New", Font.BOLD, 24);

        String base = """
                When you press the button all duped
                variables in your file and unused
                two-letter variables in the file
                "variables.txt" will be output.
                Errors in your variable.yml file
                will also be checked.""";

        JTextPane textPaneTitle = createJTextPane(base, Color.WHITE, Color.DARK_GRAY, font, false, true);

        Font derivedFont = font.deriveFont(16.0f);
        JTextPane textArea = createJTextPane("", null, null, derivedFont, false, false);

        JButton dupeVariableButton = new JButton("Find duped/unused Variables!");
        dupeVariableButton.setBorderPainted(false);
        dupeVariableButton.setFocusable(false);

        panel.add(textPaneTitle, BorderLayout.PAGE_START);
        panel.add(textArea, BorderLayout.CENTER);
        panel.add(dupeVariableButton, BorderLayout.PAGE_END);
        dupeIpWindow.add(panel);

        dupeVariableButton.addActionListener(listener -> {
            String[] dupedVariables = getDupedVariables();
            String[] unusedVariables = getUnusedVariables();
            List<String[]> mistakesInConfig = findTyposInConfig();
            StringBuilder text = new StringBuilder();


            if (dupedVariables.length == 0) {
                text.append("No duped Variables were found!\n");
            } else {
                text.append("Duped IDs:\n");
                for (String dupedVariable : dupedVariables) {
                    text.append(dupedVariable);
                }
                text.append("\n");
            }
            text.append("\n\n");


            if (unusedVariables.length == 0) {
                text.append("No unused two-letter Variables were found!\n");
            } else {
                text.append("Unused two-letter variables:");
                for (String unusedVariable : unusedVariables) {
                    text.append(unusedVariable).append(" ");
                }
                text.append("\n");
            }
            text.append("\n\n");


            if (mistakesInConfig.size() == 0) {
                text.append("No mistakes found in config!");
            } else {
                text.append("Mistakes found:\n");
                for (int i = 0; i < mistakesInConfig.size(); i++) {
                    String[] strings = mistakesInConfig.get(i);
                    if (strings.length < 2) {
                        text.append("Error here: ").append(Arrays.toString(strings));
                        continue;
                    }
                    text.append("Error ").append(i).append(": ").append(strings[0]).append(" in Variable: ").append(strings[1]).append("\n");
                }
            }


            textArea.setText(text.toString());
        });
    }
}
