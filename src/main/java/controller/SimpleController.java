package controller;

import model.jobs.IJob;
import model.jobs.IJobManager;
import model.IModel;
import model.ISuffixesCollection;
import model.jobs.JobObserver;
import model.simplemodel.SuffixesCollectionImpl;
import org.jetbrains.annotations.NotNull;
import view.IView;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class SimpleController implements IController, JobObserver {

    private IModel model;
    private IJobManager modelJobManager;

    private IView view;

    public SimpleController(IModel model) {
        this.model = model;
        modelJobManager = model.getJobManager();
    }

    @Override
    public void setView(IView newView) {
        view = newView;
    }

    @Override
    public void exitApplication() {
        modelJobManager.shutdown();
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
    public void newSuffixesChosenByUser(String name, String delimitedString, String delimiter) {
        ISuffixesCollection suffixesCollection = createSuffixesCollectionFromNameAndDelimitedString(name, delimitedString, delimiter);

        model.setSuffixes(suffixesCollection);
        view.setSuffixes(suffixesCollection);
        view.setStatusBar("Suffixes have been set.");
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

        view.refreshPredefinedSuffixesCollections();
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
    public void removeSuffixesCollection(@NotNull String name) {
        model.removePredefinedFileSuffixesCollection(name);
        view.refreshPredefinedSuffixesCollections();
    }

    @Override
    public void createAndStartJob() {
        if (!haveModelAlreadyHaveJob()) {
            IJob createdJob = model.createJobAsyncWithDefaultParameters();
            createdJob.addObserver(this);
            view.setStatusBar("Started mission! Using " + model.getSuffixes() + " suffixes.");
        } else {
            view.errorOccurred("Model is already busy!");
        }
    }

    private boolean haveModelAlreadyHaveJob() {
        List<IJob> jobs = modelJobManager.getJobs();
        return !jobs.isEmpty();
    }

    @Override
    public void stopAll() {
        modelJobManager.stopAll();
        view.setStatusBar("All tasks have been stopped");
    }

    @Override
    public void storeAllToPersistentStorage() {
        model.storeAll();
    }

    @Override
    public void update(IJob job, Path input, Path output) {
        view.setStatusBar(
                String.format("UPDATE|jobId:<%d>|oldFile:<%s>|newFile:<%s>",
                        job.getId(),
                        input.getFileName(),
                        output.getFileName()
                        )
        );
    }
}
