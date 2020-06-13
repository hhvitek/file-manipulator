package model;

import java.util.List;

public interface ISuffixesCollection extends Iterable<String> {

    void addSuffix(String suffix);
    void addSuffixes(List<String> newSuffixes);
    void addSuffixes(String delimitedSuffixes, String delimiter);

    String getName();
    boolean hasName(String anotherName);

    String getSuffixesAsDelimitedString(String delimiter);

    String[] getSuffixesAsStrArray();

    String getFileGlobRegexFromSuffixes();
}
