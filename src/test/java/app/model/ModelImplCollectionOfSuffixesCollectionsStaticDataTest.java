package app.model;

import app.model.ISuffixesCollection;
import app.model.simplemodel.CollectionOfSuffixesCollectionsStaticData;
import app.model.simplemodel.SuffixesCollectionImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelImplCollectionOfSuffixesCollectionsStaticDataTest {

    CollectionOfSuffixesCollectionsStaticData suffixesDb = new CollectionOfSuffixesCollectionsStaticData();

    @Test
    void addNewSuffixesCollection_DbSizeEqualsOneTest() {
        int expectedInitialSize = 0;
        assertEquals(expectedInitialSize, suffixesDb.size());

        ISuffixesCollection suffixesCollection = createTheFirstSuffixCollection();
        suffixesDb.addNewSuffixesCollectionIfAbsent(suffixesCollection);

        assertEquals(expectedInitialSize + 1, suffixesDb.size());
    }

    private ISuffixesCollection createTheFirstSuffixCollection() {
        ISuffixesCollection suffixesCollection = new SuffixesCollectionImpl("the first collection");
        suffixesCollection.addSuffix("mp3");
        return suffixesCollection;
    }

    @Test
    void addNewSuffixesCollection_TryingToAddSameCollectionTwice_SecondAttemptIsIgnoredTest() {
        int expectedSize = 1;

        ISuffixesCollection suffixesCollection = createTheFirstSuffixCollection();
        suffixesDb.addNewSuffixesCollectionIfAbsent(suffixesCollection);
        suffixesDb.addNewSuffixesCollectionIfAbsent(suffixesCollection);

        assertEquals(expectedSize, suffixesDb.size());
    }

    @Test
    void iterationOverDbTest() {

        ISuffixesCollection suffixesCollection = createTheFirstSuffixCollection();
        suffixesDb.addNewSuffixesCollectionIfAbsent(suffixesCollection);

        ISuffixesCollection suffixesCollection_2 = new SuffixesCollectionImpl("the second collection");
        suffixesDb.addNewSuffixesCollectionIfAbsent(suffixesCollection_2);

        ISuffixesCollection suffixesCollection_3 = new SuffixesCollectionImpl("the third collection");
        suffixesDb.addNewSuffixesCollectionIfAbsent(suffixesCollection_3);

        int iterationsExpected = 3;
        int iterationCounter = 0;
        for (ISuffixesCollection currentSuffixesCollection: suffixesDb) {
            iterationCounter++;
        }

        assertEquals(iterationsExpected, iterationCounter);
    }
}