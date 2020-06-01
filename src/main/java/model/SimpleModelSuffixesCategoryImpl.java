package model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class SimpleModelSuffixesCategoryImpl implements ISuffixesCategory {

    private List<String> suffixes;
    private String name;

    public SimpleModelSuffixesCategoryImpl() {
        name = "";
        suffixes = new ArrayList<>();
    }

    public SimpleModelSuffixesCategoryImpl(String categoryName) {
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
    public void addSuffixes(String delimitedSuffixes, String delimiterRegex) {
        List<String> newSuffixes = splitOneStringIntoListOfStringUsingDelimiter(delimitedSuffixes, delimiterRegex);
        addSuffixes(newSuffixes);
    }

    @Override
    public String getCategoryName() {
        return name;
    }

    @Override
    public boolean isNamed() {
        return name != null && !name.isEmpty();
    }

    @Override
    public String getSuffixesAsDelimitedString(String delimiter) {
        return joinListOfStringIntoOneDelimitedString(suffixes, delimiter);
    }

    @Override
    public int getSuffixesSize() {
        return suffixes.size();
    }

    @NotNull
    @Override
    public Iterator<String> iterator() {
        return suffixes.iterator();
    }

    private List<String> splitOneStringIntoListOfStringUsingDelimiter(String delimitedInput, String delimiterRegex)
            throws PatternSyntaxException {
        String[] splitInput = delimitedInput.split(delimiterRegex);
        return List.of(splitInput);
    }

    private String joinListOfStringIntoOneDelimitedString(List<String> listOfStrings, String delimiter) {
        return listOfStrings
                .stream()
                .collect(
                        Collectors.joining(delimiter)
                );
    }
}
