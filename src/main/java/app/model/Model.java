package app.model;

import app.model.jobs.IJobManager;
import app.model.jobs.Job;
import app.model.simplemodel.CollectionOfSuffixesCollectionsStaticData;
import app.model.jobs.Job;
import app.model.jobs.IJobManager;
import app.model.simplemodel.CollectionOfSuffixesCollectionsStaticData;
import org.jetbrains.annotations.NotNull;

import java.beans.PropertyChangeListener;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 *
 *
 * The application allows for the predefined Operation to by applied to files in the input folder.
 *
 * User sets parameters:
 *      - Operation
 *      The application operation such as file copy, rename etc.
 *      - input folder (root/source folder)
 *      any application operation shall be applied to the relevant files in this folder
 *      - output folder (target/destination folder)
 *      if the application operation produces any file results, they will be delivered into this folder
 *      - suffixes (file extensions such as bat,exe,txt)
 *      those are used as file filters, only files with those suffixes are relevant for the application's operation
 *
 * Any operation should be able to be executed both in synchronously and asynchronously (in parallel with User/caller).
 *
 * Synchronous usage:
 *      IJob newJob = model.createJobSyncWithDefaultParameters();
 *      newJob.start();
 *      .... operation is being performed in foreground
 *
 * Asynchronous usage (IJobManager):
 *      IJob newJob = model.createJobAsyncWithDefaultParameters();
 *      .... operation is being queued and will be performed in background any time soon
 *
 *      while(newJob.isFinished()) {
 *          ; // wait for it to finish
 *      }
 *
 *      OR
 *
 *      IJob newJob = model.createJobSyncWithDefaultParameters();
 *      IJobManager jobManager = model.getJobManager();
 *      jobManager.appendJob(newJob)
 *      .... operation is being queued and will be performed in background any time soon
 *
 *      while(jobManger.isFinished(newJob.getId())) {
 *           ; // wait for it to finish
 *      }
 */
public abstract class Model extends AbstractObservableModel {
    public abstract void setInputFolder(@NotNull Path newInputFolder);
    public abstract @NotNull Path getInputFolder();

    public abstract void setOutputFolder(@NotNull Path newOutputFolder);
    public abstract @NotNull Path getOutputFolder();

    public abstract void setSuffixes(@NotNull ISuffixesCollection newSuffixes);
    public abstract @NotNull ISuffixesCollection getSuffixes();

    public abstract @NotNull List<String> getSupportedOperationNames();
    public abstract void setOperation(@NotNull String operationName) throws IllegalArgumentException;
    public abstract @NotNull String getOperation();

    public abstract @NotNull IJobManager getJobManager();

    /**
     * Creates a new IJob with parameters set previously by calling setInputFolder,setOutputFolder methods
     */
    public abstract @NotNull Job createJobSyncWithDefaultParameters();

    /**
     * Creates a new IJob with parameters set previously by calling setInputFolder,setOutputFolder methods
     * This new job is internally processed by IJobManager
     * and scheduled to run as soon as asynchronous resources are available
     * (Eg from thread pool)
     */
    public abstract @NotNull Job createJobAsyncWithDefaultParameters();

    /**
     * Same as the parameterless method. Moreover allows to assign observer to the created job as soon as possible.
     */
    public abstract @NotNull Job createJobAsyncWithDefaultParameters(@NotNull PropertyChangeListener listener);

    public abstract @NotNull CollectionOfSuffixesCollectionsStaticData getPredefinedFileSuffixesDb();
    public abstract void addNewPredefinedFileSuffixesCollection(@NotNull ISuffixesCollection newPredefinedSuffixesCollection);
    public abstract void removePredefinedFileSuffixesCollection(@NotNull String name);
    public abstract @NotNull Optional<ISuffixesCollection> getPredefinesFileSuffixesCollectionByName(@NotNull String name);

    /**
     * Use persistent storage.
     */
    public abstract void storeAll();

    /**
     * This method executes in its own thread and should report using fire-observable methods.
     */
    public abstract void countRelevantFilesInInputFolder();
}