package model.simplemodel;

import model.*;
import model.file_operations.FileOperationEnum;
import model.jobs.SimpleModelJobManager;
import model.observer.IObserver;
import model.observer.ModelObservableHelper;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SimpleModel implements IModel {

    private ModelObservableHelper modelObservableHelper;

    private IJobManager jobManager;

    private SimpleModelSuffixesCategoriesDb suffixesDb;

    private IModelStaticData staticData;

    private FileOperationEnum fileOperationEnum;

    public SimpleModel() {
        modelObservableHelper = new ModelObservableHelper();
        suffixesDb = new SimpleModelSuffixesCategoriesDb();
        staticData = new SimpleModelStaticDataImpl();
        jobManager = new SimpleModelJobManager();
        fileOperationEnum = FileOperationEnum.COPY;
    }

    @Override
    public void setInputFolder(@NotNull Path newInputFolder) {
        staticData.setInputFolder(newInputFolder);
    }

    @Override
    public @NotNull Path getInputFolder() {
        return staticData.getInputFolder();
    }

    @Override
    public void setOutputFolder(@NotNull Path newOutputFolder) {
        staticData.setOutputFolder(newOutputFolder);
    }

    @Override
    public @NotNull Path getOutputFolder() {
        return staticData.getOutputFolder();
    }

    @Override
    public void setSuffixes(ISuffixesCollection newSuffixes) {
        staticData.setFileSuffixes(newSuffixes);
    }

    @Override
    public ISuffixesCollection getSuffixes() {
        return staticData.getFileSuffixes();
    }

    @Override
    public List<String> getSupportedOperationNames() {
        FileOperationEnum[] fileOperationValues = FileOperationEnum.values();
        return Arrays.stream(fileOperationValues)
                .map(FileOperationEnum::toString)
                .collect(Collectors.toList());
    }

    @Override
    public void setOperation(String chosenOperation) throws IllegalArgumentException {
        fileOperationEnum = FileOperationEnum.valueOf(chosenOperation);
    }

    @Override
    public int createJob(Path inputFolder, Path outputFolder, ISuffixesCollection suffixes) {
        return jobManager.createJob(
                inputFolder,
                outputFolder,
                suffixes,
                fileOperationEnum.getFileOperationInstance()
        );
    }

    @Override
    public int createJobWithDefaultParameters() {
        return createJob(
                getInputFolder(),
                getOutputFolder(),
                getSuffixes()
        );
    }

    @Override
    public Optional<IJob> getJobDetails(int jobId) {
        return jobManager.getJobById(jobId);
    }

    @Override
    public void stopJob(int jobId) {
        jobManager.stopJobIfExists(jobId);
    }

    @Override
    public void stopAll() {
        jobManager.stopAll();
    }

    @Override
    public void startJobInParallel(int jobId) {
        jobManager.stopJobIfExists(jobId);
    }

    @Override
    public SimpleModelSuffixesCategoriesDb getPredefinedFileSuffixesCategories() {
        return staticData.getPredefinedFileSuffixesCategories();
    }

    @Override
    public void addNewPredefinedFileSuffixesCategory(ISuffixesCollection newPredefinedSuffixesCategory) {
        staticData.addNewPredefinedFileSuffixesCategory(newPredefinedSuffixesCategory);
    }

    @Override
    public Optional<ISuffixesCollection> getPredefinesFileSuffixesByCategoryName(String categoryName) {
        return staticData.getPredefinesFileSuffixesByCategoryName(categoryName);
    }

    @Override
    public void addObserver(IObserver observer) {
        modelObservableHelper.addObserver(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        modelObservableHelper.removeObserver(observer);
    }

    @Override
    public void notifyObservers() {
        modelObservableHelper.notifyObservers();
    }
}
