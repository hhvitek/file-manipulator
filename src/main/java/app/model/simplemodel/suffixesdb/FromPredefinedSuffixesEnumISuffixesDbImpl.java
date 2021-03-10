package app.model.simplemodel.suffixesdb;

import app.model.SuffixesDbException;
import app.model.simplemodel.CollectionOfSuffixesCollectionsStaticData;

/**
 * Just process already existing Enum class...
 * Cannot store changes...
 */
public class FromPredefinedSuffixesEnumISuffixesDbImpl implements ISuffixesDb {

    private final CollectionOfSuffixesCollectionsStaticData categoriesDb;

    public FromPredefinedSuffixesEnumISuffixesDbImpl() {
        categoriesDb = new CollectionOfSuffixesCollectionsStaticData();
        loadAllKnownPredefinedSuffixesFromEnum();
    }

    private void loadAllKnownPredefinedSuffixesFromEnum() {
        for(FromPredefinedSuffixesEnum value: FromPredefinedSuffixesEnum.values()) {
            categoriesDb.addNewSuffixesCollectionIfAbsent(value.getSuffixes());
        }
    }

    @Override
    public boolean isPersistent() {
        return false;
    }

    @Override
    public CollectionOfSuffixesCollectionsStaticData load() {
        return categoriesDb;
    }

    @Override
    public void store(CollectionOfSuffixesCollectionsStaticData collectionOfSuffixesCollectionsStaticData) throws SuffixesDbException {
        throw new SuffixesDbException("Not ever implemented - cannot change predefined ENUM!");
    }
}