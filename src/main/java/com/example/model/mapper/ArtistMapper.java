package com.example.model.mapper;

import com.example.model.dto.ArtistDTO;
import com.example.model.entity.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface ArtistMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "firstname",ignore = true)
    Artist fromArtistDTO(ArtistDTO artistDTO);

    ArtistDTO fromArtist(Artist artist);

}
