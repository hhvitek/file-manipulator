package model.simplemodel.staticdata.jpa.entities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;

public class CollectionOfSuffixesCollectionsRepository extends Repository<CollectionOfSuffixesCollectionsEntity> {

    public CollectionOfSuffixesCollectionsRepository(@NotNull EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<CollectionOfSuffixesCollectionsEntity> getEntityClazz() {
        return CollectionOfSuffixesCollectionsEntity.class;
    }
}
