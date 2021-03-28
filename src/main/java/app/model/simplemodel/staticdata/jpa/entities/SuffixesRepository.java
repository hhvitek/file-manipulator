package app.model.simplemodel.staticdata.jpa.entities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;

public class SuffixesRepository extends Repository<SuffixesEntity> {

    public SuffixesRepository(@NotNull EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<SuffixesEntity> getEntityClazz() {
        return SuffixesEntity.class;
    }
}