package com.example.model.dto;

import com.example.model.entity.AlbumSong;
import com.example.model.entity.Artist;
import com.example.model.entity.Song;

import java.util.Collection;
import java.util.List;

public record AlbumDTOO(String title, String edition, String artist, Collection<AlbumSongsDTO> songs) {
}
