package app.utilities.file_locators;

import app.model.ISuffixes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileLocatorImpl implements IFileLocator {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(FileLocatorImpl.class);

    private static final int MAX_RECURSIVE_DEPTH = 32;

    private Path rootFolder;
    private ISuffixes suffixes;

    private List<Path> findFilesByRegex(@NotNull String syntaxAndPattern) throws FileLocatorException {
        PathMatcher pathMatcher = compileRegex(syntaxAndPattern);

        try (Stream<Path> matchedFilePath =
                findFilesFromRootFolderRecursivelyUsingPathMatcher(rootFolder, pathMatcher)
        ) {
            return matchedFilePath
                    .collect(Collectors.toList()
            );
        } catch (IOException e) {
            logger.error("IOException encountered during file search.", e);
            throw new FileLocatorException(e);
        }
    }

    private PathMatcher compileRegex(@NotNull String syntaxAndPattern) throws FileLocatorException {
        try {
            return FileSystems.getDefault().getPathMatcher(syntaxAndPattern);
        } catch (IllegalArgumentException | UnsupportedOperationException ex) {
            logger.error("Syntax and Pattern is not valid! {}", syntaxAndPattern);
            throw new FileLocatorException(ex);
        }
    }

    private Stream<Path> findFilesFromRootFolderRecursivelyUsingPathMatcher(Path rootFolder, PathMatcher matcher)
        throws IOException
    {
        return Files.find(
                rootFolder,
                MAX_RECURSIVE_DEPTH,
                (path, basicFileAttribute) -> {
                    if (basicFileAttribute.isRegularFile()) {
                        Path fileName = path.getFileName();
                        return matcher.matches(fileName);
                    }
                    return false;
                }
        );
    }

    private boolean doRootFolderExists() {
        return rootFolder != null && Files.isDirectory(rootFolder);
    }

    @Override
    public @NotNull List<Path> findUsingRegex(@NotNull Path rootFolder, @NotNull String fileRegex) throws FileLocatorException {
        this.rootFolder = rootFolder;

        if (doRootFolderExists()) {
            return findFilesByRegex(fileRegex);
        } else {
            String errorMessage = String.format("Root folder <%s> does not exists!", rootFolder);
            logger.error(errorMessage);
            throw new FileLocatorException(errorMessage);
        }
    }

    @Override
    public @NotNull List<Path> findUsingSuffixes(@NotNull Path rootFolder, @Nullable ISuffixes suffixes) throws FileLocatorException {
        if (suffixes != null) {
            return findUsingRegex(
                    rootFolder,
                    suffixes.getFileGlobRegexFromSuffixes()
            );
        } else {
            return listAllFiles(rootFolder);
        }
    }

    @Override
    public List<Path> listAllFiles(@NotNull Path rootFolder) throws FileLocatorException {
        this.rootFolder = rootFolder;

        try (Stream<Path> foundFiles =
                     findAllRegularFilesFromRootFolderRecursively(rootFolder)
        ) {
            return foundFiles.collect(Collectors.toList());
        }
        catch (IOException e) {
            logger.error("IOException encountered during file search.", e);
            throw new FileLocatorException(e);
        }
    }

    private Stream<Path> findAllRegularFilesFromRootFolderRecursively(Path rootFolder)
            throws IOException
    {
        return Files.find(
                rootFolder,
                MAX_RECURSIVE_DEPTH,
                (path, basicFileAttribute) -> basicFileAttribute.isRegularFile()
        );
    }

    @Override
    public void setRootFolder(@NotNull Path rootFolder) {
        this.rootFolder = rootFolder;
    }

    @Override
    public void setSuffixes(@NotNull ISuffixes suffixes) {
        this.suffixes = suffixes;
    }

    @Override
    public @NotNull List<Path> find() throws FileLocatorException {
        if (rootFolder == null || suffixes == null) {
            throw new FileLocatorException("Attributes rootFolder and suffixes must be nonNull...");
        }

        return findUsingSuffixes(rootFolder, suffixes);
    }
}