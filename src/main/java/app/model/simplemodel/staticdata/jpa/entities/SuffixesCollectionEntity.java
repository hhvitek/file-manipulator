package app.model.simplemodel.staticdata.jpa.entities;

import app.model.ISuffixesCollection;
import app.model.simplemodel.SuffixesCollectionImpl;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "suffixes_collection")
public class SuffixesCollectionEntity {

    private static String DB_SUFFIXES_DELIMITER = ",";

    @Id
    private String name;

    @Column(name = "suffixes", nullable = true)
    private String suffixes;

    @ManyToMany(mappedBy = "suffixesCollections")
    private List<CollectionOfSuffixesCollectionsEntity> belongingToCollections = new ArrayList<>();

    SuffixesCollectionEntity() {

    }

    public SuffixesCollectionEntity(@NotNull String name, @NotNull String suffixes) {
        this.name = name;
        this.suffixes = suffixes;
    }

    public static SuffixesCollectionEntity fromISuffixesCollection(@NotNull ISuffixesCollection suffixesCollection) {
        String name = suffixesCollection.getName();
        String delimitedSuffixes = suffixesCollection.getSuffixesAsDelimitedString(DB_SUFFIXES_DELIMITER);
        SuffixesCollectionEntity suffixesCollectionEntity = new SuffixesCollectionEntity(name, delimitedSuffixes);
        return suffixesCollectionEntity;
    }

    public ISuffixesCollection toISuffixesCollection() {
        ISuffixesCollection suffixesCollection = new SuffixesCollectionImpl(name);
        suffixesCollection.addSuffixes(suffixes, DB_SUFFIXES_DELIMITER);

        return suffixesCollection;
    }

    public String getName() {
        return name;
    }

    public String getSuffixes() {
        return suffixes;
    }

    public void setSuffixes(String suffixes) {
        this.suffixes = suffixes;
    }

    public List<CollectionOfSuffixesCollectionsEntity> getBelongingToCollections() {
        return belongingToCollections;
    }

    public void setBelongingToCollections(List<CollectionOfSuffixesCollectionsEntity> belongingToCollections) {
        for (CollectionOfSuffixesCollectionsEntity entity: belongingToCollections) {
            addIntoCollection(entity);
        }
    }

    public void addIntoCollection(@NotNull CollectionOfSuffixesCollectionsEntity collection) {
        if (!belongingToCollections.contains(collection)) {
            belongingToCollections.add(collection);
            collection.addSuffixesCollection(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuffixesCollectionEntity that = (SuffixesCollectionEntity) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(suffixes, that.suffixes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, suffixes);
    }


}