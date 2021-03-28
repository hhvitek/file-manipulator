package app.utilities.file_locators;

import app.model.ISuffixes;
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
    @NotNull List<Path> findUsingRegex(@NotNull Path rootFolder, @NotNull String fileRegex) throws FileLocatorException;

    /**
     * Searches rootFolder recursively. Finds all Files with defined file suffixes (extensions).
     * Suffixes are defined by ISuffixCollection
     *
     * If suffixes parameter is null, all files in rootFolder are returned (recursively).
     */
    @NotNull List<Path> findUsingSuffixes(@NotNull Path rootFolder, @Nullable ISuffixes suffixes) throws FileLocatorException;
    @NotNull List<Path> listAllFiles(@NotNull Path rootFolder) throws FileLocatorException;

    void setRootFolder(@NotNull Path rootFolder);
    void setSuffixes(@NotNull ISuffixes suffixes);

    /**
     * Executes search using previously set attributes using setRootFolder() and setSuffixes() methods.

     * @throws FileLocatorException if attributes are not set properly OR on search error.
     */
    @NotNull List<Path> find() throws FileLocatorException;
}