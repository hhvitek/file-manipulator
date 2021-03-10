package app.model.jobs;


import app.model.AbstractObservableModel;

/**
 * The Job. Represents an Operation.
 * start() starts the Operation sequentially.
 * stop() signals to the Operation that it should stop processing as soon as possible.
 *        stop() does NOT stops operation immediately. Actually may take a several minutes to finishes...
 *        E.g. Let's define the Operation as copying many files. If the current file which is being copied is very large
 *        (several dozens GB), the Operation may stops only after copying is finished.
 */
public abstract class Job extends AbstractObservableModel {
    public abstract int getId();
    public abstract boolean isRunning();
    public abstract boolean isFinished();
    public abstract void start();
    public abstract void stop();
}