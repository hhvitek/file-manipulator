package app.view;

import app.model.ISuffixes;
import org.jetbrains.annotations.NotNull;

import java.beans.PropertyChangeEvent;

public abstract class AbstractView {
    public abstract void startView();
    public abstract void destroyView();
    public abstract void setSuffixes(@NotNull ISuffixes suffixes);
    public abstract void setInfoMessage(@NotNull String message);
    public abstract void setErrorMessage(@NotNull String errorMessage);

    public abstract void modelPropertyChange(PropertyChangeEvent evt);
}