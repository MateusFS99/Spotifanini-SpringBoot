package com.stefanini.spotifanini.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stefanini.spotifanini.model.Artist;
import com.stefanini.spotifanini.model.Music;
import com.stefanini.spotifanini.repository.ArtistRepository;
import com.stefanini.spotifanini.repository.MusicRepository;
import com.stefanini.spotifanini.util.Validations;

@Service
public class MusicService {

    private final MusicRepository musicRepository;
    private final ArtistRepository artistRepository;

    public MusicService(MusicRepository musicRepository, ArtistRepository artistRepository) {
        this.musicRepository = musicRepository;
        this.artistRepository = artistRepository;
    }

    public List<Music> findAllMusics() {

        try {
            return musicRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    public Music findById(Long id) {

        try {

            Optional<Music> music = musicRepository.findById(id);

            Validations.notPresent(music, "Music Not Found");

            return music.get();
        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseEntity<String> save(Music music) {

        try {

            List<Music> oldMusic = musicRepository.findByName(music.getName());

            Validations.notExists(music.getName(), "Empty Name");
            Validations.notExists(music.getAlbum(), "Empty Album");
            if (oldMusic.size() > 0)
                for (Music oldMusicItem : oldMusic)
                    if (oldMusicItem.getAlbum().getArtist().getId() == music.getAlbum().getArtist().getId())
                        throw new RuntimeException("Music Already Exists");

            musicRepository.save(music);

            return new ResponseEntity<String>("Music Saved", HttpStatus.valueOf(200));

        } catch (RuntimeException e) {
            if (e.getMessage().equals("Empty Name") || e.getMessage().equals("Empty Album"))
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
            else
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(401));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    public ResponseEntity<String> update(Long id, Music music) {

        try {

            List<Music> oldMusic = musicRepository.findByName(music.getName());

            Validations.notExists(music.getName(), "Empty Name");
            Validations.notExists(music.getAlbum(), "Empty Album");
            if (oldMusic.size() > 0)
                for (Music oldMusicItem : oldMusic)
                    if (oldMusicItem.getId() != id
                            && oldMusicItem.getAlbum().getArtist().getId() == music.getAlbum().getArtist().getId())
                        throw new RuntimeException("Music Already Exists");

            music.setId(id);
            musicRepository.save(music);

            return new ResponseEntity<String>("Music Updated", HttpStatus.valueOf(200));

        } catch (RuntimeException e) {
            if (e.getMessage().equals("Empty Name") || e.getMessage().equals("Empty Album"))
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
            else
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(401));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    public ResponseEntity<String> delete(Long id) {

        try {

            musicRepository.deleteById(id);

            return new ResponseEntity<String>("Music Deleted", HttpStatus.valueOf(200));

        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    public ResponseEntity<String> addParticipant(Long musicId, Long artistId) {

        try {

            Optional<Music> music = musicRepository.findById(musicId);
            Optional<Artist> artist = artistRepository.findById(artistId);

            Validations.notPresent(music, "Music Not Found");
            Validations.notPresent(artist, "Participant Not Found");
            if (music.get().getAlbum().getArtist() == artist.get())
                throw new RuntimeException("This Artist is The Author of The Album");
            if (music.get().getParticipants().contains(artist.get()))
                throw new RuntimeException("Participant Already Added");

            music.get().getParticipants().add(artist.get());
            musicRepository.save(music.get());

            return new ResponseEntity<String>("Participant Added to the Music", HttpStatus.valueOf(200));

        } catch (RuntimeException e) {
            if (e.getMessage().equals("Music Not Found"))
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(404));
            else if (e.getMessage().equals("Participant Not Found"))
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(405));
            else if (e.getMessage().equals("This Artist is The Author of The Album"))
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
            else
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(401));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    public ResponseEntity<String> removeParticipant(Long musicId, Long artistId) {

        try {

            Optional<Music> music = musicRepository.findById(musicId);
            Optional<Artist> artist = artistRepository.findById(artistId);

            Validations.notPresent(music, "Music Not Found");
            Validations.notPresent(artist, "Participant Not Found");
            if (music.get().getAlbum().getArtist() == artist.get())
                throw new RuntimeException("The Author of The Album Cannot Be Removed");
            if (!music.get().getParticipants().contains(artist.get()))
                throw new RuntimeException("Participant Doesn't Exists in The Music");

            music.get().getParticipants().remove(artist.get());
            musicRepository.save(music.get());

            return new ResponseEntity<String>("Participant Removed of the Music", HttpStatus.valueOf(200));

        } catch (RuntimeException e) {
            if (e.getMessage().equals("Music Not Found"))
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(404));
            else if (e.getMessage().equals("Participant Not Found"))
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(405));
            else if (e.getMessage().equals("The Author of The Album Cannot Be Removed"))
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
            else
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(401));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
