package com.example.spotify.interfaces;

import com.example.spotify.model.dto.SearchAlbumResponse;
import com.example.spotify.model.dto.AlbumSpotify;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;

@RegisterRestClient(configKey = "spotify-client")
public interface SpotifyInterface {
    @Path("/albums/{name}")
    @GET
    AlbumSpotify getAlbum(
            @HeaderParam("Authorization") String authorization,
            @PathParam("name") String name
    );

    @Path("/search")
    @GET
    SearchAlbumResponse getSearch(
            @HeaderParam("Authorization") String authorization,
            @QueryParam("q") String search,
            @QueryParam("type") String type
    );
}
