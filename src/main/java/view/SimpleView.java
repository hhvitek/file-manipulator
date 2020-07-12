package view;

import controller.IController;
import model.IModel;
import model.ISuffixesCollection;

import java.nio.file.Path;

public class SimpleView implements IView {

    private MainForm form;

    public SimpleView(IModel model, IController controller) {
        form = new MainForm(model, controller);
        controller.setView(this);
    }

    @Override
    public void createView() {
        form.startSwingApplication();
    }

    @Override
    public void destroyView() {
        form.stopSwingApplication();
    }

    @Override
    public void setSuffixes(ISuffixesCollection suffixes) {
        form.setSuffixes(suffixes);
    }

    @Override
    public void setInputFolder(Path inputFolder) {
        form.setInputFolder(inputFolder);
    }

    @Override
    public void setOutputFolder(Path outputFolder) {
        form.setOutputFolder(outputFolder);
    }

    @Override
    public void setStatusBar(String message) {
        form.setStatusBarMessage(message);
    }

    @Override
    public void refreshPredefinedSuffixesCollections() {
        form.refreshPredefinedSuffixesCollections();
    }

    @Override
    public void errorOccurred(String errorMessage) {
        setStatusBar("ERROR: <" + errorMessage + ">");
    }

    @Override
    public void update() {

    }
}
