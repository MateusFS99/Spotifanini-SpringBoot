package com.stefanini.spotifanini.model.mapper;

import com.stefanini.spotifanini.model.Genre;
import com.stefanini.spotifanini.model.dto.GenreDTO;

public class GenreMapper {

    public Genre mapGenre(GenreDTO genre) {

        return new Genre(null, genre.getName(), null);
    }
}