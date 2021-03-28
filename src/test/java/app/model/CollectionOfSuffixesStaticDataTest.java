package app.model;


import app.model.simplemodel.CollectionOfSuffixesStaticData;
import app.model.simplemodel.SuffixesImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CollectionOfSuffixesStaticDataTest {

    @Test
    void addTwoCollectionsWithSameName_ModifiesExistingOne() {

        CollectionOfSuffixesStaticData collection = new CollectionOfSuffixesStaticData();

        ISuffixes suffixesCollection1 = new SuffixesImpl("AUDIO");
        suffixesCollection1.addSuffixes("mp3,mp4");

        ISuffixes suffixesCollection2 = new SuffixesImpl("AUDIO");
        suffixesCollection2.addSuffixes("mp4");

        collection.updateSuffixesOrAddNewOne(suffixesCollection1);
        collection.updateSuffixesOrAddNewOne(suffixesCollection2);

        Assertions.assertEquals(1, collection.size());
        Assertions.assertTrue(collection.getSuffixesByName("AUDIO").isPresent());
        Assertions.assertEquals(suffixesCollection2, collection.getSuffixesByName("AUDIO").get());
    }

}