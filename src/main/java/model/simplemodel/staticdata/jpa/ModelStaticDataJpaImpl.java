package model.simplemodel.staticdata.jpa;

import model.ISuffixesCollection;
import model.file_operations.FileOperationEnum;
import model.simplemodel.CollectionOfSuffixesCollections;
import model.simplemodel.staticdata.IModelStaticData;
import model.simplemodel.staticdata.jpa.dboperations.CollectionOperation;
import model.simplemodel.staticdata.jpa.dboperations.CurrentDataOperation;
import model.simplemodel.staticdata.jpa.dboperations.ElemNotFoundException;
import model.simplemodel.staticdata.jpa.dboperations.SuffixesCollectionOperation;
import model.simplemodel.staticdata.jpa.entities.CollectionOfSuffixesCollectionsEntity;
import model.simplemodel.staticdata.jpa.entities.SuffixesCollectionEntity;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.OperationNotSupportedException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.nio.file.Path;
import java.util.Optional;

public class ModelStaticDataJpaImpl implements IModelStaticData {

    private static final Logger logger = LoggerFactory.getLogger(ModelStaticDataJpaImpl.class);

    private static EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("my-sqlite");

    private final CurrentDataOperation currentDataOperation;
    private final SuffixesCollectionOperation suffixesCollectionOperation;
    private final CollectionOperation collectionOperation;

    public ModelStaticDataJpaImpl() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager(); // Retrieve an application managed entity manager
        currentDataOperation = new CurrentDataOperation(entityManager);
        suffixesCollectionOperation = new SuffixesCollectionOperation(entityManager);
        collectionOperation = new CollectionOperation(entityManager);

        initializeDatabase();
    }

    private void initializeDatabase() {
        initInputFolder();
        initOutputFolder();
        initCollectionsOfSuffixesCollections();
        initSuffixesCollection();
    }

    private void initInputFolder() {
        Path inputFolder = currentDataOperation.getPath("input_folder", null);
        if (inputFolder == null) {
            currentDataOperation.storePath("input_folder", currentDataOperation.getDefaultInputFolder());
        }
    }

    private void initOutputFolder() {
        Path outputFolder = currentDataOperation.getPath("output_folder", null);
        if (outputFolder == null) {
            currentDataOperation.storePath("output_folder", currentDataOperation.getDefaultOutputFolder());
        }
    }

    private void initSuffixesCollection() {
        if (!suffixesCollectionOperation.doCurrentSuffixesCollectionExists()) {
            logger.info("Initializing current suffixesCollection with default values.");
            initSuffixesCollectionToDefault();
        }
    }

    private void initSuffixesCollectionToDefault() {
        final ISuffixesCollection defaultSuffixesCollection = suffixesCollectionOperation.getDefaultSuffixesCollection();
        suffixesCollectionOperation.addNewSuffixesCollection(defaultSuffixesCollection);
        try {
            suffixesCollectionOperation.setSuffixesCollectionAsCurrent(defaultSuffixesCollection.getName());
        } catch (ElemNotFoundException e) {
            logger.error("This should not happen. Indicates Database error.", e);
        }
    }

    private void initCollectionsOfSuffixesCollections() {
        if (!collectionOperation.doCurrentCollectionExists()) {
            logger.info("Initializing current collection with default values.");
            initCollectionToDefault();
        }
    }

    private void initCollectionToDefault() {
        final CollectionOfSuffixesCollections defaultCollection = collectionOperation.getDefaultCollection();
        String collectionId = String.valueOf(collectionOperation.addNewCollection(defaultCollection));
        try {
            collectionOperation.setCollectionAsCurrent(collectionId);
        } catch (ElemNotFoundException e) {
            logger.error("This should not happen. Indicates Database error.", e);
        }
    }

    @Override
    public void setInputFolder(@NotNull Path newInputFolder) {
        currentDataOperation.storePath("input_folder", newInputFolder);
    }

    @Override
    public @NotNull Path getInputFolder() {
        Path outputFolder = currentDataOperation.getPath("input_folder", null);
        if (outputFolder == null) {
            logger.error("The input_folder parameter was not found in database. It should have already been initialized previously!");
            return currentDataOperation.getDefaultInputFolder();
        }
        return outputFolder;
    }


    @Override
    public void setOutputFolder(@NotNull Path newOutputFolder) {
        currentDataOperation.storePath("output_folder", newOutputFolder);
    }

    @Override
    public @NotNull Path getOutputFolder() {
        Path outputFolder = currentDataOperation.getPath("output_folder", null);
        if (outputFolder == null) {
            logger.error("The output_folder parameter was not found in database. It should have already been initialized previously!");
            return currentDataOperation.getDefaultOutputFolder();
        }
        return outputFolder;
    }

    @Override
    public void setCurrentSuffixesCollection(@NotNull ISuffixesCollection newSuffixesCollection) {
        currentDataOperation.setSuffixesCollection(newSuffixesCollection.getName());
        // TODO dangerous if not added prevoiuslt
    }

    @Override
    public @NotNull ISuffixesCollection getCurrentSuffixesCollection() {
        try {
            SuffixesCollectionEntity entity = suffixesCollectionOperation.getCurrentSuffixesCollection();
            return entity.toISuffixesCollection();
        } catch (ElemNotFoundException ex) {
            logger.error("The suffixesCollection parameter was not found in database. It should have already been initialized previously.");
            return suffixesCollectionOperation.getDefaultSuffixesCollection();
        }
    }

    @Override
    public CollectionOfSuffixesCollections getSuffixesDb() {
        try {
            CollectionOfSuffixesCollectionsEntity entity = collectionOperation.getCurrentCollectionEntity();
            return entity.toCollection();
        } catch (ElemNotFoundException ex) {
            logger.error("The collection parameter was not found in database. It should have already been initialized previously.");
            return collectionOperation.getDefaultCollection();
        }
    }

    @Override
    public void addNewPredefinedSuffixesCollection(@NotNull ISuffixesCollection newPredefinedSuffixesCollection) {
        suffixesCollectionOperation.addNewSuffixesCollection(newPredefinedSuffixesCollection);
        try {
            collectionOperation.addSuffixesCollectionIntoCurrentCollection(newPredefinedSuffixesCollection.getName());
        } catch (ElemNotFoundException e) {
            logger.error("This should not happen. Indicates Database error.", e);
        }
        setCurrentSuffixesCollection(newPredefinedSuffixesCollection);

    }

    @Override
    public void removePredefinedSuffixesCollection(@NotNull String name) {
        suffixesCollectionOperation.removeSuffixesCollectionIfExist(name);
        //TODO what happen if removed collection is the current one...
    }

    @Override
    public Optional<ISuffixesCollection> getPredefinedSuffixesCollectionByName(@NotNull String name) {
        try {
            SuffixesCollectionEntity entity = suffixesCollectionOperation.getSuffixesCollectionEntityByName(name);
            return Optional.of(entity.toISuffixesCollection());
        } catch (ElemNotFoundException e) {
            return Optional.empty();
        }
    }

    @Override
    public @NotNull FileOperationEnum getCurrentOperation() {
        throw new UnsupportedOperationException("This operation is not supported.");
    }

    @Override
    public void setCurrentOperation(@NotNull FileOperationEnum newOperation) {
        throw new UnsupportedOperationException("This operation is not supported.");
    }
}
