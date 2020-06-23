package model;

import model.jobs.IJob;
import model.jobs.IJobManager;
import model.simplemodel.CollectionOfSuffixesCollections;
import org.jetbrains.annotations.NotNull;

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
public interface IModel {
    void setInputFolder(@NotNull Path newInputFolder);
    @NotNull Path getInputFolder();

    void setOutputFolder(@NotNull Path newOutputFolder);
    @NotNull Path getOutputFolder();

    void setSuffixes(@NotNull ISuffixesCollection newSuffixes);
    @NotNull ISuffixesCollection getSuffixes();

    @NotNull List<String> getSupportedOperationNames();
    void setOperation(@NotNull String operationName) throws IllegalArgumentException;

    @NotNull IJobManager getJobManager();

    /**
     * Creates a new IJob with parameters set previously by calling setInputFolder,setOutputFolder methods
     */
    @NotNull IJob createJobSyncWithDefaultParameters();

    /**
     * Creates a new IJob with parameters set previously by calling setInputFolder,setOutputFolder methods
     * This new job is internally processed by IJobManager
     * and scheduled to run as soon as asynchronous resources are available
     * (Eg from thread pool)
     */
    @NotNull IJob createJobAsyncWithDefaultParameters();

    @NotNull CollectionOfSuffixesCollections getPredefinedFileSuffixesDb();
    void addNewPredefinedFileSuffixesCollection(@NotNull ISuffixesCollection newPredefinedSuffixesCollection);
    Optional<ISuffixesCollection> getPredefinesFileSuffixesCollectionByName(@NotNull String name);
}
