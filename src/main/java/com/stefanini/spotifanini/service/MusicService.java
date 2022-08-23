package com.stefanini.spotifanini.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stefanini.spotifanini.model.Music;
import com.stefanini.spotifanini.repository.MusicRepository;
import com.stefanini.spotifanini.util.Validations;

@Service
public class MusicService {

    private final MusicRepository musicRepository;

    public MusicService(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
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
            if (oldMusic.size() > 0)
                for (Music oldMusicItem : oldMusic)
                    if (oldMusicItem.getAlbum().getArtist().getId() == music.getAlbum().getArtist().getId())
                        throw new RuntimeException("Music Already Exists");

            musicRepository.save(music);

            return new ResponseEntity<String>("Music Saved", HttpStatus.valueOf(200));

        } catch (RuntimeException e) {
            if (e.getMessage().equals("Empty Name"))
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
            if (oldMusic.size() > 0)
                for (Music oldMusicItem : oldMusic)
                    if (oldMusicItem.getId() != id
                            && oldMusicItem.getAlbum().getArtist().getId() == music.getAlbum().getArtist().getId())
                        throw new RuntimeException("Music Already Exists");

            music.setId(id);
            musicRepository.save(music);

            return new ResponseEntity<String>("Music Updated", HttpStatus.valueOf(200));

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

            musicRepository.deleteById(id);

            return new ResponseEntity<String>("Music Deleted", HttpStatus.valueOf(200));

        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
