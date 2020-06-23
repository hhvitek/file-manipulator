package model.simplemodel;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AllSuffixesCollection extends SuffixesCollectionImpl {

    private static final String name = "ALL";

    @Override
    public void addSuffix(String suffix) {

    }

    @Override
    public void addSuffixes(List<String> newSuffixes) {

    }

    @Override
    public void addSuffixes(String delimitedSuffixes, String delimiter) {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasName(String anotherName) {
        return true;
    }

    @Override
    public String getSuffixesAsDelimitedString(@NotNull String delimiter) {
        return "";
    }

    @Override
    public String[] getSuffixesAsStrArray() {
        return null;
    }

    @Override
    public String getFileGlobRegexFromSuffixes() {
        return "glob:*";
    }

    @NotNull
    @Override
    public Iterator<String> iterator() {
        return Collections.emptyIterator();
    }
}
