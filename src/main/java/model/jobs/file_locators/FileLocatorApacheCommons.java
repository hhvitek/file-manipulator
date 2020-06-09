package model.jobs.file_locators;

import model.ISuffixesCollection;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FileLocatorApacheCommons implements IFileLocator {


    @Override
    public List<Path> findUsingRegex(Path rootFolder, String fileRegex) {
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
    public List<Path> findUsingSuffixes(Path rootFolder, ISuffixesCollection suffixes) {
        Collection<File> foundFiles = FileUtils.listFiles(
                rootFolder.toFile(),
                suffixes.getSuffixesAsStrArray(),
                true
        );
        return convertCollectionOfFilesIntoListOfPaths(foundFiles);
    }
}
