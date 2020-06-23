package model.jobs.file_locators;

import model.ISuffixesCollection;
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

    @Override
    public List<Path> findUsingRegex(@NotNull Path rootFolder, @NotNull String fileRegex) {
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
    public List<Path> findUsingSuffixesCollection(@NotNull Path rootFolder, @Nullable ISuffixesCollection suffixesCollection) throws FileLocatorException {
        if (suffixesCollection != null) {
            Collection<File> foundFiles = FileUtils.listFiles(
                    rootFolder.toFile(),
                    suffixesCollection.getSuffixesAsStrArray(),
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
}
