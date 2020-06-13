package controller;

import model.IModel;
import model.ISuffixesCollection;
import view.IView;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public void exitApplication() {
        model.stopAll();
        view.destroyView();
        //System.exit(0);
    }

    @Override
    public void newInputFolderChosenByUser(String newInputFolder) {
        try {
            Path newInputFolderAsPath = convertStringIntoPath(newInputFolder);
            model.setInputFolder(newInputFolderAsPath);
            view.setInputFolder(newInputFolderAsPath);
            view.setStatusBar("Input folder has been set.");
        } catch (InvalidPathException e) {
            view.errorOccurred("Invalid input folder path: " + newInputFolder);
        }
    }

    private Path convertStringIntoPath(String input) throws InvalidPathException {
        return Paths.get(input);
    }

    @Override
    public void newOutputFolderChosenByUser(String newOutputFolder) {
        try {
            Path newOutputFolderAsPath = convertStringIntoPath(newOutputFolder);
            model.setOutputFolder(newOutputFolderAsPath);
            view.setOutputFolder(newOutputFolderAsPath);
            view.setStatusBar("Output folder has been set.");
        } catch (InvalidPathException e) {
            view.errorOccurred("Invalid output folder path: " + newOutputFolder);
        }
    }

    @Override
    public void newSuffixesChosenByUser(String newSuffixes, String delimiter) {
        ISuffixesCollection suffixes = new ControllerSuffixesCollectionImplSimple();
        suffixes.addSuffixes(newSuffixes, delimiter);
        model.setSuffixes(suffixes);
        view.setSuffixes(suffixes);
        view.setStatusBar("Suffixes have been set.");
    }

    @Override
    public void newPredefinedSuffixesChosenByUser(String categoryName) {
        Optional<ISuffixesCollection> categoryOpt = model.getPredefinesFileSuffixesCollectionByName(categoryName);
        if (categoryOpt.isPresent()) {
            model.setSuffixes(categoryOpt.get());
            view.setSuffixes(categoryOpt.get());
            view.setStatusBar("Suffixes have been set.");
        } else {
            view.errorOccurred(
                    String.format("The chosen suffixes category: \"%s\" doesn't exist.", categoryName)
            );
        }
    }

    @Override
    public void newFileOperationChosenByUser(String operationName) {
        try {
            model.setOperation(operationName);
            view.setStatusBar(
                    String.format("The file operation \"%s\" has been set successfully.", operationName)
            );
        } catch (IllegalArgumentException e) {
            view.errorOccurred(
                    String.format("The file operation \"%s\" is not supported", operationName)
            );
        }
    }

    @Override
    public void createJob() {
        view.setStatusBar("Started mission! Using " + model.getSuffixes()  + " suffixes");
        model.createJobWithDefaultParameters();
    }

    @Override
    public void stopAll() {
        model.stopAll();
        view.setStatusBar("All tasks have been stopped");
    }
}
