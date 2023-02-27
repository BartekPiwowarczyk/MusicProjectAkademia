package com.example.service;


import com.example.model.dto.AlbumDTO;
import com.example.model.dto.AlbumDTOO;
import com.example.model.dto.AlbumSongsDTO;
import com.example.model.dto.ArtistDTO;
import com.example.model.entity.*;
import com.example.model.mapper.AlbumMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class AlbumService {

    Logger LOGGER = LoggerFactory.getLogger(AlbumService.class);
    @Inject
    EntityManager em;

    @Inject
    ArtistService artistService;
    @Inject
    AlbumMapper albumMapper;

    public AlbumDTO getAlbumDTOById(Long id) {
        LOGGER.info("AlbumService getAlbumById({})",id);
        Album album = em.createNamedQuery("Album.findById",Album.class).setParameter("id",id).getResultList().stream().findFirst().orElse(null);
        AlbumDTO albumDTO = albumMapper.fromAlbum(album);
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

//    select distinct a from Album a join fetch a.artistId ar join fetch a.albumSongs s join fetch s.song where a.id= :id order by s.position asc
public Album getAlbumByIdWithCriteria(Long id) {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<Album> query = criteriaBuilder.createQuery(Album.class);
    Root<Album> root = query.from(Album.class);
    Join<Album, Artist> artistJoin = (Join<Album, Artist>) root.fetch(Album_.artistId);
    Join<AlbumSong, Song> songJoin = (Join<AlbumSong, Song>) root.fetch(Album_.albumSongs).fetch(AlbumSong_.song);

    query.where(
            criteriaBuilder.equal(root.get(Album_.id), criteriaBuilder.parameter(Long.class, "id"))
    )
            .distinct(true);

    return em.createQuery(query).setParameter("id", id).getResultList().stream().findFirst().orElseThrow(() -> new NotFoundException());
}


    public AlbumDTOO getAlbumDTOByIdWithCriteria(Long id) {
        LOGGER.info("AlbumService with criteria method");
        CriteriaBuilder criteriaBuilder= em.getCriteriaBuilder();
        CriteriaQuery<AlbumDTOO> query = criteriaBuilder.createQuery(AlbumDTOO.class);
        Root<Album> root = query.from(Album.class);
        LOGGER.info("Podstawy criteria buildera");

         Join<Album, Artist> artistJoin = (Join<Album, Artist>) root.fetch(Album_.artistId);
        final Join<Album, AlbumSong> albumSongsJoin = root.join(Album_.albumSongs);
        final ListJoin<Album, AlbumSong> albumSongsListJoin = root.join(Album_.albumSongs,JoinType.INNER);
        final Join<AlbumSong,Song> songsJoin = root.join(Album_.albumSongs).join(AlbumSong_.song);
        LOGGER.info("Join AlbumSong i Artist");

//        criteriaBuilder.construct(ArtistDTO.class,artistJoin.get(Artist_.name),artistJoin.get(Artist_.firstname))

        query.select(
                criteriaBuilder.construct(AlbumDTOO.class,root.get(Album_.title),root.get(Album_.edition), root.get(Album_.artistId))
        ).where(
                criteriaBuilder.equal(root.get(Album_.id),criteriaBuilder.parameter(Long.class,"id"))
                );
//                .orderBy(criteriaBuilder.asc(root.get(Album_.albumSongs)))
        LOGGER.info("Stworzono query");


        return em.createQuery(query).setParameter("id",id).getResultList().stream().findFirst().orElseThrow(()->new NotFoundException());
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


