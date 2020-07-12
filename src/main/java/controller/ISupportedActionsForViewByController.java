package controller;

import org.jetbrains.annotations.NotNull;

public interface ISupportedActionsForViewByController {

    void newInputFolderChosenByUser(String newInputFolder);
    void newOutputFolderChosenByUser(String newOutputFolder);
    void newSuffixesChosenByUser(String name, String delimitedString, String delimiter);
    void newSuffixesModifiedByUser(String name, String delimitedString, String delimiter);
    void newPredefinedSuffixesChosenByUser(String categoryName);
    void newFileOperationChosenByUser(String operationName);
    void removeSuffixesCollection(@NotNull String name);
    void createAndStartJob();
    void stopAll();
    void storeAllToPersistentStorage();
}
