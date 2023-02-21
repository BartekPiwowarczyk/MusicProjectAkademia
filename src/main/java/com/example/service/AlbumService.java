package com.example.service;


import com.example.model.dto.AlbumDTO;
import com.example.model.dto.ArtistDTO;
import com.example.model.dto.SongDTO;
import com.example.model.entity.Album;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AlbumService {

    Logger LOGGER = LoggerFactory.getLogger(AlbumService.class);
    @Inject
    EntityManager em;

    public AlbumDTO getAlbumById(Long id) {
        LOGGER.info("AlbumService getAlbumById({})", id);
        Album album = em.createNamedQuery("Album.findById", Album.class).setParameter("id", id).getResultList().stream().findFirst().orElse(null);
        LOGGER.info("Created Album entity with namedQuery: Album.findById({})",id);
        ArtistDTO artistDTO = new ArtistDTO(album.getArtistId().getName(), album.getArtistId().getFirstname());
        List<SongDTO> songs = album.getAlbumSongs().stream()
                .map(albumSong -> new SongDTO(albumSong.getPosition(), albumSong.getSong().getTitle()))
                .collect(Collectors.toList());
        return new AlbumDTO(album.getTitle(), album.getEdition(), artistDTO, songs);
    }
}

