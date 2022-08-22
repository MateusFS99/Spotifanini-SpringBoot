package com.stefanini.spotifanini.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stefanini.spotifanini.model.Music;

public interface MusicRepository extends JpaRepository<Music, Long>{
    
    Optional<Music> findByName(String name);
}
