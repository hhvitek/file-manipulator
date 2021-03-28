CREATE TABLE IF NOT EXISTS suffixes
(
    name     TEXT PRIMARY KEY,
    suffixes TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS collection_of_suffixes
(
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT
);

CREATE TABLE IF NOT EXISTS collections_to_suffixes_mapping
(
    suffixes_id   TEXT REFERENCES suffixes (name) ON DELETE CASCADE
        ON UPDATE CASCADE,
    collection_id INTEGER REFERENCES collection_of_suffixes (id) ON DELETE CASCADE
        ON UPDATE CASCADE,
    PRIMARY KEY (
                 suffixes_id,
                 collection_id
        )
);

CREATE TABLE IF NOT EXISTS current_data
(
    name  TEXT PRIMARY KEY,
    value TEXT NOT NULL
);