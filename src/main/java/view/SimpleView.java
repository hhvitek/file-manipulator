package view;

import controller.IController;
import model.IModel;
import model.ISuffixesCategory;

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
    public void setSuffixes(ISuffixesCategory suffixes) {
        form.setAudioExtensions(suffixes);
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
    public void errorOccurred(String errorMessage) {

    }

    @Override
    public void update() {

    }
}
