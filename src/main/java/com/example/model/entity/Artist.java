package com.example.model.entity;

import javax.persistence.*;

@NamedQuery(name="Artist.findById",query = "SELECT a FROM Artist a where a.id= :id")
@Entity
@Table(name="ARTISTS")
public class Artist {

    @Column(name = "ARTIST_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="NAME")
    private String name;

    @Column(name="FIRSTNAME")
    private String firstname;

    public Artist() {
    }

    public Artist(String name, String firstname) {
        this.name = name;
        this.firstname = firstname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }


}
