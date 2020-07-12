-- Table: suffixes_db
DROP TABLE IF EXISTS suffixes_db;

CREATE TABLE suffixes_db (
    name     TEXT PRIMARY KEY,
    suffixes TEXT NOT NULL
);

INSERT INTO suffixes_db (name, suffixes) VALUES ('AUDIO', 'mp3,mpa,ogg');
INSERT INTO suffixes_db (name, suffixes) VALUES ('VIDEO', 'mp4,avi,webm');
INSERT INTO suffixes_db (name, suffixes) VALUES ('MY_LIST', 'txt,out');
