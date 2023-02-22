package com.example.model.mapper;

import com.example.model.dto.AlbumDTO;
import com.example.model.entity.Album;
import com.example.service.ArtistService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.inject.Inject;

@Mapper(uses={ArtistMapper.class,SongMapper.class},componentModel = "cdi")
public abstract class AlbumMapper {

    @Inject
    ArtistService artistService;

    @Mapping(ignore = true,target = "albumSongs")
    @Mapping(target = "artistId",expression = "java(artistService.findArtistById(albumDTO.artistId()))")
    public abstract Album fromAlbumDTO(AlbumDTO albumDTO);
}
