package model.simplemodel;

import model.ISuffixesCollection;
import model.string_operations.CustomStringAdditionalOperationsImpl;
import model.string_operations.StringAdditionalOperations;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.PatternSyntaxException;

public class SuffixesCollectionImpl implements ISuffixesCollection {

    private static final Logger logger = LoggerFactory.getLogger(SuffixesCollectionImpl.class);

    private final List<String> suffixes;
    private final String name;

    // to automatically generate alphanumeric name of the collection.
    private static final StringAdditionalOperations stringAdditionalOperations = new CustomStringAdditionalOperationsImpl();

    // default suffixes delimiter used e.g. in toString() overridden method
    private static final String DELIMITER = ",";

    public SuffixesCollectionImpl() {
        suffixes = new ArrayList<>();
        name = stringAdditionalOperations.generateRandomAlphanumericString(0);
    }

    public SuffixesCollectionImpl(@NotNull String categoryName) {
        suffixes = new ArrayList<>();
        name = categoryName;
    }

    public static ISuffixesCollection getAllSuffixCollection() {
        return new AllSuffixesCollection();
    }

    @Override
    public void addSuffix(@NotNull String suffix) {
        if (suffix != null) {
            String trimmedSuffix = suffix.trim();
            if (!suffixes.contains(trimmedSuffix)) {
                suffixes.add(trimmedSuffix);
            } else {
                logger.warn("This suffix <{}> is already in this collection <{}>. Ignoring.", suffix, this);
            }
        }
    }

    @Override
    public void addSuffixes(@NotNull List<String> newSuffixes) {
        newSuffixes.forEach(
                this::addSuffix
        );
    }

    @Override
    public void addSuffixes(String delimitedSuffixes, String delimiter) {
        try {
            Arrays.stream(delimitedSuffixes.split(delimiter))
                    .forEach(this::addSuffix);
        } catch (PatternSyntaxException e) {
            logger.error("Cannot split delimitedString <{}> using delimiter <{}>. Ignoring.", delimitedSuffixes, delimiter);
        }
    }

    @Override
    public void addSuffixes(String delimitedSuffixes) {
        addSuffixes(delimitedSuffixes, DELIMITER);
    }

    @Override
    public String getSuffixesAsDelimitedString(@NotNull String delimiter) throws NullPointerException {
        return joinListOfStringIntoOneDelimitedString(suffixes, delimiter);
    }

    private String joinListOfStringIntoOneDelimitedString(@NotNull List<String> listOfStrings, @NotNull String delimiter)
        throws NullPointerException {
        return String.join(delimiter, listOfStrings);
    }

    @Override
    public String[] getSuffixesAsStrArray() {
        String[] output = new String[suffixes.size()];
        suffixes.toArray(output);
        return output;
    }

    @Override
    public String getFileGlobRegexFromSuffixes() throws PatternSyntaxException {
        // regex: "glob:*.{exe,bat,sh}"
        String delimitedSuffixes = getSuffixesAsDelimitedString(",");
        return String.format("glob:*.{%s}", delimitedSuffixes);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasName(@NotNull String anotherName) {
        return name.equalsIgnoreCase(anotherName);
    }

    @NotNull
    @Override
    public Iterator<String> iterator() {
        return suffixes.iterator();
    }

    @Override
    public String toString() {
        return getSuffixesAsDelimitedString(DELIMITER + " ");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SuffixesCollectionImpl that = (SuffixesCollectionImpl) obj;
        return suffixes.equals(that.suffixes)
                && hasName(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suffixes, name);
    }
}
