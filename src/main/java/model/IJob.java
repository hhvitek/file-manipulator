package model;

public interface IJob {
    int getId();
    boolean isRunning();
    void start();
    void stop();
}
