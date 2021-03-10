package app.view;

import app.model.ISuffixesCollection;
import org.jetbrains.annotations.NotNull;

import java.beans.PropertyChangeEvent;

public abstract class AbstractView {
    public abstract void startView();
    public abstract void destroyView();
    public abstract void setSuffixes(@NotNull ISuffixesCollection suffixes);
    //public abstract void setInputFolder(@NotNull Path inputFolder);
    //public abstract void setOutputFolder(@NotNull Path outputFolder);
    public abstract void setInfoMessage(@NotNull String message);
    public abstract void setErrorMessage(@NotNull String errorMessage);

    public abstract void modelPropertyChange(PropertyChangeEvent evt);
}