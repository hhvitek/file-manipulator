package view;

import controller.IController;
import model.IModel;
import model.ISuffixesCollection;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.util.Objects;
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
    private JButton buttonStore;
    private JPanel panelSuffixes;
    private JTextField textFieldFileSuffixes;
    private JButton buttonLoad;
    private JComboBox comboBoxChoosePredefinedFileSuffixes;
    private JPanel panelOperations;
    private JComboBox comboBoxFileOperations;
    private JTextField textFieldName;
    private JLabel labelName;
    private JLabel labelCategories;
    private JButton buttonAddSuffixesCategory;
    private JButton buttonDeleteSuffixesCategory;
    private JLabel labelSuffixes;
    private JPanel panelInnerSuffixesControls;
    private JButton buttonDeleteAll;


    //################CUSTOM VARIABLES
    private static final String SUFFIXES_DELIMITER = ",";
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

        swingFrame = mainFormUtility.createMainForm(panelMainForm);

        buttonInputFolderChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Optional<Path> optionalFile = mainFormUtility.showFileChooserAndGetFolder(model.getInputFolder());
                optionalFile.ifPresent(
                        file -> controller.newInputFolderChosenByUser(optionalFile.get().toString())
                );
            }
        });
        buttonOutputFolderChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Optional<Path> optionalFile = mainFormUtility.showFileChooserAndGetFolder(model.getOutputFolder());
                if (optionalFile.isPresent()) {
                    controller.newOutputFolderChosenByUser(optionalFile.get().toString());
                }
            }
        });
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loadAllAndUpdateFromModel();
                controller.createAndStartJob();
            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.stopAll();
            }
        });
        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.exitApplication();
            }
        });
        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loadAllAndUpdateFromModel();
                setStatusBarMessage("All files updated from model.");
            }
        });
        buttonStore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                storeAllToModel();
                controller.storeAllToPersistentStorage();
                setStatusBarMessage("Stored successfully");
            }
        });
        comboBoxChoosePredefinedFileSuffixes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedItem = comboBoxChoosePredefinedFileSuffixes.getSelectedItem();
                if (Objects.nonNull(selectedItem)) {
                    newPredefinedSuffixesChosenByUser(selectedItem.toString());
                }
            }
        });
        comboBoxFileOperations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedItem = comboBoxFileOperations.getSelectedItem();
                if (Objects.nonNull(selectedItem)) {
                    newFileOperationChosenByUser(selectedItem.toString());
                }
            }
        });
        buttonAddSuffixesCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textFieldName.getText();
                String delimitedString = textFieldFileSuffixes.getText();
                addNewSuffixesCategory(name, delimitedString);
            }
        });
        buttonDeleteAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String suffixesCollectionName = textFieldName.getText();
                controller.removeSuffixesCollection(suffixesCollectionName);
            }
        });

        buttonDeleteSuffixesCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String suffixesCollectionName = textFieldName.getText();
                controller.removeSuffixesCollection(suffixesCollectionName);
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
                loadAllAndUpdateFromModel();
                fillPredefinedSuffixes();
                fillSupportedFileOperations();

                mainFormUtility.packAndShow();
            }
        });
    }

    /**
     * Stops UI of the application
     */
    public void stopSwingApplication() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainFormUtility.unvisibleAndDispose();
            }
        });
    }

    public void setStatusBarMessage(String message) {
        labelStatusBarStatusText.setText(message);
    }

    private void newPredefinedSuffixesChosenByUser(String newSuffixesCategoryName) {
        controller.newPredefinedSuffixesChosenByUser(newSuffixesCategoryName);
    }

    private void newFileOperationChosenByUser(String operationName) {
        controller.newFileOperationChosenByUser(operationName);
    }

    private void setFolder(JTextField folderTextField, Path newFolder) {
        folderTextField.setText(newFolder.toString());
    }

    private void addNewSuffixesCategory(String name, String delimitedString) {
        controller.newSuffixesModifiedByUser(name, delimitedString, SUFFIXES_DELIMITER);
    }

    public void setSuffixes(ISuffixesCollection suffixes) {
        textFieldFileSuffixes.setText(
                suffixes.getSuffixesAsDelimitedString(SUFFIXES_JOINER_STRING)
        );
        textFieldName.setText(
                suffixes.getName()
        );
    }

    public void setInputFolder(Path newInputFolder) {
        setFolder(textFieldInputFolder, newInputFolder);
    }

    public void setOutputFolder(Path newOutputFolder) {
        setFolder(textFieldOutputFolder, newOutputFolder);
    }

    public void refreshPredefinedSuffixesCollections() {
        String selectedItem = textFieldName.getText();
        comboBoxChoosePredefinedFileSuffixes.removeAllItems();
        fillPredefinedSuffixes();
        selectPredefinedSuffix(selectedItem);
    }

    private void selectPredefinedSuffix(String selectedItem) {
        if (selectedItem != null) {
            comboBoxChoosePredefinedFileSuffixes.setSelectedItem(selectedItem);
        }
    }


    private void fillPredefinedSuffixes() {
        for(ISuffixesCollection predefinedCategory: model.getPredefinedFileSuffixesDb()) {
            comboBoxChoosePredefinedFileSuffixes.addItem(
                    predefinedCategory.getName()
            );
        }
    }

    private void fillSupportedFileOperations() {
        for(String operationName: model.getSupportedOperationNames()) {
            comboBoxFileOperations.addItem(operationName);
        }
    }

    private void loadAllAndUpdateFromModel() {
        setInputFolder(model.getInputFolder());
        setOutputFolder(model.getOutputFolder());
        setSuffixes(model.getSuffixes());
    }

    private void storeAllToModel() {
        controller.newInputFolderChosenByUser(textFieldInputFolder.getText());
        controller.newOutputFolderChosenByUser(textFieldOutputFolder.getText());

        Object selectedItem = comboBoxFileOperations.getSelectedItem();
        if (Objects.nonNull(selectedItem)) {
            newFileOperationChosenByUser(selectedItem.toString());
        }

        newPredefinedSuffixesChosenByUser(textFieldFileSuffixes.getText());
    }


}
