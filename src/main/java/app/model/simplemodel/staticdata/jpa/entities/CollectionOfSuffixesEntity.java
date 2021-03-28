package app.model.simplemodel.staticdata.jpa.entities;

import app.model.ISuffixes;
import app.model.simplemodel.CollectionOfSuffixesStaticData;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "collection_of_suffixes")
public class CollectionOfSuffixesEntity {

    private static String DB_SUFFIXES_DELIMITER = ",";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = true)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "collections_to_suffixes_mapping",
            joinColumns = @JoinColumn(name = "collection_id"),
            inverseJoinColumns = @JoinColumn(name = "suffixes_name")
    )
    List<SuffixesEntity> suffixesEntities = new ArrayList<>();

    CollectionOfSuffixesEntity() {

    }

    public CollectionOfSuffixesEntity(@NotNull String name) {
        this.name = name;
    }

    public static CollectionOfSuffixesEntity fromCollection(@NotNull CollectionOfSuffixesStaticData newCollection) {
        String name = newCollection.getName();
        CollectionOfSuffixesEntity collectionEntity = new CollectionOfSuffixesEntity(name);

        List<SuffixesEntity> entities = new ArrayList<>();
        for(ISuffixes suffixes: newCollection) {
            SuffixesEntity entity = SuffixesEntity.fromISuffixes(suffixes);
            entities.add(entity);
        }
        collectionEntity.setSuffixess(entities);
        return collectionEntity;
    }

    public CollectionOfSuffixesStaticData toCollection() {
        CollectionOfSuffixesStaticData collection = new CollectionOfSuffixesStaticData(name);

        suffixesEntities.stream()
                .map(SuffixesEntity::toISuffixes)
                .forEach(collection::addNewSuffixesIfAbsent);

        return collection;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public List<SuffixesEntity> getSuffixess() {
        return suffixesEntities;
    }

    public void setSuffixess(List<SuffixesEntity> suffixesCollections) {
        for(SuffixesEntity entity: suffixesCollections) {
            addSuffixes(entity);
        }
    }

    public void addSuffixes(SuffixesEntity suffixesCollectionEntity) {
        if(!suffixesEntities.contains(suffixesCollectionEntity)) {
            suffixesEntities.add(suffixesCollectionEntity);
            suffixesCollectionEntity.addIntoCollection(this);
        }
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final CollectionOfSuffixesEntity that = (CollectionOfSuffixesEntity) o;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }
}