package com.stefanini.spotifanini.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stefanini.spotifanini.exception.MusicNotFoundException;
import com.stefanini.spotifanini.model.Music;
import com.stefanini.spotifanini.repository.MusicRepository;

@Service
public class MusicService {

    private final MusicRepository musicRepository;

    public MusicService(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    public List<Music> findAllMusics() {
        return musicRepository.findAll();
    }

    public Music findById(Long id) throws MusicNotFoundException {
        return musicRepository.findById(id).orElseThrow(() -> new MusicNotFoundException(id));
    }

    public Music save(Music music) {
        return musicRepository.save(music);
    }

    public Music update(Long id, Music music) {

        music.setId(id);

        return musicRepository.save(music);
    }

    public void delete(Long id) {
        musicRepository.deleteById(id);
    }
}
