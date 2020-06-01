package model;

import model.file_operations.FileOperationEnum;
import model.observer.IObserver;
import model.observer.ModelObservableHelper;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Optional;

public class SimpleModel implements IModel {

    private ModelObservableHelper modelObservableHelper;
    private IModelStaticData staticData;

    public SimpleModel() {
        modelObservableHelper = new ModelObservableHelper();
        staticData = new SimpleModelStaticDataImpl();
    }

    @Override
    public void setInputFolder(@NotNull Path newInputFolder) {
        staticData.setInputFolder(newInputFolder);
    }

    @Override
    public @NotNull Path getInputFolder() {
        return staticData.getInputFolder();
    }

    @Override
    public void setOutputFolder(@NotNull Path newOutputFolder) {
        staticData.setOutputFolder(newOutputFolder);
    }

    @Override
    public @NotNull Path getOutputFolder() {
        return staticData.getOutputFolder();
    }

    @Override
    public void setFileSuffixes(ISuffixesCategory newSuffixes) {
        staticData.setFileSuffixes(newSuffixes);
    }

    @Override
    public ISuffixesCategory getFileSuffixes() {
        return staticData.getFileSuffixes();
    }

    @Override
    public SimpleModelSuffixesCategoriesDb getPredefinedFileSuffixesCategories() {
        return staticData.getPredefinedFileSuffixesCategories();
    }

    @Override
    public void addNewPredefinedFileSuffixesCategory(ISuffixesCategory newPredefinedSuffixesCategory) {
        staticData.addNewPredefinedFileSuffixesCategory(newPredefinedSuffixesCategory);
    }

    @Override
    public Optional<ISuffixesCategory> getPredefinesFileSuffixesByCategoryName(String categoryName) {
        return staticData.getPredefinesFileSuffixesByCategoryName(categoryName);
    }

    @Override
    public void setFileOperation(FileOperationEnum fileOperation) {

    }

    @Override
    public FileOperationEnum getFileOperation() {
        return null;
    }

    @Override
    public void getCurrentStatusObject() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void addObserver(IObserver observer) {
        modelObservableHelper.addObserver(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        modelObservableHelper.removeObserver(observer);
    }

    @Override
    public void notifyObservers() {
        modelObservableHelper.notifyObservers();
    }
}
