package com.example.service;

import com.example.model.dto.SongDTO;
import com.example.model.entity.Song;
import com.example.model.entity.Song_;
import com.example.model.mapper.SongMapper;



import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;


@ApplicationScoped
public class SongService {

    @Inject
    EntityManager em;

    @Inject
    SongMapper songMapper;
    public SongDTO getSongDTOById(Long id) {
        return em.createNamedQuery("Song.findById",SongDTO.class).setParameter("id",id).getResultList().stream().findFirst().orElse(null);
    }

    @Transactional
    public SongDTO createNewSong(SongDTO songDTO) {
        Song song = songMapper.fromSongDTO(songDTO);
        em.persist(song);
        return songDTO;
    }

//    SELECT NEW com.example.model.dto.SongDTO(s.title,s.remarks,s.duration) from Song s where s.id = :id

    public SongDTO getSongDTOByIdWithCriteria(Long id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<SongDTO> query = criteriaBuilder.createQuery(SongDTO.class);
        Root<Song> song = query.from(Song.class);

        query.select(criteriaBuilder.construct(SongDTO.class,song.get(Song_.title),song.get(Song_.remarks),song.get(Song_.duration)))
                .where(criteriaBuilder.equal(song.get(Song_.id),criteriaBuilder.parameter(Long.class,"id")));
        return em.createQuery(query).setParameter("id",id).getResultList().stream().findFirst().orElseThrow(()->new NotFoundException());
    }
}
