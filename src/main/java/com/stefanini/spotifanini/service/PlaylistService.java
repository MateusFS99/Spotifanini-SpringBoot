package com.stefanini.spotifanini.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.stefanini.spotifanini.exception.MusicNotFoundException;
import com.stefanini.spotifanini.exception.PlaylistNotFoundException;
import com.stefanini.spotifanini.model.Music;
import com.stefanini.spotifanini.model.Playlist;
import com.stefanini.spotifanini.repository.MusicRepository;
import com.stefanini.spotifanini.repository.PlaylistRepository;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final MusicRepository musicRepository;

    public PlaylistService(PlaylistRepository playlistRepository, MusicRepository musicRepository) {
        this.playlistRepository = playlistRepository;
        this.musicRepository = musicRepository;
    }

    public List<Playlist> findAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Playlist findById(Long id) throws PlaylistNotFoundException {
        return playlistRepository.findById(id).orElseThrow(() -> new PlaylistNotFoundException(id));
    }

    public Playlist save(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public Playlist update(Long id, Playlist playlist) {

        playlist.setId(id);

        return playlistRepository.save(playlist);
    }

    public void delete(Long id) {
        playlistRepository.deleteById(id);
    }

    public void addMusic(Long playlistId, Long musicId) throws PlaylistNotFoundException, MusicNotFoundException {

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException(playlistId));
        Music music = musicRepository.findById(musicId)
                .orElseThrow(() -> new MusicNotFoundException(musicId));

        playlist.getMusics().add(music);
    }
}
