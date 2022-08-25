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
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String release;

    private String audio;

    @JsonManagedReference(value = "music_genre")
    @ManyToOne
    private Genre genre;

    @JsonManagedReference(value = "music_album")
    @ManyToOne
    private Album album;

    @JsonManagedReference(value = "music_participants")
    @ManyToMany
    @JoinTable(name = "music_participants", joinColumns = @JoinColumn(name = "music_id"), inverseJoinColumns = @JoinColumn(name = "artist_id"))
    private List<Artist> participants;

    @JsonBackReference(value = "playlist_musics")
    @ManyToMany(mappedBy = "musics")
    private List<Playlist> playlists;
}
