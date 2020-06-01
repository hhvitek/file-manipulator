package controller;

import model.ISuffixesCategory;

import java.nio.file.Path;

public interface ISupportedViewActions {

    void newInputFolderChosenByUser(Path newInputFolder);
    void newOutputFolderChosenByUser(Path newOutputFolder);
    void newSuffixesChosenByUser(ISuffixesCategory newSuffixes);
    void newPredefinedSuffixesChosenByUser(String categoryName);
}
