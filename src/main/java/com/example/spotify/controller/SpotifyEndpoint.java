package com.example.spotify.controller;

import com.example.spotify.interfaces.SpotifyInterface;
import com.example.spotify.model.dto.AlbumSpotify;
import com.example.spotify.model.dto.SearchAlbumResponse;
import com.example.spotify.TokenService;
import com.example.spotify.service.SpotifyAlbumService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api")
//@RegisterRestClient(configKey = "spotify-client")
public class SpotifyEndpoint {

    Logger LOGGER = LoggerFactory.getLogger(SpotifyEndpoint.class);

    @RestClient
    SpotifyInterface spotifyInterface;

    @Inject
    AuthEndpoint authEndpoint;

    @Inject
    SpotifyAlbumService spotifyAlbumService;

    @GET
    @Path("/albums/{albumId}")
    public AlbumSpotify getAlbum(@PathParam("albumId") String albumId) {
        if(TokenService.getAuth() == null) {
            authEndpoint.authSpotify();
        }
        LOGGER.info("Authorization: "  + TokenService.getToken());
        return  spotifyInterface.getAlbum(TokenService.getToken(), albumId);
        }

    @GET
    @Path("/albums/search")
    public SearchAlbumResponse getAlbumFromSearch(@QueryParam("album") String album) {
        if(TokenService.getAuth() == null) {
            authEndpoint.authSpotify();
        }

        LOGGER.info("Authorization: "  + TokenService.getToken());
        return spotifyInterface.getSearch(TokenService.getToken(),album, "album");
    }

    @POST
    @Path("/albums/{albumId}")
    public AlbumSpotify addAlbum(@PathParam("albumId") String albumId) {
        if(TokenService.getAuth() == null) {
            authEndpoint.authSpotify();
        }
        LOGGER.info("Authorization: "  + TokenService.getToken());
        AlbumSpotify albumToSave = spotifyInterface.getAlbum(TokenService.getToken(), albumId);
        spotifyAlbumService.addAlbumSpotifyToDb(albumToSave);
        return  albumToSave;
    }


    }

