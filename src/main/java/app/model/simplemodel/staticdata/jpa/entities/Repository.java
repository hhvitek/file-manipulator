package app.model.simplemodel.staticdata.jpa.entities;

import app.model.simplemodel.staticdata.jpa.dboperations.ElemNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class Repository<T> {

    protected static final Logger logger = LoggerFactory.getLogger(Repository.class);

    protected final EntityManager entityManager;

    protected Repository(@NotNull EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public abstract Class<T> getEntityClazz();

    public T findOneById(@NotNull Object id) throws ElemNotFoundException {
        T entity = entityManager.find(getEntityClazz(), id);
        if (entity != null) {
            return entity;
        } else {
            throw new ElemNotFoundException(getEntityClazz(), id);
        }
    }

    public boolean exists(@NotNull Object id) {
        return entityManager.find(getEntityClazz(), id) != null;
    }

    public List<T> findAll() {
        String query = String.format("SELECT a FROM %s a", getEntityClazz().getName());
        return entityManager.createQuery(query).getResultList();
    }

    public void delete(@NotNull T entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            logger.error("Failed to remove {} from DB. {}.", getEntityClazz(), entity);
        }
    }

    public void deleteById(@NotNull Object id) {
        try {
            T entity = findOneById(id);
            delete(entity);
        } catch (ElemNotFoundException e) {
            logger.info("Element: {} was not found in {}.", id, getEntityClazz());
        }

    }

    public T create(@NotNull T entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            return entity;
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            logger.error("Failed to persist/create {} into DB. {}.", getEntityClazz(), entity);
            throw new RuntimeException(ex);
        }
    }

    public T merge(@NotNull T entity) {
        try {
            entityManager.getTransaction().begin();
            entity = entityManager.merge(entity);
            entityManager.getTransaction().commit();
            return entity;
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            logger.error("Failed to merge/update {} into DB. {}.", getEntityClazz(), entity);
            throw new RuntimeException(ex);
        }
    }
}