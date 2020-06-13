package model.simplemodel;

import model.ISuffixesCollection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleModelSuffixesDbTest {

    SimpleModelSuffixesDb suffixesDb = new SimpleModelSuffixesDb();

    @Test
    void addNewSuffixesCollection_DbSizeEqualsOneTest() {
        int expectedInitialSize = 0;
        assertEquals(expectedInitialSize, suffixesDb.size());

        ISuffixesCollection suffixesCollection = createTheFirstSuffixCollection();
        suffixesDb.addNewSuffixesCollection(suffixesCollection);

        assertEquals(expectedInitialSize + 1, suffixesDb.size());
    }

    private ISuffixesCollection createTheFirstSuffixCollection() {
        ISuffixesCollection suffixesCollection = new SimpleModelSuffixesCollectionImpl("the first collection");
        suffixesCollection.addSuffix("mp3");
        return suffixesCollection;
    }

    @Test
    void addNewSuffixesCollection_TryingToAddSameCollectionTwice_SecondAttemptIsIgnoredTest() {
        int expectedSize = 1;

        ISuffixesCollection suffixesCollection = createTheFirstSuffixCollection();
        suffixesDb.addNewSuffixesCollection(suffixesCollection);
        suffixesDb.addNewSuffixesCollection(suffixesCollection);

        assertEquals(expectedSize, suffixesDb.size());
    }

    @Test
    void iterationOverDbTest() {

        ISuffixesCollection suffixesCollection = createTheFirstSuffixCollection();
        suffixesDb.addNewSuffixesCollection(suffixesCollection);

        ISuffixesCollection suffixesCollection_2 = new SimpleModelSuffixesCollectionImpl("the second collection");
        suffixesDb.addNewSuffixesCollection(suffixesCollection_2);

        ISuffixesCollection suffixesCollection_3 = new SimpleModelSuffixesCollectionImpl("the third collection");
        suffixesDb.addNewSuffixesCollection(suffixesCollection_3);

        int iterationsExpected = 3;
        int iterationCounter = 0;
        for (ISuffixesCollection currentSuffixesCollection: suffixesDb) {
            iterationCounter++;
        }

        assertEquals(iterationsExpected, iterationCounter);
    }
}
