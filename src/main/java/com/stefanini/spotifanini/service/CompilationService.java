package com.stefanini.spotifanini.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stefanini.spotifanini.model.Artist;
import com.stefanini.spotifanini.model.Compilation;
import com.stefanini.spotifanini.model.Music;
import com.stefanini.spotifanini.repository.CompilationRepository;
import com.stefanini.spotifanini.repository.MusicRepository;
import com.stefanini.spotifanini.util.Validations;

@Service
public class CompilationService {

    private final CompilationRepository compilationRepository;
    private final MusicRepository musicRepository;

    public CompilationService(CompilationRepository compilationRepository, MusicRepository musicRepository) {
        this.compilationRepository = compilationRepository;
        this.musicRepository = musicRepository;
    }

    private void atualizaArtistas(Compilation compilation) {

        List<Artist> list = new ArrayList<>();

        for (Music music : compilation.getMusics())
            list.add(music.getAlbum().getArtist());
        compilation.getArtists().clear();
        compilation.setArtists(list);
    }

    public List<Compilation> findAllCompilations() {

        try {
            return compilationRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    public Compilation findById(Long id) {

        try {

            Optional<Compilation> compilation = compilationRepository.findById(id);

            Validations.notPresent(compilation, "Compilation Not Found");

            return compilation.get();
        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseEntity<String> save(Compilation compilation) {

        try {

            Validations.notExists(compilation.getName(), "Empty Name");

            atualizaArtistas(compilation);
            compilationRepository.save(compilation);

            return new ResponseEntity<String>("Compilation Saved", HttpStatus.valueOf(200));

        } catch (RuntimeException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    public ResponseEntity<String> update(Long id, Compilation compilation) {

        try {

            Validations.notExists(compilation.getName(), "Empty Name");

            atualizaArtistas(compilation);
            compilation.setId(id);
            compilationRepository.save(compilation);

            return new ResponseEntity<String>("Compilation Updated", HttpStatus.valueOf(200));

        } catch (RuntimeException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    public ResponseEntity<String> delete(Long id) {

        try {

            compilationRepository.deleteById(id);

            return new ResponseEntity<String>("Compilation Deleted", HttpStatus.valueOf(200));

        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    public ResponseEntity<String> addMusic(Long compilationId, Long musicId) {

        try {

            Optional<Compilation> compilation = compilationRepository.findById(compilationId);
            Optional<Music> music = musicRepository.findById(musicId);

            Validations.notPresent(compilation, "Compilation Not Found");
            Validations.notPresent(music, "Music Not Found");
            if (compilation.get().getMusics().contains(music.get()))
                throw new RuntimeException("Music Already Added");

            compilation.get().getMusics().add(music.get());
            compilation.get().getArtists().add(music.get().getAlbum().getArtist());
            compilationRepository.save(compilation.get());

            return new ResponseEntity<String>("Music Added to the Compilation", HttpStatus.valueOf(200));

        } catch (RuntimeException e) {
            if (e.getMessage().equals("Compilation Not Found") || e.getMessage().equals("Music Not Found"))
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
            else
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(401));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    public ResponseEntity<String> removeMusic(Long compilationId, Long musicId) {

        try {

            Optional<Compilation> compilation = compilationRepository.findById(compilationId);
            Optional<Music> music = musicRepository.findById(musicId);

            Validations.notPresent(compilation, "Compilation Not Found");
            Validations.notPresent(music, "Music Not Found");
            if (!compilation.get().getMusics().contains(music.get()))
                throw new RuntimeException("Music Doesn't Exists in The Compilation");

            compilation.get().getMusics().remove(music.get());
            compilation.get().getArtists().remove(music.get().getAlbum().getArtist());
            compilationRepository.save(compilation.get());

            return new ResponseEntity<String>("Music Removed of the Compilation", HttpStatus.valueOf(200));

        } catch (RuntimeException e) {
            if (e.getMessage().equals("Compilation Not Found") || e.getMessage().equals("Music Not Found"))
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
            else
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(401));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
