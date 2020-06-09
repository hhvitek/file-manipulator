package model;

import java.util.List;

public interface ISuffixesCollection extends Iterable<String> {

    void addSuffix(String suffix);
    void addSuffixes(List<String> suffixes);
    void addSuffixes(String delimitedSuffixes, String delimiter);
    String getCategoryName();
    boolean isNamed();

    String getSuffixesAsDelimitedString(String delimiter);
    String[] getSuffixesAsStrArray();
    String getGlobRegexFromSuffixes();
    int getSuffixesSize();
}
