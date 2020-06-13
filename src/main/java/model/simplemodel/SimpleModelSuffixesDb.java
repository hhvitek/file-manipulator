package model.simplemodel;

import model.ISuffixesCollection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class SimpleModelSuffixesDb implements Iterable<ISuffixesCollection> {

    private final List<ISuffixesCollection> suffixesCollections;

    public SimpleModelSuffixesDb() {
        suffixesCollections = new ArrayList<>();
    }

    public void addNewSuffixesCollection(ISuffixesCollection newSuffixesCollection) {
        if (!suffixesCollections.contains(newSuffixesCollection)) {
            suffixesCollections.add(newSuffixesCollection);
        }
    }

    public Optional<ISuffixesCollection> getSuffixesCollectionByName(String name) {
        for(ISuffixesCollection suffixesCollection: suffixesCollections) {
            if (suffixesCollection.hasName(name)) {
                return Optional.of(suffixesCollection);
            }
        }
        return Optional.empty();
    }

    public int size() {
        return suffixesCollections.size();
    }

    public ISuffixesCollection getFirstSuffixesCollection() {
        if (suffixesCollections.isEmpty()) {
            return new SimpleModelSuffixesCollectionImpl();
        } else {
            return suffixesCollections.get(0);
        }
    }

    @NotNull
    @Override
    public Iterator<ISuffixesCollection> iterator() {
        return suffixesCollections.iterator();
    }
}
