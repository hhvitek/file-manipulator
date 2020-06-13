package model.simplemodel;

import model.IPredefinedSuffixesLoader;

public class SimpleModelPredefinesSuffixesLoaderImpl implements IPredefinedSuffixesLoader {

    private final SimpleModelSuffixesDb categoriesDb;

    public SimpleModelPredefinesSuffixesLoaderImpl() {
        categoriesDb = new SimpleModelSuffixesDb();
        loadAllKnownPredefinedSuffixesFromEnum();
    }

    private void loadAllKnownPredefinedSuffixesFromEnum() {
        for(PredefinedSuffixesEnum value: PredefinedSuffixesEnum.values()) {
            categoriesDb.addNewSuffixesCollection(value.getSuffixes());
        }
    }

    @Override
    public SimpleModelSuffixesDb load() {
        return categoriesDb;
    }
}
