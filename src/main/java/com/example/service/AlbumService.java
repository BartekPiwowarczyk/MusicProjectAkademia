package com.example.service;


import com.example.model.dto.AlbumDTO;
import com.example.model.dto.ArtistDTO;
import com.example.model.entity.Album;
import com.example.model.entity.Artist;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.stream.Collectors;

@ApplicationScoped
public class AlbumService {

    @Inject
    EntityManager em;

    public AlbumDTO getAlbumById(Long id) {
        Album album = em.createNamedQuery("Album.findById",Album.class).setParameter("id",id).getResultList().stream().findFirst().orElse(null);
        ArtistDTO artistDTO = new ArtistDTO(album.getArtistId().getName(),album.getArtistId().getFirstname());
        AlbumDTO albumDTO = new AlbumDTO(album.getTitle(),album.getEdition(),artistDTO,album.getAlbumSongs());
        return  albumDTO;
    }
}
