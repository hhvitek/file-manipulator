package model.simplemodel.suffixesdb;

import model.ISuffixesCollection;
import model.SuffixesDbException;
import model.simplemodel.CollectionOfSuffixesCollections;
import model.simplemodel.SuffixesCollectionImpl;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

/**
 * Process and manages file in the file system containing file suffixes
 */
public class FromFileSuffixesDbImpl implements ISuffixesDb {

    public static final String COLUMN_SEPARATOR = "||";
    public static final String SUFFIXES_SEPARATOR = ",";

    private static final int COLUMN_LENGTH = 2;
    private static final Pattern COLUMN_SEPARATOR_PATTERN = Pattern.compile("\\|\\|");

    private Path suffixesDbFilePath;
    private CollectionOfSuffixesCollections suffixesDb;

    public FromFileSuffixesDbImpl(Path suffixesDbFilePath) {
        this.suffixesDbFilePath = suffixesDbFilePath;
    }

    @Override
    public CollectionOfSuffixesCollections load() throws SuffixesDbException {
        this.suffixesDb = new CollectionOfSuffixesCollections();

        try (BufferedReader reader =
                new BufferedReader(
                        new FileReader(suffixesDbFilePath.toFile())
                )
        ) {
            CollectionOfSuffixesCollections collection = processFileLineByLine(reader);
            return collection;
        } catch (FileNotFoundException ex) {
            throw new SuffixesDbException("The storage file not found: " + suffixesDbFilePath, ex);
        } catch (IOException ex) {
            throw new SuffixesDbException("IOError working with file: " + suffixesDbFilePath, ex);
        }
    }

    private CollectionOfSuffixesCollections processFileLineByLine(@NotNull BufferedReader reader) throws InvalidSuffixesFileFormatException, IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (!shouldIgnoreLine(line)) {
                processSuffixesCollectionLine(line);
            }
        }
        return suffixesDb;
    }

    private boolean shouldIgnoreLine(String line) {
        return line.isBlank() || isComment(line);
    }

    private void processSuffixesCollectionLine(@NotNull String suffixesLine) throws InvalidSuffixesFileFormatException {
        String[] splitLine = COLUMN_SEPARATOR_PATTERN.split(suffixesLine);

        if (splitLine.length != COLUMN_LENGTH) {
            throw new InvalidSuffixesFileFormatException(
                    String.format("Line must contain <%d> items separated by <%s>. Found <%d> items. <%s>",
                            COLUMN_LENGTH,
                            COLUMN_SEPARATOR,
                            splitLine.length,
                            suffixesLine
                    )
            );
        } else {
            String name = splitLine[0];
            String suffixes = splitLine[1];
            ISuffixesCollection suffixesCollection = createSuffixesCollection(name, suffixes);
            suffixesDb.addNewSuffixesCollectionIfAbsent(suffixesCollection);
        }
    }

    private ISuffixesCollection createSuffixesCollection(@NotNull String name, @NotNull String suffixes) {
        ISuffixesCollection suffixesCollection = new SuffixesCollectionImpl(name);
        suffixesCollection.addSuffixes(suffixes, SUFFIXES_SEPARATOR);
        return suffixesCollection;
    }

    private boolean isComment(String line) {
        return line.startsWith("#") || line.startsWith(";");
    }

    @Override
    public void store(@NotNull CollectionOfSuffixesCollections collectionOfSuffixesCollections) throws SuffixesDbException {
        String textToStore = String.format("%s%n%s",
                getFileHeaderComment(),
                createFileSuffixesLines(collectionOfSuffixesCollections)
        );

        try {
            Files.writeString(suffixesDbFilePath, textToStore);
        } catch (IOException ex) {
            throw new SuffixesDbException("IOError working with file: " + suffixesDbFilePath, ex);
        }
    }

    private static String getFileHeaderComment() {
        return String.format("# This file represents file suffixes collections%n" +
                "# name|suffix1,suffix2,suffix3%n" +
                "# COLUMN_SEPARATOR = \"||\"%n" +
                "# SUFFIX_SEPARATOR = \",\"%n"
        );
    }

    private String createFileSuffixesLines(@NotNull CollectionOfSuffixesCollections collectionOfSuffixesCollections) {
        StringBuilder stringBuilder = new StringBuilder();
        for(ISuffixesCollection suffixesCollection: collectionOfSuffixesCollections) {
            String suffixLine = createSuffixesLine(suffixesCollection);
            stringBuilder.append(suffixLine);
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    private String createSuffixesLine(@NotNull ISuffixesCollection suffixesCollection) {
        String name = suffixesCollection.getName();
        String suffixes = suffixesCollection.getSuffixesAsDelimitedString(SUFFIXES_SEPARATOR);

        return name + COLUMN_SEPARATOR + suffixes;
    }
}
