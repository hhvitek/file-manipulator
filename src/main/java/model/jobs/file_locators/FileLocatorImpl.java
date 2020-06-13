package model.jobs.file_locators;

import model.ISuffixesCollection;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileLocatorImpl implements IFileLocator {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(FileLocatorImpl.class);

    private Path rootFolder;

    private List<Path> findFilesByRegex(@NotNull String syntaxAndPattern) throws FileLocatorException {
        PathMatcher pathMatcher = compileRegex(syntaxAndPattern);

        try (Stream<Path> matchedFilePath = Files.find(
                rootFolder,
                10,
                (path, basicFileAttribute) -> {
                    if (basicFileAttribute.isRegularFile()) {
                        return pathMatcher.matches(path);
                    }
                    return false;
                }
        )) {
            return matchedFilePath
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("IOException encountered during file search.", e);
            throw new FileLocatorException(e);
        }
    }

    private PathMatcher compileRegex(@NotNull String syntaxAndPattern) throws FileLocatorException {
        try (FileSystem defaultFileSystem = FileSystems.getDefault()) {
            return defaultFileSystem.getPathMatcher(syntaxAndPattern);
        } catch (IOException ex) {
            logger.error("Failed to create default File System. Using static FileSystems.getDefault() method");
            throw  new FileLocatorException(ex);
        } catch (IllegalArgumentException | UnsupportedOperationException ex) {
            logger.error("Regex pattern is not valid! {}", syntaxAndPattern);
            throw new FileLocatorException(ex);
        }
    }

    private boolean doesRootFolderExist() {
        return rootFolder != null && Files.isDirectory(rootFolder);
    }

    @Override
    public List<Path> findUsingRegex(Path rootFolder, String fileRegex) {
        this.rootFolder = rootFolder;
        return findFilesByRegex(fileRegex);
    }

    @Override
    public List<Path> findUsingSuffixes(Path rootFolder, ISuffixesCollection suffixes) {
        return findUsingRegex(
                rootFolder,
                suffixes.getFileGlobRegexFromSuffixes()
        );
    }
}