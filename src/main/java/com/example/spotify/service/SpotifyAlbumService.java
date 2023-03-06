package com.example.spotify.service;

import com.example.model.entity.Album;
import com.example.spotify.model.dto.AlbumSpotify;
import com.example.spotify.model.mapper.SpotifyAlbumMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;


@ApplicationScoped
public class SpotifyAlbumService {

    Logger LOGGER = LoggerFactory.getLogger(SpotifyAlbumService.class);
    @Inject
    EntityManager em;
    @Inject
    SpotifyAlbumMapper spotifyAlbumMapper;

    @Transactional
    public AlbumSpotify addAlbumSpotifyToDb(AlbumSpotify albumSpotify) {
        LOGGER.info("create new Album");
        Album album = spotifyAlbumMapper.fromAlbumSpotifyResponse(albumSpotify);
        LOGGER.info("Album created, artistId:{},title:{},albumsongs:{}", album.getArtistId(), album.getTitle(), album.getAlbumSongs());
        em.persist(album);
        LOGGER.info("Album added to db");
        return albumSpotify;

    }
}
