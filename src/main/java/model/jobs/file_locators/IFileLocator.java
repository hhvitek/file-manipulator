package model.jobs.file_locators;

import model.ISuffixesCollection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.List;

public interface IFileLocator {

    /**
     * Searches rootFolder recursively. Finds all Files matched by @param fileRegex. Given
     * regex syntax adheres to implementation class
     * @param rootFolder Folder when the search is started.
     * @param fileRegex Regular expression defining file name format. The regex syntax is defined by implementation class
     * @return Found files as List of Paths.
     */
    List<Path> findUsingRegex(@NotNull Path rootFolder, @NotNull String fileRegex) throws FileLocatorException;

    /**
     * Searches rootFolder recursively. Finds all Files with defined file suffixes (extensions).
     * Suffixes are defined by ISuffixCollection
     *
     * If suffixesCollection parameter is null, all files in rootFolder are returned (recursively).
     */
    List<Path> findUsingSuffixesCollection(@NotNull Path rootFolder, @Nullable ISuffixesCollection suffixesCollection) throws FileLocatorException;
    List<Path> listAllFiles(@NotNull Path rootFolder) throws FileLocatorException;
}
