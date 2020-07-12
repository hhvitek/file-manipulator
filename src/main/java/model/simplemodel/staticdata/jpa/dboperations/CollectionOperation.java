package model.simplemodel.staticdata.jpa.dboperations;

import model.simplemodel.CollectionOfSuffixesCollections;
import model.simplemodel.staticdata.jpa.entities.CollectionOfSuffixesCollectionsEntity;
import model.simplemodel.staticdata.jpa.entities.CollectionOfSuffixesCollectionsRepository;
import model.simplemodel.staticdata.jpa.entities.SuffixesCollectionEntity;
import model.simplemodel.suffixesdb.FromPredefinedSuffixesEnumSuffixesDbImpl;
import model.simplemodel.suffixesdb.ISuffixesDb;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

public class CollectionOperation {

    private static final Logger logger = LoggerFactory.getLogger(CollectionOperation.class);

    private final CollectionOfSuffixesCollectionsRepository collectionsRepository;
    private final CurrentDataOperation currentDataOperation;
    private final SuffixesCollectionOperation suffixesCollectionOperation;

    public CollectionOperation(@NotNull EntityManager entityManager) {
        collectionsRepository = new CollectionOfSuffixesCollectionsRepository(entityManager);
        currentDataOperation = new CurrentDataOperation(entityManager);
        suffixesCollectionOperation = new SuffixesCollectionOperation(entityManager);
    }

    public CollectionOfSuffixesCollectionsEntity getCollectionEntityById(@NotNull String id) throws ElemNotFoundException {
        return collectionsRepository.findOneById(id);
    }

    public boolean doCollectionExists(@NotNull String id) {
        try {
            CollectionOfSuffixesCollectionsEntity entity = getCollectionEntityById(id);
            return true;
        } catch (ElemNotFoundException e) {
            return false;
        }
    }

    public boolean doCurrentCollectionExists() {
        try {
            CollectionOfSuffixesCollectionsEntity entity = getCurrentCollectionEntity();
            return true;
        } catch (ElemNotFoundException e) {
            return false;
        }
    }

    public CollectionOfSuffixesCollectionsEntity getCurrentCollectionEntity() throws ElemNotFoundException {
        String id = currentDataOperation.getCollectionId();
        return getCollectionEntityById(id);
    }

    public CollectionOfSuffixesCollections getDefaultCollection() {
        ISuffixesDb db = new FromPredefinedSuffixesEnumSuffixesDbImpl();
        CollectionOfSuffixesCollections predefinedCollection = db.load();
        return predefinedCollection;
    }

    public void setCollectionAsCurrent(@NotNull String id) throws ElemNotFoundException {
        if (doCollectionExists(id)) {
            currentDataOperation.setCollection(id);
        } else {
            throw new ElemNotFoundException(collectionsRepository.getEntityClazz(), id);
        }
    }

    public Integer addNewCollection(@NotNull CollectionOfSuffixesCollections newCollection) {
        CollectionOfSuffixesCollectionsEntity entity = CollectionOfSuffixesCollectionsEntity.fromCollection(newCollection);
        collectionsRepository.create(entity);
        return entity.getId();
    }

    public void addSuffixesCollectionIntoCurrentCollection(@NotNull String suffixesCollectionName) throws ElemNotFoundException {
        addSuffixesCollectionIntoCollection(
                suffixesCollectionName,
                getCurrentCollectionEntity().getId().toString()
        );
    }

    public void addSuffixesCollectionIntoCollection(
            @NotNull String suffixesCollectionName,
            @NotNull String collectionId
    ) throws ElemNotFoundException {

        SuffixesCollectionEntity suffixesCollectionEntity =
                suffixesCollectionOperation.getSuffixesCollectionEntityByName(suffixesCollectionName);

        CollectionOfSuffixesCollectionsEntity collectionsEntity =
                getCollectionEntityById(collectionId);

        collectionsEntity.addSuffixesCollection(suffixesCollectionEntity);
        collectionsRepository.update(collectionsEntity);
    }
}
