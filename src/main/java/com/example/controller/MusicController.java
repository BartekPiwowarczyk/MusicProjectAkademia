package com.example.controller;

import com.example.model.dto.AlbumDTO;
import com.example.service.AlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;



@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MusicController {

    Logger LOGGER = LoggerFactory.getLogger(MusicController.class);

    @Inject
    AlbumService albumService;

    @GET
    @Path("/albums/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public AlbumDTO getAlbumById(@PathParam("id") Long id) {
        LOGGER.info("Request arrived with param: {}",id);
        try {
            return albumService.getAlbumById(id);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }
}
