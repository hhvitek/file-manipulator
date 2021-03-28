package app.model.simplemodel.suffixesdb;

import app.model.SuffixesDbException;
import app.model.simplemodel.CollectionOfSuffixesStaticData;

/**
 * Just process already existing Enum class...
 * Cannot store changes...
 */
public class FromPredefinedSuffixesEnumISuffixesDbImpl implements ISuffixesDb {

    private final CollectionOfSuffixesStaticData categoriesDb;

    public FromPredefinedSuffixesEnumISuffixesDbImpl() {
        categoriesDb = new CollectionOfSuffixesStaticData();
        loadAllKnownPredefinedSuffixesFromEnum();
    }

    private void loadAllKnownPredefinedSuffixesFromEnum() {
        for(FromPredefinedSuffixesEnum value: FromPredefinedSuffixesEnum.values()) {
            categoriesDb.addNewSuffixesIfAbsent(value.getSuffixes());
        }
    }

    @Override
    public boolean isPersistent() {
        return false;
    }

    @Override
    public CollectionOfSuffixesStaticData load() {
        return categoriesDb;
    }

    @Override
    public void store(CollectionOfSuffixesStaticData collectionOfSuffixesStaticData) throws SuffixesDbException {
        throw new SuffixesDbException("Not ever implemented - cannot change predefined ENUM!");
    }
}