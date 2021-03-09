package view;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class MainFormUtility {

    private JFrame mainForm;

    public JFrame createMainForm(@NotNull JPanel contentPanel) {
        mainForm = new JFrame("FileManipulator");
        mainForm.setContentPane(contentPanel);
        mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    public void unvisibleAndDispose() {
        mainForm.setVisible(false);
        mainForm.dispose();
    }
}