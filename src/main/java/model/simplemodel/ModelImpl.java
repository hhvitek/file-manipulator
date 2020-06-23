package model.simplemodel;

import model.IModel;
import model.IModelStaticData;
import model.ISuffixesCollection;
import model.file_operations.FileOperationEnum;
import model.jobs.IJob;
import model.jobs.IJobManager;
import model.jobs.JobImpl;
import model.jobs.JobManagerImpl;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModelImpl implements IModel {
    private final IJobManager jobManager;

    private final IModelStaticData staticData;

    private FileOperationEnum fileOperationEnum;

    public ModelImpl() {
        staticData = new ModelStaticDataImpl();
        jobManager = new JobManagerImpl();
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
    public void setSuffixes(@NotNull ISuffixesCollection newSuffixes) {
        staticData.setCurrentSuffixesCollection(newSuffixes);
    }

    @Override
    public @NotNull ISuffixesCollection getSuffixes() {
        return staticData.getCurrentSuffixesCollection();
    }

    @Override
    public @NotNull List<String> getSupportedOperationNames() {
        FileOperationEnum[] fileOperationValues = FileOperationEnum.values();
        return Arrays.stream(fileOperationValues)
                .map(FileOperationEnum::toString)
                .collect(Collectors.toList());
    }

    @Override
    public void setOperation(@NotNull String operationName) throws IllegalArgumentException {
        fileOperationEnum = FileOperationEnum.valueOf(operationName);
    }

    @Override
    public @NotNull IJobManager getJobManager() {
        return jobManager;
    }

    @Override
    public IJob createJobSyncWithDefaultParameters() {
        IJob newJob = new JobImpl(
                getInputFolder(),
                getOutputFolder(),
                getSuffixes(),
                fileOperationEnum.getFileOperationInstance()
        );
        return newJob;
    }

    @Override
    public IJob createJobAsyncWithDefaultParameters() {
        IJob newJob = createJobSyncWithDefaultParameters();
        jobManager.appendJob(newJob);
        return newJob;
    }

    @Override
    public @NotNull CollectionOfSuffixesCollections getPredefinedFileSuffixesDb() {
        return staticData.getSuffixesDb();
    }

    @Override
    public void addNewPredefinedFileSuffixesCollection(@NotNull ISuffixesCollection newPredefinedSuffixesCollection) {
        staticData.addNewPredefinedSuffixesCollection(newPredefinedSuffixesCollection);
    }

    @Override
    public Optional<ISuffixesCollection> getPredefinesFileSuffixesCollectionByName(@NotNull String name) {
        return staticData.getPredefinedSuffixesCollectionByName(name);
    }
}
