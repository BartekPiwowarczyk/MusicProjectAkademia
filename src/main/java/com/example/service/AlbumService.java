package com.example.service;


import com.example.model.dto.*;
import com.example.model.entity.*;
import com.example.model.mapper.AlbumMapper;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import java.util.*;


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
        LOGGER.info("AlbumService getAlbumById({})", id);
        Album album = em.createNamedQuery("Album.findById", Album.class).setParameter("id", id).getResultList().stream().findFirst().orElse(null);
        AlbumDTO albumDTO = albumMapper.fromAlbum(album);
        LOGGER.info("AlbumDTO with param({})", id);
        return albumDTO;
    }


    @Transactional
    public AlbumDTO createNewAlbum(AlbumDTO albumDTO) {
        LOGGER.info("create new Album");
        Album album = albumMapper.fromAlbumDTO(albumDTO);
        LOGGER.info("Album created, artistId:{},title:{},albumsongs:{}", album.getArtistId(), album.getTitle(), album.getAlbumSongs());
        em.persist(album);
        LOGGER.info("Album added to db");
        return albumDTO;
    }

    //    select distinct a from Album a join fetch a.artistId ar join fetch a.albumSongs s join fetch s.song where a.id= :id order by s.position asc
    public Album getAlbumByIdWithCriteria(Long id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Album> query = criteriaBuilder.createQuery(Album.class);
        Root<Album> root = query.from(Album.class);
        Join<Album, Artist> artistJoin = (Join<Album, Artist>) root.fetch(Album_.artistId, JoinType.LEFT);
        Join<AlbumSong, Song> songJoin = (Join<AlbumSong, Song>) root.fetch(Album_.albumSongs, JoinType.LEFT).fetch(AlbumSong_.song, JoinType.LEFT);
//        Join<Album, AlbumSong> albumSongJoin = (Join<Album, AlbumSong>) root.fetch(Album_.albumSongs, JoinType.LEFT);

        query.where(criteriaBuilder.equal(root.get(Album_.id), criteriaBuilder.parameter(Long.class, "id")))
                .distinct(true);

        return em.createQuery(query).setParameter("id", id).getResultList().stream().findFirst().orElseThrow(() -> new NotFoundException());
    }


    public AlbumDTOO getAlbumDTOByIdWithCriteria(Long id) {
        LOGGER.info("AlbumService with criteria method");
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<AlbumDTOO> query = criteriaBuilder.createQuery(AlbumDTOO.class);
        Root<Album> root = query.from(Album.class);
        LOGGER.info("Podstawy criteria buildera");
//
        Join<Album, Artist> artistJoin = (Join<Album, Artist>) root.join(Album_.artistId, JoinType.LEFT);
        Join<Album,AlbumSong> albumAlbumSongJoin = (Join<Album, AlbumSong>) root.join(Album_.albumSongs, JoinType.LEFT);
        LOGGER.info("Join AlbumSong i Artist");


        query.select(
                criteriaBuilder.construct(AlbumDTOO.class, root.get(Album_.title), root.get(Album_.edition), artistJoin.get(Artist_.name))
        ).where(
                criteriaBuilder.equal(root.get(Album_.id), criteriaBuilder.parameter(Long.class, "id"))
        ).distinct(true);
        LOGGER.info("Stworzono query");


        return em.createQuery(query).setParameter("id", id).getResultList().stream().findFirst().orElseThrow(() -> new NotFoundException());
    }

//select a.id,a.title,a.edition, ar.name, aso.position, song.title,song.remarks,song.duration from Album a left join a.artistId ar left join a.albumSongs aso left join aso.song song where a.id=:id
    @SuppressWarnings("unchecked")
    public AlbumDTOO getAlbumDTOOWithResultTransform(Long id) throws Throwable {
        return (AlbumDTOO) em.createNamedQuery("Album.findByIdWithResultTransformer")
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(
                        new ResultTransformer() {

                            final Map<Long,AlbumDTOO> albumDTOOMap = new LinkedHashMap<>();

                            @Override
                            public Object transformTuple(Object[] objects, String[] strings) {

                                Long albumId = (Long) objects[0];
                                albumDTOOMap.putIfAbsent(albumId,new AlbumDTOO(
                                        (String) objects[1],
                                        (String) objects[2],
                                        (String) objects[3],
                                        new LinkedHashSet<>()
                                ));

                                if (objects[4] instanceof Integer && objects[5] instanceof String && objects[6] instanceof String && objects[7] instanceof Integer) {
                                    albumDTOOMap.get(albumId).songs().add(new AlbumSongsDTO((Integer) objects[4],new SongDTO((String) objects[5],(String) objects[6],(Integer) objects[7])));
                                }

                                return albumDTOOMap.get(albumId);
                            }

                            @Override
                            public List transformList(List list) {
                                return new ArrayList(albumDTOOMap.values());
                            }
                        }
                ).setParameter("id",id).getResultList().stream().findFirst().orElse(null);
    }

