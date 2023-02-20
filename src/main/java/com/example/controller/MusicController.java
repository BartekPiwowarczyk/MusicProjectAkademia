package com.example.controller;

import com.example.model.dto.AlbumDTO;
import com.example.service.AlbumService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MusicController {

    @Inject
    AlbumService albumService;

    @GET
    @Path("/albums/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAlbumById(@PathParam("id") Long id) {
        try {
            AlbumDTO albumDTO = albumService.getAlbumById(id);
            return Response.ok().entity(albumDTO).build();
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }
}
