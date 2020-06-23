package model.jobs;

import model.ISuffixesCollection;
import model.file_operations.IFileOperation;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Spawns/creates and manages Jobs.
 * Every job should run concurrently of its caller.
 */
public interface IJobManager {
    int createJob(Path inputFolder, Path outputFolder, ISuffixesCollection suffixes, IFileOperation fileOperation);
    void appendJob(IJob newJob);
    void stopJobIfExists(int jobId);

    List<IJob> getJobs();
    Optional<IJob> getJobById(int id);

    void stopAll();
    void cancelAll();
    void shutdown();

    boolean isRunning(int jobId);
    boolean isFinished(int jobId);
}
