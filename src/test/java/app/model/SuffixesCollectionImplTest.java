package app.model;

import app.model.ISuffixesCollection;
import app.model.simplemodel.SuffixesCollectionImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SuffixesCollectionImplTest {

    @Test
    void createEmptySuffixesCollection_theNameIsGeneratedAtRandomTest() {
        ISuffixesCollection actualCollection = new SuffixesCollectionImpl();
        String actualName = actualCollection.getName();

        Assertions.assertNotNull(actualName);
        Assertions.assertTrue(actualName.length() > 0);
    }

    @Test
    void variousAddMethodsUsedWithSameParameters_ResultingInTheSameObject_AND_TrimmingOfSuffixesTest() {
        ISuffixesCollection firstCollection = new SuffixesCollectionImpl("the first collection");
        firstCollection.addSuffix("   mp3   ");
        firstCollection.addSuffix("   mp4");
        firstCollection.addSuffix("mpa    ");

        ISuffixesCollection secondCollection = new SuffixesCollectionImpl("the first collection");
        secondCollection.addSuffixes("mp3,      mp4  ,    mpa", ",");

        Assertions.assertEquals(firstCollection, secondCollection);
    }

    @Test
    void addTwoSuffixesCollections_withSameNameDifferentSuffixesTest() {
        ISuffixesCollection firstCollection = new SuffixesCollectionImpl("the first collection");
        firstCollection.addSuffix("   mp3   ");
        firstCollection.addSuffix("   mp4");
        firstCollection.addSuffix("mpa    ");

        ISuffixesCollection secondCollection = new SuffixesCollectionImpl("the first collection");
        secondCollection.addSuffixes("aaa,      bbb  ,    ccc", ",");

        Assertions.assertNotEquals(firstCollection, secondCollection);
    }

    @Test
    void addTwoSuffixesCollections_withDifferentNameSameSuffixesTest() {
        ISuffixesCollection firstCollection = new SuffixesCollectionImpl("the first collection");
        firstCollection.addSuffix("   mp3   ");
        firstCollection.addSuffix("   mp4");
        firstCollection.addSuffix("mpa    ");

        ISuffixesCollection secondCollection = new SuffixesCollectionImpl("the second collection");
        secondCollection.addSuffixes("mp3,      mp4  ,    mpa", ",");

        Assertions.assertNotEquals(firstCollection, secondCollection);
    }

    @Test
    void emptySuffixesCollection_returnsCorrectGlobPatternTest() {
        String expectedGlobPattern = "glob:*.{}";

        ISuffixesCollection emptyCollection = new SuffixesCollectionImpl("an empty collection");
        String actualGlobPattern = emptyCollection.getFileGlobRegexFromSuffixes();

        Assertions.assertEquals(expectedGlobPattern, actualGlobPattern);

    }
}