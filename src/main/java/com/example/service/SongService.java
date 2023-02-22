package com.example.service;

import com.example.model.dto.SongDTO;
import com.example.model.entity.Song;
import com.example.model.mapper.SongMapper;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
}
