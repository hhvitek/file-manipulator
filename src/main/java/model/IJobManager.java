package model;

import model.file_operations.IFileOperation;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface IJobManager {
    int createJob(Path inputFolder, Path outputFolder, ISuffixesCollection suffixes, IFileOperation fileOperation);
    List<IJob> getJobs();
    Optional<IJob> getJobById(int id);
    void stopJobIfExists(int jobId);
    void stopAll();
    void cancel();
    void startJobIfExists(int jobId);
    boolean isRunning(int jobId);
}
