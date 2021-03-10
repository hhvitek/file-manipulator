package app.model.simplemodel.staticdata.jpa.entities;

import app.model.simplemodel.staticdata.jpa.dboperations.ElemNotFoundException;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;

public class CurrentDataRepository extends Repository<CurrentDataEntity> {

    public CurrentDataRepository(@NotNull EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<CurrentDataEntity> getEntityClazz() {
        return CurrentDataEntity.class;
    }

    public String findValueByName(@NotNull String name) throws ElemNotFoundException {
        CurrentDataEntity currentDataEntity = findOneById(name);
        return currentDataEntity.getValue();
    }
}