package com.example.service;


import com.example.model.dto.AlbumDTO;
import com.example.model.entity.Album;
import com.example.model.mapper.AlbumMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class AlbumService {

    Logger LOGGER = LoggerFactory.getLogger(AlbumService.class);
    @Inject
    EntityManager em;

    @Inject
    AlbumMapper albumMapper;

    public AlbumDTO getAlbumDTOById(Long id) {
        LOGGER.info("AlbumService getAlbumById({})",id);
     AlbumDTO albumDTO = em.createNamedQuery("Album.findById",AlbumDTO.class).setParameter("id",id).getResultList().stream().findFirst().orElse(null);
        LOGGER.info("AlbumDTO with param({})",id);
        return albumDTO;
    }

    @Transactional
    public AlbumDTO createNewAlbum(AlbumDTO albumDTO) {
        LOGGER.info("create new Album");
        Album album = albumMapper.fromAlbumDTO(albumDTO);
        LOGGER.info("Album created, artistId:{},title:{},albumsongs:{}",album.getArtistId(),album.getTitle(),album.getAlbumSongs());
        em.persist(album);
        LOGGER.info("Album added to db");
        return albumDTO;
    }
}
//
//   LOGGER.info("AlbumService getAlbumById({})", id);
//           Album album = em.createNamedQuery("Album.findById", Album.class).setParameter("id", id).getResultList().stream().findFirst().orElse(null);
//        LOGGER.info("Created Album entity with namedQuery: Album.findById({})",id);
//        ArtistDTO artistDTO = new ArtistDTO(album.getArtistId().getName(), album.getArtistId().getFirstname());
//        List<SongDTO> songs = album.getAlbumSongs().stream()
//        .map(albumSong -> new SongDTO(albumSong.getPosition(), albumSong.getSong().getTitle()))
//        .collect(Collectors.toList());
