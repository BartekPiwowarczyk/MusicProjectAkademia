CREATE TABLE SONGS (
    SONG_ID BIGINT(20) NOT NULL AUTO_INCREMENT,
    TITLE VARCHAR(255) NOT NULL,
    DURATION INTEGER NOT NULL,
    REMARKS VARCHAR(255) NOT NULL,
    CONSTRAINT PK_SONG PRIMARY KEY (SONG_ID)
);

CREATE TABLE ARTISTS (
    ARTIST_ID BIGINT(20) NOT NULL AUTO_INCREMENT,
    NAME VARCHAR(255) NOT NULL DEFAULT 'UNKNOWN',
    FIRSTNAME VARCHAR(255),
    CONSTRAINT PK_ARTIST PRIMARY KEY (ARTIST_ID)
);

CREATE TABLE ALBUMS (
    ALBUM_ID BIGINT(20) NOT NULL AUTO_INCREMENT,
    TITLE VARCHAR(255) NOT NULL,
    EDITION VARCHAR(255),
    ARTIST_id BIGINT(20),
    CONSTRAINT PK_ALBUMS PRIMARY KEY (ALBUM_ID),
    CONSTRAINT FK_ALBUMS_ARTIST FOREIGN KEY (ARTIST_ID) REFERENCES ARTISTS(ARTIST_ID)
);

CREATE TABLE ALBUMS_SONGS (
    ALBUM_ID BIGINT(20),
    SONG_ID BIGINT(20),
    POSITION INTEGER(20),
    CONSTRAINT FK_SONGS_ALBUMS FOREIGN KEY (ALBUM_ID) REFERENCES ALBUMS(ALBUM_ID),
    CONSTRAINT FK_ALBUMS_SONGS FOREIGN KEY (SONG_ID) REFERENCES SONGS(SONG_ID)
    );