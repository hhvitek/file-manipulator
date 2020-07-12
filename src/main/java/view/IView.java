package view;

import model.ISuffixesCollection;
import model.observer.IObserver;

import java.nio.file.Path;

public interface IView extends IObserver {
    void createView();
    void destroyView();
    void setSuffixes(ISuffixesCollection suffixes);
    void setInputFolder(Path inputFolder);
    void setOutputFolder(Path outputFolder);
    void setStatusBar(String message);
    void refreshPredefinedSuffixesCollections();
    void errorOccurred(String errorMessage);
}
