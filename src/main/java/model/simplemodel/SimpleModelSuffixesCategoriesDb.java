package model.simplemodel;

import model.ISuffixesCollection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class SimpleModelSuffixesCategoriesDb implements Iterable<ISuffixesCollection> {

    private List<ISuffixesCollection> categories;

    public SimpleModelSuffixesCategoriesDb() {
        categories = new ArrayList<>();
    }

    void addNewPredefinedFileSuffixesCategory(ISuffixesCollection newPredefinedSuffixesCategory) {
        categories.add(newPredefinedSuffixesCategory);
    }

    public Optional<ISuffixesCollection> getPredefinesFileSuffixesByCategoryName(String categoryName) {
        for(ISuffixesCollection suffixesCategory: categories) {
            if (suffixesCategory.isNamed()) {
                String name = suffixesCategory.getCategoryName();
                if (name.equalsIgnoreCase(categoryName)) {
                    return Optional.of(suffixesCategory);
                }
            }
        }
        return Optional.empty();
    }

    public int size() {
        return categories.size();
    }

    public ISuffixesCollection getFirstSuffixesCategory() {
        if (categories.isEmpty()) {
            return new SimpleModelSuffixesCollectionImpl();
        } else {
            return categories.get(0);
        }
    }

    @NotNull
    @Override
    public Iterator<ISuffixesCollection> iterator() {
        return categories.iterator();
    }

    private class CustomIterator implements Iterator<ISuffixesCollection> {

        private Iterator<ISuffixesCollection> iterator = categories.iterator();

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public ISuffixesCollection next() {
            return iterator.next();
        }
    }
}
