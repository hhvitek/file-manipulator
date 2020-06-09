package model.jobs;

import model.IJob;
import model.IJobManager;
import model.ISuffixesCollection;
import model.file_operations.IFileOperation;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SimpleModelJobManager implements IJobManager {

    private List<IJob> jobs;
    private List<Future> jobTasks;
    private final ExecutorService executor;

    public SimpleModelJobManager() {
        jobs = new ArrayList<>();
        jobTasks = new ArrayList<>();
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public int createJob(Path inputFolder, Path outputFolder, ISuffixesCollection suffixes, IFileOperation fileOperation) {
        IJob newJob = new SimpleModelIJobWithoutSwitch(inputFolder, outputFolder, suffixes, fileOperation);
        jobs.add(newJob);
        createNewJobTask(newJob);
        return newJob.getId();
    }

    private void createNewJobTask(IJob job) {
        Runnable runnableJob = () -> {
            job.start();
        };
        Future future = executor.submit(runnableJob);
        jobTasks.add(future);
    }

    @Override
    public List<IJob> getJobs() {
        return jobs;
    }

    @Override
    public Optional<IJob> getJobById(int id) {
        for(IJob job: jobs) {
            if (job.getId() == id) {
                return Optional.of(job);
            }
        }
        return Optional.empty();
    }

    @Override
    public void stopJobIfExists(int jobId) {
        Optional<IJob> job = getJobById(jobId);
        job.ifPresent(IJob::stop);
    }

    @Override
    public void stopAll() {
        jobs.forEach(
                IJob::stop
        );
    }

    @Override
    public void cancel() {
        stopAll();
        executor.shutdown();
    }

    @Override
    public void startJobIfExists(int jobId) {
        Optional<IJob> job = getJobById(jobId);
        job.ifPresent(IJob::start);
    }

    @Override
    public boolean isRunning(int jobId) {
        Optional<IJob> job = getJobById(jobId);
        if (job.isPresent()) {
            return job.get().isRunning();
        } else {
            return false;
        }
    }


}
