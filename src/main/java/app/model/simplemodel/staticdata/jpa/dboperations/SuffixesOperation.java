package app.model.simplemodel.staticdata.jpa.dboperations;


import app.model.ISuffixes;
import app.model.simplemodel.SuffixesImpl;
import app.model.simplemodel.staticdata.jpa.entities.SuffixesEntity;
import app.model.simplemodel.staticdata.jpa.entities.SuffixesRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

public class SuffixesOperation {

    private static final Logger logger = LoggerFactory.getLogger(SuffixesOperation.class);

    private static final String DEFAULT_SUFFIXES_COLLECTION_NAME = "DEFAULT_SUFFIXES_COLLECTION_NAME";

    private final SuffixesRepository suffixesCollectionRepository;
    private final CurrentDataOperation currentDataOperation;

    public SuffixesOperation(@NotNull EntityManager entityManager) {
        suffixesCollectionRepository = new SuffixesRepository(entityManager);
        currentDataOperation = new CurrentDataOperation(entityManager);
    }

    public ISuffixes getDefaultSuffixes() {
        return new SuffixesImpl(DEFAULT_SUFFIXES_COLLECTION_NAME);
    }

    public SuffixesEntity createNewUpdateIfAlreadyExists(@Nullable ISuffixes suffixes) {
        SuffixesEntity suffixesCollectionEntity = SuffixesEntity.fromISuffixes(suffixes);
        String nameId = suffixesCollectionEntity.getName();
        if (suffixesCollectionRepository.exists(nameId)) {
            return suffixesCollectionRepository.merge(suffixesCollectionEntity);
        } else {
            return suffixesCollectionRepository.create(suffixesCollectionEntity);
        }
    }

    public SuffixesEntity getSuffixesEntityByName(@NotNull String name) throws ElemNotFoundException {
        SuffixesEntity suffixesCollectionEntityOpt = suffixesCollectionRepository.findOneById(name);
        return suffixesCollectionEntityOpt;
    }

    public boolean doSuffixesExists(@NotNull String name) {
        try {
            SuffixesEntity entity = getSuffixesEntityByName(name);
            return true;
        } catch (ElemNotFoundException e) {
            return false;
        }
    }

    public void removeSuffixesIfExist(@NotNull String name) {
        suffixesCollectionRepository.deleteById(name);
    }

    public boolean doCurrentSuffixesExists() {
        try {
            SuffixesEntity entity = getCurrentSuffixes();
            return true;
        } catch (ElemNotFoundException e) {
            return false;
        }
    }

    public SuffixesEntity getCurrentSuffixes() throws ElemNotFoundException {
        String name = currentDataOperation.getSuffixesNameId();
        return getSuffixesEntityByName(name);
    }

    public void setSuffixesAsCurrent(@NotNull String name) throws ElemNotFoundException {
        if (doSuffixesExists(name)) {
            currentDataOperation.setSuffixes(name);
        } else {
            throw new ElemNotFoundException(suffixesCollectionRepository.getEntityClazz(), name);
        }
    }

}