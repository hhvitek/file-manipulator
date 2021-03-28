package app.model.simplemodel.staticdata.jpa;

import app.model.ISuffixes;
import app.model.simplemodel.CollectionOfSuffixesStaticData;
import app.model.simplemodel.staticdata.IModelStaticData;
import app.model.simplemodel.staticdata.jpa.dboperations.CollectionOfSuffixesOperation;
import app.model.simplemodel.staticdata.jpa.dboperations.CurrentDataOperation;
import app.model.simplemodel.staticdata.jpa.dboperations.ElemNotFoundException;
import app.model.simplemodel.staticdata.jpa.dboperations.SuffixesOperation;
import app.model.simplemodel.staticdata.jpa.entities.CollectionOfSuffixesEntity;
import app.model.simplemodel.staticdata.jpa.entities.SuffixesEntity;
import app.model.file_operations.FileOperationEnum;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private final SuffixesOperation suffixesCollectionOperation;
    private final CollectionOfSuffixesOperation collectionOfSuffixesOperation;

    public ModelStaticDataJpaImpl() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager(); // Retrieve an application managed entity manager
        currentDataOperation = new CurrentDataOperation(entityManager);
        suffixesCollectionOperation = new SuffixesOperation(entityManager);
        collectionOfSuffixesOperation = new CollectionOfSuffixesOperation(entityManager);

        initializeDatabase();
    }

    private void initializeDatabase() {
        initInputFolder();
        initOutputFolder();
        initCollectionsOfSuffixes();
        initSuffixes();
        initOperation();
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

    private void initSuffixes() {
        if (!suffixesCollectionOperation.doCurrentSuffixesExists()) {
            logger.info("Initializing current suffixes with default values.");
            initSuffixesToDefault();
        }
    }

    private void initSuffixesToDefault() {
        final ISuffixes defaultSuffixes = suffixesCollectionOperation.getDefaultSuffixes();
        suffixesCollectionOperation.createNewUpdateIfAlreadyExists(defaultSuffixes);
        try {
            suffixesCollectionOperation.setSuffixesAsCurrent(defaultSuffixes.getName());
        } catch (ElemNotFoundException e) {
            logger.error("This should not happen. Indicates Database error.", e);
        }
    }

    private void initCollectionsOfSuffixes() {
        if (!collectionOfSuffixesOperation.doCurrentCollectionExists()) {
            logger.info("Initializing current collection with default values.");
            initCollectionToDefault();
        }
    }

    private void initCollectionToDefault() {
        final CollectionOfSuffixesStaticData defaultCollection = collectionOfSuffixesOperation.getDefaultCollection();
        Integer collectionId = collectionOfSuffixesOperation.addNewCollection(defaultCollection);
        try {
            collectionOfSuffixesOperation.setCollectionAsCurrent(collectionId);
        } catch (ElemNotFoundException e) {
            logger.error("This should not happen. Indicates Database error.", e);
        }
    }

    private void initOperation() {
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
    public void setCurrentSuffixes(@NotNull ISuffixes newSuffixes) {
        currentDataOperation.setSuffixes(newSuffixes.getName());
        // TODO dangerous if not added prevoiuslt
    }

    @Override
    public @NotNull ISuffixes getCurrentSuffixes() {
        try {
            SuffixesEntity entity = suffixesCollectionOperation.getCurrentSuffixes();
            return entity.toISuffixes();
        } catch (ElemNotFoundException ex) {
            logger.error("The suffixes parameter was not found in database. It should have already been initialized previously.");
            return suffixesCollectionOperation.getDefaultSuffixes();
        }
    }

    @Override
    public CollectionOfSuffixesStaticData getCollectionOfSuffixesStaticData() {
        try {
            CollectionOfSuffixesEntity entity = collectionOfSuffixesOperation.getCurrentCollectionEntity();
            return entity.toCollection();
        } catch (ElemNotFoundException ex) {
            logger.error("The collection parameter was not found in database. It should have already been initialized previously.");
            return collectionOfSuffixesOperation.getDefaultCollection();
        }
    }

    @Override
    public void addNewPredefinedSuffixes(@NotNull ISuffixes newPredefinedSuffixes) {
        SuffixesEntity entity = suffixesCollectionOperation.createNewUpdateIfAlreadyExists(newPredefinedSuffixes); // stored into db as new

        try {
            CollectionOfSuffixesEntity collectionOfSuffixesEntity = collectionOfSuffixesOperation.getCurrentCollectionEntity();
            collectionOfSuffixesEntity.addSuffixes(entity);
        } catch (ElemNotFoundException e) {
            // no current collection ignore
            logger.warn("No current collection, cannot add a new suffixes into whole collection....");
        }

        setCurrentSuffixes(newPredefinedSuffixes);

    }

    @Override
    public void removePredefinedSuffixes(@NotNull String name) {
        suffixesCollectionOperation.removeSuffixesIfExist(name);
        //TODO what happen if removed collection is the current one...
    }

    @Override
    public Optional<ISuffixes> getPredefinedSuffixesByName(@NotNull String name) {
        try {
            SuffixesEntity entity = suffixesCollectionOperation.getSuffixesEntityByName(name);
            return Optional.of(entity.toISuffixes());
        } catch (ElemNotFoundException e) {
            return Optional.empty();
        }
    }

    @Override
    public @NotNull FileOperationEnum getCurrentOperation() {
        return currentDataOperation.getCurrentFileOperation();
    }

    @Override
    public void setCurrentOperation(@NotNull FileOperationEnum newOperation) {
        currentDataOperation.setCurrentFileOperation(newOperation);
    }
}