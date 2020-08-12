package model.jobs;

import java.nio.file.Path;

public class JobStatus {

    private final int jobId;
    private final int totalFilesCount;

    private Path oldPath;
    private Path newPath;

    private int remainingFileCount;

    private String errorMessage;

    public JobStatus(int jobId, int totalFilesCount) {
        this.jobId = jobId;
        this.totalFilesCount = totalFilesCount;
        remainingFileCount = totalFilesCount;
        errorMessage = "";
    }

    public void fileProcessed(Path oldPath, Path newPath) {
        this.oldPath = oldPath;
        this.newPath = newPath;

        remainingFileCount--;
    }

    public void fileProcessedWithError(Path oldPath, Path newPath, String errorMessage) {
        fileProcessed(oldPath, newPath);
        this.errorMessage = errorMessage;
    }

    public int getJobId() {
        return jobId;
    }

    public int getTotalFilesCount() {
        return totalFilesCount;
    }

    public Path getOldPath() {
        return oldPath;
    }

    public Path getNewPath() {
        return newPath;
    }

    public int getRemainingFileCount() {
        return remainingFileCount;
    }

    public boolean encounteredError() {
        return !errorMessage.isBlank();
    }
}
