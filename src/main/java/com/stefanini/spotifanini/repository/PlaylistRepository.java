package com.stefanini.spotifanini.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stefanini.spotifanini.model.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    Optional<Playlist> findByName(String name);
}
