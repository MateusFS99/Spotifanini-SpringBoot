package com.stefanini.spotifanini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stefanini.spotifanini.model.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long>{
    
}
