package app.model.jobs;

import app.model.ISuffixes;
import app.model.file_operations.IFileOperation;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Spawns/creates and manages Jobs.
 * Every job should run concurrently of its caller.
 */
public interface IJobManager {
    int createJob(Path inputFolder, Path outputFolder, ISuffixes suffixes, IFileOperation fileOperation);
    void appendJob(Job newJob);
    void stopJobIfExists(int jobId);

    List<Job> getJobs();
    Optional<Job> getJobById(int id);

    void stopAll();
    void cancelAll();
    void shutdown();

    boolean isRunning(int jobId);
    boolean isFinished(int jobId);

    /**
     * Whether a new job is executed immediately or it has to wait for free resources.
     * @return true if it has to wait.
     */
    boolean isJobManagerBusy();
}