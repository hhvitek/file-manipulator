package app.model.simplemodel;

import app.model.ISuffixesCollection;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Represents collection/database/list of ISuffixesCollection (collection of suffixes)
 * {@code
 * <DB>
 *  -> <ISuffixCollection>
 *          -> mp3, mpa,
 *          -> exa, bat
 *  -> <ISuffixCollection>
 *          -> aaa, bbb
 *          -> ccc, ddd
 *  }
 */
public class CollectionOfSuffixesCollectionsStaticData implements Iterable<ISuffixesCollection> {

    private static final Logger logger = LoggerFactory.getLogger(CollectionOfSuffixesCollectionsStaticData.class);

    private String name = "";

    private final Map<String,ISuffixesCollection> suffixesCollections;

    public CollectionOfSuffixesCollectionsStaticData() {
        suffixesCollections = new HashMap<>();
    }

    public CollectionOfSuffixesCollectionsStaticData(@NotNull String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Add new ISuffixesCollection if it is not already present (Optional operation).
     */
    public void addNewSuffixesCollectionIfAbsent(@NotNull ISuffixesCollection newSuffixesCollection) {
        String name = newSuffixesCollection.getName();
        suffixesCollections.putIfAbsent(name, newSuffixesCollection);
    }

    /**
     * If the ISuffixesCollection is already present in a collection, this method behaves same as
     * addNewSuffixesCollectionIfAbsent().
     * Otherwise it just updates its suffixes list according to the modifiedSuffixesCollection.
     */
    public void updateSuffixesCollectionOrAddNewOne(@NotNull ISuffixesCollection modifiedSuffixesCollection) {
        String name = modifiedSuffixesCollection.getName();
        suffixesCollections.put(name, modifiedSuffixesCollection);
    }

    public void removeSuffixesCollectionIfExists(@NotNull String name) {
        suffixesCollections.remove(name);
    }

    public Optional<ISuffixesCollection> getSuffixesCollectionByName(@NotNull String name) {
        ISuffixesCollection suffixesCollection = suffixesCollections.get(name);
        if(suffixesCollection != null) {
            return Optional.of(suffixesCollection);
        } else {
            return Optional.empty();
        }
    }

    public int size() {
        return suffixesCollections.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public ISuffixesCollection getFirst() throws NoSuchElementException {
        return iterator().next();
    }

    @NotNull
    @Override
    public Iterator<ISuffixesCollection> iterator() {
        return suffixesCollections.values().iterator();
    }
}