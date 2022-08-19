package com.stefanini.spotifanini.model.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.stefanini.spotifanini.exception.ArtistNotFoundException;
import com.stefanini.spotifanini.exception.GenreNotFoundException;
import com.stefanini.spotifanini.model.Music;
import com.stefanini.spotifanini.model.dto.MusicDTO;
import com.stefanini.spotifanini.service.ArtistService;
import com.stefanini.spotifanini.service.GenreService;

public class MusicMapper {

    private final GenreService genreService;
    private final ArtistService artistService;

    @Autowired
    public MusicMapper(GenreService genreService, ArtistService artistService) {
        this.genreService = genreService;
        this.artistService = artistService;
    }

    public Music mapMusic(MusicDTO music) throws GenreNotFoundException, ArtistNotFoundException {

        return new Music(null, music.getName(), music.getRelease(), music.getImage(), music.getAudio(),
                genreService.findById(music.getGenreId()), artistService.findById(music.getArtistId()));
    }
}