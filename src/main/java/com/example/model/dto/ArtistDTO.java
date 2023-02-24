package com.example.model.dto;


import javax.persistence.NamedQuery;

public class ArtistDTO {

    private String firstName;
    private String name;

    public ArtistDTO(String firstName, String name) {
        this.firstName = firstName;
        this.name = name;
    }

    public ArtistDTO(String name) {
        this.name = name;
    }

    public ArtistDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
