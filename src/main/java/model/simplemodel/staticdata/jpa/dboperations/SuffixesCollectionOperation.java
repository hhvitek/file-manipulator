package model.simplemodel.staticdata.jpa.dboperations;

import model.ISuffixesCollection;
import model.simplemodel.SuffixesCollectionImpl;
import model.simplemodel.staticdata.jpa.entities.SuffixesCollectionEntity;
import model.simplemodel.staticdata.jpa.entities.SuffixesCollectionRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

public class SuffixesCollectionOperation {

    private static final Logger logger = LoggerFactory.getLogger(SuffixesCollectionOperation.class);

    private static final String DEFAULT_SUFFIXES_COLLECTION_NAME = "DEFAULT_SUFFIXES_COLLECTION_NAME";

    private final SuffixesCollectionRepository suffixesCollectionRepository;
    private final CurrentDataOperation currentDataOperation;
    private final CollectionOperation collectionOperation;

    public SuffixesCollectionOperation(@NotNull EntityManager entityManager) {
        suffixesCollectionRepository = new SuffixesCollectionRepository(entityManager);
        currentDataOperation = new CurrentDataOperation(entityManager);
        collectionOperation = new CollectionOperation(entityManager);
    }

    public ISuffixesCollection getDefaultSuffixesCollection() {
        return new SuffixesCollectionImpl(DEFAULT_SUFFIXES_COLLECTION_NAME);
    }

    public void addNewSuffixesCollection(@Nullable ISuffixesCollection suffixesCollection) {
        SuffixesCollectionEntity suffixesCollectionEntity = SuffixesCollectionEntity.fromISuffixesCollection(suffixesCollection);
        suffixesCollectionRepository.update(suffixesCollectionEntity);
    }

    public SuffixesCollectionEntity getSuffixesCollectionEntityByName(@NotNull String name) throws ElemNotFoundException {
        SuffixesCollectionEntity suffixesCollectionEntityOpt = suffixesCollectionRepository.findOneById(name);
        return suffixesCollectionEntityOpt;
    }

    public boolean doSuffixesCollectionExists(@NotNull String name) {
        try {
            SuffixesCollectionEntity entity = getSuffixesCollectionEntityByName(name);
            return true;
        } catch (ElemNotFoundException e) {
            return false;
        }
    }

    public void removeSuffixesCollectionIfExist(@NotNull String name) {
        suffixesCollectionRepository.deleteById(name);
    }

    public boolean doCurrentSuffixesCollectionExists() {
        try {
            SuffixesCollectionEntity entity = getCurrentSuffixesCollection();
            return true;
        } catch (ElemNotFoundException e) {
            return false;
        }
    }

    public SuffixesCollectionEntity getCurrentSuffixesCollection() throws ElemNotFoundException {
        String name = currentDataOperation.getSuffixesCollectionName();
        return getSuffixesCollectionEntityByName(name);
    }

    public void setSuffixesCollectionAsCurrent(@NotNull String name) throws ElemNotFoundException {
        if (doSuffixesCollectionExists(name)) {
            currentDataOperation.setSuffixesCollection(name);
        } else {
            throw new ElemNotFoundException(suffixesCollectionRepository.getEntityClazz(), name);
        }
    }

    public void addSuffixesCollectionIntoCollection(
            @NotNull String suffixesCollectionName,
            @NotNull String collectionId
    ) throws ElemNotFoundException {

        collectionOperation.addSuffixesCollectionIntoCollection(suffixesCollectionName, collectionId);
    }
}
