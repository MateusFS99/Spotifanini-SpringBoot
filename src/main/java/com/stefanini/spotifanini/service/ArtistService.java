package com.stefanini.spotifanini.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stefanini.spotifanini.model.Artist;
import com.stefanini.spotifanini.repository.ArtistRepository;
import com.stefanini.spotifanini.util.Validations;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> findAllArtists() {

        try {
            return artistRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    public Artist findById(Long id) {

        try {

            Optional<Artist> artist = artistRepository.findById(id);

            Validations.notPresent(artist, "Artist Not Found");

            return artist.get();
        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseEntity<String> save(Artist artist) {

        try {

            Validations.notExists(artist.getName(), "Empty Name");
            Validations.isPresent(artistRepository.findByName(artist.getName()), "Artist Already Exists");

            artistRepository.save(artist);

            return new ResponseEntity<String>("Artist Saved", HttpStatus.valueOf(200));

        } catch (RuntimeException e) {
            if (e.getMessage().equals("Empty Name"))
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
            else
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(401));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    public ResponseEntity<String> update(Long id, Artist artist) {

        try {

            Optional<Artist> oldArtist = artistRepository.findByName(artist.getName());

            Validations.notExists(artist.getName(), "Empty Name");
            if (oldArtist.isPresent() && oldArtist.get().getId() != id)
                Validations.isPresent(oldArtist, "Artist Already Exists");

            artist.setId(id);
            artistRepository.save(artist);

            return new ResponseEntity<String>("Artist Updated", HttpStatus.valueOf(200));

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

            artistRepository.deleteById(id);

            return new ResponseEntity<String>("Artist Deleted", HttpStatus.valueOf(200));

        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
