package com.example.controller;

import com.example.model.dto.AlbumDTO;
import com.example.model.dto.AlbumDTOO;
import com.example.model.dto.ArtistDTO;
import com.example.model.dto.SongDTO;
import com.example.model.entity.Album;
import com.example.model.entity.Artist;
import com.example.service.AlbumService;
import com.example.service.ArtistService;
import com.example.service.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MusicController {

    Logger LOGGER = LoggerFactory.getLogger(MusicController.class);

    @Inject
    AlbumService albumService;

    @Inject
    ArtistService artistService;

    @Inject
    SongService songService;

    @GET
    @Path("/albums/artist/{id}")
    public List<AlbumDTOO> getAlbumsByArtistIdDTOs(@PathParam("id") Long id){
        LOGGER.info("Request arrived");
        try {
            return albumService.getAlbumsByArtistId(id);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }

    @GET
    @Path("/albumsDTO/criteria")
    public List<AlbumDTOO> getAllAlbumsDTO() {
        LOGGER.info("Request arrived");
        try {
            return albumService.getAllAlbumDTOOrderByAlbumTitle();
        } catch (Exception e) {
            LOGGER.info("Exception in MusicController");
            throw new NotFoundException();
        }
    }

    @GET
    @Path("/albumsDTO/criteria/{id}")
    public AlbumDTOO getAlbumDTOById(@PathParam("id") Long id) {
        LOGGER.info("Request arrived with param: {}", id);
        try {
            return albumService.getAlbumDTOByIdWithCriteria(id);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }


    @GET
    @Path("/albums/criteria")
    public List<Album> getAllAlbums() {
        LOGGER.info("Request arrived");
        try {
            return albumService.getAllAlbum();
        } catch (Exception e) {
            LOGGER.info("Exception in MusicController");
            throw new NotFoundException();
        }
    }
    @GET
    @Path("/albums/criteria/{id}")
    public Album getAlbumById(@PathParam("id") Long id) {
        LOGGER.info("Request arrived with param: {}", id);
        try {
            return albumService.getAlbumByIdWithCriteria(id);
        } catch (Exception e) {
            LOGGER.info("Exception in MusicController");
            throw new NotFoundException();
        }
    }

    @GET
    @Path("/albumsDTO/transform")
    public List<AlbumDTO> getAlbumDTOListByIdWithResultTransform() {
        LOGGER.info("Request arrived");
        try {
            return albumService.getAlbumDTOOListWithResultTransform();
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }
    @GET
    @Path("/albumsDTO/transform/{id}")
    public AlbumDTO getAlbumDTOByIdWithResultTransform(@PathParam("id") Long id) {
        LOGGER.info("Request arrived with param: {}", id);
        try {
            return albumService.getAlbumDTOOWithResultTransform(id);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }


    @PUT
    @Path("/albums")
    public Response createNewAlbum(AlbumDTO albumDTO) {
        try {
            albumService.createNewAlbum(albumDTO);
            return Response.status(201).entity(albumDTO).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }

    @GET
    @Path("/artists/{id}")
    public ArtistDTO getArtistDTOById(@PathParam("id") Long id) {
        return artistService.getArtistDTOForIdWithCriteria(id);
    }

    @GET
    @Path("/artists/names/{name}")
    public Artist getArtistByName(@PathParam("name") String name) {
        return artistService.getArtistByNameWithCriteria(name);
    }


    @POST
    @Path("/artists")
    public Response createNewArtist(ArtistDTO artistDTO) {
        try {
            artistService.createNewArtist(artistDTO);
            return Response.status(201).entity(artistDTO).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }

    @GET
    @Path("/songs/{id}")
    public SongDTO getSongDTOById(@PathParam("id") Long id) {
        return songService.getSongDTOByIdWithCriteria(id);
    }

//    @POST
//    @Path("/songs")
//    public Response createNewSong(SongDTO songDTO) {
//        try {
//            songService.createNewSong(songDTO);
//            return Response.status(201).entity(songDTO).build();
//        } catch (Exception e) {
//            return Response.status(404).build();
//        }
//    }


}
