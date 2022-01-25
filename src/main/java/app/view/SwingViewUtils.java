package app.view;

import java.awt.*;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.JTextComponent;

import org.jetbrains.annotations.NotNull;
import com.formdev.flatlaf.FlatIntelliJLaf;

public final class SwingViewUtils {

    private SwingViewUtils() {
    }

    public static void setLookAndFeelToSystemDefault() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }

	public static void setLookAndFeelToFlatLaf() {
		FlatIntelliJLaf.setup();
	}

    public static void setDefaultFont() {
        setUIFont(new FontUIResource(null, Font.PLAIN, 14));
    }

    public static void setUIFont(FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource)
                UIManager.put(key, f);
        }
    }

    public static void runAndShowWindow(JFrame frame) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.pack();
                setCenteredToGoldenRatio(frame);
                frame.setVisible(true);
            }
        });
    }

    /**
     * Callable after swing frame.pack() function to center application window.
     *
     * @param frame Swing's JFrame to be centered.
     */
    public static void setCenteredToGoldenRatio(JFrame frame) {
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = (int) screenDimension.getHeight();
        int screenWidth = (int) screenDimension.getWidth();

        int frameHeight = frame.getHeight();
        int frameWidth = frame.getWidth();

        int goldenRatioHeight = (int) ((screenHeight - frameHeight) * 0.38);

        frame.setLocation((screenWidth / 2) - (frameWidth / 2), goldenRatioHeight);
    }

    public static void showErrorMessageDialog(@NotNull JFrame parentFrame, @NotNull String message) {
        JOptionPane.showMessageDialog(parentFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void addChangeListenerToJTextComponentOnChange(@NotNull JTextComponent text, @NotNull Runnable runnable) {

        text.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
            }
            public void removeUpdate(DocumentEvent e) {
            }
            public void insertUpdate(DocumentEvent e) {
                runnable.run();
            }
        });
    }

}