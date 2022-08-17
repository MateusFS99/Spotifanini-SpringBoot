package com.stefanini.spotifanini.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stefanini.spotifanini.exception.GenreNotFoundException;
import com.stefanini.spotifanini.model.Genre;
import com.stefanini.spotifanini.repository.GenreRepository;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    public Genre findById(Long id) throws GenreNotFoundException {
        return genreRepository.findById(id).orElseThrow(() -> new GenreNotFoundException(id));
    }

    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }
}
