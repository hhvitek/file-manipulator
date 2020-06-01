package model;

import model.file_operations.FileOperationEnum;
import model.observer.IObservable;

public interface IModel extends IObservable, IModelStaticData {
    void setFileOperation(FileOperationEnum fileOperation);
    FileOperationEnum getFileOperation();

    void getCurrentStatusObject();
    void start();
    void stop();
}
