package model;

import java.util.List;

public interface ISuffixesCategory extends Iterable<String> {

    void addSuffix(String suffix);
    void addSuffixes(List<String> suffixes);
    void addSuffixes(String delimitedSuffixes, String delimiterRegex);
    String getCategoryName();
    boolean isNamed();

    String getSuffixesAsDelimitedString(String delimiter);
    int getSuffixesSize();
}
