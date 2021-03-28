package app.model.simplemodel.staticdata;


import app.model.simplemodel.suffixesdb.FromFileISuffixesDbImpl;

import java.nio.file.Path;

/**
 * Implementation of simplemodel's data.
 *
 * Does represent data persistence using local sqlite database.
 *
 * Loads predefined CollectionOfSuffixes from sqlite db.
 */
public class ModelStaticDataFromFileImpl extends AbstractIModelStaticData {

    public final static Path DEFAULT_PATH = Path.of("./src/main/resources/app/model/simplemodel/suffixesdb/suffixdb.txt");

    public ModelStaticDataFromFileImpl() {
        super(new FromFileISuffixesDbImpl(DEFAULT_PATH));
    }

    public ModelStaticDataFromFileImpl(Path customPathToDb) {
        super(new FromFileISuffixesDbImpl(customPathToDb));
    }

}