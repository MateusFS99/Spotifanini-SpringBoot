package com.stefanini.spotifanini.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    @ManyToMany
    @JoinTable(name = "compilation_artists", joinColumns = @JoinColumn(name = "compilation_id"), inverseJoinColumns = @JoinColumn(name = "artist_id"))
    private List<Artist> artists;

    @ManyToMany
    @JoinTable(name = "compilation_musics", joinColumns = @JoinColumn(name = "compilation_id"), inverseJoinColumns = @JoinColumn(name = "music_id"))
    private List<Music> musics;
}
