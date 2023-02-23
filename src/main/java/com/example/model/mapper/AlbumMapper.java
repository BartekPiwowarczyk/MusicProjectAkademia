package com.example.model.mapper;

import com.example.model.dto.AlbumDTO;
import com.example.model.dto.AlbumSongsDTO;
import com.example.model.dto.SongDTO;
import com.example.model.entity.Album;
import com.example.model.entity.AlbumSong;
import com.example.service.ArtistService;
import com.example.service.SongService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses={ArtistMapper.class,SongMapper.class,AlbumSongMapper.class},componentModel = "cdi")
public abstract class AlbumMapper {

    @Inject
    ArtistService artistService;

    @Inject
    SongService songService;

    @Mapping(target = "artistId",expression = "java(artistService.findOrCreateArtist(albumDTO.artistDTO()))")
    @Mapping(source = "albumSongsDTO",target = "albumSongs")
//    @Mapping(target = "albumSongs",expression = )
//    @Mapping(source="artistDTO",target = "artistId")
    public abstract Album fromAlbumDTO(AlbumDTO albumDTO);


    @Mapping(source = "artistId",target = "artistDTO")
//    @Mapping(target = "songs",expression = "java(getAlbumSongsDTO(album.getAlbumSongs()))")
    @Mapping(source = "albumSongs",target = "albumSongsDTO")
    public abstract AlbumDTO fromAlbum(Album album);

//    public List<AlbumSongsDTO> getAlbumSongsDTO(List<AlbumSong> albumSongs) {
//        return albumSongs.stream()
//                .map(as -> new AlbumSongsDTO(as.getPosition(),new SongDTO(as.getSong().getTitle(),as.getSong().getRemarks(),as.getSong().getDuration())))
//                .collect(Collectors.toList());
//    }


}
