package com.example.model.entity;


import javax.persistence.*;

@Entity
@Table(name="SONGS")
public class Song {

    @Column(name="SONG_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="TITLE")
    private String title;

    @Column(name="DURATION")
    private Integer duration;

    @Column(name="REMARKS")
    private String remarks;

    public Song() {
    }

    public Song(Long id, String title, Integer duration, String remarks) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.remarks = remarks;
    }

    public Song(String title, Integer duration, String remarks) {
        this.title = title;
        this.duration = duration;
        this.remarks = remarks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


}
