package model.simplemodel;

import model.ISuffixesCollection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
public class CollectionOfSuffixesCollections implements Iterable<ISuffixesCollection> {

    private static final Logger logger = LoggerFactory.getLogger(CollectionOfSuffixesCollections.class);

    private final List<ISuffixesCollection> suffixesCollections;

    public CollectionOfSuffixesCollections() {
        suffixesCollections = new ArrayList<>();
    }

    public void addNewSuffixesCollection(@Nullable ISuffixesCollection newSuffixesCollection) {
        if (!suffixesCollections.contains(newSuffixesCollection)) {
            suffixesCollections.add(newSuffixesCollection);
        } else {
            logger.warn(
                    "This suffixes collection <{}> is already in this Db <{}>. Ignoring.",
                    newSuffixesCollection,
                    this
            );
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

    public boolean isEmpty() {
        return size() == 0;
    }

    public ISuffixesCollection getFirst() throws IndexOutOfBoundsException {
        return suffixesCollections.get(0);
    }

    @NotNull
    @Override
    public Iterator<ISuffixesCollection> iterator() {
        return suffixesCollections.iterator();
    }
}
