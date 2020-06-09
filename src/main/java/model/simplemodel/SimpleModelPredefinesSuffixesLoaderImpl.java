package model.simplemodel;

import model.IPredefinedSuffixesLoader;

public class SimpleModelPredefinesSuffixesLoaderImpl implements IPredefinedSuffixesLoader {

    private SimpleModelSuffixesCategoriesDb categoriesDb;

    public SimpleModelPredefinesSuffixesLoaderImpl() {
        categoriesDb = new SimpleModelSuffixesCategoriesDb();
        categoriesDb.addNewPredefinedFileSuffixesCategory(PredefinedSuffixesEnum.AUDIO.getSuffixes());
        categoriesDb.addNewPredefinedFileSuffixesCategory(PredefinedSuffixesEnum.VIDEO.getSuffixes());
        categoriesDb.addNewPredefinedFileSuffixesCategory(PredefinedSuffixesEnum.AUDIO_AND_VIDEO.getSuffixes());
    }

    @Override
    public SimpleModelSuffixesCategoriesDb load() {
        return categoriesDb;
    }
}
