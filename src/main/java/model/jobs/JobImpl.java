package model.jobs;

import model.ISuffixesCollection;
import model.file_operations.FileOperationException;
import model.file_operations.IFileOperation;
import model.jobs.file_locators.FileLocatorImpl;
import model.jobs.file_locators.IFileLocator;
import model.string_filters.operations.Operation;
import model.string_filters.operations.filename.FileNameFilterOperation;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * If suffixesCollection passed in constructor is null, the Operation is applied to all files...
 */
public class JobImpl implements IJob {

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

    private final List<JobObserver> observers = new ArrayList<>();

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
    public boolean isStarted() {
        return isStarted;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void start() {
        isStarted = true;

        if (!isRunning()) {
            isRunning = true;
            shouldStop = false;

            List<Path> foundFiles = fileLocator.findUsingSuffixesCollection(inputFolder, suffixesCollection);
            for (Path file : foundFiles) {
                if (shouldStop) {
                    break;
                }
                Path newFilteredPath = performFilterOperationOnFileName(file);
                performOperation(file, newFilteredPath);
                notifyObservers(file, newFilteredPath);
            }

            isRunning = false;
            isFinished = true;
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
        } catch (FileOperationException e) {
            logger.error(e.getLocalizedMessage(), e);
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

    @Override
    public void addObserver(JobObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(JobObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Path oldFile, Path newFile) {
        for (JobObserver observer : observers) {
            observer.update(this, oldFile, newFile);
        }
    }
}
