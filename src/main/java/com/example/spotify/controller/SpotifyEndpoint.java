package com.example.spotify.controller;

import com.example.spotify.auth.AuthConfig;
import com.example.spotify.auth.AuthEndpoint;
import com.example.spotify.auth.SpotifyAuthResponse;
import com.example.spotify.interfaces.SpotifyInterface;
import com.example.spotify.model.dto.AlbumSpotify;
import com.example.spotify.model.dto.SearchAlbumResponse;
import com.example.spotify.auth.TokenService;
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

    @Inject
    TokenService tokenService;


    @GET
    @Path("/albums/{albumId}")
    public AlbumSpotify getAlbum(@PathParam("albumId") String albumId) {
        if(tokenService.getAuth() == null) {
            LOGGER.info("New Token");
            authEndpoint.authSpotify();
        }
        LOGGER.info("Authorization: "  + tokenService.getToken());
        return  spotifyInterface.getAlbum(tokenService.getToken(), albumId);
        }

    @GET
    @Path("/albums/search")
    public SearchAlbumResponse getAlbumFromSearch(@QueryParam("album") String album) {
        if(tokenService.getAuth() == null) {
            LOGGER.info("New Token");
            authEndpoint.authSpotify();
        }

        LOGGER.info("Authorization: "  + tokenService.getToken());
        return spotifyInterface.getSearch(tokenService.getToken(),album, "album");
    }

    @POST
    @Path("/albums/{albumId}")
    public AlbumSpotify addAlbum(@PathParam("albumId") String albumId) {
        if(tokenService.getAuth() == null) {
            authEndpoint.authSpotify();
        }
        LOGGER.info("Authorization: "  + tokenService.getToken());
        AlbumSpotify albumToSave = spotifyInterface.getAlbum(tokenService.getToken(), albumId);
        spotifyAlbumService.addAlbumSpotifyToDb(albumToSave);
        return  albumToSave;
    }


    }

