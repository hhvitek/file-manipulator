package app.model.simplemodel.staticdata.jpa.dboperations;



import app.model.simplemodel.CollectionOfSuffixesStaticData;
import app.model.simplemodel.staticdata.jpa.entities.CollectionOfSuffixesEntity;
import app.model.simplemodel.staticdata.jpa.entities.CollectionOfSuffixesRepository;
import app.model.simplemodel.suffixesdb.ISuffixesDb;
import app.model.simplemodel.suffixesdb.FromPredefinedSuffixesEnumISuffixesDbImpl;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

public class CollectionOfSuffixesOperation {

    private static final Logger logger = LoggerFactory.getLogger(CollectionOfSuffixesOperation.class);

    private final CollectionOfSuffixesRepository collectionsRepository;
    private final CurrentDataOperation currentDataOperation;

    public CollectionOfSuffixesOperation(@NotNull EntityManager entityManager) {
        collectionsRepository = new CollectionOfSuffixesRepository(entityManager);
        currentDataOperation = new CurrentDataOperation(entityManager);
    }

    public CollectionOfSuffixesEntity getCollectionEntityById(@NotNull Integer id) throws ElemNotFoundException {
        return collectionsRepository.findOneById(id);
    }

    public boolean doCollectionExists(@NotNull Integer id) {
        try {
            CollectionOfSuffixesEntity entity = getCollectionEntityById(id);
            return true;
        } catch (ElemNotFoundException e) {
            return false;
        }
    }

    public boolean doCurrentCollectionExists() {
        try {
            CollectionOfSuffixesEntity entity = getCurrentCollectionEntity();
            return true;
        } catch (ElemNotFoundException e) {
            return false;
        }
    }

    public CollectionOfSuffixesEntity getCurrentCollectionEntity() throws ElemNotFoundException {
        Integer id = currentDataOperation.getCollectionId();
        return getCollectionEntityById(id);
    }

    public CollectionOfSuffixesStaticData getDefaultCollection() {
        ISuffixesDb db = new FromPredefinedSuffixesEnumISuffixesDbImpl();
        CollectionOfSuffixesStaticData predefinedCollection = db.load();
        return predefinedCollection;
    }

    public void setCollectionAsCurrent(@NotNull Integer id) throws ElemNotFoundException {
        if (doCollectionExists(id)) {
            currentDataOperation.setCollection(String.valueOf(id));
        } else {
            throw new ElemNotFoundException(collectionsRepository.getEntityClazz(), id);
        }
    }

    public Integer addNewCollection(@NotNull CollectionOfSuffixesStaticData newCollection) {
        CollectionOfSuffixesEntity entity = CollectionOfSuffixesEntity.fromCollection(newCollection);
        entity = collectionsRepository.create(entity);
        return entity.getId();
    }
}