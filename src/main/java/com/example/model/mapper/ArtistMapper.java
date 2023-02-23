package com.example.model.mapper;

import com.example.model.dto.ArtistDTO;
import com.example.model.entity.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface ArtistMapper {

    Artist fromArtistDTO(ArtistDTO artistDTO);

    ArtistDTO fromArtist(Artist artist);

}
