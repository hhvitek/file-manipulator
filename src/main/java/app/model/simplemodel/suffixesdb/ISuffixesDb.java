package app.model.simplemodel.suffixesdb;

import app.model.SuffixesDbException;
import app.model.simplemodel.CollectionOfSuffixesCollectionsStaticData;

/**
 * Represents abstraction of one time loading of
 * static/persistent predefined/saved suffixes
 * into CollectionOfSuffixesCollections object representation.
 *
 * Underline storage could be file, database (such as sqlite), or simple hard-coded constants.
 */
public interface ISuffixesDb {

    boolean isPersistent();
    CollectionOfSuffixesCollectionsStaticData load() throws SuffixesDbException;
    void store(CollectionOfSuffixesCollectionsStaticData collectionOfSuffixesCollectionsStaticData) throws SuffixesDbException;
}