//    select a.id,a.title,a.edition, ar.name, aso.position, song.title,song.remarks,song.duration from Album a left join a.artistId ar left join a.albumSongs aso left join aso.song song
@SuppressWarnings("unchecked")
public List<AlbumDTOO> getAlbumDTOOListWithResultTransform() {
    return em.createNamedQuery("Album.findWithResultTransformer")
            .unwrap(org.hibernate.query.Query.class)
            .setResultTransformer(
                    new ResultTransformer() {

                        final Map<Long,AlbumDTOO> albumDTOOMap = new LinkedHashMap<>();

                        @Override
                        public Object transformTuple(Object[] objects, String[] strings) {

                            Long albumId = (Long) objects[0];
                            albumDTOOMap.putIfAbsent(albumId,new AlbumDTOO(
                                    (String) objects[1],
                                    (String) objects[2],
                                    (String) objects[3],
                                    new LinkedHashSet<>()
                            ));

                            if (objects[4] instanceof Integer && objects[5] instanceof String && objects[6] instanceof String && objects[7] instanceof Integer) {
                                albumDTOOMap.get(albumId).songs().add(new AlbumSongsDTO((Integer) objects[4],new SongDTO((String) objects[5],(String) objects[6],(Integer) objects[7])));
                            }

                            return albumDTOOMap.get(albumId);
                        }

                        @Override
                        public List transformList(List list) {
                            return new ArrayList(albumDTOOMap.values());
                        }
                    }
            ).getResultList();
}

    public List<Album> getAllAlbum() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Album> query = criteriaBuilder.createQuery(Album.class);
        Root<Album> root = query.from(Album.class);
        Join<Album, Artist> artistJoin = (Join<Album, Artist>) root.join(Album_.artistId);

        return em.createQuery(query).getResultList();
    }

    public List<AlbumDTOO> getAllAlbumDTOOOrderByAlbumTitle() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<AlbumDTOO> query = criteriaBuilder.createQuery(AlbumDTOO.class);
        Root<Album> root = query.from(Album.class);
        LOGGER.info("Podstawy criteria buildera");
        Join<Album, Artist> artistJoin = (Join<Album, Artist>) root.join(Album_.artistId);
        LOGGER.info("Fetch Artist");

        query.select(criteriaBuilder.construct(AlbumDTOO.class, root.get(Album_.title), root.get(Album_.edition),artistJoin.get(Artist_.name)))
                        .orderBy(criteriaBuilder.asc(root.get(Album_.title)));
        LOGGER.info("Stworzono query");

        return em.createQuery(query).getResultList();
    }


//    public List<Album> getAllAlbum() {
//        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//        CriteriaQuery<Album> query = criteriaBuilder.createQuery(Album.class);
//        Root<Album> root = query.from(Album.class);
//        Join<Album, Artist> artistJoin = (Join<Album, Artist>) root.fetch(Album_.artistId,JoinType.LEFT);
//
//
//        return em.createQuery(query).getResultList();
//    }

    public List<AlbumDTOO> getAlbumsByArtistId(Long id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<AlbumDTOO> query = criteriaBuilder.createQuery(AlbumDTOO.class);
        Root<Album> root = query.from(Album.class);
        Join<Album, Artist> artistJoin = root.join(Album_.artistId);

        query.select(criteriaBuilder.construct(AlbumDTOO.class, root.get(Album_.title), root.get(Album_.edition), root.get(Album_.artistId)))
                .where(criteriaBuilder.equal(artistJoin.get(Artist_.id), criteriaBuilder.parameter(Long.class, "id")));

        return em.createQuery(query).setParameter("id", id).getResultList();
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


