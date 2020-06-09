package model.jobs;

import model.IJob;
import model.ISuffixesCollection;
import model.file_operations.FileOperationException;
import model.file_operations.IFileOperation;
import model.jobs.file_locators.FileLocatorImpl;
import model.jobs.file_locators.IFileLocator;
import model.string_filters.operations.Operation;
import model.string_filters.operations.filename.FileNameFilterOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class SimpleModelIJobWithoutSwitch implements IJob {

    private static final Logger logger = LoggerFactory.getLogger(SimpleModelJob.class);

    private static int autoIncrement = 0;
    private static Operation operation = new FileNameFilterOperation();
    private static IFileLocator fileLocator = new FileLocatorImpl();
    private static IFileOperation fileOperation;

    private Path inputFolder;
    private Path outputFolder;
    private ISuffixesCollection suffixes;

    private int jobId;
    private boolean isRunning;
    private volatile boolean shouldStop;

    public SimpleModelIJobWithoutSwitch(Path inputFolder, Path outputFolder, ISuffixesCollection suffixes, IFileOperation fileOperation) {
        this.inputFolder = inputFolder;
        this.outputFolder = outputFolder;
        this.suffixes = suffixes;
        jobId = autoIncrement++;
        this.fileOperation = fileOperation;
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
    public void start() {
        if (!isRunning()) {
            isRunning = true;
            shouldStop = false;
            List<Path> foundFiles = fileLocator.findUsingSuffixes(inputFolder, suffixes);
            for (Path file : foundFiles) {
                if (shouldStop) {
                    break;
                }
                performOperation(file);
            }
            isRunning = false;
        }
    }

    private void performOperation(Path file) {
        Path newFilteredPath = performFilterOperationOnFileName(file);
        try {
            fileOperation.performFileOperation(file, newFilteredPath);
        } catch (FileOperationException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    private Path performFilterOperationOnFileName(Path file) {
        String oldFileName = file.getFileName().toString();
        String newFileName = operation.filter(oldFileName);
        return outputFolder.resolve(newFileName);
    }

    @Override
    public void stop() {
        shouldStop = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleModelIJobWithoutSwitch that = (SimpleModelIJobWithoutSwitch) o;
        return jobId == that.jobId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId);
    }
}
