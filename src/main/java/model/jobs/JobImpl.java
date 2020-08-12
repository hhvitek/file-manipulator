package model.jobs;

import model.ISuffixesCollection;
import model.ModelObservableEvents;
import model.file_operations.FileOperationException;
import model.file_operations.IFileOperation;
import utilities.file_locators.FileLocatorImpl;
import utilities.file_locators.IFileLocator;
import model.string_filters.operations.Operation;
import model.string_filters.operations.filename.FileNameFilterOperation;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * If suffixesCollection passed in constructor is null, the Operation is applied to all files...
 */
public class JobImpl extends Job {

    private static final Logger logger = LoggerFactory.getLogger(JobImpl.class);

    // shared across all Jobs
    private static final AtomicInteger autoIncrement = new AtomicInteger();

    // shared between this job and the creator of job
    private volatile boolean shouldStop;
    private volatile boolean isStarted;
    private volatile boolean isFinished;
    private volatile boolean isRunning;

    private static final Operation operation = new FileNameFilterOperation();
    private static final IFileLocator fileLocator = new FileLocatorImpl();

    private final Path inputFolder;
    private final Path outputFolder;
    private final ISuffixesCollection suffixesCollection;
    private final IFileOperation fileOperation;

    private final int jobId;

    private JobStatus jobStatus;

    public JobImpl(@NotNull Path inputFolder,
                   @NotNull Path outputFolder,
                   @Nullable ISuffixesCollection suffixesCollection,
                   @NotNull IFileOperation fileOperation) {
        this.inputFolder = inputFolder;
        this.outputFolder = outputFolder;
        this.suffixesCollection = suffixesCollection;
        this.fileOperation = fileOperation;

        jobId = autoIncrement.incrementAndGet();
    }

    @Override
    public int getId() {
        return jobId;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void start() {
        if (!isRunning() && !isFinished()) {
            isRunning = true;
            shouldStop = false;

            List<Path> foundFiles = fileLocator.findUsingSuffixesCollection(inputFolder, suffixesCollection);
            jobStatus = new JobStatus(jobId, foundFiles.size());
            firePropertyChange(ModelObservableEvents.JOB_RUNNING, jobId, jobStatus);

            for (Path file : foundFiles) {
                if (shouldStop) {
                    firePropertyChange(ModelObservableEvents.JOB_STOPPED, jobId, jobStatus);
                    break;
                }
                Path newFilteredPath = performFilterOperationOnFileName(file);
                performOperation(file, newFilteredPath);

                firePropertyChange(ModelObservableEvents.JOB_FILE_PROCESSED, jobId, jobStatus);
            }

            isRunning = false;
            isFinished = true;
            firePropertyChange(ModelObservableEvents.JOB_FINISHED, jobId, jobStatus);
        }
    }

    private Path performFilterOperationOnFileName(Path file) {
        String oldFileName = file.getFileName().toString();
        String oldFileBaseName = FilenameUtils.getBaseName(oldFileName);
        String oldFileSuffix = FilenameUtils.getExtension(oldFileName);

        String newFileBaseName = operation.filter(oldFileBaseName);
        String newFileName = appendSuffixToFileBaseName(newFileBaseName, oldFileSuffix);

        return outputFolder.resolve(newFileName);
    }

    private String appendSuffixToFileBaseName(@NotNull String baseName, @Nullable String suffix) {
        if (suffix != null && !suffix.isEmpty()) {
            baseName = baseName + "." + suffix;
        }
        return baseName;
    }

    private void performOperation(Path file, Path newFilteredPath) {
        try {
            //Thread.sleep(2000);
            fileOperation.performFileOperation(file, newFilteredPath);
            jobStatus.fileProcessed(file, newFilteredPath);
        } catch (FileOperationException e) {
            jobStatus.fileProcessedWithError(file, newFilteredPath, e.getMessage());
            logger.error(e.toString());
        }
    }

    @Override
    public void stop() {
        shouldStop = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobImpl that = (JobImpl) o;
        return jobId == that.jobId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId);
    }
}
