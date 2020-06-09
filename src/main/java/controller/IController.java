package controller;

import view.IView;

public interface IController extends ISupportedActionsForViewByController {
    void setView(IView newView);
    void exitApplication();

}
