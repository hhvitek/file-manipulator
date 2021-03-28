package app.utilities.file_locators;

import app.model.ISuffixes;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FileLocatorApacheCommons implements IFileLocator {

    private Path rootFolder;
    private ISuffixes suffixes;

    @Override
    public @NotNull List<Path> findUsingRegex(@NotNull Path rootFolder, @NotNull String fileRegex) {
        Collection<File> foundFiles = FileUtils.listFiles(
                rootFolder.toFile(),
                new WildcardFileFilter(fileRegex),
                TrueFileFilter.INSTANCE
        );
        return convertCollectionOfFilesIntoListOfPaths(foundFiles);
    }

    private List<Path> convertCollectionOfFilesIntoListOfPaths(Collection<File> collection) {
        return collection.stream()
                .map(File::toPath)
                .collect(Collectors.toList());
    }

    @Override
    public @NotNull List<Path> findUsingSuffixes(@NotNull Path rootFolder, @Nullable ISuffixes suffixes) throws FileLocatorException {
        if (suffixes != null) {
            Collection<File> foundFiles = FileUtils.listFiles(
                    rootFolder.toFile(),
                    suffixes.getSuffixesAsStrArray(),
                    true
            );
            return convertCollectionOfFilesIntoListOfPaths(foundFiles);

        } else {
            return listAllFiles(rootFolder);
        }
    }

    @Override
    public List<Path> listAllFiles(@NotNull Path rootFolder) throws FileLocatorException {
        Collection<File> foundFiles = FileUtils.listFiles(rootFolder.toFile(), null, true);
        return convertCollectionOfFilesIntoListOfPaths(foundFiles);
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