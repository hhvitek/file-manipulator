package model.simplemodel;

import model.ISuffixesCollection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CollectionOfSuffixesCollectionsTest {

    @Test
    void addTwoCollectionsWithSameName_ModifiesExistingOne() {

        CollectionOfSuffixesCollections collection = new CollectionOfSuffixesCollections();

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
