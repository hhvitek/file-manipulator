package model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class SimpleModelSuffixesCategoriesDb implements Iterable<ISuffixesCategory> {

    private List<ISuffixesCategory> categories;

    public SimpleModelSuffixesCategoriesDb() {
        categories = new ArrayList<>();
    }

    void addNewPredefinedFileSuffixesCategory(ISuffixesCategory newPredefinedSuffixesCategory) {
        categories.add(newPredefinedSuffixesCategory);
    }

    public Optional<ISuffixesCategory> getPredefinesFileSuffixesByCategoryName(String categoryName) {
        for(ISuffixesCategory suffixesCategory: categories) {
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

    @NotNull
    @Override
    public Iterator<ISuffixesCategory> iterator() {
        return categories.iterator();
    }

    private class CustomIterator implements Iterator<ISuffixesCategory> {

        private Iterator<ISuffixesCategory> iterator = categories.iterator();

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public ISuffixesCategory next() {
            return iterator.next();
        }
    }
}
