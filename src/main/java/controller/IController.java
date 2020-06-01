package controller;

import view.IView;

public interface IController extends ISupportedViewActions {
    void setView(IView newView);

}
