package com.example.spotify.model.dto;

import java.util.List;

public record AlbumSpotify(String name, String id, List<ArtistSpotify> artists, TracksSpotify tracks) {

    public AlbumSpotify(String name, String id, List<ArtistSpotify> artists) {
        this(name, id, artists, null);
    }

    public record TracksSpotify(List<TrackSpotify> items) {
    }

}