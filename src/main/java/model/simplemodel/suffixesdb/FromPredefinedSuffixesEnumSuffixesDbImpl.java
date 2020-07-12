package model.simplemodel.suffixesdb;

import model.SuffixesDbException;
import model.simplemodel.CollectionOfSuffixesCollections;
import model.simplemodel.PredefinedSuffixesEnum;

/**
 * Just process already existing Enum class...
 * Cannot store changes...
 */
public class FromPredefinedSuffixesEnumSuffixesDbImpl implements ISuffixesDb {

    private final CollectionOfSuffixesCollections categoriesDb;

    public FromPredefinedSuffixesEnumSuffixesDbImpl() {
        categoriesDb = new CollectionOfSuffixesCollections();
        loadAllKnownPredefinedSuffixesFromEnum();
    }

    private void loadAllKnownPredefinedSuffixesFromEnum() {
        for(PredefinedSuffixesEnum value: PredefinedSuffixesEnum.values()) {
            categoriesDb.addNewSuffixesCollectionIfAbsent(value.getSuffixes());
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
