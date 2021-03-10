package app.controller;

import org.jetbrains.annotations.NotNull;
import app.view.AbstractView;

public interface ISupportedActionsForViewByController {

    void setView(AbstractView view);
    void exitApplication();

    void newInputFolderChosenByUser(String newInputFolder);
    void newOutputFolderChosenByUser(String newOutputFolder);
    void newSuffixesModifiedByUser(String name, String delimitedString, String delimiter);
    void newPredefinedSuffixesChosenByUser(String categoryName);
    void newFileOperationChosenByUser(String operationName);
    void removeSuffixesCollection(@NotNull String name);

    void createAndStartJob();
    void stopAll();

    /**
     * wait for relevant event to receive an answer.
     */
    void countRelevantFilesInInputFolder();
}