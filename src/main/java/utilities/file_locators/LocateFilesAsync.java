package utilities.file_locators;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Simple wrapper around IFileLocator
 * It executes the locator in separate thread.
 * The finished result is signaled using Observer pattern...
 */
public class LocateFilesAsync extends AbstractObservable {

    private final ExecutorService executor;

    private Future future;

    public LocateFilesAsync() {
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Wait for relevant event using observer pattern...
     * @param fileLocator
     */
    public void locate(@NotNull IFileLocator fileLocator) {
        Runnable runnable = () -> task(fileLocator);
        future = executor.submit(runnable);
    }

    private void task(IFileLocator fileLocator) {
        List<Path> foundFiles = fileLocator.find();
        firePropertyChange("SEARCH_FINISHED", foundFiles.size(), foundFiles);
    }
}
