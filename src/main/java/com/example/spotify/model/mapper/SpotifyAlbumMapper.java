package com.example.spotify.model.mapper;

import com.example.model.entity.Album;
import com.example.spotify.model.dto.AlbumSpotify;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses={SpotifyArtistMapper.class,SpotifySongMapper.class,SpotifyAlbumSongMapper.class},componentModel = "cdi")
public abstract class SpotifyAlbumMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "edition",source = "title")
    @Mapping(target = "artistId",ignore = true)
    @Mapping(target = "albumSongs",ignore = true)
    public abstract Album fromAlbumSpotifyResponse(AlbumSpotify albumSpotify);
}
