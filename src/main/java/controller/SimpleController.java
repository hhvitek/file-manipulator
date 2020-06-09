package controller;

import model.IModel;
import model.ISuffixesCollection;
import model.file_operations.FileOperationEnum;
import view.IView;
import view.ViewSuffixesCollectionImplSimple;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class SimpleController implements IController {

    private IModel model;
    private IView view;
    private int lastJobId;

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
        } catch (InvalidPathException e) {
            view.errorOccurred("Invalid output folder path: " + newOutputFolder);
        }
    }

    @Override
    public void newSuffixesChosenByUser(String newSuffixes, String delimiter) {
        ISuffixesCollection suffixes = new ViewSuffixesCollectionImplSimple();
        suffixes.addSuffixes(newSuffixes, delimiter);
        model.setSuffixes(suffixes);
    }

    @Override
    public void newPredefinedSuffixesChosenByUser(String categoryName) {
        Optional<ISuffixesCollection> categoryOpt = model.getPredefinesFileSuffixesByCategoryName(categoryName);
        if (categoryOpt.isPresent()) {
            model.setSuffixes(categoryOpt.get());
            view.setSuffixes(categoryOpt.get());
        }
    }

    @Override
    public void newFileOperationChosenByUser(String operationName) {
        try {
            FileOperationEnum operationEnum = FileOperationEnum.valueOf(operationName);
            model.setOperation(operationName);
            view.setStatusBar(String.format("The operation \"%s\" has been set successfully.", operationName));
        } catch (IllegalArgumentException e) {
            view.errorOccurred(String.format("The operation \"%s\" is not supported", operationName));
        }
    }

    @Override
    public void createJob() {
        view.setStatusBar("Started mission! Using " + model.getSuffixes()  + " suffixes");
        int jobId = model.createJobWithDefaultParameters();
        lastJobId = jobId;
    }

    @Override
    public void stopAll() {
        model.stopAll();
        view.setStatusBar("All tasks has been stopped");
    }
}
