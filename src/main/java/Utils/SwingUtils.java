package Utils;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class SwingUtils {
    public static JFrame createJFrame(int width, int height, String title, boolean resizable, boolean visible) {
        JFrame frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setResizable(resizable);
        frame.setVisible(visible);
        frame.setLocationRelativeTo(null);
        return frame;
    }

    public static JTextPane createJTextPane(String text, Color foreground, Color background, Font font, boolean editable, boolean centered) {
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, centered ? StyleConstants.ALIGN_CENTER : StyleConstants.ALIGN_LEFT);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        textPane.setText(text);

        if (foreground != null)
            textPane.setForeground(foreground);
        if (background != null)
            textPane.setBackground(background);
        if (font != null)
            textPane.setFont(font);
        textPane.setEditable(editable);
        return textPane;
    }

    public static String translateLineToHtml(String text) {
        return "<html>" + "<center>" + text.replaceAll("\\n", "<br>") + "</center>" + "</html>";
    }
}
