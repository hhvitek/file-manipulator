package model.observer;

import java.util.ArrayList;
import java.util.List;

public class ModelObservableHelper implements IObservable {

    private List<IObserver> observers;

    public ModelObservableHelper() {
        observers = new ArrayList<>();
    }

    @Override
    public void addObserver(IObserver observer) {
        if (!doesAlreadyContain(observer)) {
            observers.add(observer);
        }
    }

    private boolean doesAlreadyContain(IObserver observer) {
        return observers.contains(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(
                observer -> observer.update()
        );
    }
}
