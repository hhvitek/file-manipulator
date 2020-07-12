package model.simplemodel.suffixesdb;

import model.SuffixesDbException;
import model.simplemodel.CollectionOfSuffixesCollections;

/**
 * Represents abstraction of one time loading of
 * static/persistent predefined/saved suffixes
 * into CollectionOfSuffixesCollections object representation.
 *
 * Underline storage could be file, database (such as sqlite), or simple hard-coded constants.
 */
public interface ISuffixesDb {
    CollectionOfSuffixesCollections load() throws SuffixesDbException;
    void store(CollectionOfSuffixesCollections collectionOfSuffixesCollections) throws SuffixesDbException;
}
