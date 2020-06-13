package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class MainFormUtility {

    private JFrame mainForm;

    public JFrame createMainForm() {
        mainForm = new JFrame("Main Frame");
        mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setCenteredToGoldenRatio(mainForm);
        return mainForm;
    }

    public Optional<Path> showFileChooserAndGetFolder(Path currentDirectory) {
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(currentDirectory.toFile());
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnVal = fc.showOpenDialog(mainForm);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            return Optional.of(file.toPath());
        }
        return Optional.empty();
    }

    /**
     * Callable after swing frame.pack() function to center application window.
     */
    private static void setCenteredToGoldenRatio(JFrame frame) {
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = (int) screenDimension.getHeight();
        int screenWidth = (int) screenDimension.getWidth();

        int frameHeight = frame.getHeight();
        int frameWidth = frame.getWidth();

        int goldenRatioHeight = (int) ((screenHeight - frameHeight) * 0.38);

        frame.setLocation((screenWidth / 2) - (frameWidth / 2), goldenRatioHeight);
    }
}
