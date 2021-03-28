package app.model.simplemodel.staticdata;

import app.model.simplemodel.suffixesdb.FromPredefinedSuffixesEnumISuffixesDbImpl;

/**
 * Implementation of simplemodel's data.
 *
 * Does not represents any persistence...
 *
 * Loads predefined CollectionOfSuffixes from existing ENUM
 */
public class ModelStaticDataFromEnumImpl extends AbstractIModelStaticData {

    public ModelStaticDataFromEnumImpl() {
        super(new FromPredefinedSuffixesEnumISuffixesDbImpl());
    }
}