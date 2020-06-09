package controller;

public interface ISupportedActionsForViewByController {

    void newInputFolderChosenByUser(String newInputFolder);
    void newOutputFolderChosenByUser(String newOutputFolder);
    void newSuffixesChosenByUser(String newSuffixes, String delimiter);
    void newPredefinedSuffixesChosenByUser(String categoryName);
    void newFileOperationChosenByUser(String operationName);
    void createJob();
    void stopAll();
}
