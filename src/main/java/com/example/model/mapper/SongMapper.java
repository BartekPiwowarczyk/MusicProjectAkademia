package com.example.model.mapper;

import com.example.model.dto.SongDTO;
import com.example.model.entity.Song;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface SongMapper {

    Song fromSongDTO(SongDTO songDTO);

    SongDTO fromSong(Song song);
}
