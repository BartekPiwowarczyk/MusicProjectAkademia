package com.example.spotify.controller;

import com.example.spotify.TokenService;
import com.example.spotify.interfaces.SpotifyAuthInterface;
import com.example.spotify.model.SpotifyAuthResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api")
public class AuthEndpoint {

    @Inject
    TokenService tokenService;

    final String CLIENT_ID = "02f1c18445dd4955b674899586967b41";
    final String CLIENT_SECRET = "be27c4aa85b64b3dbc7c0507e8aa5e54";

    @RestClient
    SpotifyAuthInterface spotifyAuthInterface;

    @GET
    @Path("/access_token")
    public SpotifyAuthResponse authSpotify() {
        String authorization = computeBasicHeader();
        SpotifyAuthResponse token = spotifyAuthInterface.getToken(authorization, "client_credentials");
        tokenService.setAuth(token.accessToken());
        return token;
    }

    private String computeBasicHeader() {
        return "Basic " + Base64.getEncoder().encodeToString(
                (CLIENT_ID + ":" + CLIENT_SECRET).getBytes(StandardCharsets.UTF_8)
        );
    }



}
