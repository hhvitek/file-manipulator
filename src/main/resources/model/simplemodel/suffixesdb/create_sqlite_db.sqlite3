CREATE TABLE IF EXISTS suffixes_collection (
    name     TEXT PRIMARY KEY,
    suffixes TEXT NOT NULL
);

CREATE TABLE IF EXISTS collection_of_suffixes_collections (
    id INTEGER PRIMARY KEY AUTOINCREMENT
);

CREATE TABLE IF EXISTS suffixes_collections_in_collections (
    suffixes_id   TEXT    REFERENCES suffixes_collection (name) ON DELETE CASCADE
                                                                ON UPDATE CASCADE,
    collection_id INTEGER REFERENCES collection_of_suffixes_collections (id) ON DELETE CASCADE
                                                                             ON UPDATE CASCADE,
    PRIMARY KEY (
        suffixes_id,
        collection_id
    )
);

CREATE TABLE IF EXISTS current_data (
    id                  INTEGER PRIMARY KEY AUTOINCREMENT,
    input_folder        TEXT,
    output_folder       TEXT,
    suffixes_collection TEXT    REFERENCES suffixes_collection (name) ON DELETE SET NULL
                                                                      ON UPDATE CASCADE
);
