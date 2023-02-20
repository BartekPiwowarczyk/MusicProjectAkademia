package com.example.model.entity;

import javax.persistence.*;
import java.util.Set;

@NamedQuery(name="Album.findById",query="Select a from Album a where a.id= :id")
//@NamedQuery(name="Album.findAllSongsById")
@Entity
@Table(name="ALBUMS")
public class Album {

    @Column(name="ALBUM_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="TITLE")
    private String title;

    @Column(name = "EDITION")
    private String edition;

    @ManyToOne
    @JoinColumn(name = "ARTIST_ID", referencedColumnName = "ARTIST_ID")
    private Artist artistId;

    @OneToMany
    @JoinColumn(name = "ALBUM_ID", referencedColumnName="ALBUM_ID")
    private Set<AlbumSong> albumSongs;

    public Set<AlbumSong> getAlbumSongs() {
        return albumSongs;
    }

    public void setAlbumSongs(Set<AlbumSong> albumSongs) {
        this.albumSongs = albumSongs;
    }


    public Album() {
    }

    public Album(Long id, String title, String edition, Artist artistId, Set<AlbumSong> albumSongs) {
        this.id = id;
        this.title = title;
        this.edition = edition;
        this.artistId = artistId;
        this.albumSongs = albumSongs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public Artist getArtistId() {
        return artistId;
    }

    public void setArtistId(Artist artistId) {
        this.artistId = artistId;
    }


}
