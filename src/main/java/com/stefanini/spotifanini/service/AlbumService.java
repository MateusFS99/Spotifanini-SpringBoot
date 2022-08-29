package com.stefanini.spotifanini.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stefanini.spotifanini.model.Album;
import com.stefanini.spotifanini.repository.AlbumRepository;
import com.stefanini.spotifanini.util.Validations;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public Page<Album> findAllAlbums(Pageable pagination) {

        try {
            return albumRepository.findAll(pagination);
        } catch (Exception e) {
            throw e;
        }
    }

    public Album findById(Long id) {

        try {

            Optional<Album> album = albumRepository.findById(id);

            Validations.notPresent(album, "Album Not Found");

            return album.get();
        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseEntity<String> save(Album album) {

        try {

            List<Album> oldAlbum = albumRepository.findByName(album.getName());

            Validations.notExists(album.getName(), "Empty Name");
            if (oldAlbum.size() > 0)
                for (Album oldAlbumItem : oldAlbum)
                    if (oldAlbumItem.getArtist().getId() == album.getArtist().getId())
                        throw new RuntimeException("Album Already Exists");

            albumRepository.save(album);

            return new ResponseEntity<String>("Album Saved", HttpStatus.valueOf(200));

        } catch (RuntimeException e) {
            if (e.getMessage().equals("Empty Name"))
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
            else
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(401));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    public ResponseEntity<String> update(Long id, Album album) {

        try {

            List<Album> oldAlbum = albumRepository.findByName(album.getName());

            Validations.notExists(album.getName(), "Empty Name");
            if (oldAlbum.size() > 0)
                for (Album oldAlbumItem : oldAlbum)
                    if (oldAlbumItem.getId() != id
                            && oldAlbumItem.getArtist().getId() == album.getArtist().getId())
                        throw new RuntimeException("Album Already Exists");

            album.setId(id);
            albumRepository.save(album);

            return new ResponseEntity<String>("Album Updated", HttpStatus.valueOf(200));

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

            albumRepository.deleteById(id);

            return new ResponseEntity<String>("Album Deleted", HttpStatus.valueOf(200));

        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
