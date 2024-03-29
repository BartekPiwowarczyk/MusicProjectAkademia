package com.example.spotify.auth;

import com.example.spotify.auth.SpotifyAuthResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;

@RegisterRestClient(configKey = "token-client")
@Path("/api")
public interface SpotifyAuthInterface {

    @Path("/token")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    SpotifyAuthResponse getToken(
            @HeaderParam("Authorization") String authorization,
            @FormParam("grant_type") String grantType
    );

}