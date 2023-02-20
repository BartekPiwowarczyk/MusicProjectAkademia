package com.example.model.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
public class AlbumSongPK implements Serializable {
    @Column(name="ALBUM_ID")
    private Long albumId;

    @Column(name="SONG_ID")
    private Long songId;


}
