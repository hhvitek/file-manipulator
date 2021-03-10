package app.model.simplemodel.staticdata.jpa.entities;

import app.model.ISuffixesCollection;
import app.model.simplemodel.CollectionOfSuffixesCollectionsStaticData;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "collection_of_suffixes_collections")
public class CollectionOfSuffixesCollectionsEntity {

    private static String DB_SUFFIXES_DELIMITER = ",";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = true)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "collections_to_suffixes_collections_mapping",
            joinColumns = @JoinColumn(name = "collection_id"),
            inverseJoinColumns = @JoinColumn(name = "suffixes_name")
    )
    List<SuffixesCollectionEntity> suffixesCollections = new ArrayList<>();

    CollectionOfSuffixesCollectionsEntity() {

    }

    public CollectionOfSuffixesCollectionsEntity(@NotNull String name) {
        this.name = name;
    }

    public static CollectionOfSuffixesCollectionsEntity fromCollection(@NotNull CollectionOfSuffixesCollectionsStaticData newCollection) {
        String name = newCollection.getName();
        CollectionOfSuffixesCollectionsEntity collectionEntity = new CollectionOfSuffixesCollectionsEntity(name);

        List<SuffixesCollectionEntity> entities = new ArrayList<>();
        for(ISuffixesCollection suffixesCollection: newCollection) {
            SuffixesCollectionEntity entity = SuffixesCollectionEntity.fromISuffixesCollection(suffixesCollection);
            entities.add(entity);
        }
        collectionEntity.setSuffixesCollections(entities);
        return collectionEntity;
    }

    public CollectionOfSuffixesCollectionsStaticData toCollection() {
        CollectionOfSuffixesCollectionsStaticData collection = new CollectionOfSuffixesCollectionsStaticData(name);

        suffixesCollections.stream()
                .map(SuffixesCollectionEntity::toISuffixesCollection)
                .forEach(collection::addNewSuffixesCollectionIfAbsent);

        return collection;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public List<SuffixesCollectionEntity> getSuffixesCollections() {
        return suffixesCollections;
    }

    public void setSuffixesCollections(List<SuffixesCollectionEntity> suffixesCollections) {
        for(SuffixesCollectionEntity entity: suffixesCollections) {
            addSuffixesCollection(entity);
        }
    }

    public void addSuffixesCollection(SuffixesCollectionEntity suffixesCollectionEntity) {
        if(!suffixesCollections.contains(suffixesCollectionEntity)) {
            suffixesCollections.add(suffixesCollectionEntity);
            suffixesCollectionEntity.addIntoCollection(this);
        }
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final CollectionOfSuffixesCollectionsEntity that = (CollectionOfSuffixesCollectionsEntity) o;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }
}