package app.model;


import app.model.ISuffixesCollection;
import app.model.simplemodel.CollectionOfSuffixesCollectionsStaticData;
import app.model.simplemodel.SuffixesCollectionImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CollectionOfSuffixesCollectionsStaticDataTest {

    @Test
    void addTwoCollectionsWithSameName_ModifiesExistingOne() {

        CollectionOfSuffixesCollectionsStaticData collection = new CollectionOfSuffixesCollectionsStaticData();

        ISuffixesCollection suffixesCollection1 = new SuffixesCollectionImpl("AUDIO");
        suffixesCollection1.addSuffixes("mp3,mp4");

        ISuffixesCollection suffixesCollection2 = new SuffixesCollectionImpl("AUDIO");
        suffixesCollection2.addSuffixes("mp4");

        collection.updateSuffixesCollectionOrAddNewOne(suffixesCollection1);
        collection.updateSuffixesCollectionOrAddNewOne(suffixesCollection2);

        Assertions.assertEquals(1, collection.size());
        Assertions.assertTrue(collection.getSuffixesCollectionByName("AUDIO").isPresent());
        Assertions.assertEquals(suffixesCollection2, collection.getSuffixesCollectionByName("AUDIO").get());
    }

}