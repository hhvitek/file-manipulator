package model.simplemodel;

import model.ISuffixesCollection;
import model.string_operations.CustomStringAdditionalOperationsImpl;
import model.string_operations.StringAdditionalOperations;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class SimpleModelSuffixesCollectionImpl implements ISuffixesCollection {

    private final List<String> suffixes;
    private final String name;
    private static final StringAdditionalOperations stringAdditionalOperations = new CustomStringAdditionalOperationsImpl();
    private static final String DELIMITER = ",";

    public SimpleModelSuffixesCollectionImpl() {
        suffixes = new ArrayList<>();
        name = stringAdditionalOperations.generateRandomAlphanumericString(0);
    }

    public SimpleModelSuffixesCollectionImpl(String categoryName) {
        suffixes = new ArrayList<>();
        name = categoryName;
    }

    @Override
    public void addSuffix(String suffix) {
        if (!suffixes.contains(suffix)) {
            suffixes.add(suffix);
        }
    }

    @Override
    public void addSuffixes(List<String> newSuffixes) {
        newSuffixes.forEach(
                this::addSuffix
        );
    }

    @Override
    public void addSuffixes(String delimitedSuffixes, String delimiter) {
        List<String> newSuffixes = splitAndTrimStringIntoListOfStringUsingDelimiter(delimitedSuffixes, delimiter);
        addSuffixes(newSuffixes);
    }

    private List<String> splitAndTrimStringIntoListOfStringUsingDelimiter(String delimitedInput, String delimiter)
            throws PatternSyntaxException {
        return Arrays.stream(delimitedInput.split(delimiter))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    @Override
    public String getSuffixesAsDelimitedString(String delimiter) {
        return joinListOfStringIntoOneDelimitedString(suffixes, delimiter);
    }

    private String joinListOfStringIntoOneDelimitedString(List<String> listOfStrings, String delimiter) {
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
        // regex: "glob:**.{exe,bat,sh}"
        String delimitedSuffixes = getSuffixesAsDelimitedString(DELIMITER);
        return String.format("glob:**.{%s}", delimitedSuffixes);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasName(String anotherName) {
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
        SimpleModelSuffixesCollectionImpl that = (SimpleModelSuffixesCollectionImpl) obj;
        return suffixes.equals(that.suffixes)
                && hasName(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suffixes, name);
    }
}
