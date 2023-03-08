package com.example.spotify.auth;

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

    @Inject
    AuthConfig authConfig;

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
                (authConfig.getClientId() + ":" + authConfig.getClientSecret()).getBytes(StandardCharsets.UTF_8)
        );
    }



}
