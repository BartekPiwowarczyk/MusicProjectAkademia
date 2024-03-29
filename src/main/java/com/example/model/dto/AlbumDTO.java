package com.example.model.dto;

import com.example.model.entity.AlbumSong;
import com.example.model.entity.Artist;
import com.example.model.entity.Song;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public record AlbumDTO (String title, String edition, ArtistDTO artistDTO, Collection<AlbumSongsDTO> albumSongsDTO) {

}
