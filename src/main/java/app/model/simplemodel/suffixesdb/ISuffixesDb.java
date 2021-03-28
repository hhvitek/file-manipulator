package app.model.simplemodel.suffixesdb;

import app.model.SuffixesDbException;
import app.model.simplemodel.CollectionOfSuffixesStaticData;

/**
 * Represents abstraction of one time loading of
 * static/persistent predefined/saved suffixes
 * into CollectionOfSuffixess object representation.
 *
 * Underline storage could be file, database (such as sqlite), or simple hard-coded constants.
 */
public interface ISuffixesDb {

    boolean isPersistent();
    CollectionOfSuffixesStaticData load() throws SuffixesDbException;
    void store(CollectionOfSuffixesStaticData collectionOfSuffixesStaticData) throws SuffixesDbException;
}