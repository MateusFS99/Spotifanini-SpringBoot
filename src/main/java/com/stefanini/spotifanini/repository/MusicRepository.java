package com.stefanini.spotifanini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stefanini.spotifanini.model.Music;

public interface MusicRepository extends JpaRepository<Music, Long>{
    
}
