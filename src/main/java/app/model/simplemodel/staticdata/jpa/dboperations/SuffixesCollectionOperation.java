package app.model.simplemodel.staticdata.jpa.dboperations;


import app.model.ISuffixesCollection;
import app.model.simplemodel.SuffixesCollectionImpl;
import app.model.simplemodel.staticdata.jpa.entities.SuffixesCollectionEntity;
import app.model.simplemodel.staticdata.jpa.entities.SuffixesCollectionRepository;
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

    public SuffixesCollectionOperation(@NotNull EntityManager entityManager) {
        suffixesCollectionRepository = new SuffixesCollectionRepository(entityManager);
        currentDataOperation = new CurrentDataOperation(entityManager);
    }

    public ISuffixesCollection getDefaultSuffixesCollection() {
        return new SuffixesCollectionImpl(DEFAULT_SUFFIXES_COLLECTION_NAME);
    }

    public SuffixesCollectionEntity createNewUpdateIfAlreadyExists(@Nullable ISuffixesCollection suffixesCollection) {
        SuffixesCollectionEntity suffixesCollectionEntity = SuffixesCollectionEntity.fromISuffixesCollection(suffixesCollection);
        String nameId = suffixesCollectionEntity.getName();
        if (suffixesCollectionRepository.exists(nameId)) {
            return suffixesCollectionRepository.merge(suffixesCollectionEntity);
        } else {
            return suffixesCollectionRepository.create(suffixesCollectionEntity);
        }
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
        String name = currentDataOperation.getSuffixesCollectionNameId();
        return getSuffixesCollectionEntityByName(name);
    }

    public void setSuffixesCollectionAsCurrent(@NotNull String name) throws ElemNotFoundException {
        if (doSuffixesCollectionExists(name)) {
            currentDataOperation.setSuffixesCollection(name);
        } else {
            throw new ElemNotFoundException(suffixesCollectionRepository.getEntityClazz(), name);
        }
    }

}