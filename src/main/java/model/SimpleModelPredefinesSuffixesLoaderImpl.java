package model;

public class SimpleModelPredefinesSuffixesLoaderImpl implements IPredefinedSuffixesLoader {

    private SimpleModelSuffixesCategoriesDb categoriesDb;

    public SimpleModelPredefinesSuffixesLoaderImpl() {
        categoriesDb = new SimpleModelSuffixesCategoriesDb();
        addCategory("AUDIO", "mp3,ogg");
        addCategory("VIDEO", "mp4,avi");
    }

    public void addCategory(String name, String delimitedSuffixes) {
        ISuffixesCategory category = new SimpleModelSuffixesCategoryImpl(name);
        category.addSuffixes(delimitedSuffixes, ",");
        categoriesDb.addNewPredefinedFileSuffixesCategory(category);
    }

    @Override
    public SimpleModelSuffixesCategoriesDb load() {
        return categoriesDb;
    }
}
