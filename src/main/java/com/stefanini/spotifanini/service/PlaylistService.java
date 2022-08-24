package com.stefanini.spotifanini.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stefanini.spotifanini.model.Music;
import com.stefanini.spotifanini.model.Playlist;
import com.stefanini.spotifanini.repository.MusicRepository;
import com.stefanini.spotifanini.repository.PlaylistRepository;
import com.stefanini.spotifanini.util.Validations;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final MusicRepository musicRepository;

    public PlaylistService(PlaylistRepository playlistRepository, MusicRepository musicRepository) {
        this.playlistRepository = playlistRepository;
        this.musicRepository = musicRepository;
    }

    public List<Playlist> findAllPlaylists() {

        try {
            return playlistRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    public Playlist findById(Long id) {

        try {

            Optional<Playlist> playlist = playlistRepository.findById(id);

            Validations.notPresent(playlist, "Playlist Not Found");

            return playlist.get();
        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseEntity<String> save(Playlist playlist) {

        try {

            Validations.notExists(playlist.getName(), "Empty Name");
            Validations.isPresent(playlistRepository.findByName(playlist.getName()), "Playlist Already Exists");

            playlistRepository.save(playlist);

            return new ResponseEntity<String>("Playlist Saved", HttpStatus.valueOf(200));

        } catch (RuntimeException e) {
            if (e.getMessage().equals("Empty Name"))
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
            else
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(401));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    public ResponseEntity<String> update(Long id, Playlist playlist) {

        try {

            Optional<Playlist> oldPlaylist = playlistRepository.findByName(playlist.getName());

            Validations.notExists(playlist.getName(), "Empty Name");
            if (oldPlaylist.isPresent() && oldPlaylist.get().getId() != id)
                Validations.isPresent(oldPlaylist, "Playlist Already Exists");

            playlist.setId(id);
            playlistRepository.save(playlist);

            return new ResponseEntity<String>("Playlist Updated", HttpStatus.valueOf(200));

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

            playlistRepository.deleteById(id);

            return new ResponseEntity<String>("Playlist Deleted", HttpStatus.valueOf(200));

        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    public ResponseEntity<String> addMusic(Long playlistId, Long musicId) {

        try {

            Optional<Playlist> playlist = playlistRepository.findById(playlistId);
            Optional<Music> music = musicRepository.findById(musicId);

            Validations.notPresent(playlist, "Playlist Not Found");
            Validations.notPresent(music, "Music Not Found");
            if (playlist.get().getMusics().contains(music.get()))
                throw new RuntimeException("Music Already Added");

            playlist.get().getMusics().add(music.get());
            playlistRepository.save(playlist.get());

            return new ResponseEntity<String>("Music Added to the Playlist", HttpStatus.valueOf(200));

        } catch (RuntimeException e) {
            if (e.getMessage().equals("Playlist Not Found") || e.getMessage().equals("Music Not Found"))
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
            else
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(401));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    public ResponseEntity<String> removeMusic(Long playlistId, Long musicId) {

        try {

            Optional<Playlist> playlist = playlistRepository.findById(playlistId);
            Optional<Music> music = musicRepository.findById(musicId);

            Validations.notPresent(playlist, "Playlist Not Found");
            Validations.notPresent(music, "Music Not Found");
            if (!playlist.get().getMusics().contains(music.get()))
                throw new RuntimeException("Music Doesn't Exists in The Playlist");

            playlist.get().getMusics().remove(music.get());
            playlistRepository.save(playlist.get());

            return new ResponseEntity<String>("Music Removed of the Playlist", HttpStatus.valueOf(200));

        } catch (RuntimeException e) {
            if (e.getMessage().equals("Playlist Not Found") || e.getMessage().equals("Music Not Found"))
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
            else
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(401));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
