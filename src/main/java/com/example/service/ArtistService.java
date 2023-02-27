package com.example.service;

import com.example.model.dto.ArtistDTO;
import com.example.model.entity.Artist;
import com.example.model.entity.Artist_;
import com.example.model.mapper.ArtistMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

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

    //SELECT NEW com.example.model.dto.ArtistDTO(a.name,a.firstname) FROM Artist a where a.id= :id


    public ArtistDTO getArtistDTOForIdWithCriteria(Long id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<ArtistDTO> query = criteriaBuilder.createQuery(ArtistDTO.class);
        Root<Artist> root = query.from(Artist.class);

        query.select(criteriaBuilder.construct(ArtistDTO.class,root.get(Artist_.firstname),root.get(Artist_.name)))
                .where(criteriaBuilder.equal(root.get(Artist_.id),criteriaBuilder.parameter(Long.class,"id")));

        return em.createQuery(query).setParameter("id",id).getResultList().stream().findFirst().orElseThrow(()->new NotFoundException("Brak elementu"));
    }

//    SELECT a FROM Artist a where a.name= :name


    public Artist getArtistByNameWithCriteria(String name) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Artist> query = criteriaBuilder.createQuery(Artist.class);
        Root<Artist> root = query.from(Artist.class);

        query.where(criteriaBuilder.equal(root.get(Artist_.name),criteriaBuilder.parameter(String.class,"name")));

        return em.createQuery(query).setParameter("name",name).getResultList().stream().findFirst().orElseThrow(()->new NotFoundException("Brak elementu"));
    }
}
