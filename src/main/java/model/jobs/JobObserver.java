package model.jobs;

import java.nio.file.Path;

public interface JobObserver {
    void update(IJob job, Path input, Path output);
}
