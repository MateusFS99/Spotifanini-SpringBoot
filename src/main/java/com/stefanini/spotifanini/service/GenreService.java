package com.stefanini.spotifanini.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stefanini.spotifanini.model.Genre;
import com.stefanini.spotifanini.repository.GenreRepository;
import com.stefanini.spotifanini.util.Validations;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> findAllGenres() {

        try {
            return genreRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    public Genre findById(Long id) {

        try {

            Optional<Genre> genre = genreRepository.findById(id);

            Validations.notPresent(genre, "Genre Not Found");

            return genre.get();
        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseEntity<String> save(Genre genre) {

        try {

            Validations.notExists(genre.getName(), "Empty Name");
            Validations.isPresent(genreRepository.findByName(genre.getName()), "Genre Already Exists");

            genreRepository.save(genre);

            return new ResponseEntity<String>("Genre Saved", HttpStatus.valueOf(200));

        } catch (RuntimeException e) {
            if (e.getMessage().equals("Empty Name"))
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
            else
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(401));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    public ResponseEntity<String> update(Long id, Genre genre) {

        try {

            Optional<Genre> oldGenre = genreRepository.findByName(genre.getName());

            Validations.notExists(genre.getName(), "Empty Name");
            if (oldGenre.isPresent() && oldGenre.get().getId() != id)
                Validations.isPresent(oldGenre, "Genre Already Exists");

            genre.setId(id);
            genreRepository.save(genre);

            return new ResponseEntity<String>("Genre Updated", HttpStatus.valueOf(200));

        } catch (RuntimeException e) {
            if (e.getMessage().equals("Empty Name"))
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
            else
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(401));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    public ResponseEntity<String> delete(Long id) {

        try {

            genreRepository.deleteById(id);

            return new ResponseEntity<String>("Genre Deleted", HttpStatus.valueOf(200));

        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
