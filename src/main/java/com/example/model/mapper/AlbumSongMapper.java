package com.example.model.mapper;

import com.example.model.dto.AlbumSongsDTO;
import com.example.model.entity.AlbumSong;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = SongMapper.class,componentModel = "cdi")
public interface AlbumSongMapper {

    @Mapping(source="song",target = "song")
    AlbumSong fromAlbumSongDTO(AlbumSongsDTO albumSongsDTO);

    @Mapping(source="song",target = "song")
    AlbumSongsDTO fromAlbumSong(AlbumSong albumSong);
}
