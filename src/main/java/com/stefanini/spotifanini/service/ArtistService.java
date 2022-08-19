package com.stefanini.spotifanini.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stefanini.spotifanini.exception.ArtistNotFoundException;
import com.stefanini.spotifanini.model.Artist;
import com.stefanini.spotifanini.repository.ArtistRepository;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> findAllArtists() {
        return artistRepository.findAll();
    }

    public Artist findById(Long id) throws ArtistNotFoundException {
        return artistRepository.findById(id).orElseThrow(() -> new ArtistNotFoundException(id));
    }

    public Artist save(Artist artist) {
        return artistRepository.save(artist);
    }

    public Artist update(Long id, Artist artist) {

        artist.setId(id);

        return artistRepository.save(artist);
    }

    public void delete(Long id) {
        artistRepository.deleteById(id);
    } 
}
