package view;

import controller.IController;
import model.IModel;
import model.ISuffixesCategory;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.util.Optional;

public class MainForm {
    private JPanel panelMainForm;
    private JPanel panelStatusBar;
    private JPanel panelApp;
    private JTextField textFieldInputFolder;
    private JButton buttonInputFolderChoose;
    private JTextField textFieldOutputFolder;
    private JButton buttonOutputFolderChoose;
    private JButton buttonStart;
    private JButton buttonExit;
    private JButton buttonCancel;
    private JProgressBar progressBar;
    private JPanel panelInputFolder;
    private JPanel panelOutputFolder;
    private JPanel panelControls;
    private JLabel labelStatusBarStatusText;
    private JLabel labelStatusBarCopyright;
    private JPanel panelIntermediateActions;
    private JButton buttonCountAudioFiles;
    private JPanel panelExtensions;
    private JTextField textFieldAudioExtensions;
    private JButton buttonSetAudioExtensions;
    private JButton buttonValidate;
    private JComboBox comboBoxChoosePredefinedAudioExtensions;


    //################CUSTOM VARIABLES
    private static final String SUFFIXES_DELIMITER_REGEX = "\\*s,\\s*";
    private static final String SUFFIXES_JOINER_STRING = ", ";

    private final MainFormUtility mainFormUtility = new MainFormUtility();

    // logger
    private static final Logger logger = LoggerFactory.getLogger(MainForm.class);

    // Swings-JFrame represents main application window
    private final JFrame swingFrame;

    // IModel
    private final IModel model;
    private final IController controller;

    //################END CUSTOM VARIABLES

    public MainForm(@NotNull IModel model, @NotNull IController controller) {
        this.model = model;
        this.controller = controller;

        swingFrame = mainFormUtility.createMainForm();
        swingFrame.setContentPane(panelMainForm);

        buttonInputFolderChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Optional<Path> optionalFile = mainFormUtility.showFileChooserAndGetFolder(model.getInputFolder());
                optionalFile.ifPresent(
                        file -> newInputFolderChosenByUser(optionalFile.get())
                );
            }
        });
        buttonOutputFolderChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Optional<Path> optionalFile = mainFormUtility.showFileChooserAndGetFolder(model.getOutputFolder());
                if (optionalFile.isPresent()) {
                    newOutputFolderChosenByUser(optionalFile.get());
                }
            }
        });
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        buttonValidate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateAllFields();
                //String validateText = model.validate();
                //JOptionPane.showMessageDialog(swingFrame, validateText, "Validation result", JOptionPane.PLAIN_MESSAGE);
                setStatusBarMessage("Validation Performed.");
            }
        });
        buttonCountAudioFiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateAllFields();
                //int count = model.countRelevantAudioFiles();
                //setStatusBarMessage("There are around: " + count + " audio files.");
            }
        });
        buttonSetAudioExtensions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                newSuffixesChosenByUser(textFieldAudioExtensions.getText());
            }
        });
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setStatusBarMessage("Mission started!");
            }
        });
        comboBoxChoosePredefinedAudioExtensions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newPredefinedSuffixesChosenByUser(comboBoxChoosePredefinedAudioExtensions.getSelectedItem().toString());
            }
        });
    }

    /**
     * Starts UI of the application
     */
    public void startSwingApplication() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setDefaultState();

                swingFrame.pack();
                swingFrame.setVisible(true);

            }
        });
    }

    private void setStatusBarMessage(String message) {
        labelStatusBarStatusText.setText(message);
    }

    private void newInputFolderChosenByUser(Path newInputFolder) {
        controller.newInputFolderChosenByUser(newInputFolder);
    }

    private void newOutputFolderChosenByUser(Path newOutputFolder) {
        controller.newOutputFolderChosenByUser(newOutputFolder);
    }

    private void newSuffixesChosenByUser(String newSuffixes) {
        ISuffixesCategory suffixes = new ViewSuffixesCategoryImplSimple();
        suffixes.addSuffixes(newSuffixes, SUFFIXES_DELIMITER_REGEX);
        controller.newSuffixesChosenByUser(suffixes);
    }

    private void newPredefinedSuffixesChosenByUser(String newSuffixesCategoryName) {
        controller.newPredefinedSuffixesChosenByUser(newSuffixesCategoryName);
    }

    private void setFolder(JTextField folderTextField, Path newFolder) {
        folderTextField.setText(newFolder.toString());
    }

    public void setAudioExtensions(ISuffixesCategory suffixes) {
        textFieldAudioExtensions.setText(
                suffixes.getSuffixesAsDelimitedString(SUFFIXES_JOINER_STRING)
        );
    }

    public void setInputFolder(Path newInputFolder) {
        setFolder(textFieldInputFolder, newInputFolder);
    }

    public void setOutputFolder(Path newOutputFolder) {
        setFolder(textFieldOutputFolder, newOutputFolder);
    }

    private void fillPredefinedSuffixes() {
        for(ISuffixesCategory predefinedCategory: model.getPredefinedFileSuffixesCategories()) {
            comboBoxChoosePredefinedAudioExtensions.addItem(
                    predefinedCategory.getCategoryName()
            );
        }
    }

    private void updateAllFields() {
        setInputFolder(model.getInputFolder());
        setOutputFolder(model.getOutputFolder());
        setAudioExtensions(model.getFileSuffixes());
        fillPredefinedSuffixes();
    }

    /**
     * Sets App to default state
     */
    private void setDefaultState() {
        updateAllFields();
    }
}
