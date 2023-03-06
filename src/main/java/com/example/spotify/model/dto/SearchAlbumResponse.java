package com.example.spotify.model.dto;

import com.example.spotify.model.dto.AlbumSpotify;

import java.util.List;

public record SearchAlbumResponse(AlbumsSearched albums) {

    public record AlbumsSearched(List<AlbumSpotify> items ) {
    }
}
