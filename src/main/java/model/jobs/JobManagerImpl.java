package model.jobs;

import model.ISuffixesCollection;
import model.file_operations.IFileOperation;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JobManagerImpl implements IJobManager {

    private final Map<Integer, IJob> jobs;
    private final Map<Integer, Future<IJob>> jobTasks;

    private final ExecutorService executor;

    public JobManagerImpl() {
        jobs = new HashMap<>();
        jobTasks = new HashMap<>();
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public int createJob(Path inputFolder, Path outputFolder, ISuffixesCollection suffixes, IFileOperation fileOperation) {
        IJob newJob = new JobImpl(inputFolder, outputFolder, suffixes, fileOperation);
        appendJob(newJob);
        return newJob.getId();
    }

    public void appendJob(IJob newJob) {
        jobs.put(newJob.getId(), newJob);
        createNewJobTask(newJob);
    }

    private void createNewJobTask(IJob job) {
        Runnable runnableJob = job::start;
        Future future = executor.submit(runnableJob);
        jobTasks.put(job.getId(), future);
    }

    @Override
    public List<IJob> getJobs() {
        List<IJob> jobs = getListFromCollection(this.jobs.values());
        return Collections.unmodifiableList(jobs);
    }

    private List<IJob> getListFromCollection(Collection<IJob> collection) {
        return new ArrayList<>(collection);
    }

    @Override
    public Optional<IJob> getJobById(int id) {
        if (jobs.containsKey(id)) {
            return Optional.of(jobs.get(id));
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
        jobs.values().forEach(
                IJob::stop
        );
    }

    @Override
    public void cancelAll() {
        jobTasks.values().forEach(
                x -> x.cancel(true)
        );
    }

    @Override
    public void shutdown() {
        stopAll();
        cancelAll();
        executor.shutdown();
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

    @Override
    public boolean isFinished(int jobId) {
        Optional<IJob> foundJob = getJobById(jobId);
        if (foundJob.isPresent()) {
            return foundJob.get().isFinished();
        } else {
            return false;
        }
    }
}
