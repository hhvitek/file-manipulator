package controller;

import model.ISuffixesCollection;
import model.Model;
import model.jobs.IJobManager;
import model.jobs.Job;
import model.simplemodel.SuffixesCollectionImpl;
import org.jetbrains.annotations.NotNull;
import view.AbstractView;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class SimpleController extends AbstractController implements ISupportedActionsForViewByController {

    private Model model;
    private IJobManager modelJobManager;

    private AbstractView view;

    public SimpleController(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);

        modelJobManager = model.getJobManager();
    }

    @Override
    public void setView(AbstractView newView) {
        view = newView;
    }

    @Override
    public void exitApplication() {
        stopAll();
        System.exit(0);
    }

    @Override
    public void newInputFolderChosenByUser(String newInputFolder) {
        try {
            Path newInputFolderAsPath = convertStringIntoPath(newInputFolder);
            model.setInputFolder(newInputFolderAsPath);
            view.setInfoMessage("Input: " + newInputFolder);
        } catch (InvalidPathException e) {
            view.setErrorMessage("Invalid Input: " + newInputFolder);
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
            view.setInfoMessage("Output: " + newOutputFolder);
        } catch (InvalidPathException e) {
            view.setErrorMessage("Invalid Output: " + newOutputFolder);
        }
    }

    private ISuffixesCollection createSuffixesCollectionFromNameAndDelimitedString(String name, String delimitedString, String delimiter) {
        ISuffixesCollection suffixesCollection;
        if (name == null) {
            suffixesCollection = new SuffixesCollectionImpl();
        } else {
            suffixesCollection = new SuffixesCollectionImpl(name);
        }

        if (delimitedString != null && !delimitedString.isEmpty()) {
            suffixesCollection.addSuffixes(delimitedString, delimiter);
        } else {
            suffixesCollection = SuffixesCollectionImpl.getAllSuffixCollection();
        }
        return suffixesCollection;
    }

    @Override
    public void newSuffixesModifiedByUser(String name, String delimitedString, String delimiter) {
        ISuffixesCollection suffixesCollection = createSuffixesCollectionFromNameAndDelimitedString(name, delimitedString, delimiter);

        model.addNewPredefinedFileSuffixesCollection(suffixesCollection);

        view.setInfoMessage("The new suffixes added: " + name);
    }

    @Override
    public void newPredefinedSuffixesChosenByUser(String categoryName) {
        Optional<ISuffixesCollection> collectionOpt = model.getPredefinesFileSuffixesCollectionByName(categoryName);
        if (collectionOpt.isPresent()) {
            ISuffixesCollection collection = collectionOpt.get();
            model.setSuffixes(collection);
            view.setSuffixes(collection);
            view.setInfoMessage("Suffixes have been set: " + categoryName);
        } else {
            view.setErrorMessage(
                    String.format("The chosen suffixes category: \"%s\" doesn't exist.", categoryName)
            );
        }
    }

    @Override
    public void newFileOperationChosenByUser(String operationName) {
        try {
            model.setOperation(operationName);
            view.setInfoMessage(
                    String.format("The file operation \"%s\" has been set successfully.", operationName)
            );
        } catch (IllegalArgumentException e) {
            view.setErrorMessage(
                    String.format("The file operation \"%s\" is not supported", operationName)
            );
        }
    }

    @Override
    public void removeSuffixesCollection(@NotNull String name) {
        model.removePredefinedFileSuffixesCollection(name);
    }

    @Override
    public void createAndStartJob() {
        if (!haveModelAlreadyHaveJob()) {
            Job createdJob = model.createJobAsyncWithDefaultParameters(this);
            view.setInfoMessage("Started mission! Using " + model.getSuffixes() + " suffixes.");
        } else {
            view.setErrorMessage("Model is already busy!");
        }
    }

    private boolean haveModelAlreadyHaveJob() {
        return modelJobManager.isJobManagerBusy();
    }

    @Override
    public void stopAll() {
        modelJobManager.stopAll();
        view.setInfoMessage("All tasks have been stopped");
    }

    @Override
    public void countRelevantFilesInInputFolder() {
        model.countRelevantFilesInInputFolder();
        view.setInfoMessage("Counting relevant files...");
    }
}