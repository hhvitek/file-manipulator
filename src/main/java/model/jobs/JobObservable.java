package model.jobs;

import java.nio.file.Path;

public interface JobObservable {
    void addObserver(JobObserver observer);
    void removeObserver(JobObserver observer);
    void notifyObservers(Path oldFile, Path newFile);
}
