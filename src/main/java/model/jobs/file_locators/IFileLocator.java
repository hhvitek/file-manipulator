package model.jobs.file_locators;

import model.ISuffixesCollection;

import java.nio.file.Path;
import java.util.List;

public interface IFileLocator {

    /**
     * Searches rootFolder recursively. Finds all Files matched by @param fileRegex. Given
     * regex syntax adheres to implementation class
     * @param rootFolder Folder when the search is started.
     * @param fileRegex Regular expression defining file name format. The regex syntax is defined by implementation class
     * @return Found files as List<Path>
     */
    List<Path> findUsingRegex(Path rootFolder, String fileRegex) throws FileLocatorException;
    List<Path> findUsingSuffixes(Path rootFolder, ISuffixesCollection suffixes);
}
