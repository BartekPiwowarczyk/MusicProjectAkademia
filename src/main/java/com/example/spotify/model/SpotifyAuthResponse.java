package com.example.spotify.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SpotifyAuthResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("expires_in") Long expiresIn)
 {
}
