package model;

import model.observer.IObservable;
import model.simplemodel.SimpleModelSuffixesDb;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface IModel extends IObservable {
    void setInputFolder(@NotNull Path newInputFolder);
    @NotNull Path getInputFolder();

    void setOutputFolder(@NotNull Path newOutputFolder);
    @NotNull Path getOutputFolder();

    void setSuffixes(ISuffixesCollection newSuffixes);
    ISuffixesCollection getSuffixes();

    List<String> getSupportedOperationNames();
    void setOperation(String operationName) throws IllegalArgumentException;

    int createJob(Path inputFolder, Path outputFolder, ISuffixesCollection suffixes);
    int createJobWithDefaultParameters();

    Optional<IJob> getJobDetails(int jobId);
    void stopJob(int jobId);
    void stopAll();
    void startJobInParallel(int jobId);

    SimpleModelSuffixesDb getPredefinedFileSuffixesDb();
    void addNewPredefinedFileSuffixesCollection(ISuffixesCollection newPredefinedSuffixesCollection);
    Optional<ISuffixesCollection> getPredefinesFileSuffixesCollectionByName(String name);
}
