package com.example.service;

import com.example.model.dto.ArtistDTO;
import com.example.model.entity.Artist;
import com.example.model.mapper.ArtistMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class ArtistService {

    @Inject
    EntityManager em;

    @Inject
    ArtistMapper artistMapper;

    public ArtistDTO getArtistDTOById(Long id) {
        return em.createNamedQuery("Artist.findById",ArtistDTO.class).setParameter("id",id).getResultList().stream().findFirst().orElse(null);
    }

    public Artist findOrCreateArtist(ArtistDTO artistDTO) {
        if (artistDTO == null) {
            Artist artist = em.createNamedQuery("Artist.findUnknownArtist",Artist.class).getSingleResult();
            artist.setName("Unknown");
            em.persist(artist);
            return artist;
        } else {
            Artist artist = em.createNamedQuery("Artist.findArtistByName", Artist.class)
                    .setParameter("name", artistDTO.getName())
                    .getResultList()
                    .stream()
                    .findFirst().orElse(null);
            if (artist == null)
                artist = createNewArtist(artistDTO);
            return artist;
        }
    }

    @Transactional
    public Artist createNewArtist(ArtistDTO artistDTO) {
        Artist artist = artistMapper.fromArtistDTO(artistDTO);
        em.persist(artist);
        return artist;
    }
}
