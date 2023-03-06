package com.example.spotify.model.mapper;


import com.example.model.entity.Artist;
import com.example.spotify.model.dto.ArtistSpotify;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface SpotifyArtistMapper {

    @Mapping(target = "name",source = "name")
    @Mapping(target = "id",ignore = true)
    public Artist fromArtistSpotify(ArtistSpotify artistSpotify);
}
