package app.model.simplemodel.suffixesdb;


import app.model.ISuffixes;
import app.model.SuffixesDbException;
import app.model.simplemodel.AllSuffixes;
import app.model.simplemodel.CollectionOfSuffixesStaticData;
import app.model.simplemodel.SuffixesImpl;
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
public class FromFileISuffixesDbImpl implements ISuffixesDb {

    public static final String COLUMN_SEPARATOR = "||";
    public static final String SUFFIXES_SEPARATOR = ",";

    private static final int COLUMN_LENGTH = 2;
    private static final Pattern COLUMN_SEPARATOR_PATTERN = Pattern.compile("\\|\\|");

    private Path suffixesDbFilePath;
    private CollectionOfSuffixesStaticData suffixesDb;

    public FromFileISuffixesDbImpl(Path suffixesDbFilePath) {
        this.suffixesDbFilePath = suffixesDbFilePath;
    }

    @Override
    public boolean isPersistent() {
        return true;
    }

    @Override
    public CollectionOfSuffixesStaticData load() throws SuffixesDbException {
        this.suffixesDb = new CollectionOfSuffixesStaticData();

        try (BufferedReader reader =
                new BufferedReader(
                        new FileReader(suffixesDbFilePath.toFile())
                )
        ) {
            CollectionOfSuffixesStaticData collection = processFileLineByLine(reader);
            return collection;
        } catch (FileNotFoundException ex) {
            throw new SuffixesDbException("The storage file not found: " + suffixesDbFilePath, ex);
        } catch (IOException ex) {
            throw new SuffixesDbException("IOError working with file: " + suffixesDbFilePath, ex);
        }
    }

    private CollectionOfSuffixesStaticData processFileLineByLine(@NotNull BufferedReader reader) throws InvalidSuffixesFileFormatException, IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (!shouldIgnoreLine(line)) {
                processSuffixesLine(line);
            }
        }
        return suffixesDb;
    }

    private boolean shouldIgnoreLine(String line) {
        return line.isBlank() || isComment(line);
    }

    private void processSuffixesLine(@NotNull String suffixesLine) throws InvalidSuffixesFileFormatException {
        String[] splitLine = COLUMN_SEPARATOR_PATTERN.split(suffixesLine);

        if (splitLine.length == 1 && splitLine[0].equalsIgnoreCase("ALL")) {
            String name = splitLine[0];
            ISuffixes suffixes = createSuffixes(name, "");
            suffixesDb.addNewSuffixesIfAbsent(suffixes);
            return;
        }

        if (splitLine.length != COLUMN_LENGTH && splitLine.length != 1) {
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
            String suffixesString = splitLine[1];
            ISuffixes suffixes = createSuffixes(name, suffixesString);
            suffixesDb.addNewSuffixesIfAbsent(suffixes);
        }
    }

    private ISuffixes createSuffixes(@NotNull String name, @NotNull String suffixesString) {
        if (name.equalsIgnoreCase("ALL") && suffixesString.isEmpty()) {
            return new AllSuffixes();
        }

        ISuffixes suffixes = new SuffixesImpl(name);
        suffixes.addSuffixes(suffixesString, SUFFIXES_SEPARATOR);
        return suffixes;
    }

    private boolean isComment(String line) {
        return line.startsWith("#") || line.startsWith(";");
    }

    @Override
    public void store(@NotNull CollectionOfSuffixesStaticData collectionOfSuffixesStaticData) throws SuffixesDbException {
        String textToStore = String.format("%s%n%s",
                getFileHeaderComment(),
                createFileSuffixesLines(collectionOfSuffixesStaticData)
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

    private String createFileSuffixesLines(@NotNull CollectionOfSuffixesStaticData collectionOfSuffixesStaticData) {
        StringBuilder stringBuilder = new StringBuilder();
        for(ISuffixes suffixes: collectionOfSuffixesStaticData) {
            String suffixLine = createSuffixesLine(suffixes);
            stringBuilder.append(suffixLine);
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    private String createSuffixesLine(@NotNull ISuffixes suffixes) {
        String name = suffixes.getName();
        String suffixesString = suffixes.getSuffixesAsDelimitedString(SUFFIXES_SEPARATOR);

        return name + COLUMN_SEPARATOR + suffixesString;
    }
}