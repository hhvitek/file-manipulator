package model.simplemodel;

import model.ISuffixesCollection;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class SimpleModelSuffixesCollectionImpl implements ISuffixesCollection {

    private List<String> suffixes;
    private String name;

    public SimpleModelSuffixesCollectionImpl() {
        name = "";
        suffixes = new ArrayList<>();
    }

    public SimpleModelSuffixesCollectionImpl(String categoryName) {
        this();
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

    @Override
    public String[] getSuffixesAsStrArray() {
        String[] output = new String[suffixes.size()];
        suffixes.toArray(output);
        return output;
    }

    @Override
    public String getGlobRegexFromSuffixes() throws PatternSyntaxException {
        // regex: "glob:**.{exe,bat,sh}"
        String delimitedSuffixes = getSuffixesAsDelimitedString(",");
        return String.format("glob:**.{%s}", delimitedSuffixes);
    }

    @Override
    public int getSuffixesSize() {
        return suffixes.size();
    }

    @Override
    public String getCategoryName() {
        return name;
    }

    @Override
    public boolean isNamed() {
        return name != null && !name.isEmpty();
    }

    @NotNull
    @Override
    public Iterator<String> iterator() {
        return suffixes.iterator();
    }



    private String joinListOfStringIntoOneDelimitedString(List<String> listOfStrings, String delimiter) {
        return listOfStrings
                .stream()
                .collect(
                        Collectors.joining(delimiter)
                );
    }

    @Override
    public String toString() {
        return getSuffixesAsDelimitedString(", ");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleModelSuffixesCollectionImpl strings = (SimpleModelSuffixesCollectionImpl) o;
        return suffixes.equals(strings.suffixes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suffixes);
    }
}
