package app.model;

import app.model.simplemodel.SuffixesImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SuffixesCollectionImplTest {

    @Test
    void createEmptySuffixesCollection_theNameIsGeneratedAtRandomTest() {
        ISuffixes actualCollection = new SuffixesImpl();
        String actualName = actualCollection.getName();

        Assertions.assertNotNull(actualName);
        Assertions.assertTrue(actualName.length() > 0);
    }

    @Test
    void variousAddMethodsUsedWithSameParameters_ResultingInTheSameObject_AND_TrimmingOfSuffixesTest() {
        ISuffixes firstCollection = new SuffixesImpl("the first collection");
        firstCollection.addSuffix("   mp3   ");
        firstCollection.addSuffix("   mp4");
        firstCollection.addSuffix("mpa    ");

        ISuffixes secondCollection = new SuffixesImpl("the first collection");
        secondCollection.addSuffixes("mp3,      mp4  ,    mpa", ",");

        Assertions.assertEquals(firstCollection, secondCollection);
    }

    @Test
    void addTwoSuffixesCollections_withSameNameDifferentSuffixesTest() {
        ISuffixes firstCollection = new SuffixesImpl("the first collection");
        firstCollection.addSuffix("   mp3   ");
        firstCollection.addSuffix("   mp4");
        firstCollection.addSuffix("mpa    ");

        ISuffixes secondCollection = new SuffixesImpl("the first collection");
        secondCollection.addSuffixes("aaa,      bbb  ,    ccc", ",");

        Assertions.assertNotEquals(firstCollection, secondCollection);
    }

    @Test
    void addTwoSuffixesCollections_withDifferentNameSameSuffixesTest() {
        ISuffixes firstCollection = new SuffixesImpl("the first collection");
        firstCollection.addSuffix("   mp3   ");
        firstCollection.addSuffix("   mp4");
        firstCollection.addSuffix("mpa    ");

        ISuffixes secondCollection = new SuffixesImpl("the second collection");
        secondCollection.addSuffixes("mp3,      mp4  ,    mpa", ",");

        Assertions.assertNotEquals(firstCollection, secondCollection);
    }

    @Test
    void emptySuffixesCollection_returnsCorrectGlobPatternTest() {
        String expectedGlobPattern = "glob:*.{}";

        ISuffixes emptyCollection = new SuffixesImpl("an empty collection");
        String actualGlobPattern = emptyCollection.getFileGlobRegexFromSuffixes();

        Assertions.assertEquals(expectedGlobPattern, actualGlobPattern);

    }
}