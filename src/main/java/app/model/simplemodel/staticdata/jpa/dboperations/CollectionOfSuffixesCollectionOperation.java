package app.model.simplemodel.staticdata.jpa.dboperations;



import app.model.simplemodel.CollectionOfSuffixesCollectionsStaticData;
import app.model.simplemodel.staticdata.jpa.entities.CollectionOfSuffixesCollectionsEntity;
import app.model.simplemodel.staticdata.jpa.entities.CollectionOfSuffixesCollectionsRepository;
import app.model.simplemodel.suffixesdb.ISuffixesDb;
import app.model.simplemodel.suffixesdb.FromPredefinedSuffixesEnumISuffixesDbImpl;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

public class CollectionOfSuffixesCollectionOperation {

    private static final Logger logger = LoggerFactory.getLogger(CollectionOfSuffixesCollectionOperation.class);

    private final CollectionOfSuffixesCollectionsRepository collectionsRepository;
    private final CurrentDataOperation currentDataOperation;

    public CollectionOfSuffixesCollectionOperation(@NotNull EntityManager entityManager) {
        collectionsRepository = new CollectionOfSuffixesCollectionsRepository(entityManager);
        currentDataOperation = new CurrentDataOperation(entityManager);
    }

    public CollectionOfSuffixesCollectionsEntity getCollectionEntityById(@NotNull Integer id) throws ElemNotFoundException {
        return collectionsRepository.findOneById(id);
    }

    public boolean doCollectionExists(@NotNull Integer id) {
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
        Integer id = currentDataOperation.getCollectionId();
        return getCollectionEntityById(id);
    }

    public CollectionOfSuffixesCollectionsStaticData getDefaultCollection() {
        ISuffixesDb db = new FromPredefinedSuffixesEnumISuffixesDbImpl();
        CollectionOfSuffixesCollectionsStaticData predefinedCollection = db.load();
        return predefinedCollection;
    }

    public void setCollectionAsCurrent(@NotNull Integer id) throws ElemNotFoundException {
        if (doCollectionExists(id)) {
            currentDataOperation.setCollection(String.valueOf(id));
        } else {
            throw new ElemNotFoundException(collectionsRepository.getEntityClazz(), id);
        }
    }

    public Integer addNewCollection(@NotNull CollectionOfSuffixesCollectionsStaticData newCollection) {
        CollectionOfSuffixesCollectionsEntity entity = CollectionOfSuffixesCollectionsEntity.fromCollection(newCollection);
        entity = collectionsRepository.create(entity);
        return entity.getId();
    }
}