package app.view;

import app.controller.ISupportedActionsForViewByController;
import app.model.Model;
import app.model.ISuffixes;
import app.model.ModelObservableEvents;
import app.model.jobs.JobStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

public class MainForm extends AbstractView {
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
    private JButton buttonCount;
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
    private JScrollPane scrollPaneStatusBarMessageContainer;


    //################CUSTOM VARIABLES
    private static final String SUFFIXES_DELIMITER = ",";
    private static final String SUFFIXES_JOINER_STRING = ", ";

    private final MainFormUtility mainFormUtility = new MainFormUtility();

    // logger
    private static final Logger logger = LoggerFactory.getLogger(MainForm.class);

    // Swings-JFrame represents main application window
    private final JFrame swingFrame;

    // IModel
    private final Model model;
    private final ISupportedActionsForViewByController controller;

    //################END CUSTOM VARIABLES

    public MainForm(@NotNull Model model, @NotNull ISupportedActionsForViewByController controller) {
        this.model = model;
        this.controller = controller;
        controller.setView(this);

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
        buttonCount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.countRelevantFilesInInputFolder();
            }
        });
        buttonStore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //TODO app.controller.storeAllToPersistentStorage();
                setInfoMessage("Not supported yet.");
            }
        });
        /*
            User used comboBox to select another suffixes...
         */
        comboBoxChoosePredefinedFileSuffixes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedItem = comboBoxChoosePredefinedFileSuffixes.getSelectedItem();
                if (Objects.nonNull(selectedItem)) {
                    controller.newPredefinedSuffixesChosenByUser(selectedItem.toString());
                }
            }
        });
        /*
            Follows controls for suffixes
         */
        buttonAddSuffixesCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textFieldName.getText();
                String delimitedString = textFieldFileSuffixes.getText();
                controller.newSuffixesModifiedByUser(name, delimitedString, SUFFIXES_DELIMITER);
            }
        });
        buttonDeleteAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String suffixesCollectionName = textFieldName.getText();
                controller.removeSuffixes(suffixesCollectionName);
            }
        });
        buttonDeleteSuffixesCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String suffixesCollectionName = textFieldName.getText();
                controller.removeSuffixes(suffixesCollectionName);
            }
        });
        /*
            User used comboBox to select another file operation...
         */
        comboBoxFileOperations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedItem = comboBoxFileOperations.getSelectedItem();
                if (Objects.nonNull(selectedItem)) {
                    controller.newFileOperationChosenByUser(selectedItem.toString());
                }
            }
        });

        Runnable inputFolderOnChange = () -> {
            String inputFolder = textFieldInputFolder.getText();
            controller.newInputFolderChosenByUser(inputFolder);
        };
        SwingViewUtils.addChangeListenerToJTextComponentOnChange(textFieldInputFolder, inputFolderOnChange);

        Runnable outputFolderOnChange = () -> {
            String outputFolder = textFieldOutputFolder.getText();
            controller.newOutputFolderChosenByUser(outputFolder);
        };
        SwingViewUtils.addChangeListenerToJTextComponentOnChange(textFieldOutputFolder, outputFolderOnChange);

    }

    /**
     * Starts UI of the application
     */
    @Override
    public void startView() {
        fillPredefinedSuffixesFromModel();
        fillSupportedFileOperationsFromModel();
        fillInputAndOutputFoldersFromModel();
        fillLastCurrentFileOperation();

        SwingViewUtils.runAndShowWindow(swingFrame);

    }

    private void fillPredefinedSuffixesFromModel() {
        for(ISuffixes predefinedCategory: model.getPredefinedFileSuffixesDb()) {
            comboBoxChoosePredefinedFileSuffixes.addItem(
                    predefinedCategory.getName()
            );
        }
    }

    private void fillSupportedFileOperationsFromModel() {
        for(String operationName: model.getSupportedOperationNames()) {
            comboBoxFileOperations.addItem(operationName);
        }
    }

    private void fillInputAndOutputFoldersFromModel() {
        setInputFolder(model.getInputFolder());
        setOutputFolder(model.getOutputFolder());
    }

    private void fillLastCurrentFileOperation() {
        controller.newFileOperationChosenByUser(model.getOperation());
    }

    /**
     * Stops UI of the application
     */
    @Override
    public void destroyView() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainFormUtility.unvisibleAndDispose();
            }
        });
    }

    @Override
    public void setInfoMessage(@NotNull String message) {
        labelStatusBarStatusText.setText(message);
        labelStatusBarStatusText.setToolTipText(message);
    }

    @Override
    public void setErrorMessage(@NotNull String errorMessage) {
        setInfoMessage(errorMessage);
        SwingViewUtils.showErrorMessageDialog(swingFrame, errorMessage);
    }

    @Override
    public void setSuffixes(@NotNull ISuffixes suffixes) {
        textFieldFileSuffixes.setText(
                suffixes.getSuffixesAsDelimitedString(SUFFIXES_JOINER_STRING)
        );
        textFieldName.setText(
                suffixes.getName()
        );
    }

    private void setInputFolder(@NotNull Path newInputFolder) {
        setFolder(textFieldInputFolder, newInputFolder);
    }

    private void setFolder(@NotNull JTextField folderTextField, @NotNull Path newFolder) {
        folderTextField.setText(newFolder.toString());
        folderTextField.setToolTipText(newFolder.toString());
    }

    private void setOutputFolder(@NotNull Path newOutputFolder) {
        setFolder(textFieldOutputFolder, newOutputFolder);
    }

    /**
     * Call when underline model's suffixes changed.
     */
    private void refillPredefinedSuffixesFromModelPreservingSelection() {
        String selectedItem = textFieldName.getText();
        comboBoxChoosePredefinedFileSuffixes.removeAllItems();
        fillPredefinedSuffixesFromModel();
        selectPredefinedSuffix(selectedItem);
    }

    private void selectPredefinedSuffix(String selectedItem) {
        if (selectedItem != null) {
            comboBoxChoosePredefinedFileSuffixes.setSelectedItem(selectedItem);
        }
    }


    /*

    private void loadAllAndUpdateFromModel() {
        setInputFolder(model.getInputFolder());
        setOutputFolder(model.getOutputFolder());
        setSuffixes(model.getSuffixes());
    }

    private void storeAllToModel() {
        app.controller.newInputFolderChosenByUser(textFieldInputFolder.getText());
        app.controller.newOutputFolderChosenByUser(textFieldOutputFolder.getText());

        Object selectedItem = comboBoxFileOperations.getSelectedItem();
        if (Objects.nonNull(selectedItem)) {
            newFileOperationChosenByUser(selectedItem.toString());
        }

        newPredefinedSuffixesChosenByUser(textFieldFileSuffixes.getText());
    }
    */

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        logger.debug("Event received: {}", evt.getPropertyName());

        String eventName = evt.getPropertyName();
        ModelObservableEvents modelEvent = ModelObservableEvents.valueOf(eventName);

        switch (modelEvent) {
            case INPUT_FOLDER_CHANGED: {
                Path newInputPath = (Path) evt.getNewValue();
                setInputFolder(newInputPath);

                controller.newOutputFolderChosenByUser(newInputPath.resolve("OUTPUT").toString());
                break;
            }
            case OUTPUT_FOLDER_CHANGED: {
                Path newOutputPath = (Path) evt.getNewValue();
                setOutputFolder(newOutputPath);
                break;
            }
            case NEW_SUFFIXES_COLLECTION_ADDED: {
                refillPredefinedSuffixesFromModelPreservingSelection();
                break;
            }
            case COUNTING_RELEVANT_FILES_FINISHED: {
                int countedRelevantFiles = (int) evt.getOldValue();
                setInfoMessage("Counted <" + countedRelevantFiles + "> relevant files.");
                break;
            }
            case JOB_RUNNING: {
                JobStatus jobStatus = (JobStatus) evt.getNewValue();
                int totalFilesCount = jobStatus.getTotalFilesCount();
                progressBar.setMaximum(totalFilesCount);
                progressBar.setIndeterminate(true);
                break;
            }
            case JOB_FILE_PROCESSED: {
                JobStatus jobStatus = (JobStatus) evt.getNewValue();
                int currentValue = jobStatus.getTotalFilesCount() - jobStatus.getRemainingFileCount();
                progressBar.setValue(currentValue);
                break;
            }
            case JOB_FINISHED: {
                JobStatus jobStatus = (JobStatus) evt.getNewValue();
                progressBar.setIndeterminate(false);
                String message = "Job: " + jobStatus.getJobId() + " finished.";
                if (jobStatus.encounteredError()) {
                    message += " Some errors encountered.";
                }
                progressBar.setString(message);
                setInfoMessage(message);
                break;
            }
        }
    }

    private void createUIComponents() {
        scrollPaneStatusBarMessageContainer = new JScrollPane();
        scrollPaneStatusBarMessageContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneStatusBarMessageContainer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPaneStatusBarMessageContainer.setBorder(BorderFactory.createEmptyBorder());
    }
}