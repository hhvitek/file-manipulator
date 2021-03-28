package app.model;

import app.model.simplemodel.CollectionOfSuffixesStaticData;
import app.model.simplemodel.SuffixesImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelImplCollectionOfSuffixesStaticDataTest {

    CollectionOfSuffixesStaticData suffixesDb = new CollectionOfSuffixesStaticData();

    @Test
    void addNewSuffixesCollection_DbSizeEqualsOneTest() {
        int expectedInitialSize = 0;
        assertEquals(expectedInitialSize, suffixesDb.size());

        ISuffixes suffixesCollection = createTheFirstSuffixCollection();
        suffixesDb.addNewSuffixesIfAbsent(suffixesCollection);

        assertEquals(expectedInitialSize + 1, suffixesDb.size());
    }

    private ISuffixes createTheFirstSuffixCollection() {
        ISuffixes suffixesCollection = new SuffixesImpl("the first collection");
        suffixesCollection.addSuffix("mp3");
        return suffixesCollection;
    }

    @Test
    void addNewSuffixesCollection_TryingToAddSameCollectionTwice_SecondAttemptIsIgnoredTest() {
        int expectedSize = 1;

        ISuffixes suffixesCollection = createTheFirstSuffixCollection();
        suffixesDb.addNewSuffixesIfAbsent(suffixesCollection);
        suffixesDb.addNewSuffixesIfAbsent(suffixesCollection);

        assertEquals(expectedSize, suffixesDb.size());
    }

    @Test
    void iterationOverDbTest() {

        ISuffixes suffixesCollection = createTheFirstSuffixCollection();
        suffixesDb.addNewSuffixesIfAbsent(suffixesCollection);

        ISuffixes suffixesCollection_2 = new SuffixesImpl("the second collection");
        suffixesDb.addNewSuffixesIfAbsent(suffixesCollection_2);

        ISuffixes suffixesCollection_3 = new SuffixesImpl("the third collection");
        suffixesDb.addNewSuffixesIfAbsent(suffixesCollection_3);

        int iterationsExpected = 3;
        int iterationCounter = 0;
        for (ISuffixes currentSuffixesCollection: suffixesDb) {
            iterationCounter++;
        }

        assertEquals(iterationsExpected, iterationCounter);
    }
}