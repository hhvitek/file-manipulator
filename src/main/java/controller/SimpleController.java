package controller;

import model.IModel;
import model.ISuffixesCategory;
import view.IView;

import java.nio.file.Path;
import java.util.Optional;

public class SimpleController implements IController {

    private IModel model;
    private IView view;

    public SimpleController(IModel model) {
        this.model = model;
    }

    @Override
    public void setView(IView newView) {
        view = newView;
    }

    @Override
    public void newInputFolderChosenByUser(Path newInputFolder) {
        model.setInputFolder(newInputFolder);
        view.setInputFolder(newInputFolder);
    }

    @Override
    public void newOutputFolderChosenByUser(Path newOutputFolder) {
        model.setOutputFolder(newOutputFolder);
        view.setOutputFolder(newOutputFolder);
    }

    @Override
    public void newSuffixesChosenByUser(ISuffixesCategory newSuffixes) {
        model.setFileSuffixes(newSuffixes);
    }

    @Override
    public void newPredefinedSuffixesChosenByUser(String categoryName) {
        Optional<ISuffixesCategory> categoryOpt = model.getPredefinesFileSuffixesByCategoryName(categoryName);
        if (categoryOpt.isPresent()) {
            view.setSuffixes(categoryOpt.get());
        }
    }

}
