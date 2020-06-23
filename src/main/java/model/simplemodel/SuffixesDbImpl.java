package model.simplemodel;

import model.ISuffixesDb;
import model.SuffixesDbException;

public class SuffixesDbImpl implements ISuffixesDb {

    private final CollectionOfSuffixesCollections categoriesDb;

    public SuffixesDbImpl() {
        categoriesDb = new CollectionOfSuffixesCollections();
        loadAllKnownPredefinedSuffixesFromEnum();
    }

    private void loadAllKnownPredefinedSuffixesFromEnum() {
        for(PredefinedSuffixesEnum value: PredefinedSuffixesEnum.values()) {
            categoriesDb.addNewSuffixesCollection(value.getSuffixes());
        }
    }

    @Override
    public CollectionOfSuffixesCollections load() {
        return categoriesDb;
    }

    @Override
    public void store(CollectionOfSuffixesCollections collectionOfSuffixesCollections) throws SuffixesDbException {
        throw new SuffixesDbException("Not yet implemented!");
    }
}
