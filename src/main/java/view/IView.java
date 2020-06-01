package view;

import model.ISuffixesCategory;
import model.observer.IObserver;

import java.nio.file.Path;

public interface IView extends IObserver {
    void createView();
    void setSuffixes(ISuffixesCategory suffixes);
    void setInputFolder(Path inputFolder);
    void setOutputFolder(Path outputFolder);
    void errorOccurred(String errorMessage);
}
