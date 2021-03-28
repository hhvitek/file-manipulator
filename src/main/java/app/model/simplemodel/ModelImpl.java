package app.model.simplemodel;


import app.model.ISuffixes;
import app.model.Model;
import app.model.file_operations.FileOperationEnum;

import app.model.jobs.IJobManager;
import app.model.jobs.Job;
import app.model.jobs.JobImpl;
import app.model.jobs.JobManagerImpl;
import app.model.simplemodel.staticdata.IModelStaticData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import app.utilities.file_locators.FileLocatorImpl;
import app.utilities.file_locators.IFileLocator;
import app.utilities.file_locators.LocateFilesAsync;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static app.model.ModelObservableEvents.*;

public class ModelImpl extends Model implements PropertyChangeListener {

    private static final Logger logger = LoggerFactory.getLogger(ModelImpl.class);

    private final IJobManager jobManager;

    private final IModelStaticData staticData;

    private final LocateFilesAsync locateFilesAsync;

    public ModelImpl(IModelStaticData modelStaticData) {
        staticData = modelStaticData;
        jobManager = new JobManagerImpl();
        locateFilesAsync = new LocateFilesAsync();
        locateFilesAsync.addPropertyChangeListener(this);
    }

    @Override
    public void setInputFolder(@NotNull Path newInputFolder) {

        Path oldInputFolder = staticData.getInputFolder();
        staticData.setInputFolder(newInputFolder);

        logger.debug("InputFolder changed: <{}> -> <{}>", oldInputFolder, newInputFolder);
        firePropertyChange(INPUT_FOLDER_CHANGED, oldInputFolder, newInputFolder);
    }

    @Override
    public @NotNull Path getInputFolder() {
        return staticData.getInputFolder();
    }

    @Override
    public void setOutputFolder(@NotNull Path newOutputFolder) {

        Path oldOutputFolder = staticData.getOutputFolder();
        staticData.setOutputFolder(newOutputFolder);

        logger.debug("OutputFolder changed: <{}> -> <{}>", oldOutputFolder, newOutputFolder);
        firePropertyChange(OUTPUT_FOLDER_CHANGED, oldOutputFolder, newOutputFolder);
    }

    @Override
    public @NotNull Path getOutputFolder() {
        return staticData.getOutputFolder();
    }

    @Override
    public void setSuffixes(@NotNull ISuffixes newSuffixes) {

        staticData.setCurrentSuffixes(newSuffixes);

        logger.debug("Suffixes changed: <{}>", newSuffixes);
        firePropertyChange(SELECTED_SUFFIXES_COLLECTION_CHANGED, newSuffixes.getName(), newSuffixes);
    }

    @Override
    public @NotNull ISuffixes getSuffixes() {
        return staticData.getCurrentSuffixes();
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

        FileOperationEnum newOperation = FileOperationEnum.valueOf(operationName);
        FileOperationEnum oldOperation = staticData.getCurrentOperation();
        staticData.setCurrentOperation(newOperation);

        logger.debug("Operation changed: <{}> -> <{}>", oldOperation, newOperation);
        firePropertyChange(OPERATION_CHANGED, oldOperation, newOperation);
    }

    @Override
    public String getOperation() {
        FileOperationEnum fileOperationEnum = staticData.getCurrentOperation();
        return fileOperationEnum.name();
    }

    @Override
    public @NotNull IJobManager getJobManager() {
        return jobManager;
    }

    @Override
    public Job createJobSyncWithDefaultParameters() {
        Job newJob = new JobImpl(
                getInputFolder(),
                getOutputFolder(),
                getSuffixes(),
                staticData.getCurrentOperation().getFileOperationInstance()
        );
        return newJob;
    }

    @Override
    public Job createJobAsyncWithDefaultParameters() {
        return createJobAsyncWithDefaultParameters(null);
    }

    @Override
    public @NotNull Job createJobAsyncWithDefaultParameters(@Nullable PropertyChangeListener listener) {
        Job newJob = createJobSyncWithDefaultParameters();
        if (listener != null) {
            newJob.addPropertyChangeListener(listener);
        }
        jobManager.appendJob(newJob);
        return newJob;
    }

    @Override
    public @NotNull CollectionOfSuffixesStaticData getPredefinedFileSuffixesDb() {
        return staticData.getCollectionOfSuffixesStaticData();
    }

    @Override
    public void addNewPredefinedFileSuffixes(@NotNull ISuffixes newPredefinedSuffixes) {
        staticData.addNewPredefinedSuffixes(newPredefinedSuffixes);

        logger.debug("New suffixes added: <{}>", newPredefinedSuffixes.getName());
        firePropertyChange(NEW_SUFFIXES_COLLECTION_ADDED, newPredefinedSuffixes.getName(), newPredefinedSuffixes);
    }

    @Override
    public void removePredefinedFileSuffixes(@NotNull String name) {
        staticData.removePredefinedSuffixes(name);

        logger.debug("Suffixes removed: <{}>", name);
        firePropertyChange(SUFFIXES_COLLECTION_DELETED, name, -0);
    }

    @Override
    public Optional<ISuffixes> getPredefinesFileSuffixesByName(@NotNull String name) {
        return staticData.getPredefinedSuffixesByName(name);
    }

    @Override
    public void storeAll() {
        throw new UnsupportedOperationException("This operation is not supported.");
    }

    @Override
    public void countRelevantFilesInInputFolder() {
        IFileLocator fileLocator = new FileLocatorImpl();
        fileLocator.setRootFolder(getInputFolder());
        fileLocator.setSuffixes(getSuffixes());

        locateFilesAsync.locate(fileLocator);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String eventName = evt.getPropertyName();
        if(eventName.equalsIgnoreCase("SEARCH_FINISHED")) {
            firePropertyChange(COUNTING_RELEVANT_FILES_FINISHED, evt.getOldValue(), evt.getNewValue());
        }
    }
}