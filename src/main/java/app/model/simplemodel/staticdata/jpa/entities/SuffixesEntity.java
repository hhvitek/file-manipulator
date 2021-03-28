package app.model.simplemodel.staticdata.jpa.entities;

import app.model.ISuffixes;
import app.model.simplemodel.AllSuffixes;
import app.model.simplemodel.SuffixesImpl;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "suffixes")
public class SuffixesEntity {

    private static String DB_SUFFIXES_DELIMITER = ",";

    @Id
    private String name;

    @Column(name = "suffixes", nullable = true)
    private String suffixes;

    @ManyToMany(mappedBy = "suffixesEntities")
    private List<CollectionOfSuffixesEntity> belongingToCollections = new ArrayList<>();

    SuffixesEntity() {

    }

    public SuffixesEntity(@NotNull String name, @NotNull String suffixes) {
        this.name = name;
        this.suffixes = suffixes;
    }

    public static SuffixesEntity fromISuffixes(@NotNull ISuffixes suffixes) {
        String name = suffixes.getName();
        String delimitedSuffixes = suffixes.getSuffixesAsDelimitedString(DB_SUFFIXES_DELIMITER);
        SuffixesEntity suffixesEntity = new SuffixesEntity(name, delimitedSuffixes);
        return suffixesEntity;
    }

    public ISuffixes toISuffixes() {
        if ("ALL".equalsIgnoreCase(name) && suffixes.isEmpty()) {
            return new AllSuffixes();
        }

        ISuffixes suffixes = new SuffixesImpl(name);
        suffixes.addSuffixes(this.suffixes, DB_SUFFIXES_DELIMITER);

        return suffixes;
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

    public List<CollectionOfSuffixesEntity> getBelongingToCollections() {
        return belongingToCollections;
    }

    public void setBelongingToCollections(List<CollectionOfSuffixesEntity> belongingToCollections) {
        for (CollectionOfSuffixesEntity entity: belongingToCollections) {
            addIntoCollection(entity);
        }
    }

    public void addIntoCollection(@NotNull CollectionOfSuffixesEntity collection) {
        if (!belongingToCollections.contains(collection)) {
            belongingToCollections.add(collection);
            collection.addSuffixes(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuffixesEntity that = (SuffixesEntity) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(suffixes, that.suffixes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, suffixes);
    }


}