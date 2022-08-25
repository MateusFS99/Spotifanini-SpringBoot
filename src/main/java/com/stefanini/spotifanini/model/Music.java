package com.stefanini.spotifanini.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.stefanini.spotifanini.util.EntityIdResolver;

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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", resolver = EntityIdResolver.class, scope = Music.class)
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String release;

    private String audio;

    @ManyToOne
    private Genre genre;

    @ManyToOne
    private Album album;

    @ManyToMany
    @JoinTable(name = "music_participants", joinColumns = @JoinColumn(name = "music_id"), inverseJoinColumns = @JoinColumn(name = "artist_id"))
    private List<Artist> participants;

    @ManyToMany(mappedBy = "playlistMusics")
    @JsonBackReference
    private List<Playlist> playlists;

    @ManyToMany(mappedBy = "compilationMusics")
    @JsonBackReference
    private List<Compilation> compilations;
}
