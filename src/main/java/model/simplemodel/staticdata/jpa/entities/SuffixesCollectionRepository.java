package model.simplemodel.staticdata.jpa.entities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;

public class SuffixesCollectionRepository extends Repository<SuffixesCollectionEntity> {

    public SuffixesCollectionRepository(@NotNull EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<SuffixesCollectionEntity> getEntityClazz() {
        return SuffixesCollectionEntity.class;
    }
}
