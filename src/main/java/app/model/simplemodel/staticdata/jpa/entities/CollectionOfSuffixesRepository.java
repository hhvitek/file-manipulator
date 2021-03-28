package app.model.simplemodel.staticdata.jpa.entities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;

public class CollectionOfSuffixesRepository extends Repository<CollectionOfSuffixesEntity> {

    public CollectionOfSuffixesRepository(@NotNull EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<CollectionOfSuffixesEntity> getEntityClazz() {
        return CollectionOfSuffixesEntity.class;
    }
}