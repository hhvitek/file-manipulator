package app.model.simplemodel;

import app.model.ISuffixes;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Represents collection/database/list of ISuffixes (collection of suffixes strings)
 * {@code
 * <DB>
 *  -> <ISuffixes>
 *          -> mp3, mpa,
 *          -> exa, bat
 *  -> <ISuffixes>
 *          -> aaa, bbb
 *          -> ccc, ddd
 *  }
 */
public class CollectionOfSuffixesStaticData implements Iterable<ISuffixes> {

    private static final Logger logger = LoggerFactory.getLogger(CollectionOfSuffixesStaticData.class);

    private String name = "";

    private final Map<String, ISuffixes> suffixesCollections;

    public CollectionOfSuffixesStaticData() {
        suffixesCollections = new HashMap<>();
    }

    public CollectionOfSuffixesStaticData(@NotNull String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Add new ISuffixes if it is not already present (Optional operation).
     */
    public void addNewSuffixesIfAbsent(@NotNull ISuffixes newSuffixes) {
        String name = newSuffixes.getName();
        suffixesCollections.putIfAbsent(name, newSuffixes);
    }

    /**
     * If the ISuffixes is already present in a collection, this method behaves same as
     * addNewSuffixesIfAbsent().
     * Otherwise it just updates its suffixes list according to the modifiedSuffixes.
     */
    public void updateSuffixesOrAddNewOne(@NotNull ISuffixes modifiedSuffixes) {
        String name = modifiedSuffixes.getName();
        suffixesCollections.put(name, modifiedSuffixes);
    }

    public void removeSuffixesIfExists(@NotNull String name) {
        suffixesCollections.remove(name);
    }

    public Optional<ISuffixes> getSuffixesByName(@NotNull String name) {
        ISuffixes suffixes = suffixesCollections.get(name);
        if(suffixes != null) {
            return Optional.of(suffixes);
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

    public ISuffixes getFirst() throws NoSuchElementException {
        return iterator().next();
    }

    @NotNull
    @Override
    public Iterator<ISuffixes> iterator() {
        return suffixesCollections.values().iterator();
    }
}